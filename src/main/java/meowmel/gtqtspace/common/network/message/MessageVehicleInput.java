package meowmel.gtqtspace.common.network.message;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

/**
 * 飞船输入控制消息 - 从客户端发送到服务端
 */
public class MessageVehicleInput extends AbstractMessage.AbstractServerMessage<MessageVehicleInput> {

    private int entityId;
    private boolean accelerating;
    private float pitch;
    private float yaw;

    public MessageVehicleInput() {}

    public MessageVehicleInput(int entityId, boolean accelerating, float pitch, float yaw) {
        this.entityId = entityId;
        this.accelerating = accelerating;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.entityId = buffer.readInt();
        this.accelerating = buffer.readBoolean();
        this.pitch = buffer.readFloat();
        this.yaw = buffer.readFloat();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(entityId);
        buffer.writeBoolean(accelerating);
        buffer.writeFloat(pitch);
        buffer.writeFloat(yaw);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        // 这个方法在服务端被调用
        World world = player.world;
        Entity entity = world.getEntityByID(this.entityId);
        if (entity instanceof EntityCustomVehicle) {
            EntityCustomVehicle vehicle = (EntityCustomVehicle) entity;
            vehicle.updateInputsFromServer(this.accelerating, this.pitch, this.yaw);
        }
    }
}