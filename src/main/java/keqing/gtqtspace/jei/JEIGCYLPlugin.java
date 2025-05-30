package keqing.gtqtspace.jei;

import keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import keqing.gtqtspace.loaders.recipes.categories.SpaceMiningRecipes;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@JEIPlugin
public class JEIGCYLPlugin implements IModPlugin {
    /*
    private IIngredientBlacklist itemBlacklist;
    private IIngredientRegistry iItemRegistry;
    public static IJeiRuntime jeiRuntime;
    public static IGuiHelper guiHelper;

    @Override
    public void registerCategories( IRecipeCategoryRegistration registry) {
        guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new SpaceMiningCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpacePumpCategory(registry.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void register(IModRegistry registry) {
        itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
        iItemRegistry = registry.getIngredientRegistry();

        String spaceMineID = MODID + ":" + "space_mining";
        List<SpaceMiningInfo> spaceMiningInfo1 = new ArrayList<>();
        List<SpaceMiningInfo> spaceMiningInfo2 = new ArrayList<>();
        List<SpaceMiningInfo> spaceMiningInfo3 = new ArrayList<>();

        for(Map.Entry<Integer, List<SpaceMiningRecipes.SpaceMiningRecipePartTwo>> entry : SPACE_MINING_RECIPES.entrySet()) {
            Integer key = entry.getKey();
            List<SpaceMiningRecipes.SpaceMiningRecipePartTwo> recipe = entry.getValue();

            List<ItemStack> inputs = HASH_TO_ITEMS.get(key);

            for(SpaceMiningRecipes.SpaceMiningRecipePartTwo recipeIterate : recipe) {
                spaceMiningInfo1.add(new SpaceMiningInfo(inputs, recipeIterate));
            }
        }

        registry.addRecipes(spaceMiningInfo1, spaceMineID);
        registry.addRecipes(spaceMiningInfo2, spaceMineID);
        registry.addRecipes(spaceMiningInfo3, spaceMineID);

        registry.addRecipeCatalyst(MINING_MODULE[0].getStackForm(), spaceMineID);
        registry.addRecipeCatalyst(MINING_MODULE[1].getStackForm(), spaceMineID);
        registry.addRecipeCatalyst(MINING_MODULE[2].getStackForm(), spaceMineID);



        List<SpacePumpInfo> spacePumpInfos = new ArrayList<>();
        for(Map.Entry<String, FluidStack> entry : GAS_SIPHON_RECIPES.entrySet()) {
            String key = entry.getKey();
            FluidStack value = entry.getValue();

            int planetID = 0;
            int fluidID = 0;

            int index = key.indexOf(',');
            char[] arr = key.toCharArray();

            StringBuilder stringBuilderPlanet = new StringBuilder();
            StringBuilder stringBuilderFluid = new StringBuilder();

            for(int i = 0; i < arr.length; i++) {
                if(i < index) {
                    stringBuilderPlanet.append(arr[i]);
                }

                if(i > index) {
                    stringBuilderFluid.append(arr[i]);
                }
            }

            planetID = Integer.parseInt(stringBuilderPlanet.toString());
            fluidID = Integer.parseInt(stringBuilderFluid.toString());

            spacePumpInfos.add(new SpacePumpInfo(getPlanetNameByID(planetID), planetID, fluidID, value));
        }


        String spacePumpID = MODID + ":" + "space_pump_fluids";
        registry.addRecipes(spacePumpInfos, spacePumpID);
        registry.addRecipeCatalyst(GTQTSMetaTileEntities.PUMP_MODULE[0].getStackForm(), spacePumpID);
        registry.addRecipeCatalyst(GTQTSMetaTileEntities.PUMP_MODULE[1].getStackForm(), spacePumpID);
        registry.addRecipeCatalyst(GTQTSMetaTileEntities.PUMP_MODULE[2].getStackForm(), spacePumpID);
    }

     */
}
