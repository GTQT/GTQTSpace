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
                        .EUt(VA[MV]))
                .duration(800).EUt(VA[HV]).buildAndRegister();


        //test

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .input(FIELD_GENERATOR_HV, 1)
                .input(EMITTER_HV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER1)
                .NB(1)
                .CWUt(24)
                .totalCWU(240000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(FIELD_GENERATOR_EV, 2)
                .input(EMITTER_EV, 2)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER2)
                .NB(2)
                .CWUt(96)
                .totalCWU(960000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(FIELD_GENERATOR_IV, 4)
                .input(EMITTER_IV, 4)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER3)
                .NB(3)
                .CWUt(384)
                .totalCWU(3840000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(FIELD_GENERATOR_LuV, 8)
                .input(EMITTER_LuV, 8)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER4)
                .NB(4)
                .CWUt(1536)
                .totalCWU(15360000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(FIELD_GENERATOR_ZPM, 16)
                .input(EMITTER_ZPM, 16)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER5)
                .NB(5)
                .CWUt(6144)
                .totalCWU(514)
                .EUt(30)
                .duration(61440000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(FIELD_GENERATOR_UHV, 32)
                .input(EMITTER_UHV, 32)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER6)
                .NB(6)
                .CWUt(24576)
                .totalCWU(514)
                .EUt(30)
                .duration(245760000)
                .buildAndRegister();

    }
}