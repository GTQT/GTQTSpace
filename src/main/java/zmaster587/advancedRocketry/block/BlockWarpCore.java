package zmaster587.advancedRocketry.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zmaster587.advancedRocketry.api.ARConfiguration;
import zmaster587.advancedRocketry.api.stations.ISpaceObject;
import zmaster587.advancedRocketry.stations.SpaceObjectManager;
import zmaster587.advancedRocketry.stations.SpaceStationObject;
import zmaster587.libVulpes.block.multiblock.BlockMultiblockMachine;
import zmaster587.libVulpes.tile.multiblock.TileMultiBlock;
import zmaster587.libVulpes.util.HashedBlockPosition;

import javax.annotation.Nonnull;

public class BlockWarpCore extends BlockMultiblockMachine {

    public BlockWarpCore(Class<? extends TileMultiBlock> tileClass,
                         int guiId) {
        super(tileClass, guiId);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
                                EntityLivingBase placer, @Nonnull ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        if (!world.isRemote && world.provider.getDimension() == ARConfiguration.getCurrentConfig().spaceDimId) {
            ISpaceObject spaceObj = SpaceObjectManager.getSpaceManager().getSpaceStationFromBlockCoords(pos);

            if (spaceObj instanceof SpaceStationObject)
                ((SpaceStationObject) spaceObj).addWarpCore(new HashedBlockPosition(pos));
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        // 在真正破坏前执行你的逻辑
        if (!world.isRemote && world.provider.getDimension() == ARConfiguration.getCurrentConfig().spaceDimId) {
            ISpaceObject spaceObj = SpaceObjectManager.getSpaceManager().getSpaceStationFromBlockCoords(pos);
            if (spaceObj instanceof SpaceStationObject)
                ((SpaceStationObject) spaceObj).removeWarpCore(new HashedBlockPosition(pos));
        }

        // 调用原逻辑，确保正常破坏流程
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
}
