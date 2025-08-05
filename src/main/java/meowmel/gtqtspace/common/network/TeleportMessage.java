package meowmel.gtqtspace.common.network;

import meowmel.gtqtspace.common.block.TileEntityTeleporterDeepDank;
import meowmel.gtqtspace.common.world.TeleporterSpaceStation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class TeleportMessage extends AbstractMessage.AbstractServerMessage<TeleportMessage> {

    private BlockPos pos;

    public TeleportMessage() {}

    public TeleportMessage(BlockPos pos){
        this.pos = pos;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {

        pos = buffer.readBlockPos();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {

        buffer.writeBlockPos(pos);
    }

    @Override
    public void process(EntityPlayer player, Side side) {

        if(player instanceof EntityPlayerMP){
            EntityPlayerMP thePlayer = (EntityPlayerMP)player;

            if(!thePlayer.isRiding() && !thePlayer.isBeingRidden()) {
                if (thePlayer.timeUntilPortal > 0)
                    thePlayer.timeUntilPortal = thePlayer.getPortalCooldown();
                else if (thePlayer.dimension !=50)
                {
                    if(!ForgeHooks.onTravelToDimension(thePlayer, 50)) return;
                    thePlayer.timeUntilPortal = 10;
                    thePlayer.getServer().getPlayerList().transferPlayerToDimension(thePlayer,50, new TeleporterSpaceStation(thePlayer.getServer().getWorld(50), pos, thePlayer.world.provider));
                } else {
                    TileEntityTeleporterDeepDank tile = (TileEntityTeleporterDeepDank)thePlayer.world.getTileEntity(pos);
                    if(!ForgeHooks.onTravelToDimension(thePlayer, tile.getDimension())) return;
                    thePlayer.timeUntilPortal = 10;
                    thePlayer.getServer().getPlayerList().transferPlayerToDimension(thePlayer, tile.getDimension(), new TeleporterSpaceStation(thePlayer.getServer().getWorld(tile.getDimension()), pos, thePlayer.world.provider));
                }
            } else {
                Entity mount = thePlayer.getRidingEntity();
                if(mount != null) {
                    thePlayer.dismountRidingEntity();
                    if(thePlayer.getRNG().nextInt(10) == 0) {
                        mount.setDead();
                        thePlayer.sendMessage(new TextComponentString("Your mount crashed into a diamond car made out of 400 walls/hour at the speed of iron cars/km"));
                        thePlayer.sendMessage(new TextComponentString("It's in a better place now (far away from you)"));
                    } else thePlayer.sendStatusMessage(new TextComponentString("You can't enter The Beneath while riding something!"), true);
                }
                if(thePlayer.isBeingRidden()) {
                    thePlayer.removePassengers();
                    thePlayer.sendStatusMessage(new TextComponentString("You can't enter The Beneath while something is riding on your back!"), true);
                }
            }
        }
    }
}