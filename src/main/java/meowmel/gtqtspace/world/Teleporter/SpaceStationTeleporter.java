package meowmel.gtqtspace.world.Teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class SpaceStationTeleporter extends Teleporter {

    BlockPos pos;
    WorldServer world;

    public SpaceStationTeleporter(WorldServer worldIn, BlockPos pos) {
        super(worldIn);
        world = worldIn;
        this.pos = pos;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {

        pos = new BlockPos(pos.getX() / 8, 250, pos.getZ() / 8);

        for (int i = 0; i < 255; i++)
            if (world.getBlockState(pos.add(0, -i, 0)) != Blocks.AIR.getDefaultState()) {
                entityIn.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY() - i + 1, (double) pos.getZ() + 0.5, entityIn.rotationYaw, 0.0F);
                entityIn.motionX = 0.0D;
                entityIn.motionY = 0.0D;
                entityIn.motionZ = 0.0D;
                break;
            }
    }
}