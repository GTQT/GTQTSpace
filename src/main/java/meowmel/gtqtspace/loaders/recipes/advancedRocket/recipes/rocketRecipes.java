package meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes;
import gregtech.api.fluids.store.FluidStorageKeys;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import meowmel.gtqtspace.api.unifications.ore.GTQTSOrePrefix;
import zmaster587.advancedRocketry.api.fuel.FuelRegistry;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static zmaster587.advancedRocketry.api.AdvancedRocketryBlocks.*;
import static zmaster587.advancedRocketry.api.AdvancedRocketryItems.itemPressureTank;
import static zmaster587.libVulpes.api.LibVulpesBlocks.blockAdvStructureBlock;
import static zmaster587.libVulpes.api.LibVulpesBlocks.blockStructureBlock;
import static zmaster587.libVulpes.api.LibVulpesItems.itemHoloProjector;
import static zmaster587.libVulpes.api.LibVulpesItems.itemLinker;

public class rocketRecipes {
    public static void init()
    {
        fuelRegister();
        //机器结构方块
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[3])
                .input(OrePrefix.frameGt, Materials.Steel)
                .input(OrePrefix.plate, Materials.StainlessSteel)
                .fluidInputs(Lead.getFluid(576))
                .output(blockStructureBlock)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //高级机器结构方块
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(OrePrefix.frameGt, Materials.Aluminium)
                .input(OrePrefix.plate, Materials.Titanium)
                .fluidInputs(Lead.getFluid(576))
                .output(blockAdvStructureBlock)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //火箭组装机
        //blockRocketBuilder
        //组装机配方 HV组装机+3机械臂+电路板
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ASSEMBLER[3])
                .input(MetaItems.COVER_SCREEN)
                .input(blockStructureBlock,4)
                .input(MetaItems.ROBOT_ARM_HV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(blockRocketBuilder)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //结构塔
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, Materials.StainlessSteel)
                .input(OrePrefix.plate,Aluminium)
                .fluidInputs(Concrete.getFluid(144))
                .output(blockStructureTower)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //发射台
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock,4)
                .input(OrePrefix.plate,Aluminium)
                .input(OrePrefix.frameGt,Materials.Steel)
                .fluidInputs(Concrete.getFluid(144))
                .output(blockLaunchpad)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock,4)
                .input(OrePrefix.plate,Aluminium)
                .input(OrePrefix.frameGt, StainlessSteel)
                .fluidInputs(Concrete.getFluid(144))
                .output(blockLandingPad)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //燃料加注
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CANNER[3])
                .input(OrePrefix.pipeNormalFluid,StainlessSteel)
                .input(blockStructureBlock,4)
                .input(MetaItems.ELECTRIC_PUMP_HV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(blockFuelingStation)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //连接器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate,StainlessSteel,6)
                .input(MetaItems.SENSOR_HV,1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(itemLinker)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate,StainlessSteel,6)
                .input(MULTIBLOCK_BUILDER,1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .output(itemHoloProjector)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();
        //座位
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Aluminium)
                .input(OrePrefix.plate,StainlessSteel,6)
                .input(GTQTOrePrefix.wrap,Polyethylene,8)
                .circuitMeta(1)
                .output(blockGenericSeat)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //导航计算机
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock,4)
                .input(MetaItems.COVER_SCREEN)
                .input(FIELD_GENERATOR_HV,4)
                .input(SENSOR_HV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 16)
                .output(blockGuidanceComputer)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();
        //火箭发动机

        //单推+单腿单推燃料 blockFuelTank
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.QUANTUM_TANK[2])
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .circuitMeta(3)
                .output(blockFuelTank,1)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        //blockEngine
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Aluminium)
                .input(GTQTSOrePrefix.densePlate,StainlessSteel,4)
                .input(ELECTRIC_PUMP_HV,4)
                .input(MetaTileEntities.COMBUSTION_GENERATOR[2],4)
                .circuitMeta(2)
                .output(blockEngine)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //blockAdvEngine
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,Aluminium)
                .input(GTQTSOrePrefix.densePlate,Titanium,4)
                .input(ELECTRIC_PUMP_EV,4)
                .input(MetaTileEntities.COMBUSTION_GENERATOR[3],4)
                .circuitMeta(2)
                .output(blockAdvEngine)
                .EUt(VA[EV])
                .duration(100)
                .buildAndRegister();

        //双组元 blockBipropellantFuelTank
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.QUANTUM_TANK[3])
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(OrePrefix.pipeNormalFluid,Materials.StainlessSteel)
                .circuitMeta(3)
                .output(blockBipropellantFuelTank,1)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        //氧化剂仓
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.QUANTUM_TANK[3])
                .input(OrePrefix.plate,Materials.Steel,4)
                .input(ELECTRIC_PUMP_EV,2)
                .circuitMeta(3)
                .output(blockOxidizerFuelTank,1)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        //blockBipropellantEngine
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,StainlessSteel)
                .input(GTQTSOrePrefix.densePlate,Titanium,4)
                .input(ELECTRIC_PUMP_EV,4)
                .input(MetaTileEntities.COMBUSTION_GENERATOR[3],4)
                .circuitMeta(2)
                .output(blockBipropellantEngine)
                .EUt(VA[EV])
                .duration(100)
                .buildAndRegister();

        //blockAdvBipropellantEngine
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt,StainlessSteel)
                .input(GTQTSOrePrefix.densePlate,TungstenSteel,4)
                .input(ELECTRIC_PUMP_IV,4)
                .input(MetaTileEntities.COMBUSTION_GENERATOR[4],4)
                .circuitMeta(2)
                .output(blockAdvBipropellantEngine)
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(ELECTRIC_PISTON_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockOrientationController)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(FIELD_GENERATOR_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockGravityController)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(SENSOR_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockAltitudeController)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(ELECTRIC_MOTOR_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockPlanetSelector)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(CONVEYOR_MODULE_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockPlanetHoloSelector)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();
        //跃迁
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockAdvStructureBlock,4)
                .input(EMITTER_EV,4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(5)
                .output(blockWarpShipMonitor)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.WORLD_ACCELERATOR[4],8)
                .input(blockAdvStructureBlock,4)
                .input(FIELD_GENERATOR_EV,16)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 16)
                .circuitMeta(5)
                .output(blockWarpCore)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();
    }

    private static void fuelRegister() {
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, Materials.RocketFuel.getFluid(), 1.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, GTQTMaterials.RP1RocketFuel.getFluid(), 1.5f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, GTQTMaterials.DenseHydrazineMixtureFuel.getFluid(), 2.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, GTQTMaterials.MethylhydrazineNitrateRocketFuel.getFluid(), 2.5f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, GTQTMaterials.HydrazineFluorideFuel.getFluid(), 3.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_MONOPROPELLANT, GTQTMaterials.TrihydraziniumGel.getFluid(), 3.5f);

        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, Materials.RocketFuel.getFluid(), 1.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, GTQTMaterials.RP1RocketFuel.getFluid(), 2.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, GTQTMaterials.DenseHydrazineMixtureFuel.getFluid(), 3.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, GTQTMaterials.MethylhydrazineNitrateRocketFuel.getFluid(), 4.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, GTQTMaterials.HydrazineFluorideFuel.getFluid(), 5.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_BIPROPELLANT, GTQTMaterials.TrihydraziniumGel.getFluid(), 6.0f);

        //液体氧化剂
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_OXIDIZER, GTQTMaterials.HydrogenPeroxide.getFluid(), 1.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_OXIDIZER, SulfuricAcid.getFluid(), 1.2f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_OXIDIZER, NitricAcid.getFluid(), 1.4f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.LIQUID_OXIDIZER, HydrofluoricAcid.getFluid(), 1.6f);

        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.NUCLEAR_WORKING_FLUID, Thorium.getFluid(), 5.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.NUCLEAR_WORKING_FLUID, Uranium.getFluid(), 7.5f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.NUCLEAR_WORKING_FLUID, Plutonium.getFluid(), 10.0f);

        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.ION, Oxygen.getFluid(FluidStorageKeys.PLASMA), 10.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.ION, Hydrogen.getFluid(FluidStorageKeys.PLASMA), 15.0f);
        FuelRegistry.instance.registerFuel(FuelRegistry.FuelType.ION, Helium.getFluid(FluidStorageKeys.PLASMA), 20.0f);
    }
}
