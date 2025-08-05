package meowmel.gtqtspace.common.network;


import java.io.IOException;



import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SetTimerMessage extends AbstractMessage.AbstractClientMessage<SetTimerMessage> {

    public SetTimerMessage() {}

    @Override
    protected void read(PacketBuffer buffer) throws IOException {

    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {

    }

    @Override
    public void process(EntityPlayer player, Side side) {
    }

}