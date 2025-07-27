package meowmel.gtqtspace.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.Dilithium;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.SbVOx;

public class DilithiumChain {
    public static void init()
    {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Stibnite,1)
                .input(OrePrefix.dust, GTQTMaterials.VanadiumOxide,1)
                .output(OrePrefix.dust,SbVOx)
                .blastFurnaceTemp(3600)
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .input(OrePrefix.dust,SbVOx)
                .fluidInputs(Materials.Argon.getFluid(1000))
                .output(OrePrefix.dust,Dilithium)
                .cleanroom(CleanroomType.CLEANROOM)
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();
    }
}
