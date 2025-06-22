package meowmel.gtqtspace.loaders.recipes.hanlder;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.wireGtSingle;
import static keqing.gtqtcore.api.utils.GTQTUtil.*;
import static meowmel.gtqtspace.api.utils.GTQTSUtil.*;

public class ComponentCasingsRecipes {

    public static void init() {


        for (int i = 0; i < 14; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(ring, Steel, 2)
                    .input(getMotorByTier(i + 1))
                    .outputs(motorCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(gearSmall, Steel, 2)
                    .input(getPistonByTier(i + 1))
                    .outputs(pistonCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(gear, Steel, 2)
                    .input(getRobotArmByTier(i + 1))
                    .outputs(robotArmCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(rotor, Steel, 2)
                    .input(getPumpByTier(i + 1))
                    .outputs(pumpCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(round, Steel, 2)
                    .input(getConveyorByTier(i + 1))
                    .outputs(conveyorCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(foil, Steel, 2)
                    .input(getEmitterByTier(i + 1))
                    .outputs(emitterCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(wireFine, Steel, 2)
                    .input(getSensorByTier(i + 1))
                    .outputs(sensorCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(frameGt, Steel)
                    .input(plate, Steel, 4)
                    .input(wireGtSingle, Steel, 2)
                    .input(getFieldGenByTier(i + 1))
                    .outputs(fieldGenCasings[i])
                    .EUt(VA[LV])
                    .duration(50)
                    .buildAndRegister();
        }
    }
}
