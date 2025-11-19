package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;
import zmaster587.advancedRocketry.world.gen.WorldGenNoTree;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.MERCURY_DIRT;
import static meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock.StoneType.MERCURY_STONE;

/**
 * 水星极端炎热生物群系 - 水星向阳面，极端高温
 */
public class BiomeGenMercuryExtremeHeat extends CustomBiome {

    private final static WorldGenNoTree noTree = new WorldGenNoTree(false);

    public BiomeGenMercuryExtremeHeat(BiomeProperties properties) {
        super(properties);

        // 清空所有生物生成
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        
        // 禁用所有装饰 - 极端环境
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        
        // 使用熔岩冷却后形成的黑色岩石
        this.topBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
        this.fillerBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
        this.STONE = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
            .getState(MERCURY_STONE);
    }

    @Override
    @Nonnull
    public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType p_76747_1_) {
        return new LinkedList<>();
    }

    @Override
    public float getSpawningChance() {
        return 0f; // 完全禁用生物生成
    }

    @Override
    public int getGrassColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 极端炎热的岩石颜色 - 深黑色偏红
        return 0x2a1a1a;
    }

    @Override
    public int getFoliageColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 无植被，返回岩石颜色
        return 0x2a1a1a;
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        // 炎热区域的天空呈现微红色调
        return 0x4a2020;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return noTree; // 禁用所有树生成
    }
}
