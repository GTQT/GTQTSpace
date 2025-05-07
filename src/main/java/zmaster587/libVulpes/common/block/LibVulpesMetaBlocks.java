package zmaster587.libVulpes.common.block;

import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zmaster587.libVulpes.common.block.blocks.*;

import static zmaster587.libVulpes.LibVulpes.tabMultiblock;

public class LibVulpesMetaBlocks {

    public static BlockMotorCasing MOTOR_CASING;
    public static BlockPistonCasing PISTON_CASING;
    public static BlockPumpCasing PUMP_CASING;
    public static BlockConveyorCasing CONVEYOR_CASING;
    public static BlockRobotArmCasing ROBOT_ARM_CASING;
    public static BlockEmitterCasing EMITTER_CASING;
    public static BlockSensorCasing SENSOR_CASING;
    public static BlockFieldGenCasing FIELD_GEN_CASING;

    private LibVulpesMetaBlocks() {}

    public static void init() {
        MOTOR_CASING = new BlockMotorCasing();
        MOTOR_CASING.setRegistryName("motor_casing");
        MOTOR_CASING.setCreativeTab(tabMultiblock);

        PISTON_CASING = new BlockPistonCasing();
        PISTON_CASING.setRegistryName("piston_casing");
        PISTON_CASING.setCreativeTab(tabMultiblock);

        PUMP_CASING = new BlockPumpCasing();
        PUMP_CASING.setRegistryName("pump_casing");
        PUMP_CASING.setCreativeTab(tabMultiblock);

        CONVEYOR_CASING = new BlockConveyorCasing();
        CONVEYOR_CASING.setRegistryName("conveyor_casing");
        CONVEYOR_CASING.setCreativeTab(tabMultiblock);

        ROBOT_ARM_CASING = new BlockRobotArmCasing();
        ROBOT_ARM_CASING.setRegistryName("robot_arm_casing");
        ROBOT_ARM_CASING.setCreativeTab(tabMultiblock);

        EMITTER_CASING = new BlockEmitterCasing();
        EMITTER_CASING.setRegistryName("emitter_casing");
        EMITTER_CASING.setCreativeTab(tabMultiblock);

        SENSOR_CASING = new BlockSensorCasing();
        SENSOR_CASING.setRegistryName("sensor_casing");
        SENSOR_CASING.setCreativeTab(tabMultiblock);

        FIELD_GEN_CASING = new BlockFieldGenCasing();
        FIELD_GEN_CASING.setRegistryName("field_gen_casing");
        FIELD_GEN_CASING.setCreativeTab(tabMultiblock);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(MOTOR_CASING);
        registerItemModel(PISTON_CASING);
        registerItemModel(PUMP_CASING);
        registerItemModel(CONVEYOR_CASING);
        registerItemModel(ROBOT_ARM_CASING);
        registerItemModel(EMITTER_CASING);
        registerItemModel(SENSOR_CASING);
        registerItemModel(FIELD_GEN_CASING);
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
