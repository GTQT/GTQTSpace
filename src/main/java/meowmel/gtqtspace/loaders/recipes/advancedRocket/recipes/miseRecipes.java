package meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.common.items.MetaItems.*;
import static meowmel.gtqtspace.common.items.GTQTSMetaItems.HEAT_SHIELDING_PLATE;
import static meowmel.gtqtspace.common.items.GTQTSMetaItems.SHIELDING_PLATE;
import static zmaster587.advancedRocketry.api.AdvancedRocketryBlocks.*;
import static zmaster587.advancedRocketry.api.AdvancedRocketryItems.*;
import static zmaster587.libVulpes.api.LibVulpesBlocks.blockStructureBlock;

public class miseRecipes {
    public static void init()
    {
        //抗辐射夹板
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(GTQTOrePrefix.wrap, Materials.Polyethylene)
                .input(OrePrefix.ring,Materials.Aluminium,2)
                .input(OrePrefix.plate,Materials.Lead,4)
                .output(SHIELDING_PLATE)
                .duration(20)
                .EUt(30)
                .buildAndRegister();

        //防高温夹板
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(GTQTOrePrefix.wrap, Materials.Polyethylene)
                .input(OrePrefix.ring,Materials.Aluminium,2)
                .input(OrePrefix.plate,Materials.Steel,4)
                .output(HEAT_SHIELDING_PLATE)
                .duration(20)
                .EUt(30)
                .buildAndRegister();

        //太空服
        //纳米套+两个夹板
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(NANO_HELMET)
                .input(SHIELDING_PLATE)
                .input(HEAT_SHIELDING_PLATE)
                .output(itemSpaceSuit_Helmet)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(NANO_CHESTPLATE)
                .input(SHIELDING_PLATE)
                .input(HEAT_SHIELDING_PLATE)
                .output(itemSpaceSuit_Chest)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(NANO_LEGGINGS)
                .input(SHIELDING_PLATE)
                .input(HEAT_SHIELDING_PLATE)
                .output(itemSpaceSuit_Leggings)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(NANO_BOOTS)
                .input(SHIELDING_PLATE)
                .input(HEAT_SHIELDING_PLATE)
                .output(itemSpaceSuit_Boots)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        //低压槽
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_CELL_LARGE_ALUMINIUM)
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .output(itemPressureTank,1,0)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_CELL_LARGE_STAINLESS_STEEL)
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .output(itemPressureTank,1,1)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_CELL_LARGE_TITANIUM)
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .output(itemPressureTank,1,2)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .output(itemPressureTank,1,3)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();
        //气体填充站
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock)
                .input(MetaItems.COVER_SCREEN)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel,2)
                .input(ELECTRIC_PUMP_HV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(blockOxygenCharger)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //宇航服工作台
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock)
                .input(MetaItems.COVER_SCREEN)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel,2)
                .input(ROBOT_ARM_HV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(blockSuitWorkStation)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemPlanetIdChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.Aluminium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.StainlessSteel, 6)
                .input(OrePrefix.plate, Materials.Copper, 2)
                .output(itemSpaceStationChip)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemSpaceStationChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.Aluminium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.StainlessSteel, 6)
                .input(OrePrefix.plate, GTQTSpaceMaterials.MeteoricIron, 2)
                .output(itemSpaceStationChip)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemSpaceElevatorChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.StainlessSteel)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.Titanium, 6)
                .input(OrePrefix.plate, Materials.Titanium, 2)
                .output(itemSpaceElevatorChip)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemSatelliteIdChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.Aluminium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.StainlessSteel, 6)
                .input(OrePrefix.plate, Materials.Titanium, 2)
                .output(itemSatelliteIdChip)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemAsteroidChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.StainlessSteel)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.Aluminium, 6)
                .input(OrePrefix.plate, Materials.Titanium, 2)
                .output(itemAsteroidChip)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //itemPlanetIdChip
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Materials.Steel)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 2)
                .input(RANDOM_ACCESS_MEMORY,4)
                .input(OrePrefix.wireFine, Materials.Copper, 6)
                .input(OrePrefix.plate, Materials.Steel, 2)
                .output(itemPlanetIdChip)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();
    }
}
