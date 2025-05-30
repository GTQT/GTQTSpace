package keqing.gtqtspace.loaders.recipes.categories;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtspace.api.utils.GTQTSLog;
import net.minecraft.item.ItemStack;
import org.eclipse.xtext.xbase.lib.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;


public class SpaceMiningRecipes {

    /*
    public static final int STICK_INPUT_STACK_SIZE = 4;
    public static final int DRILL_HEAD_INPUT_STACK_SIZE = 4;


    public static Object2ObjectMap<Integer, List<SpaceMiningRecipePartTwo>> SPACE_MINING_RECIPES = new Object2ObjectOpenHashMap<>();

    public static Object2ObjectMap<Integer, List<ItemStack>> HASH_TO_ITEMS = new Object2ObjectOpenHashMap<>();

    public static void init() {

        Material []material={Steel,TungstenSteel,NaquadahAlloy,Neutronium,Infinity};

        //T1-T8 对着维度填就行，参数不要修改
        //按照
        addNewRecipesForDroneAndMaterial(1, material[0],
                Arrays.asList(
                        //主世界
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 0, 50, 50, 100, 100, 400, Salt,20,Graphite, 20,Diamond,20,Coal,20,Lazurite,20,Lapis,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 10, 60, 50, 100, 100, 400,  Calcite,20,Sodalite,20,CassiteriteSand,20,Cassiterite,20,Alunite,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 20, 70, 50, 100, 100, 400,  Oilsands,20,Almandine,20,Pyrolusite,20,Tantalite,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 30, 80, 50, 100, 100, 400, Redstone,20,Ruby,20,Cinnabar,20,Kyanite,20,Mica,20,Trona,20,Calcite,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 40, 90, 50, 100, 100, 400,    Pollucite,20,Chalcopyrite,20,Magnesite,20,Talc,20,Soapstone,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 50, 100, 50, 100, 100, 400,  Barite,20,GlauconiteSand,20,Bentonite,20,Lepidolite,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 60, 110, 50, 100, 100, 400, Chalcopyrite,20,Iron,20,Copper,20,Bornite,20,BrownLimonite,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 70, 120, 50, 100, 100, 400,  Chromite,20,Gold,20,Nickel,20,Pentlandite,20,Lead,20),
                        makeSpaceMiningRecipePart(VA[HV], 1200, 1, 80, 130, 50, 100, 100, 400,  Silver,20,Tin,20,VanadiumMagnetite,20,BandedIron,20,YellowLimonite,20)
                        ));


        addNewRecipesForDroneAndMaterial(2, material[0],
                Arrays.asList(
                        //地狱
                        makeSpaceMiningRecipePart(VA[EV], 1200, 1, 100, 150, 40, 80, 100, 400, RockSalt,20,Sulfur, 20,Sphalerite,20,Wulfenite,20,Molybdenum,20,Molybdenite,20),
                        makeSpaceMiningRecipePart(VA[EV], 1200, 1, 120, 170, 40, 80, 100, 400,  NetherQuartz,20,CertusQuartz,20,Stibnite,20,Tetrahedrite,20,Beryllium,20,Emerald,20),
                        //补充
                        makeSpaceMiningRecipePart(VA[EV], 1200, 1, 140, 190, 40, 80, 100, 400, GreenSapphire,20,Garnierite,20,Galena,20,Saltpeter,20,Electrotine,20),
                        makeSpaceMiningRecipePart(VA[EV], 1200, 1, 160, 210, 40, 80, 100, 400,  Diatomite,20,BlueTopaz,20,Cobaltite,20,Bauxite,20,FullersEarth,20)

                ));

        addNewRecipesForDroneAndMaterial(3, material[0],
                Arrays.asList(
                        //月球
                        makeSpaceMiningRecipePart(VA[IV], 1200, 1, 200, 250, 30, 60, 200, 400, Bastnasite,20,Monazite,20,Neodymium,20,Ilmenite,20),
                        makeSpaceMiningRecipePart(VA[IV], 1200, 1, 220, 270, 30, 60, 200, 400, Ilmenite,20,Rutile,20,Bauxite,20,Aluminium,20),
                        //末地
                        makeSpaceMiningRecipePart(VA[IV], 1200, 2, 240, 290, 30, 60, 400, 400, Scheelite, 20,Tungstate, 20,Spodumene,20,Thorium,20),
                        makeSpaceMiningRecipePart(VA[IV], 1200, 2, 260, 310, 30, 60, 400, 400, Cooperite, 20,Uraninite, 20,Pitchblende,20,Arsenic,20)
                ));

        addNewRecipesForDroneAndMaterial(4, material[0],
                Arrays.asList(
                        makeSpaceMiningRecipePart(VA[LuV], 1200, 2, 300, 350, 20, 50, 600, 400, Plutonium239, 20,Uranium235, 20,Thorium,20),
                        makeSpaceMiningRecipePart(VA[LuV], 1200, 2, 320, 370, 20, 50, 600, 400, Naquadah, 20)
                ));



        /*
        addNewRecipesForDroneAndMaterial(9, material[0],
                Arrays.asList(
                       //makeSpaceMiningRecipePart(VA[IV+4], 1200, 2, 2, 800, 250, 200, 800, 400, Nickel,20)
                ));

        addNewRecipesForDroneAndMaterial(11, material[0],
                Arrays.asList(
                        //makeSpaceMiningRecipePart(VA[IV+5], 1200, 3, 2, 1200, 300, 200, 1000, 400, Aluminium,20)
                ));

        addNewRecipesForDroneAndMaterial(13, material[0],
                Arrays.asList(
                        //makeSpaceMiningRecipePart(VA[IV+6], 1200, 3, 2, 1600, 350, 200, 1200, 400, Coal,20)
                ));

        addNewRecipesForDroneAndMaterial(14, material[0],
                Arrays.asList(
                        //makeSpaceMiningRecipePart(VA[IV+7], 1200, 3, 2, 2000, 400, 200, 1400, 400, Diamond,20)
                ));


        //无人机升级 材料升级 多方块不变
        for(int j=2;j<9;j++)
           addNewRecipesForDroneAndMaterial(j, material[0], upTierAllRecipes(j - 1, material[0], j));


        for(int j=2;j<=9;j++)
            for (int i = 1; i < material.length; i++)
                addNewRecipesForDroneAndMaterial(j-1, material[i], upTierAllRecipes(j-1, material[i - 1], i));

    }

    public static SpaceMiningRecipePartTwo makeSpaceMiningRecipePart(int EUt, int duration, int minModuleTier, int minDistance, int maxDistance, int minSize, int maxSize, int CWUt, int weight, Object... outputsAndWeights) {
        return new SpaceMiningRecipePartTwo(generateListOfPairs(outputsAndWeights), EUt, CWUt, weight, minSize, maxSize, duration, minDistance, maxDistance, minModuleTier);
    }

    public static void addNewRecipeForDroneAndMaterial(int droneTier, Material material, SpaceMiningRecipePartTwo recipe) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            SPACE_MINING_RECIPES.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), new ArrayList<>());
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).add(recipe);

        if(HASH_TO_ITEMS.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null)
            HASH_TO_ITEMS.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), Arrays.asList(droneStack, OreDictUnifier.get(OrePrefix.stick, material, STICK_INPUT_STACK_SIZE), OreDictUnifier.get(OrePrefix.toolHeadDrill, material, DRILL_HEAD_INPUT_STACK_SIZE)));
    }


    public static void addNewRecipesForDroneAndMaterial(int droneTier, Material material, List<SpaceMiningRecipePartTwo> list) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            SPACE_MINING_RECIPES.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), new ArrayList<>());
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).addAll(list);

        if(HASH_TO_ITEMS.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null)
            HASH_TO_ITEMS.put(new SpaceMiningRecipePartOne(droneStack, material).hashCode(), Arrays.asList(droneStack, OreDictUnifier.get(OrePrefix.stick, material, STICK_INPUT_STACK_SIZE), OreDictUnifier.get(OrePrefix.toolHeadDrill, material, DRILL_HEAD_INPUT_STACK_SIZE)));
    }

    public static void removeRecipeForDroneAndMaterial(int droneTier, Material material, SpaceMiningRecipePartTwo recipe) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        if(!SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).remove(recipe)) {
            throw new RuntimeException("No matching Recipe found for Drone and Material!");
        }

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).isEmpty())
            HASH_TO_ITEMS.remove(new SpaceMiningRecipePartOne(droneStack, material).hashCode());

    }

    public static void removeAllRecipesForDroneAndMaterial(int droneTier, Material material) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()).clear();

        HASH_TO_ITEMS.remove(new SpaceMiningRecipePartOne(droneStack, material).hashCode());
    }

    public static List<SpaceMiningRecipePartTwo> upTierAllRecipes(int droneTier, Material material, int tier) {

        ItemStack droneStack = getDroneByTier(droneTier);

        if(SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode()) == null) {
            throw new RuntimeException("No matching Drone and Material found!");
        }

        List<SpaceMiningRecipePartTwo> recipes = SPACE_MINING_RECIPES.get(new SpaceMiningRecipePartOne(droneStack, material).hashCode());
        List<SpaceMiningRecipePartTwo> recipesUp = new ArrayList<>();

        for(SpaceMiningRecipePartTwo recipe : recipes) {
            recipesUp.add(recipe.copyAndUpTierRecipe(tier));
        }

        return recipesUp;
    }

    private static ItemStack getDroneByTier(int tier) {
        return switch (tier) {
            case (1) -> MINING_DRONE_LV.getStackForm();
            case (2) -> MINING_DRONE_MV.getStackForm();
            case (3) -> MINING_DRONE_HV.getStackForm();
            case (4) -> MINING_DRONE_EV.getStackForm();
            case (5) -> MINING_DRONE_IV.getStackForm();
            case (6) -> MINING_DRONE_LuV.getStackForm();
            case (7) -> MINING_DRONE_ZPM.getStackForm();
            case (8) -> MINING_DRONE_UV.getStackForm();
            case (9) -> MINING_DRONE_UHV.getStackForm();
            case (10) -> MINING_DRONE_UEV.getStackForm();
            case (11) -> MINING_DRONE_UIV.getStackForm();
            case (12) -> MINING_DRONE_UXV.getStackForm();
            case (13) -> MINING_DRONE_OpV.getStackForm();
            case (14) -> MINING_DRONE_MAX.getStackForm();
            default -> null;
        };
    }


    public static List<Pair<Material, Integer>> generateListOfPairs(Object... objects) {
        List<Pair<Material, Integer>> stuff = new ArrayList<>();
        for(int i = 0; i < objects.length; i+=2) {
            if(!(objects[i] instanceof Material mat) || !(objects[i + 1] instanceof Integer weight))
                return null;

            stuff.add(new Pair<>(mat, weight));
        }

        return stuff;
    }

    public static class SpaceMiningRecipePartOne {

        private final int drone;
        private final Material rodAndDrill;

        public SpaceMiningRecipePartOne( ItemStack drone, Material rodAndDrill) {
            this.drone = drone != null ? drone.getMetadata() : 0;
            this.rodAndDrill = rodAndDrill;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SpaceMiningRecipePartOne that = (SpaceMiningRecipePartOne) o;
            return Objects.equals(drone, that.drone) && Objects.equals(rodAndDrill, that.rodAndDrill);
        }

        @Override
        public int hashCode() {
            return Objects.hash(drone, rodAndDrill);
        }
    }

    public static class SpaceMiningRecipePartTwo {
        private final List<Pair<Material, Integer>> outputs;
        private final int computation;
        private final int weight;
        private final int minSize;
        private final int maxSize;
        private final int minDistance;
        private final int maxDistance;
        private final long EUt;
        private final int duration;
        private final int minModuleTier;


        private SpaceMiningRecipePartTwo(List<Pair<Material, Integer>> outputs, long EUt, int computation, int weight, int minSize, int maxSize, int duration,  int minDistance, int maxDistance, int minModuleTier) {
            this.outputs = outputs;
            this.computation = computation;
            this.weight = weight;
            this.minSize = minSize;
            this.maxSize = maxSize;
            this.duration = duration;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.EUt = EUt;
            this.minModuleTier = minModuleTier;
        }

        public SpaceMiningRecipePartTwo copyAndUpTierRecipe(int tier) {
            return new SpaceMiningRecipePartTwo(this.outputs, this.EUt, this.computation, this.weight, this.minSize + tier * 4, this.maxSize + tier * 8, (int) (this.duration *(30.0-tier)/30), this.minDistance, this.maxDistance, this.minModuleTier);
        }


        public List<Pair<Material, Integer>> getOutputs() {
            return outputs;
        }

        public int getComputation() {
            return computation;
        }

        public int getWeight() {
            return weight;
        }


        public int getDuration() {
            return duration;
        }

        public int getMinSize() {
            return minSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public int getMinDistance() {
            return minDistance;
        }

        public int getMaxDistance() {
            return maxDistance;
        }

        public long getEUt() {
            return EUt;
        }

        public int getMinModuleTier() {
            return minModuleTier;
        }
    }

     */

}
