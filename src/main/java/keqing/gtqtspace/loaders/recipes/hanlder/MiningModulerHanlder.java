package keqing.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtspace.common.items.GTQTSMetaItems;

import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.CONVEYOR_MODULE_UV;
import static gregtech.common.items.MetaItems.ELECTRIC_MOTOR_UV;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Infinity;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.MINING_DRONE_LV;

public class MiningModulerHanlder {

    public static final MetaItem<?>.MetaValueItem[] MINING_DRONES = {
            GTQTSMetaItems.MINING_DRONE_LV,
            GTQTSMetaItems.MINING_DRONE_MV,
            GTQTSMetaItems.MINING_DRONE_HV,
            GTQTSMetaItems.MINING_DRONE_EV,
            GTQTSMetaItems.MINING_DRONE_IV,
            GTQTSMetaItems.MINING_DRONE_LuV,
            GTQTSMetaItems.MINING_DRONE_ZPM,
            GTQTSMetaItems.MINING_DRONE_UV,
            GTQTSMetaItems.MINING_DRONE_UHV,
            GTQTSMetaItems.MINING_DRONE_UEV,
            GTQTSMetaItems.MINING_DRONE_UIV,
            GTQTSMetaItems.MINING_DRONE_UXV,
            GTQTSMetaItems.MINING_DRONE_OpV,
            GTQTSMetaItems.MINING_DRONE_MAX
    };
    public static Material[] toolMaterial = {
            Steel, Titanium, TungstenSteel,Naquadah,Neutronium,Infinity
    };
    public static void init() {
        //test
        //矿脉如何注册？ 每个矿脉都需要不同的无人机+钻头组件 无人机代表获取该矿脉的阶段 钻头组件用于区分 矿脉 类型
        //注册方法不同于NH的，注意辨别，并不考虑真正的使用轮询功能
        //如果你需要注册等级差不多的矿脉，但是受限于无人机等级，可以考虑使用更多的钻头材料 例如铜 铝等
        registerMingingRecipes(1, 1, 10, 10, toolMaterial[0],64, Iron,Copper,Tin,Coal);

        PUMPING_MODULE_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(100)
                .duration(100)
                .tier(1)
                .CWUt(100)
                .dim(10)
                .buildAndRegister();

        PUMPING_MODULE_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .fluidOutputs(Lava.getFluid(1000))
                .EUt(100)
                .duration(100)
                .tier(1)
                .CWUt(100)
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
    /**
     * 注册挖掘模块的配方
     *
     * @param droneTier 无人机的等级
     * @param motorTier 电梯轨道等级
     * @param distance 距离
     * @param range 范围
     * @param material 材料
     * @param oreList 矿石材料列表
     */
    public static void registerMingingRecipes(int droneTier, int motorTier, int distance, int range, Material material, int amount, Material... oreList) {
        RecipeBuilder<?> builder;

        builder = MINING_MODULE_RECIPES.recipeBuilder()
                .notConsumable(MINING_DRONES[droneTier - 1])
                .input(OrePrefix.toolHeadDrill, material)
                .input(OrePrefix.stick, material)
                .EUt(VA[6 + motorTier])
                .duration(motorTier * 400)
                .tier(motorTier)
                .CWUt(CWT[5 + motorTier])
                .minDistence(distance - range / 2)
                .maxDistence(distance + range / 2);

        for (Material ore : oreList)
            builder.output(OrePrefix.ore, ore,amount);

        builder.buildAndRegister();
    }
}
