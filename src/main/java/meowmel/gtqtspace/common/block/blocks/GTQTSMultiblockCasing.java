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
public class GTQTSMultiblockCasing extends VariantBlock<GTQTSMultiblockCasing.CasingType> {
	public GTQTSMultiblockCasing() {
		super(Material.IRON);
		this.setTranslationKey("multiblock_casing");
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setHarvestLevel("wrench", 4);
		this.setDefaultState(this.getState(CasingType.TAZ_CASING));
	}

	public boolean canCreatureSpawn(@Nonnull IBlockState state,
	                                @Nonnull IBlockAccess world,
	                                @Nonnull BlockPos pos,
	                                @Nonnull EntityLiving.SpawnPlacementType type) {
		return false;
	}

	public enum CasingType implements IStringSerializable {
		TAZ_CASING("taz_casing"),
		TAZ_HEAT_VENT("taz_heat_vent"),

		SAZ_CASING("saz_casing"),
		SAZ_HEAT_VENT("saz_heat_vent"),

		IAZ_CASING("iaz_casing"),
		IAZ_HEAT_VENT("iaz_heat_vent"),

		CAZ_CASING("caz_casing"),
		CAZ_HEAT_VENT("caz_heat_vent");

		private final String name;

		CasingType(String name) {
			this.name = name;
		}

		@Nonnull
		@Override
		public String getName() {
			return name;
		}
	}
}
