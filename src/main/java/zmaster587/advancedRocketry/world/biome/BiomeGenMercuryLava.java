package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
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
 * 水星熔岩生物群系 - 水星上的活跃火山区域，有熔岩湖
 */
public class BiomeGenMercuryLava extends CustomBiome {

    private final static WorldGenNoTree noTree = new WorldGenNoTree(false);

    public BiomeGenMercuryLava(BiomeProperties properties) {
        super(properties);

        // 清空友善生物生成，但添加一些火属性敌对生物
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCreeper.class, 5, 1, 1));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 2));
        
        // 禁用所有装饰
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 0;
        this.decorator.mushroomsPerChunk = 0;
        
        // 使用熔岩冷却后的黑曜石状岩石
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
        return 0.02f; // 较低但非零的生物生成几率
    }

    @Override
    public int getGrassColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 熔岩地貌的深红黑色调
        return 0x2a0a0a;
    }

    @Override
    public int getFoliageColorAtPos(@javax.annotation.Nonnull BlockPos pos) {
        // 无植被，返回岩石颜色
        return 0x2a0a0a;
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        // 熔岩区域的天空 - 鲜艳的橙红色调
        return 0x4a2010;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return noTree; // 禁用所有树生成
    }

    @Override
    public void decorate(net.minecraft.world.World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        
        // 在熔岩生物群系中添加小概率的熔岩池生成
        if (rand.nextInt(100) == 0) {
            int x = pos.getX() + rand.nextInt(16);
            int z = pos.getZ() + rand.nextInt(16);
            int y = worldIn.getHeight(new BlockPos(x, 0, z)).getY();
            
            // 在地表附近生成熔岩池
            if (y > 0 && y < 100) {
                // 这里可以添加熔岩池生成的逻辑
                // 但需要相应的世界生成器，暂时留空
            }
        }
    }
}
