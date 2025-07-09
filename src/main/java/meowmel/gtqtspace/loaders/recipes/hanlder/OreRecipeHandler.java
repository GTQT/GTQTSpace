package meowmel.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.ConfigHolder;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix;

public class OreRecipeHandler {
    public static void register() {
        if (ConfigHolder.worldgen.allUniqueStoneTypes) {
            GTQTSOrePrefix.oreMoon.addProcessingHandler(PropertyKey.ORE, gregtech.loaders.recipe.handlers.OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreMars.addProcessingHandler(PropertyKey.ORE, gregtech.loaders.recipe.handlers.OreRecipeHandler::processOre);
            GTQTSOrePrefix.oreVenus.addProcessingHandler(PropertyKey.ORE, gregtech.loaders.recipe.handlers.OreRecipeHandler::processOre);


        }
    }
}
