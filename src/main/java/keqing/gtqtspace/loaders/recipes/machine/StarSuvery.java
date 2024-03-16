package keqing.gtqtspace.loaders.recipes.machine;

import gregtech.api.GTValues;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_0;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_8;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;
import static keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.COSMIC_RAY_DETECTOR;

public class StarSuvery {
    public static void init() {

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[3],16)
                .inputs(MetaItems.FIELD_GENERATOR_HV.getStackForm(16))
                .inputs(MetaItems.SENSOR_HV.getStackForm(16))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .input(OrePrefix.pipeSmallFluid, Aluminium, 4)
                .input(OrePrefix.plate, StainlessSteel, 4)
                .fluidInputs(Polyethylene.getFluid(GTValues.L * 4))
                .output(COSMIC_RAY_DETECTOR)
                .scannerResearch(b -> b
                        .researchStack(DISK_8.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .duration(800).EUt(VA[HV]).buildAndRegister();


        //T1
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_1)
                .circuitMeta(1)
                .NB(1)
                .CWUt(24)
                .totalCWU(240000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .input(FIELD_GENERATOR_HV, 1)
                .input(EMITTER_HV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER1)
                .scannerResearch(b -> b
                        .researchStack(SDISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .duration(800).EUt(VA[HV]).buildAndRegister();



        //T2
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_2)
                .circuitMeta(2)
                .NB(2)
                .CWUt(48)
                .totalCWU(480000)
                .EUt(120)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 1)
                .input(FIELD_GENERATOR_EV, 1)
                .input(EMITTER_EV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER2)
                .scannerResearch(b -> b
                        .researchStack(SDISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[EV]).buildAndRegister();


        //T3
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_3)
                .circuitMeta(3)
                .NB(1)
                .CWUt(96)
                .totalCWU(960000)
                .EUt(1960)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV, 1)
                .input(FIELD_GENERATOR_IV, 1)
                .input(EMITTER_IV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER3)
                .scannerResearch(b -> b
                        .researchStack(SDISK_3.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(800).EUt(VA[IV]).buildAndRegister();


        //T4
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_4)
                .circuitMeta(4)
                .NB(1)
                .CWUt(128)
                .totalCWU(1280000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV, 1)
                .input(FIELD_GENERATOR_LuV, 1)
                .input(EMITTER_LuV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER4)
                .scannerResearch(b -> b
                        .researchStack(SDISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .duration(800).EUt(VA[HV]).buildAndRegister();



        //T5
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_5)
                .circuitMeta(5)
                .NB(1)
                .CWUt(256)
                .totalCWU(2560000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM, 1)
                .input(FIELD_GENERATOR_ZPM, 1)
                .input(EMITTER_ZPM, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER5)
                .stationResearch(b -> b
                        .researchStack(SDISK_5.getStackForm())
                        .CWUt(1024,10240000)
                        .EUt(VA[ZPM]))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();



        //T6
        STAR_SURVEY.recipeBuilder()
                .input(DISK_0)
                .output(SDISK_6)
                .circuitMeta(6)
                .NB(1)
                .CWUt(1024)
                .totalCWU(10240000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UV, 1)
                .input(FIELD_GENERATOR_UV, 1)
                .input(EMITTER_UV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER6)
                .stationResearch(b -> b
                        .researchStack(SDISK_6.getStackForm())
                        .CWUt(2048,20480000)
                        .EUt(VA[UV]))
                .duration(800).EUt(VA[UV]).buildAndRegister();

    }
}
