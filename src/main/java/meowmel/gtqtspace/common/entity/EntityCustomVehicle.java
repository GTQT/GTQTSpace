package meowmel.gtqtspace.common.entity;

import meowmel.gtqtspace.common.entity.weapon.missile.EntityMissileBase;
import meowmel.gtqtspace.common.entity.weapon.WeaponConfig;
import meowmel.gtqtspace.common.entity.weapon.WeaponType;
import meowmel.gtqtspace.common.network.message.MessageVehicleDimensionJump;
import meowmel.gtqtspace.common.network.message.MessageVehicleInput;
import meowmel.gtqtspace.common.network.PacketDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityCustomVehicle extends Entity {

    // 数据监视器参数
    public static final DataParameter<Boolean> ACCELERATING = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Float> PITCH_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> YAW_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> ROLL_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);

    // 在 EntityCustomVehicle 类中添加数据参数
    private static final DataParameter<NBTTagCompound> WEAPON_AMMO_DATA = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<NBTTagCompound> WEAPON_COOLDOWN_DATA = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.COMPOUND_TAG);

    @Override
    protected void entityInit() {
        this.dataManager.register(ACCELERATING, false);
        this.dataManager.register(PITCH_INPUT, 0F);
        this.dataManager.register(YAW_INPUT, 0F);
        this.dataManager.register(ROLL_INPUT, 0F);
        // 添加武器数据同步
        this.dataManager.register(WEAPON_AMMO_DATA, new NBTTagCompound());
        this.dataManager.register(WEAPON_COOLDOWN_DATA, new NBTTagCompound());
    }

    // 添加同步武器数据的方法
    private void syncWeaponData() {
        if (!this.world.isRemote) {
            NBTTagCompound ammoData = new NBTTagCompound();
            NBTTagCompound cooldownData = new NBTTagCompound();

            for (WeaponType weaponType : WeaponType.values()) {
                ammoData.setInteger(weaponType.name(), this.missileAmmo.get(weaponType));
                cooldownData.setInteger(weaponType.name(), this.missileCooldowns.get(weaponType));
            }

            this.dataManager.set(WEAPON_AMMO_DATA, ammoData);
            this.dataManager.set(WEAPON_COOLDOWN_DATA, cooldownData);
        }
    }


    // 物理参数
    private final float acceleration = 0.1f;
    private final float rotationSpeed = 3.0f;
    private final float maxSpeed = 5.0f;
    private final float drag = 0.98f;
    private final float airResistance = 0.995f;

    // 输入状态
    private boolean accelerating = false;
    private float pitchInput = 0;
    private float yawInput = 0;
    private float rollInput = 0;

    // 输入平滑处理
    private float smoothedPitch = 0;
    private float smoothedYaw = 0;
    private float smoothedRoll = 0;
    private final float smoothFactor = 0.7f;

    // 输入超时保护
    private long lastInputTime = 0;
    private static final long INPUT_TIMEOUT_MS = 500;

    // 武器系统 - 使用枚举和配置管理武器
    private final Map<WeaponType, Integer> missileCooldowns = new HashMap<>();
    private final Map<WeaponType, Integer> missileAmmo = new HashMap<>();

    // 维度传送相关
    private boolean hasTriggeredDimensionJump = false;
    private static final int DIMENSION_JUMP_HEIGHT = 300;
    private static final int TARGET_DIMENSION = 50;

    // 调试计数
    private AtomicInteger debugCounter = new AtomicInteger(0);

    public EntityCustomVehicle(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.0F);
        this.stepHeight = 1.0F;
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
        this.setNoGravity(true); // 太空环境无重力

        // 初始化所有武器的状态
        for (WeaponType weaponType : WeaponType.values()) {
            WeaponConfig config = WeaponConfig.getConfig(weaponType);
            missileCooldowns.put(weaponType, 0);
            missileAmmo.put(weaponType, config.getMaxAmmo());
        }
    }

    public EntityCustomVehicle(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
    }


    public float prevRotationPitch;
    public float prevRotationYaw;

    @Override
    public void onUpdate() {
        // 保存上一tick的旋转角度
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;

        // 更新乘客位置
        this.updatePassengerPositions();

        // 处理输入超时
        handleInputTimeout();

        // 平滑输入处理
        smoothInputs();

        // 更新武器系统
        updateWeapons();

        // 同步武器数据
        syncWeaponData();

        // 处理运动
        updateMotion();

        // 检查维度跳跃条件
        checkDimensionJump();

        // 调试输出
        if (debugCounter.incrementAndGet() % 400 == 0) {
            // 调试代码可以在这里添加
            reloadAllMissiles();
        }
    }

    /**
     * 检查维度跳跃条件
     */
    private void checkDimensionJump() {
        if (this.world.isRemote || this.hasTriggeredDimensionJump) return;

        // 检查高度是否超过阈值
        if(this.dimension == 0) {
            if (this.posY > 600) {
                Entity controllingPassenger = this.getControllingPassenger();
                if (controllingPassenger instanceof EntityPlayerMP) {
                    // 计算目标位置 (x, 0, z)
                    BlockPos targetPos = new BlockPos(this.posX, 0, this.posZ);

                    // 发送维度跳跃网络包
                    PacketDispatcher.sendToServer(new MessageVehicleDimensionJump(
                            this.getEntityId(),
                            50,
                            targetPos
                    ));

                    this.hasTriggeredDimensionJump = true;
                    System.out.println("Dimension jump triggered! Sending network packet.");
                }
            }
        }
        if(this.dimension == 50){
            if (this.posY < -50) {
                Entity controllingPassenger = this.getControllingPassenger();
                if (controllingPassenger instanceof EntityPlayerMP) {
                    // 计算目标位置 (x, 550, z)
                    BlockPos targetPos = new BlockPos(this.posX, 550, this.posZ);

                    // 发送维度跳跃网络包
                    PacketDispatcher.sendToServer(new MessageVehicleDimensionJump(
                            this.getEntityId(),
                            0,
                            targetPos
                    ));

                    this.hasTriggeredDimensionJump = true;
                    System.out.println("Dimension jump triggered! Sending network packet.");
                }
            }
        }
    }

    private void handleInputTimeout() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastInputTime > INPUT_TIMEOUT_MS) {
            // 超时后逐渐清零输入
            accelerating = false;
            pitchInput *= 0.5f;
            yawInput *= 0.5f;
            rollInput *= 0.5f;

            // 如果输入值很小，直接清零
            if (Math.abs(pitchInput) < 0.01f) pitchInput = 0;
            if (Math.abs(yawInput) < 0.01f) yawInput = 0;
            if (Math.abs(rollInput) < 0.01f) rollInput = 0;
        }
    }

    private void smoothInputs() {
        smoothedPitch = lerp(smoothedPitch, pitchInput, smoothFactor);
        smoothedYaw = lerp(smoothedYaw, yawInput, smoothFactor);
        smoothedRoll = lerp(smoothedRoll, rollInput, smoothFactor);
    }

    private float lerp(float a, float b, float factor) {
        return a + (b - a) * factor;
    }

    private void updateMotion() {
        // 应用加速
        if (accelerating) {
            applyAcceleration();
        }

        // 应用旋转
        applyRotation();

        // 应用阻力和空气阻力
        applyResistance();

        // 限制最大速度
        limitMaxSpeed();

        // 应用运动
        if (!this.world.isRemote) {
            // 服务端：实际移动并同步到客户端
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        } else {
            // 客户端：预测移动（服务端会校正）
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        }
    }

    private void applyAcceleration() {
        // 基于当前朝向计算前进方向
        float yawRad = (float) Math.toRadians(-this.rotationYaw);
        float pitchRad = (float) Math.toRadians(-this.rotationPitch);

        double forwardX = Math.sin(yawRad) * Math.cos(pitchRad);
        double forwardY = Math.sin(pitchRad);
        double forwardZ = Math.cos(yawRad) * Math.cos(pitchRad);

        // 应用加速度
        this.motionX += forwardX * acceleration;
        this.motionY += forwardY * acceleration;
        this.motionZ += forwardZ * acceleration;
    }

    private void applyRotation() {
        // 应用平滑后的旋转输入
        this.rotationPitch += smoothedPitch * rotationSpeed;
        this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);

        this.rotationYaw += smoothedYaw * rotationSpeed;
        // 确保偏航角在合理范围内，但不限制持续旋转
        this.rotationYaw = this.rotationYaw % 360.0F;
    }

    private void applyResistance() {
        // 基础阻力
        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;

        // 空气阻力（在太空环境中可能较小）
        this.motionX *= airResistance;
        this.motionY *= airResistance;
        this.motionZ *= airResistance;
    }

    private void limitMaxSpeed() {
        double currentSpeed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        if (currentSpeed > maxSpeed) {
            double ratio = maxSpeed / currentSpeed;
            this.motionX *= ratio;
            this.motionY *= ratio;
            this.motionZ *= ratio;
        }
    }

    /**
     * 武器系统方法
     */

    /**
     * 发射导弹（服务端调用）- 使用枚举类型
     */
    public void launchMissile(WeaponType weaponType) {
        WeaponConfig config = WeaponConfig.getConfig(weaponType);

        if (this.missileCooldowns.get(weaponType) > 0) {
            //System.out.println(weaponType.getDisplayName() + " on cooldown: " + this.missileCooldowns.get(weaponType));
            return;
        }

        if (this.missileAmmo.get(weaponType) <= 0) {
            //System.out.println("Out of ammo for: " + weaponType.getDisplayName());
            return;
        }

        if (!this.world.isRemote) {
            // 使用枚举的工厂方法创建导弹
            EntityMissileBase missile = weaponType.createMissile(world, this);
            if (missile != null) {
                // 生成导弹并发射
                world.spawnEntity(missile);
                missile.launch();

                // 更新武器状态
                this.missileCooldowns.put(weaponType, config.getCooldownTicks());
                this.missileAmmo.put(weaponType, this.missileAmmo.get(weaponType) - 1);

                //System.out.println("Launched " + weaponType.getDisplayName() +"! Ammo remaining: " + this.missileAmmo.get(weaponType));

                // 播放发射音效
                this.world.playSound(null, this.posX, this.posY, this.posZ,
                        net.minecraft.init.SoundEvents.ENTITY_FIREWORK_LAUNCH,
                        net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 0.8F);
            }
        }
    }

    /**
     * 更新武器冷却
     */
    private void updateWeapons() {
        for (WeaponType weaponType : WeaponType.values()) {
            int cooldown = this.missileCooldowns.get(weaponType);
            if (cooldown > 0) {
                this.missileCooldowns.put(weaponType, cooldown - 1);
            }
        }
    }

    /**
     * 重新装填所有导弹
     */
    public void reloadAllMissiles() {
        for (WeaponType weaponType : WeaponType.values()) {
            WeaponConfig config = WeaponConfig.getConfig(weaponType);
            this.missileAmmo.put(weaponType, config.getMaxAmmo());
        }
        //System.out.println("All missiles reloaded!");
    }

    /**
     * 重新装填指定类型的导弹
     */
    public void reloadMissileType(WeaponType weaponType) {
        WeaponConfig config = WeaponConfig.getConfig(weaponType);
        this.missileAmmo.put(weaponType, config.getMaxAmmo());
        //System.out.println(weaponType.getDisplayName() + " reloaded! Ammo: " + this.missileAmmo.get(weaponType));
    }

    /**
     * 添加指定类型的导弹弹药
     */
    public void addMissileAmmo(WeaponType weaponType, int amount) {
        WeaponConfig config = WeaponConfig.getConfig(weaponType);
        int currentAmmo = this.missileAmmo.get(weaponType);
        this.missileAmmo.put(weaponType, Math.min(currentAmmo + amount, config.getMaxAmmo()));
        //System.out.println("Added " + amount + " " + weaponType.getDisplayName() +
        //        ". Total: " + this.missileAmmo.get(weaponType));
    }

    /**
     * 获取指定导弹的冷却状态
     */
    public int getMissileCooldown(WeaponType weaponType) {
        if (this.world.isRemote) {
            // 客户端从数据监视器获取
            NBTTagCompound cooldownData = this.dataManager.get(WEAPON_COOLDOWN_DATA);
            return cooldownData.getInteger(weaponType.name());
        } else {
            return this.missileCooldowns.getOrDefault(weaponType, 0);
        }
    }

    /**
     * 获取指定导弹的冷却进度
     */
    public float getMissileCooldownProgress(WeaponType weaponType) {
        WeaponConfig config = WeaponConfig.getConfig(weaponType);
        int cooldown = getMissileCooldown(weaponType);
        return (float) cooldown / config.getCooldownTicks();
    }

    /**
     * 获取剩余导弹数量
     */
    public int getMissileAmmo(WeaponType weaponType) {
        if (this.world.isRemote) {
            // 客户端从数据监视器获取
            NBTTagCompound ammoData = this.dataManager.get(WEAPON_AMMO_DATA);
            return ammoData.getInteger(weaponType.name());
        } else {
            return this.missileAmmo.getOrDefault(weaponType, 0);
        }
    }

    /**
     * 获取最大导弹数量
     */
    public int getMaxMissileAmmo(WeaponType weaponType) {
        WeaponConfig config = WeaponConfig.getConfig(weaponType);
        return config.getMaxAmmo();
    }

    /**
     * 是否可以发射指定类型的导弹
     */
    public boolean canLaunchMissile(WeaponType weaponType) {
        return getMissileCooldown(weaponType) <= 0 && getMissileAmmo(weaponType) > 0;
    }

    /**
     * 设置指定武器的弹药数量 - 用于维度跳跃状态恢复
     */
    public void setWeaponAmmo(WeaponType weaponType, int ammo) {
        if (weaponType != null) {
            WeaponConfig config = WeaponConfig.getConfig(weaponType);
            int clampedAmmo = Math.min(ammo, config.getMaxAmmo());
            this.missileAmmo.put(weaponType, clampedAmmo);
            syncWeaponData(); // 同步数据
        }
    }

    /**
     * 设置指定武器的冷却时间 - 用于维度跳跃状态恢复
     */
    public void setWeaponCooldown(WeaponType weaponType, int cooldown) {
        if (weaponType != null) {
            this.missileCooldowns.put(weaponType, Math.max(0, cooldown));
            syncWeaponData(); // 同步数据
        }
    }

    /**
     * 获取所有武器状态（用于调试和状态保存）
     */
    public Map<WeaponType, Integer> getAllWeaponAmmo() {
        return new HashMap<>(missileAmmo);
    }

    /**
     * 获取所有武器冷却状态（用于调试和状态保存）
     */
    public Map<WeaponType, Integer> getAllWeaponCooldowns() {
        return new HashMap<>(missileCooldowns);
    }

    /**
     * 获取武器状态摘要（用于调试）
     */
    public String getWeaponStateSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weapon State:\n");
        for (WeaponType weaponType : WeaponType.values()) {
            sb.append(String.format("  %s: %d/%d (CD: %d)\n",
                    weaponType.getDisplayName(),
                    getMissileAmmo(weaponType),
                    getMaxMissileAmmo(weaponType),
                    getMissileCooldown(weaponType)
            ));
        }
        return sb.toString();
    }

    /**
     * 乘客和交互相关方法
     */

    private void updatePassengerPositions() {
        for (Entity passenger : this.getPassengers()) {
            updatePassenger(passenger);
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float offsetY = (float) (this.getMountedYOffset() + passenger.getYOffset());

            passenger.setPosition(
                    this.posX,
                    this.posY + offsetY,
                    this.posZ
            );

            // 乘客视角与飞船同步
            passenger.rotationYaw = this.rotationYaw;
            passenger.rotationPitch = this.rotationPitch;
            passenger.setRotationYawHead(this.rotationYaw);
        }
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!this.world.isRemote && this.getPassengers().isEmpty()) {
            player.startRiding(this);
            return true;
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public double getMountedYOffset() {
        return 0.3D;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    /**
     * NBT 数据持久化
     */

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("MotionX")) {
            this.motionX = compound.getDouble("MotionX");
            this.motionY = compound.getDouble("MotionY");
            this.motionZ = compound.getDouble("MotionZ");
        }
        if (compound.hasKey("RotationYaw")) {
            this.rotationYaw = compound.getFloat("RotationYaw");
            this.rotationPitch = compound.getFloat("RotationPitch");
        }

        // 读取武器状态 - 使用新的武器系统
        for (WeaponType weaponType : WeaponType.values()) {
            String ammoKey = "MissileAmmo_" + weaponType.getId();
            String cooldownKey = "MissileCooldown_" + weaponType.getId();

            if (compound.hasKey(ammoKey)) {
                this.missileAmmo.put(weaponType, compound.getInteger(ammoKey));
            }
            if (compound.hasKey(cooldownKey)) {
                this.missileCooldowns.put(weaponType, compound.getInteger(cooldownKey));
            }
        }

        // 读取维度跳跃状态
        if (compound.hasKey("HasTriggeredDimensionJump")) {
            this.hasTriggeredDimensionJump = compound.getBoolean("HasTriggeredDimensionJump");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setDouble("MotionX", this.motionX);
        compound.setDouble("MotionY", this.motionY);
        compound.setDouble("MotionZ", this.motionZ);
        compound.setFloat("RotationYaw", this.rotationYaw);
        compound.setFloat("RotationPitch", this.rotationPitch);

        // 保存武器状态 - 使用新的武器系统
        for (WeaponType weaponType : WeaponType.values()) {
            compound.setInteger("MissileAmmo_" + weaponType.getId(), this.missileAmmo.get(weaponType));
            compound.setInteger("MissileCooldown_" + weaponType.getId(), this.missileCooldowns.get(weaponType));
        }

        // 保存维度跳跃状态
        compound.setBoolean("HasTriggeredDimensionJump", this.hasTriggeredDimensionJump);
    }

    /**
     * 输入处理相关方法
     */

    // 客户端输入处理方法
    public void updateInputs(boolean accelerating, float pitch, float yaw) {
        // 更新输入状态
        this.accelerating = accelerating;
        this.pitchInput = pitch;
        this.yawInput = yaw;
        this.lastInputTime = System.currentTimeMillis();

        // 同步到数据监视器（用于渲染和预测）
        this.dataManager.set(ACCELERATING, accelerating);
        this.dataManager.set(PITCH_INPUT, pitch);
        this.dataManager.set(YAW_INPUT, yaw);

        // 发送网络包到服务端
        if (this.world.isRemote) {
            PacketDispatcher.sendToServer(new MessageVehicleInput(
                    this.getEntityId(), accelerating, pitch, yaw
            ));
        }
    }

    // 服务端输入更新方法 - 由网络包调用
    public void updateInputsFromServer(boolean accelerating, float pitch, float yaw) {
        // 直接更新输入状态
        this.accelerating = accelerating;
        this.pitchInput = pitch;
        this.yawInput = yaw;
        this.lastInputTime = System.currentTimeMillis();

        // 同步到数据监视器，以便在客户端显示
        this.dataManager.set(ACCELERATING, accelerating);
        this.dataManager.set(PITCH_INPUT, pitch);
        this.dataManager.set(YAW_INPUT, yaw);

        // 立即应用运动更新以确保响应性
        if (!this.world.isRemote) {
            this.updateMotion();
        }
    }

    /**
     * 调试和状态信息方法
     */

    // 获取当前输入状态（用于调试）
    public String getInputState() {
        return String.format("Input - Accel: %s, Pitch: %.1f, Yaw: %.1f",
                accelerating, pitchInput, yawInput);
    }

    // 获取运动状态（用于调试）
    public String getMotionState() {
        double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        return String.format("Pos: %.1f,%.1f,%.1f | Motion: %.2f | Speed: %.2f",
                posX, posY, posZ,
                Math.sqrt(motionX * motionX + motionZ * motionZ), speed);
    }

    /**
     * 实用方法
     */

    // 强制停止飞船
    public void stopVehicle() {
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.accelerating = false;
        this.pitchInput = 0;
        this.yawInput = 0;
        this.rollInput = 0;
    }

    // 获取当前速度
    public double getCurrentSpeed() {
        return Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
    }

    // 是否在移动
    public boolean isMoving() {
        return getCurrentSpeed() > 0.01;
    }

    /**
     * 获取维度跳跃高度阈值
     */
    public static int getDimensionJumpHeight() {
        return DIMENSION_JUMP_HEIGHT;
    }

    /**
     * 获取目标维度ID
     */
    public static int getTargetDimension() {
        return TARGET_DIMENSION;
    }
}