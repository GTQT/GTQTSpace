package meowmel.gtqtspace.common;

import gregtech.api.block.VariantItemBlock;
import meowmel.gtqtspace.api.unifications.ore.GTQTSStoneTypes;
import meowmel.gtqtspace.api.utils.GTQTSLog;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSDirtVariantBlock;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import meowmel.gtqtspace.loaders.recipes.GTQTSRecipes;
import meowmel.gtqtspace.loaders.recipes.GTQTSRecipesManager;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import static meowmel.gtqtspace.common.block.GTQTSMetaBlocks.*;
import static meowmel.gtqtspace.common.block.blocks.BlockMotorCasing.MotorCasingTier.LV;
import static meowmel.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing.ElevatorCasingType.BASIC_CASING;

@Mod.EventBusSubscriber(
        modid = "gtqtspace"
)
public class CommonProxy {

    public static final CreativeTabs GTQTSpace_TAB = new CreativeTabs("gtqtspace_core") {
        @Override
        public ItemStack createIcon() {
            return GTQTSMetaItems.MINING_DRONE_MAX.getStackForm();
        }
    };
    public static final CreativeTabs Ship_TAB = new CreativeTabs("gtqtspace_core_ship") {
        @Override
        public ItemStack createIcon() {
            return GTQTSMetaItems.ENGIN_MODEL_I.getStackForm();
        }
    };
    public static final CreativeTabs SpaceElevator_TAB = new CreativeTabs("gtqtspace_space_elevator") {
        @Override
        public ItemStack createIcon() {
            return spaceElevatorCasing.getItemVariant(BASIC_CASING);
        }
    };
    public static final CreativeTabs Part_TAB = new CreativeTabs("gtqtspace_part") {
        @Override
        public ItemStack createIcon() {
            return MOTOR_CASING.getItemVariant(LV);
        }
    };
    public static final CreativeTabs Planet_TAB = new CreativeTabs("gtqtspace_planet") {
        @Override
        public ItemStack createIcon() {
            return GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                    .getItemVariant(GTQTSStoneVariantBlock.StoneType.MOON_STONE);
        }
    };
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTQTSLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        /*
        在此处注册方块
        例子：
        registry.register(方块实例);
        在注册MetaBlock时用到
        */
        registry.register(spaceElevatorCasing);
        registry.register(GTQTSMetaBlocks.updateCasing);
        registry.register(GTQTSMetaBlocks.multiblockCasing);
        registry.register(GTQTSMetaBlocks.multiblockCasing1);

        registry.register(MOTOR_CASING);
        registry.register(GTQTSMetaBlocks.PISTON_CASING);
        registry.register(GTQTSMetaBlocks.PUMP_CASING);
        registry.register(GTQTSMetaBlocks.CONVEYOR_CASING);
        registry.register(GTQTSMetaBlocks.ROBOT_ARM_CASING);
        registry.register(GTQTSMetaBlocks.EMITTER_CASING);
        registry.register(GTQTSMetaBlocks.SENSOR_CASING);
        registry.register(GTQTSMetaBlocks.FIELD_GEN_CASING);
        registry.register(GTQTSMetaBlocks.WIRE_COIL);

        for (GTQTSStoneVariantBlock block : GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.values()) registry.register(block);
        for (GTQTSDirtVariantBlock block : GTQTS_DIRT_BLOCKS.values()) registry.register(block);

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTQTSLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();
        /*
        在此处注册方块的物品
        例子：
        registry.register(createItemBlock(方块实例, VariantItemBlock::new));
        在注册MetaBlock时用到
        */
        registry.register(createItemBlock(spaceElevatorCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.updateCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.multiblockCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.multiblockCasing1, VariantItemBlock::new));

        registry.register(createItemBlock(MOTOR_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.PISTON_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.PUMP_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.CONVEYOR_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.ROBOT_ARM_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.EMITTER_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.SENSOR_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.FIELD_GEN_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTSMetaBlocks.WIRE_COIL, VariantItemBlock::new));

        for (GTQTSStoneVariantBlock block : GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.values())
            registry.register(createItemBlock(block, VariantItemBlock::new));
        for (GTQTSDirtVariantBlock block : GTQTS_DIRT_BLOCKS.values())
            registry.register(createItemBlock(block, VariantItemBlock::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GTQTSLog.logger.info("Registering recipes...");
        GTQTSRecipesManager.init();
    }

    public void init() throws IOException {

    }
    public void preInit() {

    }
    public void preLoad() {
        GTQTSStoneTypes.init();
        GTQTSRecipes.registerOrePrefix();
    }
}