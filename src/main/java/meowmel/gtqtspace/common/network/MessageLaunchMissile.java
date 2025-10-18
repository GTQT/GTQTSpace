package meowmel.gtqtspace.common.network;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.entity.WeaponType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class MessageLaunchMissile extends AbstractMessage.AbstractServerMessage<MessageLaunchMissile> {

    private int vehicleId;
    private String weaponTypeId; // 使用字符串ID而不是数字

    public MessageLaunchMissile() {}

    public MessageLaunchMissile(int vehicleId, String weaponTypeId) {
        this.vehicleId = vehicleId;
        this.weaponTypeId = weaponTypeId;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.vehicleId = buffer.readInt();
        this.weaponTypeId = buffer.readString(50); // 读取字符串ID
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(vehicleId);
        buffer.writeString(weaponTypeId);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        World world = player.world;
        Entity entity = world.getEntityByID(this.vehicleId);

        if (entity instanceof EntityCustomVehicle vehicle) {
            // 通过字符串ID获取武器类型
            WeaponType weaponType = WeaponType.byId(this.weaponTypeId);
            vehicle.launchMissile(weaponType);
        }
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getWeaponTypeId() {
        return weaponTypeId;
    }
}