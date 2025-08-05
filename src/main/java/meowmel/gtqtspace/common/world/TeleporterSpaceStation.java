package meowmel.gtqtspace.common.world;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.TileEntityTeleporterDeepDank;
import meowmel.gtqtspace.common.block.blocks.TravelBlockInit;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;


public class TeleporterSpaceStation extends Teleporter {

    private final WorldServer worldServer;
    private final BlockPos pos;
    private final int prevDim;
    private final double movementFactor;

    public TeleporterSpaceStation(WorldServer par1WorldServer, BlockPos pos, WorldProvider provider) {
        super(par1WorldServer);
        worldServer = par1WorldServer;
        prevDim = provider.getDimension();
        movementFactor = provider.getMovementFactor() / worldServer.provider.getMovementFactor();
        this.pos = new BlockPos(pos.getX() * movementFactor, pos.getY(), pos.getZ() * movementFactor);
    }

    @Override
    public void placeInPortal(Entity entity, float par8) {
        if (worldServer.getBlockState(pos).getBlock() != TravelBlockInit.TELEPORTER && worldServer.provider.getDimension() == 50) {
            int color = world.rand.nextInt(15);
            for (int x = -3; x < 4; x++)
                    for (int z = -3; z < 4; z++)
                        worldServer.setBlockState(pos.add(x, -1, z), Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(color));

            worldServer.setBlockState(pos, TravelBlockInit.TELEPORTER.getDefaultState());
        }

        if (prevDim != 50) {
            TileEntity te = worldServer.getTileEntity(pos);
            if (te instanceof TileEntityTeleporterDeepDank)
                ((TileEntityTeleporterDeepDank) te).setDimension(prevDim);
        }
        if (movementFactor > 1) {
            double x = pos.getX() > entity.posX ? pos.getX() - 0.5 : pos.getX() + 1.5;
            double z = pos.getZ() > entity.posZ ? pos.getZ() - 0.5 : pos.getZ() + 1.5;
            entity.setPositionAndUpdate(MathHelper.floor(x), MathHelper.floor(entity.posY), MathHelper.floor(z));
        }

        entity.setPosition(MathHelper.floor(entity.posX < pos.getX() ? pos.getX() - 1 : entity.posX), MathHelper.floor(entity.posY), MathHelper.floor(entity.posZ < pos.getZ() ? pos.getZ() - 1 : entity.posZ));
        entity.motionX = entity.motionY = entity.motionZ = 0;
    }
}