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

public class GTQTSMultiblockCasing1 extends VariantBlock<GTQTSMultiblockCasing1.CasingType> {
    public GTQTSMultiblockCasing1() {
        super(Material.IRON);
        this.setTranslationKey("multiblock_casing1");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 4);
        this.setDefaultState(this.getState(CasingType.SOLAR_PLATE_CASING));
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state,
                                    @Nonnull IBlockAccess world,
                                    @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable {
        SOLAR_PLATE_CASING("solar_plate_casing"),
        SOLAR_PLATE_MKI("solar_plate_mki"),
        SOLAR_PLATE_MKII("solar_plate_mkii"),
        SOLAR_PLATE_MKIII("solar_plate_mkiii"),
        SOLAR_PLATE_MKIV("solar_plate_mkiv"),
        SOLAR_PLATE_MKV("solar_plate_mkv");

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