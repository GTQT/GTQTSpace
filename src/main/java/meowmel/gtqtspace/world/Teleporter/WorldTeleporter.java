package meowmel.gtqtspace.world.Teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import static meowmel.gtqtspace.GTQTSpace.portal;

public class WorldTeleporter extends Teleporter {

    BlockPos pos;
    WorldServer world;

    public WorldTeleporter(WorldServer worldIn,BlockPos pos) {
        super(worldIn);
        world = worldIn;
        this.pos=pos;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {

            pos = new BlockPos(pos.getX()*8, 120, pos.getZ()*8);
            if (world.getBlockState(pos).getBlock() != portal) {
                int color = world.rand.nextInt(15);
                for (int x = -3; x < 4; x++) {
                    for (int z = -3; z < 4; z++) {
                        if (world.isAirBlock(pos.add(x, 0, z))) {
                            world.setBlockState(pos.add(x, 0, z), Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(color));
                        }

                    }
                }
            }
        entityIn.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY() + 1, (double) pos.getZ() + 0.5, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = 0.0D;
        entityIn.motionY = 0.0D;
        entityIn.motionZ = 0.0D;

    }
}