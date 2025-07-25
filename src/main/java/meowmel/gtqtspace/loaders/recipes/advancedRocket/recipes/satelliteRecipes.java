package meowmel.gtqtspace.loaders.recipes.advancedRocket.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.circuit;
import static gregtech.api.unification.ore.OrePrefix.wireFine;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.SOLAR_PLATE_MKI;
import static zmaster587.advancedRocketry.api.AdvancedRocketryBlocks.*;
import static zmaster587.advancedRocketry.api.AdvancedRocketryItems.*;
import static zmaster587.libVulpes.api.LibVulpesBlocks.*;
import static zmaster587.libVulpes.api.LibVulpesItems.itemBattery;

public class satelliteRecipes {
    public static void init() {
        //卫星组装机配方
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ASSEMBLER[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockStructureBlock, 4)
                .input(MetaItems.ROBOT_ARM_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(blockSatelliteBuilder)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();
        //太空站组装机
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ASSEMBLER[4])
                .input(MetaItems.COVER_SCREEN)
                .input(blockStructureBlock, 4)
                .input(MetaItems.CONVEYOR_MODULE_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(blockStationBuilder)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();
        //卫星仓
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock)
                .input(MetaItems.COVER_SCREEN)
                .input(MetaItems.SENSOR_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(blockFuelingStation, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //卫星终端
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock)
                .input(MetaItems.EMITTER_EV, 4)
                .input(MetaItems.SENSOR_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(blockSatelliteControlCenter, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();
        //火箭监测站
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(blockStructureBlock)
                .input(MetaItems.FIELD_GENERATOR_EV, 4)
                .input(MetaItems.SENSOR_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(blockMonitoringStation, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //item.circuitIC.0.name=基础电路
        //item.circuitIC.1.name=追踪电路
        //item.circuitIC.2.name=高级电路
        //item.circuitIC.3.name=控制电路板
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 0)
                .input(MetaTileEntities.ENERGY_INPUT_HATCH[2])
                .output(blockForgeInputPlug)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 0)
                .input(MetaTileEntities.ENERGY_OUTPUT_HATCH[2])
                .output(blockForgeOutputPlug)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();
        //item.circuitIC.4.name=物品IO电路板
        //blockHatch
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 4)
                .input(MetaTileEntities.ITEM_IMPORT_BUS[2])
                .output(blockHatch, 1, 0)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 4)
                .input(MetaTileEntities.ITEM_EXPORT_BUS[2])
                .output(blockHatch, 1, 1)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();
        //item.circuitIC.5.name=流体IO电路板
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 5)
                .input(MetaTileEntities.FLUID_IMPORT_HATCH[2])
                .output(blockHatch, 1, 2)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(itemIC, 1, 5)
                .input(MetaTileEntities.FLUID_EXPORT_HATCH[2])
                .output(blockHatch, 1, 3)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();
        //基础电路
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, StainlessSteel)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .input(OrePrefix.wireFine, Gold, 16)
                .input(OrePrefix.screw, Tin, 4)
                .output(itemIC, 1, 0)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, Titanium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(OrePrefix.wireFine, Gold, 16)
                .input(OrePrefix.screw, Copper, 4)
                .output(itemIC, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, Titanium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(OrePrefix.wireFine, Platinum, 16)
                .input(OrePrefix.screw, Copper, 4)
                .output(itemIC, 1, 2)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(MetaItems.RANDOM_ACCESS_MEMORY, 4)
                .input(OrePrefix.wireFine, Platinum, 8)
                .input(OrePrefix.screw, Copper, 2)
                .output(itemIC, 1, 3)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(MetaItems.NANO_CENTRAL_PROCESSING_UNIT, 1)
                .input(OrePrefix.wireFine, Platinum, 8)
                .input(OrePrefix.screw, Copper, 2)
                .output(itemIC, 1, 4)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(MetaItems.NANO_CENTRAL_PROCESSING_UNIT, 1)
                .input(OrePrefix.wireFine, Platinum, 8)
                .input(OrePrefix.screw, Tin, 2)
                .output(itemIC, 1, 5)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();
        //卫星组件
        //透镜
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(OrePrefix.lens, Diamond)
                .output(itemLens, 1, 0)
                .fluidInputs(DistilledWater.getFluid(1000))
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //光学传感器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemLens)
                .input(OrePrefix.plate, Titanium, 2)
                .input(OrePrefix.wireFine, Gold, 4)
                .input(MetaItems.SENSOR_EV, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 0)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //成分传感器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(OrePrefix.plate, Titanium, 2)
                .input(OrePrefix.wireFine, Gold, 4)
                .input(itemIC, 1, 1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //质量检测器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(OrePrefix.plate, Titanium, 2)
                .input(OrePrefix.wireFine, Gold, 4)
                .input(itemIC, 1, 0)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 2)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //微波传输器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(MetaItems.SENSOR_EV, 2)
                .input(OrePrefix.wireFine, Platinum, 4)
                .input(itemIC, 1, 1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 3)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //矿物测绘
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(MetaItems.PROSPECTOR_EV, 2)
                .input(OrePrefix.wireFine, Platinum, 4)
                .input(itemIC, 1, 1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 4)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //生物群戏修改
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(MetaItems.FIELD_GENERATOR_EV, 2)
                .input(OrePrefix.wireFine, Platinum, 4)
                .input(itemIC, 1, 1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 5)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //天气控制器
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePrimaryFunction)
                .input(MetaItems.ELECTRIC_PUMP_EV, 2)
                .input(OrePrefix.wireFine, Platinum, 4)
                .input(itemIC, 1, 1)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .circuitMeta(4)
                .output(itemSatellitePrimaryFunction, 1, 6)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //基础太阳能板
        //光伏
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(SOLAR_PLATE_MKI)
                .input(OrePrefix.wireFine, Platinum)
                .input(OrePrefix.stick, StainlessSteel, 2)
                .output(itemSatellitePowerSource, 1, 0)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(itemSatellitePowerSource, 4, 0)
                .input(OrePrefix.frameGt, Titanium)
                .output(itemSatellitePowerSource, 1, 1)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        //电池
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, StainlessSteel)
                .input(OrePrefix.battery, MarkerMaterials.Tier.HV)
                .input(OrePrefix.wireFine, Platinum, 4)
                .output(itemBattery, 1, 0)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, StainlessSteel)
                .input(itemBattery, 1, 0)
                .output(itemBattery, 1, 1)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //数据存储单元
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(NOR_MEMORY_CHIP, 16)
                .input(NAND_MEMORY_CHIP, 32)
                .input(wireFine, Gold, 32)
                .output(itemDataUnit, 1)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();
    }
}
