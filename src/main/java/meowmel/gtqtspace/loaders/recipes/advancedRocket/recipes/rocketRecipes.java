package meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes;
import gregtech.api.fluids.store.FluidStorageKeys;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import zmaster587.advancedRocketry.api.fuel.FuelRegistry;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static zmaster587.advancedRocketry.api.AdvancedRocketryBlocks.*;
import static zmaster587.libVulpes.api.LibVulpesBlocks.blockAdvStructureBlock;
import static zmaster587.libVulpes.api.LibVulpesBlocks.blockStructureBlock;
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
                .input(MetaTileEntities.ASSEMBLER[3])
                .input(OrePrefix.plate,Aluminium)
                .fluidInputs(Concrete.getFluid(144))
                .output(blockStructureTower)
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
