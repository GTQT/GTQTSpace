package keqing.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.MINING_DRONE_LV;

public class MiningModulerHanlder {
    public static void init() {
        //test
        MINING_MODULE_RECIPES.recipeBuilder()
                .notConsumable(MINING_DRONE_LV)
                .input(OrePrefix.toolHeadDrill,Steel)
                .input(OrePrefix.stick,Steel)
                .output(OrePrefix.ore,Iron)
                .EUt(100)
                .duration(100)
                .tier(1)
                .CWUt(100)
                .minDistence(100)
                .maxDistence(200)
                .buildAndRegister();

        PUMPING_MODULE_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(100)
                .duration(100)
                .tier(1)
                .dim(10)
                .buildAndRegister();

        ASSEMBLER_MODULE_RECIPES.recipeBuilder()
                .input(ELECTRIC_MOTOR_UV, 2)
                .input(plate, Tritanium, 2)
                .input(ring, Tritanium, 4)
                .input(round, Tritanium, 16)
                .input(screw, Tritanium, 4)
                .input(cableGtSingle, YttriumBariumCuprate, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(StyreneButadieneRubber.getFluid(L * 24))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .output(CONVEYOR_MODULE_UV)
                .tier(1)
                .CWUt(100)
                .duration(600).EUt(100000).buildAndRegister();
    }
}
