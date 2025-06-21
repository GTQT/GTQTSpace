package meowmel.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.api.unification.MaterialHelper;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.EXTREME_LARGE_MINER;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.INFINITY_FLUID_DRILL_RIG;
import static meowmel.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing.ElevatorCasingType.*;
import static meowmel.gtqtspace.common.items.GTQTSMetaItems.*;
import static meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.*;

public class SpaceElevatorLoader {

    public static void init() {
        casing();
        mining();
        drilling();
        motor();
        DroneRecipes();
    }

    private static void DroneRecipes() {

        //  LV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(toolHeadDrill, Steel, 16)
                .input(frameGt, Steel, 16)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .input(ROBOT_ARM_LV, 8)
                .input(SENSOR_LV, 8)
                .input(wireGtSingle, MaterialHelper.Superconductor[0], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_LV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  MV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(toolHeadDrill, Aluminium, 16)
                .input(frameGt, Aluminium, 16)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .input(ROBOT_ARM_MV, 2)
                .input(SENSOR_MV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[1], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_MV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  HV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(toolHeadDrill, StainlessSteel, 16)
                .input(frameGt, StainlessSteel, 16)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .input(circuit, MarkerMaterials.Tier.HV, 16)
                .input(ROBOT_ARM_HV, 2)
                .input(SENSOR_HV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[2], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_HV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  EV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(toolHeadDrill, Titanium, 16)
                .input(frameGt, Titanium, 16)
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .input(circuit, MarkerMaterials.Tier.HV, 16)
                .input(circuit, MarkerMaterials.Tier.EV, 16)
                .input(ROBOT_ARM_EV, 2)
                .input(SENSOR_EV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[3], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_EV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  IV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(toolHeadDrill, TungstenSteel, 16)
                .input(frameGt, TungstenSteel, 16)
                .input(circuit, MarkerMaterials.Tier.HV, 16)
                .input(circuit, MarkerMaterials.Tier.EV, 16)
                .input(circuit, MarkerMaterials.Tier.IV, 16)
                .input(ROBOT_ARM_IV, 2)
                .input(SENSOR_IV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[4], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_IV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  LuV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(toolHeadDrill, BlackTitanium, 16)
                .input(frameGt, BlackTitanium, 16)
                .input(circuit, MarkerMaterials.Tier.EV, 16)
                .input(circuit, MarkerMaterials.Tier.IV, 16)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(ROBOT_ARM_LuV, 2)
                .input(SENSOR_LuV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[5], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Cupronickel.getFluid(L))
                .output(MINING_DRONE_LuV)
                .EUt(VA[LuV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  ZPM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(toolHeadDrill, NaquadahAlloy, 16)
                .input(frameGt, NaquadahAlloy, 16)
                .input(circuit, MarkerMaterials.Tier.IV, 16)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(ROBOT_ARM_ZPM, 2)
                .input(SENSOR_ZPM, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[6], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Kanthal.getFluid(L))
                .output(MINING_DRONE_ZPM)
                .EUt(VA[ZPM])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(toolHeadDrill, Neutronium, 16)
                .input(frameGt, Neutronium, 16)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(ROBOT_ARM_UV, 2)
                .input(SENSOR_UV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[7], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Nichrome.getFluid(L))
                .output(MINING_DRONE_UV)
                .EUt(VA[UV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[ZPM])
                        .CWUt(CWT[ZPM]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UHV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(toolHeadDrill, Orichalcum, 16)
                .input(frameGt, Orichalcum, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(ROBOT_ARM_UHV, 2)
                .input(SENSOR_UHV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[8], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(RTMAlloy.getFluid(L))
                .output(MINING_DRONE_UHV)
                .EUt(VA[UHV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[UV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        /*
        //  UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, Adamantium,16)
                .input(frameGt, Adamantium,16)
                .input(circuit, MarkerMaterials.Tier.UV,16)
                .input(circuit, MarkerMaterials.Tier.UHV,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(ROBOT_ARM_UEV, 2)
                .input(SENSOR_UEV, 2)
                .input(cableGtSingle, PedotTMA, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(HSSG.getFluid(L))
                .output(MINING_DRONE_UEV)
                .EUt(VA[UEV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UHV.getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UHV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UIV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, Infinity,16)
                .input(frameGt, Infinity,16)
                .input(circuit, MarkerMaterials.Tier.UHV,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(circuit, MarkerMaterials.Tier.UIV,16)
                .input(ROBOT_ARM_UIV, 2)
                .input(SENSOR_UIV, 2)
                .input(cableGtSingle, Solarium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Naquadah.getFluid(L))
                .output(MINING_DRONE_UIV)
                .EUt(VA[UIV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UEV.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UEV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UXV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, CosmicNeutronium,16)
                .input(frameGt, CosmicNeutronium,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(circuit, MarkerMaterials.Tier.UIV,16)
                .input(circuit, MarkerMaterials.Tier.UXV,16)
                .input(ROBOT_ARM_UXV, 2)
                .input(SENSOR_UXV, 2)
                .input(cableGtSingle, Hypogen, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Trinium.getFluid(L))
                .output(MINING_DRONE_UXV)
                .EUt(VA[UXV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UIV.getStackForm())
                        .EUt(VA[UIV])
                        .CWUt(CWT[UIV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
        */
    }

    private static void motor() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .input(VOLTAGE_COIL_ZPM, 2)
                .input(wireGtSingle, IVSuperconductor, 32)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK1))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .input(VOLTAGE_COIL_UV, 2)
                .input(wireGtSingle, LuVSuperconductor, 32)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK2))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .input(VOLTAGE_COIL_UHV, 2)
                .input(wireGtSingle, ZPMSuperconductor, 32)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK3))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UHV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .input(VOLTAGE_COIL_UEV, 2)
                .input(wireGtSingle, UVSuperconductor, 32)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK4))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UEV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_V)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .input(VOLTAGE_COIL_UIV, 2)
                .input(wireGtSingle, UHVSuperconductor, 32)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK5))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UIV]).buildAndRegister();
    }

    private static void casing() {
        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 32))
                .input(ELECTRIC_PISTON_UV, 64)
                .input(ELECTRIC_MOTOR_UV, 64)
                .input(FIELD_GENERATOR_UV, 64)
                .input(CIRCUIT_GOOD_III, 64)
                .input(NANO_POWER_IC, 64)
                .input(frameGt, Tritanium, 32)
                .input(plate, Europium, 32)
                .input(plate, HY1301, 32)
                .input(ring, Darmstadtium, 64)
                .input(gear, Pikyonium64B, 4)
                .input(stickLong, Naquadria, 64)
                .input(wireFine, NiobiumTitanium, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .output(SPACE_ELEVATOR)
                .fluidInputs(Duranium.getFluid(L * 32))
                .fluidInputs(Lutetium.getFluid(L * 64))
                .fluidInputs(UUMatter.getFluid(16000))
                .fluidInputs(KaptonE.getFluid(L * 64))
                .stationResearch(b -> b
                        .researchStack(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .duration(2000).EUt(VA[UV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III)
                .input(HULL[UV], 8)
                .input(frameGt, Europium, 4)
                .input(plate, HY1301, 8)
                .input(plate, Tritanium, 8)
                .input(gearSmall, Pikyonium64B, 16)
                .input(screw, Naquadria, 16)
                .input(wireFine, Americium, 16)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING, 4))
                .fluidInputs(Lutetium.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(KaptonK.getFluid(L * 2))
                .duration(800).EUt(VA[UV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                .input(CIRCUIT_GOOD_III)
                .input(frameGt, Europium, 4)
                .input(plate, HY1301, 8)
                .input(plate, Tritanium, 8)
                .input(gearSmall, Pikyonium64B, 16)
                .input(screw, Naquadria, 16)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(INTERNAL_STRUCTURE, 2))
                .fluidInputs(Lutetium.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(KaptonK.getFluid(L * 2))
                .duration(1000).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                .input(CIRCUIT_GOOD_III)
                .input(frameGt, Europium, 4)
                .input(plate, HY1301, 8)
                .input(plate, Tritanium, 8)
                .input(gearSmall, Pikyonium64B, 8)
                .input(screw, Naquadria, 16)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(SUPPORT_STRUCTURE, 2))
                .fluidInputs(Lutetium.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(KaptonK.getFluid(L * 2))
                .duration(1000).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                .input(CIRCUIT_GOOD_III)
                .input(plate, HY1301, 8)
                .input(FIELD_GENERATOR_IV, 1)
                .input(plate, Europium, 4)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(FLOOR, 2))
                .fluidInputs(Lutetium.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .fluidInputs(KaptonK.getFluid(L * 2))
                .duration(1000).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(CABLE_CASING, 16))
                .fluidInputs(Lutetium.getFluid(L * 64))
                .fluidInputs(NiobiumTitanium.getFluid(L * 64))
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(KaptonK.getFluid(L * 23))
                .duration(1000).EUt(VA[UV]).buildAndRegister();
    }

    private static void mining() {
        // Space Mining Module MK1
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(EXTREME_LARGE_MINER)
                .input(CIRCUIT_GOOD_III, 8)
                .input(ROBOT_ARM_UV, 8)
                .input(FIELD_GENERATOR_UV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(SENSOR_UV, 16)
                .input(wireGtQuadruple, EnrichedNaquadahTriniumEuropiumDuranide, 32)
                .input(frameGt, Tritanium, 16)
                .fluidInputs(Lutetium.getFluid(L * 20))
                .fluidInputs(KaptonK.getFluid(8000))
                .fluidInputs(Naquadria.getFluid(L * 10))
                .output(MINING_MODULE[0])
                .EUt(VA[UV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(EXTREME_LARGE_MINER.getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[ZPM]))
                .buildAndRegister();

        // Space Mining Module MK2
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MINING_MODULE[0])
                .input(CIRCUIT_GOOD_IV, 8)
                .input(ROBOT_ARM_UHV, 8)
                .input(FIELD_GENERATOR_UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(SENSOR_UHV, 16)
                .input(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate, 32)
                .input(frameGt, Vibranium, 16)
                .fluidInputs(Lutetium.getFluid(L * 40))
                .fluidInputs(KaptonK.getFluid(16000))
                .fluidInputs(Adamantium.getFluid(L * 20))
                .output(MINING_MODULE[1])
                .EUt(VA[UHV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(MINING_MODULE[0].getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .buildAndRegister();

        // Space Mining Module MK3
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MINING_MODULE[1])
                .input(CIRCUIT_GOOD_V, 8)
                .input(ROBOT_ARM_UEV, 8)
                .input(FIELD_GENERATOR_UEV, 4)
                .input(circuit, MarkerMaterials.Tier.UEV, 16)
                .input(SENSOR_UEV, 16)
                .input(wireGtQuadruple, PedotTMA, 32)
                .input(frameGt, Neutronium, 16)
                .fluidInputs(Lutetium.getFluid(L * 80))
                .fluidInputs(KaptonK.getFluid(64000))
                .fluidInputs(Infinity.getFluid(L * 40))
                .output(MINING_MODULE[2])
                .EUt(VA[UEV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(MINING_MODULE[1].getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .buildAndRegister();
    }

    private static void drilling() {
        // Space Mining Module MK1
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(INFINITY_FLUID_DRILL_RIG)
                .input(CIRCUIT_GOOD_III, 8)
                .input(ELECTRIC_PUMP_UV, 8)
                .input(FIELD_GENERATOR_UV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(SENSOR_UV, 16)
                .input(wireGtQuadruple, EnrichedNaquadahTriniumEuropiumDuranide, 32)
                .input(frameGt, Tritanium, 16)
                .fluidInputs(Lutetium.getFluid(L * 20))
                .fluidInputs(KaptonK.getFluid(8000))
                .fluidInputs(Naquadria.getFluid(L * 10))
                .output(PUMP_MODULE[0])
                .EUt(VA[UV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(INFINITY_FLUID_DRILL_RIG.getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[ZPM]))
                .buildAndRegister();

        // Space Mining Module MK2
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(PUMP_MODULE[0])
                .input(CIRCUIT_GOOD_IV, 8)
                .input(ELECTRIC_PUMP_UHV, 8)
                .input(FIELD_GENERATOR_UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(SENSOR_UHV, 16)
                .input(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate, 32)
                .input(frameGt, Vibranium, 16)
                .fluidInputs(Lutetium.getFluid(L * 40))
                .fluidInputs(KaptonK.getFluid(16000))
                .fluidInputs(Adamantium.getFluid(L * 20))
                .output(PUMP_MODULE[1])
                .EUt(VA[UHV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(PUMP_MODULE[0].getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .buildAndRegister();

        // Space Mining Module MK3
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(PUMP_MODULE[1])
                .input(CIRCUIT_GOOD_V, 8)
                .input(ELECTRIC_PUMP_UEV, 8)
                .input(FIELD_GENERATOR_UEV, 4)
                .input(circuit, MarkerMaterials.Tier.UEV, 16)
                .input(SENSOR_UEV, 16)
                .input(wireGtQuadruple, PedotTMA, 32)
                .input(frameGt, Neutronium, 16)
                .fluidInputs(Lutetium.getFluid(L * 80))
                .fluidInputs(KaptonK.getFluid(64000))
                .fluidInputs(Infinity.getFluid(L * 40))
                .output(PUMP_MODULE[2])
                .EUt(VA[UEV])
                .duration(2 * MINUTE)
                .stationResearch(r -> r
                        .researchStack(PUMP_MODULE[1].getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .buildAndRegister();
    }
}

