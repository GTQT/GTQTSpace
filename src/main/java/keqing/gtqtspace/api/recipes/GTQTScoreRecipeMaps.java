package keqing.gtqtspace.api.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMap;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtspace.api.recipes.builder.SEComputationRecipeBuilder;
import keqing.gtqtspace.api.recipes.builder.StarComputationRecipeBuilder;
import keqing.gtqtspace.api.recipes.machine.RecipeMapSE;
import keqing.gtqtspace.api.recipes.machine.RecipeMapSF;

public class GTQTScoreRecipeMaps {
    public static final RecipeMap<SEComputationRecipeBuilder> SPACE_ELEVATOR_DRILLING_MODULE;
    public static final RecipeMap<SEComputationRecipeBuilder> SPACE_ELEVATOR_MINING_MODULE;
    public static final RecipeMap<StarComputationRecipeBuilder> STAR_SURVEY;
    private GTQTScoreRecipeMaps() {}
    static {
        SPACE_ELEVATOR_MINING_MODULE = new RecipeMapSE<>("space_elevator_mining_module", 4, 16,  2, 0, new SEComputationRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

        SPACE_ELEVATOR_DRILLING_MODULE = new RecipeMapSF<>("space_elevator_drilling_module", 4, 0,  2, 16, new SEComputationRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

        STAR_SURVEY = new RecipeMap<>("star_suvery", 1, 1, 0, 0, new StarComputationRecipeBuilder(), false)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.COMPUTATION);
    }
}
