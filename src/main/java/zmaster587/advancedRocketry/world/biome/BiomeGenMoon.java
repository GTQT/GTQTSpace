package zmaster587.advancedRocketry.world.biome;

import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.MOON_DIRT;

public class BiomeGenMoon extends CustomBiome {

    public BiomeGenMoon(BiomeProperties properties) {
        super(properties);

        //cold and dry
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        this.topBlock = GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF).getState(MOON_DIRT);
        this.fillerBlock= GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT).getState(MOON_DIRT);
        this.STONE = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE);
    }

    @Override
    @Nonnull
    public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType p_76747_1_) {
        return new LinkedList<>();
    }

    @Override
    public float getSpawningChance() {
        return 0f; //Nothing spawns
    }
}
