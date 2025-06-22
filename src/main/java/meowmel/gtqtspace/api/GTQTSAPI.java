package meowmel.gtqtspace.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.blocks.IBlockTier;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.*;
import net.minecraft.block.state.IBlockState;

public class GTQTSAPI {
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_SE_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_SP_CASING = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_MOTOR_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_PISTON_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_ROBOT_ARM_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_PUMP_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_CONVEYOR_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_EMITTER_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_SENSOR_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_FIELD_GEN_CASING = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_WIRE_COIL = new Object2ObjectOpenHashMap<>();

    public static void init() {
        MAP_SP_CASING.put(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKI),
                new WrappedIntTired(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKI, 1));
        MAP_SP_CASING.put(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKII),
                new WrappedIntTired(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKII, 2));
        MAP_SP_CASING.put(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKIII),
                new WrappedIntTired(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKIII, 3));
        MAP_SP_CASING.put(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKIV),
                new WrappedIntTired(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKIV, 4));
        MAP_SP_CASING.put(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKV),
                new WrappedIntTired(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_MKV, 5));

        MAP_SE_CASING.put(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK1),
                new WrappedIntTired(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK1, 1));
        MAP_SE_CASING.put(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK2),
                new WrappedIntTired(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK2, 2));
        MAP_SE_CASING.put(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK3),
                new WrappedIntTired(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK3, 3));
        MAP_SE_CASING.put(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK4),
                new WrappedIntTired(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK4, 4));
        MAP_SE_CASING.put(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK5),
                new WrappedIntTired(GTQTSpaceElevatorCasing.ElevatorCasingType.MOTOR_CASING_MK5, 5));

        //  Component Casings Init
        for (BlockMotorCasing.MotorCasingTier tier : BlockMotorCasing.MotorCasingTier.values()) {
            MAP_MOTOR_CASING.put(GTQTSMetaBlocks.MOTOR_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockPistonCasing.PistonCasingTier tier : BlockPistonCasing.PistonCasingTier.values()) {
            MAP_PISTON_CASING.put(GTQTSMetaBlocks.PISTON_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockRobotArmCasing.RobotArmCasingTier tier : BlockRobotArmCasing.RobotArmCasingTier.values()) {
            MAP_ROBOT_ARM_CASING.put(GTQTSMetaBlocks.ROBOT_ARM_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockPumpCasing.PumpCasingTier tier : BlockPumpCasing.PumpCasingTier.values()) {
            MAP_PUMP_CASING.put(GTQTSMetaBlocks.PUMP_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockConveyorCasing.ConveyorCasingTier tier : BlockConveyorCasing.ConveyorCasingTier.values()) {
            MAP_CONVEYOR_CASING.put(GTQTSMetaBlocks.CONVEYOR_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockEmitterCasing.EmitterCasingTier tier : BlockEmitterCasing.EmitterCasingTier.values()) {
            MAP_EMITTER_CASING.put(GTQTSMetaBlocks.EMITTER_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockSensorCasing.SensorCasingTier tier : BlockSensorCasing.SensorCasingTier.values()) {
            MAP_SENSOR_CASING.put(GTQTSMetaBlocks.SENSOR_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockFieldGenCasing.FieldGenCasingTier tier : BlockFieldGenCasing.FieldGenCasingTier.values()) {
            MAP_FIELD_GEN_CASING.put(GTQTSMetaBlocks.FIELD_GEN_CASING.getState(tier),
                    new WrappedIntTired(tier, tier.ordinal() + 1));
        }

        for (BlockWireCoil.WireCoilType type : BlockWireCoil.WireCoilType.values()) {
            MAP_WIRE_COIL.put(GTQTSMetaBlocks.WIRE_COIL.getState(type),
                    new WrappedIntTired(type, type.ordinal() + 1));
        }
    }
}
