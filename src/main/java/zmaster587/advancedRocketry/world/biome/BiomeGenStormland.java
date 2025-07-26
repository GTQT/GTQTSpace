package zmaster587.advancedRocketry.world.biome;

import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zmaster587.advancedRocketry.api.AdvancedRocketryBlocks;
import zmaster587.advancedRocketry.dimension.biome.CustomBiome;
import zmaster587.advancedRocketry.world.gen.WorldGenCharredTree;
import zmaster587.advancedRocketry.world.gen.WorldGenElectricMushroom;

import java.util.Random;

import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.IO_DIRT;
import static meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock.DirtType.VENUS_DIRT;

public class BiomeGenStormland extends CustomBiome {

    WorldGenAbstractTree charTree = new WorldGenCharredTree(false, 6);

    public BiomeGenStormland(BiomeProperties properties) {
        super(properties);

        spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCreeper.class, 5, 1, 1));
        this.spawnableCreatureList.clear();
        this.decorator.generateFalls = false;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 6;
        this.topBlock = GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF).getState(IO_DIRT);
        this.fillerBlock= GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT).getState(IO_DIRT);
        this.STONE = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.IO_STONE);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);

        int x = pos.getX() + rand.nextInt(16);
        int y = rand.nextInt(28) + 80;
        int z = pos.getZ() + rand.nextInt(16);
        (new WorldGenElectricMushroom(AdvancedRocketryBlocks.blockElectricMushroom)).generate(worldIn, rand, new BlockPos(x, y, z));
    }


    @Override
    public float getSpawningChance() {
        return 0f; //Nothing spawns
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0x202020;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return charTree;
    }

    @Override
    public int getModdedBiomeGrassColor(int original) {
        return super.getModdedBiomeGrassColor(0x202020);
    }
}
