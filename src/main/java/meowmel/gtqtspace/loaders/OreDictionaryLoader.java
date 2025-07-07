package meowmel.gtqtspace.loaders;


import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.Marble;
import static gregtech.api.unification.material.Materials.Stone;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.stone;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_BLOCK;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_INGOT;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.*;
import static meowmel.gtqtspace.common.block.GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS;
import static meowmel.gtqtspace.common.block.GTQTSMetaBlocks.GTQTS_STONE_BLOCKS;

public class OreDictionaryLoader {
    public static void init(){
        loadStoneOredict();
        registerStoneBricksRecipes();
        registerDirtOredict();
    }

    private static void registerDirtOredict() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MOON_DIRT))
                .chancedOutput(dust, MoonStone, 3000, 500)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MARS_DIRT))
                .chancedOutput(dust, MarsStone, 3000, 500)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.VENUS_DIRT))
                .chancedOutput(dust, VenusStone, 3000, 500)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MOON_DIRT))
                .chancedOutput(dust, MoonStone, 2000, 500)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MARS_DIRT))
                .chancedOutput(dust, MarsStone, 2000, 500)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.VENUS_DIRT))
                .chancedOutput(dust, VenusStone, 2000, 500)
                .buildAndRegister();
        /*
        // normal variant -> cobble variant
        EnumMap<GTQTSDirtVariantBlock.DirtVariant, List<ItemStack>> variantListMap = new EnumMap<>(GTQTSDirtVariantBlock.DirtVariant.class);
        for (GTQTSDirtVariantBlock.DirtVariant shape : GTQTSDirtVariantBlock.DirtVariant.values()) {
            GTQTSDirtVariantBlock block = GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS.get(shape);
            variantListMap.put(shape,
                    Arrays.stream(GTQTSDirtVariantBlock.DirtType.values())
                            .map(block::getItemVariant)
                            .collect(Collectors.toList()));
        }

        List<ItemStack> dirt = variantListMap.get(GTQTSDirtVariantBlock.DirtVariant.DIRT);
        List<ItemStack> turf = variantListMap.get(GTQTSDirtVariantBlock.DirtVariant.TURF);


        registerDirtRecipe(dirt);
        registerTurfRecipe(turf);
         */

    }

    private static void registerStoneBricksRecipes() {
        // normal variant -> cobble variant
        EnumMap<GTQTSStoneVariantBlock.StoneVariant, List<ItemStack>> variantListMap = new EnumMap<>(GTQTSStoneVariantBlock.StoneVariant.class);
        for (GTQTSStoneVariantBlock.StoneVariant shape : GTQTSStoneVariantBlock.StoneVariant.values()) {
            GTQTSStoneVariantBlock block = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(shape);
            variantListMap.put(shape,
                    Arrays.stream(GTQTSStoneVariantBlock.StoneType.values())
                            .map(block::getItemVariant)
                            .collect(Collectors.toList()));
        }
        List<ItemStack> cobbles = variantListMap.get(GTQTSStoneVariantBlock.StoneVariant.COBBLE);
        List<ItemStack> smooths = variantListMap.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH);
        List<ItemStack> bricks = variantListMap.get(GTQTSStoneVariantBlock.StoneVariant.BRICKS);

        registerSmoothRecipe(cobbles, smooths);
        registerCobbleRecipe(smooths, cobbles);

        for (int i = 0; i < smooths.size(); i++) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(smooths.get(i))
                    .notConsumable(SHAPE_EXTRUDER_INGOT.getStackForm())
                    .outputs(bricks.get(i))
                    .duration(24).EUt(8).buildAndRegister();
        }
    }
    private static void registerSmoothRecipe(List<ItemStack> roughStack, List<ItemStack> smoothStack) {
        for (int i = 0; i < roughStack.size(); i++) {
            ModHandler.addSmeltingRecipe(roughStack.get(i), smoothStack.get(i), 0.1f);

            EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(roughStack.get(i))
                    .notConsumable(SHAPE_EXTRUDER_BLOCK.getStackForm())
                    .outputs(smoothStack.get(i))
                    .duration(24).EUt(8).buildAndRegister();
        }
    }

    private static void registerCobbleRecipe(List<ItemStack> smoothStack, List<ItemStack> cobbleStack) {
        for (int i = 0; i < smoothStack.size(); i++) {
            FORGE_HAMMER_RECIPES.recipeBuilder()
                    .inputs(smoothStack.get(i))
                    .outputs(cobbleStack.get(i))
                    .duration(12).EUt(4).buildAndRegister();
        }
    }

    public static void loadStoneOredict(){

        for (GTQTSStoneVariantBlock.StoneType type : GTQTSStoneVariantBlock.StoneType.values()) {
            ItemStack smooth = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            OreDictUnifier.registerOre(smooth, type.getOrePrefix(), type.getMaterial());
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
            OreDictionary.registerOre("stoneCobble", cobble);
        }

        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, MoonStone)
                .output(dust, MoonStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .buildAndRegister();
        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, MarsStone)
                .output(dust, MarsStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .buildAndRegister();
        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, VenusStone)
                .output(dust, VenusStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .buildAndRegister();
    }
}
