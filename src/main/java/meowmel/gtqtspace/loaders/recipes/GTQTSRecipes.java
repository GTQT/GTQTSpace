package meowmel.gtqtspace.loaders.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.MetaBlocks;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix.densePlate;

public class GTQTSRecipes {
    public static void registerOrePrefix(){
        densePlate.addProcessingHandler(PropertyKey.INGOT, GTQTSRecipes::processDensePlate);

        if (ConfigHolder.worldgen.allUniqueStoneTypes) {
            GTQTSOrePrefix.oreMoon.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreMars.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreVenus.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreMethane.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreIo.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        }
    }

    private static void processDensePlate(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        if (material.hasFlag(GENERATE_PLATE)) {
            RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                    .input(OrePrefix.plateDense, material,4)
                    .output(orePrefix, material)
                    .explosives(new ItemStack(MetaBlocks.ITNT))
                    .duration(20)
                    .EUt(VA[HV])
                    .buildAndRegister();
        }
    }
}
