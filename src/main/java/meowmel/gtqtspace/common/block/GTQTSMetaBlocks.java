package meowmel.gtqtspace.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import meowmel.gtqtspace.common.block.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class GTQTSMetaBlocks {
	public static GTQTSpaceElevatorCasing spaceElevatorCasing;
	public static GTQTUpdateCasing updateCasing;
	public static GTQTSMultiblockCasing multiblockCasing;
	public static GTQTSMultiblockCasing1 multiblockCasing1;

	private GTQTSMetaBlocks() {
	}

	public static void init() {
		spaceElevatorCasing = new GTQTSpaceElevatorCasing();
		spaceElevatorCasing.setRegistryName("space_elevator");

		updateCasing = new GTQTUpdateCasing();
		updateCasing.setRegistryName("update_core");

		multiblockCasing = new GTQTSMultiblockCasing();
		multiblockCasing.setRegistryName("multiblock_casing");

		multiblockCasing1 = new GTQTSMultiblockCasing1();
		multiblockCasing1.setRegistryName("multiblock_casing1");
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		registerItemModel(spaceElevatorCasing);
		registerItemModel(updateCasing);
		registerItemModel(multiblockCasing);
		registerItemModel(multiblockCasing1);
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemModel(Block block) {
		for (IBlockState state : block.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
					block.getMetaFromState(state),
					new ModelResourceLocation(block.getRegistryName(),
							MetaBlocks.statePropertiesToString(state.getProperties())));
		}
	}
}
