package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
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
 * 水星玄武岩生物群系 - 水星上的火山平原，玄武岩地貌
 */
public class BiomeGenMercuryBasaltic extends CustomBiome {

    private final static WorldGenNoTree noTree = new WorldGenNoTree(false);

    public BiomeGenMercuryBasaltic(BiomeProperties properties) {
        super(properties);

        // 清空友善生物生成，但保留一些敌对生物
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCreeper.class, 3, 1, 1));
        
        // 禁用所有装饰
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        
        // 使用玄武岩 - 火山岩浆冷却形成的黑色岩石
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
        if (p_76747_1_ == EnumCreatureType.MONSTER) {
            return this.spawnableMonsterList;
        }
        return new LinkedList<>();
    }

    @Override
    public float getSpawningChance() {
        return 0.01f; // 极低的生物生成几率
    }

    @Override
    public int getGrassColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 玄武岩的深黑色调
        return 0x1a1a1a;
    }

    @Override
    public int getFoliageColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 无植被，返回岩石颜色
        return 0x1a1a1a;
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        // 玄武岩地貌的天空 - 暗红色调
        return 0x3a1a1a;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return noTree; // 禁用所有树生成
    }
}
