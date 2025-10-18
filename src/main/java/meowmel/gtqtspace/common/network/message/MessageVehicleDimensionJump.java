package meowmel.gtqtspace.common.network.message;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.entity.weapon.WeaponType;
import meowmel.gtqtspace.common.world.SimpleVehicleTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 载具维度跳跃消息
 */
public class MessageVehicleDimensionJump extends AbstractMessage.AbstractServerMessage<MessageVehicleDimensionJump> {

    private int vehicleId;
    private int targetDimension;
    private BlockPos targetPos;

    public MessageVehicleDimensionJump() {}

    public MessageVehicleDimensionJump(int vehicleId, int targetDimension, BlockPos targetPos) {
        this.vehicleId = vehicleId;
        this.targetDimension = targetDimension;
        this.targetPos = targetPos;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.vehicleId = buffer.readInt();
        this.targetDimension = buffer.readInt();
        this.targetPos = buffer.readBlockPos();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(vehicleId);
        buffer.writeInt(targetDimension);
        buffer.writeBlockPos(targetPos);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            WorldServer world = playerMP.getServerWorld();

            // 获取载具实体
            Entity entity = world.getEntityByID(this.vehicleId);
            if (entity instanceof EntityCustomVehicle) {
                EntityCustomVehicle vehicle = (EntityCustomVehicle) entity;

                // 执行安全的维度跳跃
                performSafeDimensionJump(playerMP, vehicle, this.targetDimension, this.targetPos);
            }
        }
    }

    /**
     * 安全的维度跳跃执行
     */
    private void performSafeDimensionJump(EntityPlayerMP player, EntityCustomVehicle vehicle, int targetDimension, BlockPos targetPos) {
        try {
            // 检查玩家是否在骑乘
            if (!player.isRiding() || player.getRidingEntity() != vehicle) {
                System.out.println("Player is not riding the vehicle. Aborting dimension jump.");
                return;
            }

            // 检查传送冷却
            if (player.timeUntilPortal > 0) {
                player.timeUntilPortal = player.getPortalCooldown();
                return;
            }

            // 检查是否已经在目标维度
            if (player.dimension == targetDimension) {
                System.out.println("Player is already in target dimension: " + targetDimension);
                return;
            }

            // 设置传送冷却
            player.timeUntilPortal = player.getPortalCooldown();

            // 保存载具完整状态
            VehicleState savedState = saveVehicleState(vehicle);

            // 让玩家下马
            player.dismountRidingEntity();

            // 删除旧载具
            vehicle.setDead();

            // 获取目标世界
            WorldServer targetWorld = player.getServer().getWorld(targetDimension);

            // 传送玩家到新维度
            player.getServer().getPlayerList().transferPlayerToDimension(
                    player,
                    targetDimension,
                    new SimpleVehicleTeleporter(targetWorld, targetPos)
            );

            // 在目标维度创建新载具并恢复状态
            EntityCustomVehicle newVehicle = createVehicleWithState(targetWorld, targetPos, savedState);

            // 设置玩家位置并上马
            player.setPositionAndUpdate(targetPos.getX(), targetPos.getY() + 2, targetPos.getZ());
            player.startRiding(newVehicle);

            System.out.println("Dimension jump completed! Vehicle and player transferred to dimension " + targetDimension);

        } catch (Exception e) {
            System.out.println("Error during dimension jump: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 保存载具完整状态
     */
    private VehicleState saveVehicleState(EntityCustomVehicle vehicle) {
        VehicleState state = new VehicleState();

        // 基本状态
        state.rotationYaw = vehicle.rotationYaw;
        state.rotationPitch = vehicle.rotationPitch;
        state.motionX = vehicle.motionX;
        state.motionY = vehicle.motionY;
        state.motionZ = vehicle.motionZ;

        // 武器状态 - 保存所有武器类型的弹药和冷却
        for (WeaponType weaponType : WeaponType.values()) {
            state.weaponAmmo.put(weaponType, vehicle.getMissileAmmo(weaponType));
            state.weaponCooldowns.put(weaponType, vehicle.getMissileCooldown(weaponType));
        }

        // 输入状态
        state.accelerating = vehicle.dataManager.get(EntityCustomVehicle.ACCELERATING);
        state.pitchInput = vehicle.dataManager.get(EntityCustomVehicle.PITCH_INPUT);
        state.yawInput = vehicle.dataManager.get(EntityCustomVehicle.YAW_INPUT);

        System.out.println("Vehicle state saved - Weapons: " + state.weaponAmmo.size() + " types");

        return state;
    }

    /**
     * 创建新载具并恢复状态
     */
    private EntityCustomVehicle createVehicleWithState(WorldServer world, BlockPos pos, VehicleState state) {
        // 创建新载具
        EntityCustomVehicle newVehicle = new EntityCustomVehicle(world);
        newVehicle.setPosition(pos.getX(), pos.getY(), pos.getZ());

        // 恢复基本状态
        newVehicle.rotationYaw = state.rotationYaw;
        newVehicle.rotationPitch = state.rotationPitch;
        newVehicle.motionX = state.motionX;
        newVehicle.motionY = state.motionY;
        newVehicle.motionZ = state.motionZ;

        // 恢复武器状态
        for (WeaponType weaponType : state.weaponAmmo.keySet()) {
            int ammo = state.weaponAmmo.get(weaponType);
            int cooldown = state.weaponCooldowns.get(weaponType);

            // 这里需要调用载具的设置方法，而不是直接操作内部状态
            // 假设我们在EntityCustomVehicle中添加了相应的方法
            newVehicle.setWeaponAmmo(weaponType, ammo);
            newVehicle.setWeaponCooldown(weaponType, cooldown);
        }

        // 恢复输入状态
        newVehicle.dataManager.set(EntityCustomVehicle.ACCELERATING, state.accelerating);
        newVehicle.dataManager.set(EntityCustomVehicle.PITCH_INPUT, state.pitchInput);
        newVehicle.dataManager.set(EntityCustomVehicle.YAW_INPUT, state.yawInput);

        // 生成载具
        world.spawnEntity(newVehicle);

        System.out.println("New vehicle created with restored state");

        return newVehicle;
    }

    /**
     * 载具状态容器类
     */
    private static class VehicleState {
        // 基本状态
        float rotationYaw;
        float rotationPitch;
        double motionX;
        double motionY;
        double motionZ;

        // 武器状态
        Map<WeaponType, Integer> weaponAmmo = new HashMap<>();
        Map<WeaponType, Integer> weaponCooldowns = new HashMap<>();

        // 输入状态
        boolean accelerating;
        float pitchInput;
        float yawInput;
    }
}