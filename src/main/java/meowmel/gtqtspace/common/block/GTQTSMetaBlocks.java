package meowmel.gtqtspace.common.block;

import gregtech.common.blocks.MetaBlocks;
import meowmel.gtqtspace.common.block.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

import static meowmel.gtqtspace.common.CommonProxy.*;

public class GTQTSMetaBlocks {
    public static final EnumMap<GTQTSStoneVariantBlock.StoneVariant, GTQTSStoneVariantBlock> GTQTS_STONE_BLOCKS = new EnumMap<>(GTQTSStoneVariantBlock.StoneVariant.class);
    public static final EnumMap<GTQTSDirtVariantBlock.DirtVariant, GTQTSDirtVariantBlock> GTQTS_DIRT_BLOCKS = new EnumMap<>(GTQTSDirtVariantBlock.DirtVariant.class);
    public static GTQTSpaceElevatorCasing spaceElevatorCasing;
    public static GTQTUpdateCasing updateCasing;
    public static GTQTSMultiblockCasing multiblockCasing;
    public static GTQTSMultiblockCasing1 multiblockCasing1;
    public static BlockMotorCasing MOTOR_CASING;
    public static BlockPistonCasing PISTON_CASING;
    public static BlockPumpCasing PUMP_CASING;
    public static BlockConveyorCasing CONVEYOR_CASING;
    public static BlockRobotArmCasing ROBOT_ARM_CASING;
    public static BlockEmitterCasing EMITTER_CASING;
    public static BlockSensorCasing SENSOR_CASING;
    public static BlockFieldGenCasing FIELD_GEN_CASING;
    public static BlockWireCoil WIRE_COIL;

    private GTQTSMetaBlocks() {
    }

    public static void init() {
        spaceElevatorCasing = new GTQTSpaceElevatorCasing();
        spaceElevatorCasing.setRegistryName("space_elevator");
        spaceElevatorCasing.setCreativeTab(SpaceElevator_TAB);

        updateCasing = new GTQTUpdateCasing();
        updateCasing.setRegistryName("update_core");
        updateCasing.setCreativeTab(GTQTSpace_TAB);

        multiblockCasing = new GTQTSMultiblockCasing();
        multiblockCasing.setRegistryName("multiblock_casing");
        multiblockCasing.setCreativeTab(GTQTSpace_TAB);

        multiblockCasing1 = new GTQTSMultiblockCasing1();
        multiblockCasing1.setRegistryName("multiblock_casing1");
        multiblockCasing1.setCreativeTab(GTQTSpace_TAB);

        MOTOR_CASING = new BlockMotorCasing();
        MOTOR_CASING.setRegistryName("motor_casing");
        MOTOR_CASING.setCreativeTab(Part_TAB);

        PISTON_CASING = new BlockPistonCasing();
        PISTON_CASING.setRegistryName("piston_casing");
        PISTON_CASING.setCreativeTab(Part_TAB);

        PUMP_CASING = new BlockPumpCasing();
        PUMP_CASING.setRegistryName("pump_casing");
        PUMP_CASING.setCreativeTab(Part_TAB);

        CONVEYOR_CASING = new BlockConveyorCasing();
        CONVEYOR_CASING.setRegistryName("conveyor_casing");
        CONVEYOR_CASING.setCreativeTab(Part_TAB);

        ROBOT_ARM_CASING = new BlockRobotArmCasing();
        ROBOT_ARM_CASING.setRegistryName("robot_arm_casing");
        ROBOT_ARM_CASING.setCreativeTab(Part_TAB);

        EMITTER_CASING = new BlockEmitterCasing();
        EMITTER_CASING.setRegistryName("emitter_casing");
        EMITTER_CASING.setCreativeTab(Part_TAB);

        SENSOR_CASING = new BlockSensorCasing();
        SENSOR_CASING.setRegistryName("sensor_casing");
        SENSOR_CASING.setCreativeTab(Part_TAB);

        FIELD_GEN_CASING = new BlockFieldGenCasing();
        FIELD_GEN_CASING.setRegistryName("field_gen_casing");
        FIELD_GEN_CASING.setCreativeTab(Part_TAB);

        WIRE_COIL = new BlockWireCoil();
        WIRE_COIL.setRegistryName("machine_wire_coil");
        WIRE_COIL.setCreativeTab(Part_TAB);

        for (GTQTSStoneVariantBlock.StoneVariant shape : GTQTSStoneVariantBlock.StoneVariant.values()) {
            GTQTS_STONE_BLOCKS.put(shape, new GTQTSStoneVariantBlock(shape));
        }
        for (GTQTSDirtVariantBlock.DirtVariant shape : GTQTSDirtVariantBlock.DirtVariant.values()) {
            GTQTS_DIRT_BLOCKS.put(shape, new GTQTSDirtVariantBlock(shape));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(spaceElevatorCasing);
        registerItemModel(updateCasing);
        registerItemModel(multiblockCasing);
        registerItemModel(multiblockCasing1);

        registerItemModel(MOTOR_CASING);
        registerItemModel(PISTON_CASING);
        registerItemModel(PUMP_CASING);
        registerItemModel(CONVEYOR_CASING);
        registerItemModel(ROBOT_ARM_CASING);
        registerItemModel(EMITTER_CASING);
        registerItemModel(SENSOR_CASING);
        registerItemModel(FIELD_GEN_CASING);
        registerItemModel(WIRE_COIL);

        for (GTQTSStoneVariantBlock block : GTQTS_STONE_BLOCKS.values())
            registerItemModel(block);
        for (GTQTSDirtVariantBlock block : GTQTS_DIRT_BLOCKS.values())
            registerItemModel(block);
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }
    }
}
