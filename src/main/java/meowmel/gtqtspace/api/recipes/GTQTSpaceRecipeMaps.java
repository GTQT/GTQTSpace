package meowmel.gtqtspace.api.recipes;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMapBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;
import meowmel.gtqtspace.api.recipes.builder.AssemblerModulersBuilder;
import meowmel.gtqtspace.api.recipes.builder.MiningModulersBuilder;
import meowmel.gtqtspace.api.recipes.builder.PumpingModulersBuilder;
import meowmel.gtqtspace.api.recipes.ui.SpaceAssemblerUI;
import meowmel.gtqtspace.api.recipes.ui.SpaceMiningUI;

public class GTQTSpaceRecipeMaps {


    public static final RecipeMap<MiningModulersBuilder> MINING_MODULE_RECIPES;
    public static final RecipeMap<PumpingModulersBuilder> PUMPING_MODULE_RECIPES;
    public static final RecipeMap<AssemblerModulersBuilder> ASSEMBLER_MODULE_RECIPES;
    //精密部件组装 Precision component assembly
    public static final RecipeMap<SimpleRecipeBuilder> PRECISION_COMPONENT_ASSEMBLY;


    //public static final RecipeMap<SimpleRecipeBuilder> ASSEMBLER_MODULE_RECIPES;

    static {
        PRECISION_COMPONENT_ASSEMBLY = new RecipeMapBuilder<>("precision_component_assembly",
                new SimpleRecipeBuilder())
                .itemInputs(9)
                .fluidInputs(3)
                .itemOutputs(3)
                .sound(GTSoundEvents.ASSEMBLER)
                .build();

        MINING_MODULE_RECIPES = new RecipeMapBuilder<>("mining_module",
                new MiningModulersBuilder())
                .itemInputs(5)
                .itemOutputs(16)
                .fluidInputs(2)
                .sound(GTSoundEvents.MINER)
                .ui(SpaceMiningUI::new)
                .build();

        //泵无需UI
        PUMPING_MODULE_RECIPES = new RecipeMapBuilder<>("pumping_module",
                new PumpingModulersBuilder())
                .itemInputs(1)
                .fluidOutputs(1)
                .sound(GTSoundEvents.CHEMICAL_REACTOR)
                .build();

        //组装模式 使用装配线UI
        ASSEMBLER_MODULE_RECIPES = new RecipeMapBuilder<>("assembler_module",
                new AssemblerModulersBuilder())
                .itemInputs(16)
                .itemOutputs(1)
                .fluidInputs(4)
                .sound(GTSoundEvents.ASSEMBLER)
                .ui(SpaceAssemblerUI::new)
                .build();

        /*
        ASSEMBLER_MODULE_RECIPES = new RecipeMap<>("assembler_module", 16, 1, 0, 0, new SimpleRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

         */
    }

    private GTQTSpaceRecipeMaps() {
    }
}
