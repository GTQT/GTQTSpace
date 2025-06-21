package meowmel.gtqtspace.common.block.blocks;


import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GTQTUpdateCasing extends VariantBlock<GTQTUpdateCasing.CoreType> {
	public GTQTUpdateCasing() {
		super(Material.IRON);
		this.setTranslationKey("update_core");
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setHarvestLevel("wrench", 4);
		this.setDefaultState(this.getState(CoreType.UPDATE_CORE_VENT));
	}

	public boolean canCreatureSpawn(@Nonnull IBlockState state,
	                                @Nonnull IBlockAccess world,
	                                @Nonnull BlockPos pos,
	                                @Nonnull EntityLiving.SpawnPlacementType type) {
		return false;
	}

	public enum CoreType implements IStringSerializable {
		UPDATE_CORE_VENT("update_core_vent"),
		UPDATE_CORE_COLD("update_core_cold"),
		UPDATE_CORE_HOT("update_core_hot"),
		UPDATE_CORE_ELE("update_core_ele"),
		UPDATE_CORE_ENG("update_core_eng");

		private final String name;

		CoreType(String name) {
			this.name = name;
		}

		@Nonnull
		@Override
		public String getName() {
			return name;
		}
	}
}
