package meowmel.gtqtspace.loaders.recipes.hanlder;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Formaldehyde;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Infinity;
import static meowmel.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;

public class SpaceElevatorrHanlder {

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
            Steel, Titanium, TungstenSteel, NaquadahAlloy, Neutronium, Infinity
    };

    public static void init() {
        regMinig();
        regPumping();
        regAssrmbler();
    }

    private static void regAssrmbler() {

    }

    private static void regPumping() {
        //这里的维度依旧是只有象征意义
        //使用电路板编号区分流体即可（怎么可能写的满32种）

        registerPumpingRecipes(1, 1, 1, SaltWater.getFluid(1000));
        registerPumpingRecipes(1, 2, 2, Oil.getFluid(1000));
        registerPumpingRecipes(1, 3, 3, NaturalGas.getFluid(1000));
        registerPumpingRecipes(1, 4, 4, Methane.getFluid(1000));
        registerPumpingRecipes(2, 5, 5, Nitrogen.getFluid(1000));
        registerPumpingRecipes(2, 6, 6, Oxygen.getFluid(1000));
        registerPumpingRecipes(2, 7, 7, Hydrogen.getFluid(1000));
        registerPumpingRecipes(2, 8, 8, CarbonMonoxide.getFluid(1000));
        registerPumpingRecipes(2, 9, 9, Ethylene.getFluid(1000));
        registerPumpingRecipes(3, 10, 10, Propane.getFluid(1000));
        registerPumpingRecipes(3, 11, 11, Butane.getFluid(1000));
        registerPumpingRecipes(3, 12, 12, Ammonia.getFluid(1000));
        registerPumpingRecipes(3, 13, 13, Chlorobenzene.getFluid(1000));
        registerPumpingRecipes(3, 14, 14, SulfuricAcid.getFluid(1000));
        registerPumpingRecipes(3, 15, 15, NitricAcid.getFluid(1000));
        registerPumpingRecipes(3, 16, 16, Helium.getFluid(1000));
        registerPumpingRecipes(3, 17, 17, Neon.getFluid(1000));
        registerPumpingRecipes(4, 18, 18, Argon.getFluid(1000));
        registerPumpingRecipes(4, 19, 19, Krypton.getFluid(1000));
        registerPumpingRecipes(4, 20, 20, Xenon.getFluid(1000));
        registerPumpingRecipes(4, 21, 21, Radon.getFluid(1000));
        registerPumpingRecipes(4, 22, 22, Acetone.getFluid(1000));
        registerPumpingRecipes(4, 23, 23, Formaldehyde.getFluid(1000));
        registerPumpingRecipes(4, 24, 24, Ethanol.getFluid(1000));
        registerPumpingRecipes(4, 25, 25, VinylChloride.getFluid(1000));
        registerPumpingRecipes(5, 26, 26, Deuterium.getFluid(1000));
        registerPumpingRecipes(5, 27, 27, Tritium.getFluid(1000));
        registerPumpingRecipes(5, 28, 28, HydrofluoricAcid.getFluid(1000));
        registerPumpingRecipes(5, 29, 29, PhosphoricAcid.getFluid(1000));
        registerPumpingRecipes(5, 30, 30, Benzene.getFluid(1000));
    }

    private static void regMinig() {
        //矿脉如何注册？ 每个矿脉都需要不同的无人机+钻头组件 无人机代表获取该矿脉的阶段 钻头组件用于区分 矿脉 类型
        //注册方法不同于NH的，注意辨别，并不考虑真正的使用轮询功能
        //如果你需要注册等级差不多的矿脉，但是受限于无人机等级，可以考虑使用更多的钻头材料 例如铜 铝等
        //关于距离与范围 同类型矿脉根据数量均分0-300这个区间 当然更多的是象征意义

        //T0
        //主世界
        registerMingingRecipes(1, 1, 25, 25, 64, 1, Salt, Graphite, Diamond, Coal, Lazurite, Lapis);
        registerMingingRecipes(1, 1, 50, 25, 64, 2, Calcite, Sodalite, CassiteriteSand, Cassiterite, Alunite);
        registerMingingRecipes(1, 1, 75, 25, 64, 3, Oilsands, Almandine, Pyrolusite, Tantalite);
        registerMingingRecipes(1, 1, 100, 25, 64, 4, Redstone, Ruby, Cinnabar, Kyanite, Mica, Trona, Calcite);
        registerMingingRecipes(1, 1, 125, 25, 64, 5, Pollucite, Chalcopyrite, Magnesite, Talc, Soapstone);
        registerMingingRecipes(1, 1, 150, 25, 64, 6, Barite, GlauconiteSand, Bentonite, Lepidolite);
        registerMingingRecipes(1, 1, 175, 25, 64, 7, Chalcopyrite, Iron, Copper, Bornite, BrownLimonite);
        registerMingingRecipes(1, 1, 200, 25, 64, 8, Chromite, Gold, Nickel, Pentlandite, Lead);
        registerMingingRecipes(1, 1, 225, 25, 64, 9, Silver, Tin, VanadiumMagnetite, BandedIron, YellowLimonite);

        //T1
        //地狱
        registerMingingRecipes(2, 1, 50, 50, 64, 1, RockSalt, Sulfur, Sphalerite, Wulfenite, Molybdenum, Molybdenite);
        registerMingingRecipes(2, 1, 100, 50, 64, 2, NetherQuartz, CertusQuartz, Stibnite, Tetrahedrite, Beryllium, Emerald);
        //补充
        registerMingingRecipes(2, 1, 150, 50, 64, 3, GreenSapphire, Garnierite, Galena, Saltpeter, Electrotine);
        registerMingingRecipes(2, 1, 200, 50, 64, 4, Diatomite, BlueTopaz, Cobaltite, Bauxite, FullersEarth);

        //T3
        //月球
        registerMingingRecipes(3, 2, 50, 50, 64, 1, Bastnasite, Monazite, Neodymium, Ilmenite);
        registerMingingRecipes(3, 2, 100, 50, 64, 2, Ilmenite, Rutile, Bauxite, Aluminium);
        //末地
        registerMingingRecipes(3, 2, 150, 50, 64, 3, Scheelite, Tungstate, Spodumene, Thorium);
        registerMingingRecipes(3, 2, 200, 50, 64, 4, Cooperite, Uraninite, Pitchblende, Arsenic);

        //T4
        //火星
        registerMingingRecipes(4, 2, 50, 50, 64, 1, Plutonium239, Uranium235, Thorium);
        registerMingingRecipes(4, 2, 100, 50, 64, 2, Naquadah);
    }

    /**
     * 注册挖掘模块的配方
     *
     * @param droneTier 无人机的等级
     * @param motorTier 电梯轨道等级
     * @param distance  距离
     * @param range     范围
     * @param oreList   矿石材料列表
     */
    public static void registerMingingRecipes(int droneTier, int motorTier, int distance, int range, int amount, int circuit, Material... oreList) {

        int base=1;
        for (int i=motorTier;i<toolMaterial.length;i++) {

        Material drillHead=toolMaterial[i-1];

            RecipeBuilder<?> builder;

            builder = MINING_MODULE_RECIPES.recipeBuilder()
                    .notConsumable(MINING_DRONES[droneTier - 1])
                    .input(OrePrefix.toolHeadDrill, drillHead)
                    .input(OrePrefix.stick, drillHead,4)
                    .circuitMeta(circuit)
                    .EUt(VA[6 + motorTier])
                    .duration(motorTier * base * 400)
                    .tier(motorTier)
                    .CWUt(CWT[5 + motorTier])
                    .minDistence(distance - range / 2)
                    .maxDistence(distance + range / 2);

            for (Material ore : oreList)
                builder.output(OrePrefix.ore, ore, amount * base);

            builder.buildAndRegister();

            base++;
        }
    }

    /**
     * 注册抽水模块的配方
     * 该方法用于在游戏模组中注册一个抽水模块的工作配方，包括了所需的电路等级、维度、材料等信息
     *
     * @param motorTier  电 机等级，影响能源消耗和操作速度
     * @param circuit    电路等级，用于配方的电路元数据
     * @param dim        维度ID，指定配方适用的维度
     * @param fluidStack 流体
     */
    public static void registerPumpingRecipes(int motorTier, int circuit, int dim, FluidStack fluidStack) {
        PUMPING_MODULE_RECIPES.recipeBuilder()
                .circuitMeta(circuit)
                .fluidOutputs(fluidStack)
                .EUt(VA[6 + motorTier])
                .duration(motorTier * 200)
                .tier(motorTier)
                .CWUt(CWT[5 + motorTier])
                .dim(dim)
                .buildAndRegister();
    }
}
