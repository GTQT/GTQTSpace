package meowmel.gtqtspace.api.predicate;

import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.pattern.TierTraceabilityPredicate;

import java.util.Comparator;
import java.util.function.Supplier;

import static meowmel.gtqtspace.api.GTQTSAPI.*;

public class TiredTraceabilityPredicate {

    public static Supplier<TierTraceabilityPredicate> CP_SE_CASING = () -> new TierTraceabilityPredicate(MAP_SE_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_SE_CASING.get(s)).getIntTier()), "SpaceElevator", null);

    public static Supplier<TierTraceabilityPredicate> CP_SP_CASING = () -> new TierTraceabilityPredicate(MAP_SP_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_SP_CASING.get(s)).getIntTier()), "SolarPlate", null);

    public static Supplier<TierTraceabilityPredicate> CP_WIRE_COIL = () -> new TierTraceabilityPredicate(MAP_WIRE_COIL,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_WIRE_COIL.get(s)).getIntTier()), "WireCoil", null);

    //  Motor Casing Predicate
    public static Supplier<TierTraceabilityPredicate> MOTOR_CASING = () -> new TierTraceabilityPredicate(MAP_MOTOR_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_MOTOR_CASING.get(s)).getIntTier()), "MotorCasing", null);

    //  Piston Casing Predicate
    public static Supplier<TierTraceabilityPredicate> PISTON_CASING = () -> new TierTraceabilityPredicate(MAP_PISTON_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_PISTON_CASING.get(s)).getIntTier()), "PistonCasing", null);

    //  Robot Arm Casing Predicate
    public static Supplier<TierTraceabilityPredicate> ROBOT_ARM_CASING = () -> new TierTraceabilityPredicate(MAP_ROBOT_ARM_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_ROBOT_ARM_CASING.get(s)).getIntTier()), "RobotArmCasing", null);

    //  Pump Casing Predicate
    public static Supplier<TierTraceabilityPredicate> PUMP_CASING = () -> new TierTraceabilityPredicate(MAP_PUMP_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_PUMP_CASING.get(s)).getIntTier()), "PumpCasing", null);

    //  Conveyor Casing Predicate
    public static Supplier<TierTraceabilityPredicate> CONVEYOR_CASING = () -> new TierTraceabilityPredicate(MAP_CONVEYOR_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_CONVEYOR_CASING.get(s)).getIntTier()), "ConveyorCasing", null);

    //  Emitter Casing Predicate
    public static Supplier<TierTraceabilityPredicate> EMITTER_CASING = () -> new TierTraceabilityPredicate(MAP_EMITTER_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_EMITTER_CASING.get(s)).getIntTier()), "EmitterCasing", null);

    //  Sensor Casing Predicate
    public static Supplier<TierTraceabilityPredicate> SENSOR_CASING = () -> new TierTraceabilityPredicate(MAP_SENSOR_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_SENSOR_CASING.get(s)).getIntTier()), "SensorCasing", null);

    //  Field Gen Casing Predicate
    public static Supplier<TierTraceabilityPredicate> FIELD_GEN_CASING = () -> new TierTraceabilityPredicate(MAP_FIELD_GEN_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired) MAP_FIELD_GEN_CASING.get(s)).getIntTier()), "FieldGenCasing", null);

}