package meowmel.gtqtspace.api.pattern;

import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import meowmel.gtqtspace.api.blocks.IElevatorMotorTier;
import net.minecraft.block.state.IBlockState;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Supplier;

import static meowmel.gtqtspace.api.GTQTSValue.ELEVATOR_MOTORS;

public class TraceabilityPredicates {
    public static TraceabilityPredicate elevatorMotors() {return ELEVATOR_MOTOR_PREDICATE.get(); }

    private static final Supplier<TraceabilityPredicate> ELEVATOR_MOTOR_PREDICATE =
            () -> new TraceabilityPredicate(blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ELEVATOR_MOTORS.containsKey(blockState)) {
                    IElevatorMotorTier tier = ELEVATOR_MOTORS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("ElevatorMotorTier", tier);
                    if (!casing.equals(tier)) {
                        blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.elevator_motor_tier"));
                        return false;
                    }
                    blockWorldState.getMatchContext().getOrPut("VBlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
                return false;
            }, () -> ELEVATOR_MOTORS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                    .addTooltips("gtqtspace.multiblock.pattern.error.elevator_motor_tier");

}
