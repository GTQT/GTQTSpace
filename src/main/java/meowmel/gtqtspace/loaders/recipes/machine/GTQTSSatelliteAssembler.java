package meowmel.gtqtspace.loaders.recipes.machine;

import gregtech.api.unification.material.MarkerMaterials;
import net.minecraft.item.ItemStack;

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
import static zmaster587.advancedRocketry.api.AdvancedRocketryItems.itemPlanetIdChip;

public class GTQTSSatelliteAssembler {
    public static void init() {
        //导航计算机



        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(itemPlanetIdChip)
                .input(circuit,MarkerMaterials.Tier.EV , 16)
                .input(EMITTER_HV, 8)
                .input(SENSOR_HV, 8)
                .input(frameGt, Titanium, 1)
                .input(screw, NanometerBariumTitanate, 6)
                .input(plate, Nitinol, 6)
                .input(wireFine, Aluminium, 16)
                .fluidInputs(Lead.getFluid(L * 8))
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 16))
                .outputs(COMPUTERTIER1.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(new ItemStack(itemPlanetIdChip))
                        .duration(1200)
                        .EUt(VA[HV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(COMPUTERTIER1)
                .input(circuit,MarkerMaterials.Tier.IV , 16)
                .input(EMITTER_EV, 8)
                .input(SENSOR_EV, 8)
                .input(frameGt, TungstenSteel, 1)
                .input(screw, NanometerBariumTitanate, 12)
                .input(wireFine, Platinum, 16)
                .fluidInputs(Lead.getFluid(L * 8))
                .fluidInputs(Polyethylene.getFluid(L * 16))
                .outputs(COMPUTERTIER2.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(COMPUTERTIER1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(COMPUTERTIER2)
                .input(circuit,MarkerMaterials.Tier.LuV , 16)
                .input(EMITTER_LuV, 8)
                .input(SENSOR_LuV, 8)
                .input(frameGt, NiobiumTitanium, 1)
                .input(screw, NanometerBariumTitanate, 24)
                .input(wireFine, Samarium, 16)
                .fluidInputs(Lead.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .outputs(COMPUTERTIER3.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(COMPUTERTIER2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(COMPUTERTIER3)
                .input(circuit,MarkerMaterials.Tier.ZPM , 16)
                .input(EMITTER_ZPM, 8)
                .input(SENSOR_ZPM, 8)
                .input(frameGt, Naquadah, 1)
                .input(screw, NanometerBariumTitanate, 36)
                .input(wireFine, VanadiumGallium, 16)
                .fluidInputs(Lead.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .outputs(COMPUTERTIER4.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(COMPUTERTIER3.getStackForm())
                        .duration(1200)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[ZPM]).buildAndRegister();
    }
}
