package meowmel.gtqtspace.common.block.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.unification.ore.OrePrefix;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static meowmel.gtqtspace.common.CommonProxy.Planet_TAB;

public class GTQTSDirtVariantBlock extends VariantBlock<GTQTSDirtVariantBlock.DirtType> {
    private static final PropertyEnum<DirtType> PROPERTY = PropertyEnum.create("variant", DirtType.class);
    private final DirtVariant dirtVariant;

    public GTQTSDirtVariantBlock(@Nonnull DirtVariant dirtVariant) {
        super(Material.ROCK);
        this.dirtVariant = dirtVariant;
        this.setRegistryName(dirtVariant.id);
        this.setTranslationKey(dirtVariant.translationKey);
        this.setHardness(dirtVariant.hardness);
        this.setResistance(dirtVariant.resistance);

        if (dirtVariant == DirtVariant.DIRT) {
            this.setSoundType(SoundType.GROUND);
        } else if (dirtVariant == DirtVariant.TURF) {
            this.setSoundType(SoundType.SAND);
        }
        this.setHarvestLevel("shovel", 0);
        this.setDefaultState(this.getState(DirtType.MOON_DIRT));
        this.setCreativeTab(Planet_TAB);
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        this.VARIANT = PROPERTY;
        this.VALUES = DirtType.values();
        return new BlockStateContainer(this, this.VARIANT);
    }


    public static enum DirtVariant {
        DIRT("gtqts_dirt"),
        TURF("gtqts_turf");

        public final String id;
        public final String translationKey;
        public final float hardness;
        public final float resistance;

        private DirtVariant(@Nonnull String id) {
            this(id, id);
        }

        private DirtVariant(@Nonnull String id, @Nonnull String translationKey) {
            this(id, translationKey, 1.5F, 10.0F);
        }

        private DirtVariant(@Nonnull String id, float hardness, float resistance) {
            this(id, id, hardness, resistance);
        }

        private DirtVariant(@Nonnull String id, @Nonnull String translationKey, float hardness, float resistance) {
            this.id = id;
            this.translationKey = translationKey;
            this.hardness = hardness;
            this.resistance = resistance;
        }
    }

    public static enum DirtType implements IStringSerializable {
        MOON_DIRT("moon", MapColor.GRAY),
        MARS_DIRT("mars", MapColor.RED_STAINED_HARDENED_CLAY),
        VENUS_DIRT("venus", MapColor.ORANGE_STAINED_HARDENED_CLAY),;

        private final String name;
        public final MapColor mapColor;

        private DirtType(@Nonnull String name, @Nonnull MapColor mapColor) {
            this.name = name;
            this.mapColor = mapColor;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        public OrePrefix getOrePrefix() {
            switch (this) {
                case MOON_DIRT:
                case MARS_DIRT:
                case VENUS_DIRT:
                    return OrePrefix.stone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        public gregtech.api.unification.material.Material getMaterial() {
            switch (this) {
                case MOON_DIRT:
                    return GTQTSpaceMaterials.MoonStone;
                case MARS_DIRT:
                    return GTQTSpaceMaterials.MarsStone;
                case VENUS_DIRT:
                    return GTQTSpaceMaterials.VenusStone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }
    }
}