package meowmel.gtqtspace.loaders.recipes.machine;

import gregtech.api.unification.material.MarkerMaterials;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.wireGtHex;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.VACUUM_FREEZER;
import static meowmel.gtqtspace.common.items.GTQTSMetaItems.*;

public class GTQTSSatelliteAssembler {
    public static void init() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[3], 4)
                .input(circuit,MarkerMaterials.Tier.EV , 16)
                .input(EMITTER_HV, 8)
                .input(SENSOR_HV, 8)
                .input(plateDense, Titanium, 6)
                .input(frameGt, NanometerBariumTitanate, 6)
                .input(plate, Nitinol, 6)
                .input(gear, Aluminium, 6)
                .input(foil, StainlessSteel, 6)
                .fluidInputs(Polyethylene.getFluid(16000))
                .fluidInputs(Polytetrafluoroethylene.getFluid(8000))
                .fluidInputs(ReinforcedEpoxyResin.getFluid(4000))
                .fluidInputs(StyreneButadieneRubber.getFluid(4000))
                .outputs(TELESCOPE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(COMPUTERTIER1.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[4], 4)
                .input(circuit,MarkerMaterials.Tier.IV , 16)
                .input(EMITTER_EV, 8)
                .input(SENSOR_EV, 8)
                .input(plateDense, TungstenSteel, 6)
                .input(frameGt, NanometerBariumTitanate, 12)
                .input(plate, Platinum, 6)
                .input(gear, StainlessSteel, 6)
                .input(foil, Titanium, 6)
                .fluidInputs(Helium.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .fluidInputs(Zylon.getFluid(L * 2))
                .outputs(BASIC_SATELLITE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(COMPUTERTIER2.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(400).EUt(VA[IV]).buildAndRegister();
    }
}
