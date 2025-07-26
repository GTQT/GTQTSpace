package meowmel.gtqtspace.loaders;


import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.stone;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_BLOCK;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_INGOT;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.*;
import static meowmel.gtqtspace.common.block.GTQTSMetaBlocks.GTQTS_DIRT_BLOCKS;
import static meowmel.gtqtspace.common.block.GTQTSMetaBlocks.GTQTS_STONE_BLOCKS;

public class OreDictionaryLoader {
    public static void init() {
        loadStoneOredict();
        registerStoneBricksRecipes();
        registerDirtOredict();
        specialStone();
        centrifugalStone();
        
        
    }

    private static void centrifugalStone() {
        // 月球岩石粉离心配方
        CENTRIFUGE_RECIPES.recipeBuilder().duration(600).EUt(VA[HV])
                .input(dust, MoonStone)
                .chancedOutput(dust, Ilmenite, 2500, 500)
                .chancedOutput(dust, SiliconDioxide, 7500, 750)
                .chancedOutput(dust, RareEarth, 1000, 200)
                .fluidOutputs(Helium.getFluid(250))
                .buildAndRegister();

        // 火星岩石粉离心配方
        CENTRIFUGE_RECIPES.recipeBuilder().duration(600).EUt(VA[HV])
                .input(dust, MarsStone)
                .chancedOutput(dust, BandedIron, 8500, 850)
                .chancedOutput(dust, Olivine, 4500, 450)
                .chancedOutput(dust, SiliconDioxide, 1500, 150)
                .chancedOutput(dust, Magnesium, 2500, 250)
                .buildAndRegister();

        // 金星岩石粉离心配方
        CENTRIFUGE_RECIPES.recipeBuilder().duration(600).EUt(VA[HV])
                .input(dust, VenusStone)
                .chancedOutput(dust, Basalt, 6500, 650)
                .chancedOutput(dust, Sulfur, 9500, 950)
                .chancedOutput(dust, Pyrite, 3500, 350)
                .fluidOutputs(Lava.getFluid(1000))
                .buildAndRegister();

        // 木卫一岩石粉离心配方
        CENTRIFUGE_RECIPES.recipeBuilder().duration(600).EUt(VA[HV])
                .input(dust, IoStone)
                .chancedOutput(dust, Sulfur, 9900, 990)
                .chancedOutput(dust, Ash, 8000, 800)
                .chancedOutput(dust, Sodium, 3000, 300)
                .chancedOutput(dust, Potassium, 2500, 250)
                .fluidOutputs(SulfurDioxide.getFluid(500))
                .buildAndRegister();
    }

    private static void specialStone() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                        .getItemVariant(GTQTSStoneVariantBlock.StoneType.METHANE_ICE))
                .fluidOutputs(Methane.getFluid(1000))
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
    }

    private static void registerDirtOredict() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MOON_DIRT))
                .chancedOutput(dust, MoonStone, 3000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MARS_DIRT))
                .chancedOutput(dust, MarsStone, 3000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.VENUS_DIRT))
                .chancedOutput(dust, VenusStone, 3000, 500)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.DIRT)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.IO_DIRT))
                .chancedOutput(dust, IoStone, 3000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MOON_DIRT))
                .chancedOutput(dust, MoonStone, 2000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.MARS_DIRT))
                .chancedOutput(dust, MarsStone, 2000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.VENUS_DIRT))
                .chancedOutput(dust, VenusStone, 2000, 500)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(GTQTS_DIRT_BLOCKS.get(GTQTSDirtVariantBlock.DirtVariant.TURF)
                        .getItemVariant(GTQTSDirtVariantBlock.DirtType.IO_DIRT))
                .chancedOutput(dust, IoStone, 2000, 500)
                .EUt(VA[LV])
                .duration(200)
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

    public static void loadStoneOredict() {

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
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, MarsStone)
                .output(dust, MarsStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, VenusStone)
                .output(dust, VenusStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
        MACERATOR_RECIPES.recipeBuilder()
                .input(stone, IoStone)
                .output(dust, IoStone)
                .chancedOutput(dust, Stone, 1000, 380)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();
    }
}
