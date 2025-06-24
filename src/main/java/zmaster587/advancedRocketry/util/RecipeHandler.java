package zmaster587.advancedRocketry.util;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import zmaster587.advancedRocketry.block.BlockSmallPlatePress;
import zmaster587.advancedRocketry.tile.multiblock.machine.*;
import zmaster587.libVulpes.LibVulpes;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.api.material.MaterialRegistry;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.tile.multiblock.TileMultiblockMachine;

import java.util.*;
import java.util.Map.Entry;

public class RecipeHandler {

    private List<Class<? extends TileMultiblockMachine>> machineList = new ArrayList<>();

    public void registerMachine(Class<? extends TileMultiblockMachine> clazz) {
        if (!machineList.contains(clazz)) {
            machineList.add(clazz);
            RecipesMachine.getInstance().recipeList.put(clazz, new LinkedList<>());
        }

    }

    public void clearAllMachineRecipes() {
        for (Class<? extends TileMultiblockMachine> clazz : machineList) {
            RecipesMachine.getInstance().getRecipes(clazz).clear();
        }
    }

    public void registerAllMachineRecipes() {

        for (Class<? extends TileMultiblockMachine> clazz : machineList)
            try {
                clazz.newInstance().registerRecipes();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public void createAutoGennedRecipes(HashMap<AllowedProducts, HashSet<String>> modProducts) {

    }
}
