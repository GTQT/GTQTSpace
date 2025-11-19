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
 * 水星暗面生物群系 - 水星背阳面，极端低温
 */
public class BiomeGenMercuryDarkSide extends CustomBiome {

    private final static WorldGenNoTree noTree = new WorldGenNoTree(false);

    public BiomeGenMercuryDarkSide(BiomeProperties properties) {
        super(properties);

        // 清空所有生物生成
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        
        // 禁用所有装饰 - 极端寒冷环境
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        
        // 使用冷冻形成的岩石
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
        // 极端寒冷的岩石颜色 - 深蓝灰色
        return 0x1a2a4a;
    }

    @Override
    public int getFoliageColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 无植被，返回岩石颜色
        return 0x1a2a4a;
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        // 寒冷区域的天空呈现深蓝灰色调
        return 0x0a1a2a;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return noTree; // 禁用所有树生成
    }
}
