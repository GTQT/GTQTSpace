package meowmel.gtqtspace.api.utils;


import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static net.minecraft.util.EnumFacing.*;

public class GTQTSUtil {

	public static <T> T getOrDefault(BooleanSupplier canGet, Supplier<T> getter, T defaultValue) {
		return canGet.getAsBoolean() ? getter.get() : defaultValue;
	}


	public static EnumFacing getFacingFromNeighbor(BlockPos pos, BlockPos neighbor) {
		BlockPos rel = neighbor.subtract(pos);
		if (rel.getX() == 1) {
			return EAST;
		}
		if (rel.getX() == -1) {
			return WEST;
		}
		if (rel.getY() == 1) {
			return UP;
		}
		if (rel.getY() == -1) {
			return DOWN;
		}
		if (rel.getZ() == 1) {
			return SOUTH;
		}
		return NORTH;
	}

	public static String translate(String key)
	{
		String result = I18n.translateToLocal(key);
		int comment = result.indexOf('#');
		String ret = (comment > 0) ? result.substring(0, comment).trim() : result;
		for (int i = 0; i < key.length(); ++i)
		{
			Character c = key.charAt(i);
			if (Character.isUpperCase(c))
			{
				System.err.println(ret);
			}
		}
		return ret;
	}

	public static final Material[] tierList = { MarkerMaterials.Tier.ULV, MarkerMaterials.Tier.LV, MarkerMaterials.Tier.MV, MarkerMaterials.Tier.HV, MarkerMaterials.Tier.EV, MarkerMaterials.Tier.IV, MarkerMaterials.Tier.LuV, MarkerMaterials.Tier.ZPM, MarkerMaterials.Tier.UV, MarkerMaterials.Tier.UHV, MarkerMaterials.Tier.UEV, MarkerMaterials.Tier.UIV, MarkerMaterials.Tier.UXV, MarkerMaterials.Tier.OpV, MarkerMaterials.Tier.MAX };

	public static ItemStack[] motorCasings = {
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.LV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.MV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.HV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.EV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.IV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.LuV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.ZPM),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.UV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.UHV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.UEV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.UIV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.UXV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.OpV),
			GTQTSMetaBlocks.MOTOR_CASING.getItemVariant(BlockMotorCasing.MotorCasingTier.MAX)
	};

	public static ItemStack[] pistonCasings = {
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.LV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.MV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.HV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.EV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.IV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.LuV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.ZPM),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.UV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.UHV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.UEV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.UIV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.UXV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.OpV),
			GTQTSMetaBlocks.PISTON_CASING.getItemVariant(BlockPistonCasing.PistonCasingTier.MAX)
	};

	public static ItemStack[] robotArmCasings = {
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.LV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.MV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.HV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.EV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.IV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.LuV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.ZPM),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.UV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.UHV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.UEV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.UIV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.UXV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.OpV),
			GTQTSMetaBlocks.ROBOT_ARM_CASING.getItemVariant(BlockRobotArmCasing.RobotArmCasingTier.MAX)
	};

	public static ItemStack[] pumpCasings = {
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.LV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.MV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.HV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.EV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.IV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.LuV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.ZPM),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.UV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.UHV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.UEV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.UIV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.UXV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.OpV),
			GTQTSMetaBlocks.PUMP_CASING.getItemVariant(BlockPumpCasing.PumpCasingTier.MAX)
	};

	public static ItemStack[] conveyorCasings = {
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.LV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.MV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.HV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.EV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.IV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.LuV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.ZPM),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.UV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.UHV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.UEV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.UIV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.UXV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.OpV),
			GTQTSMetaBlocks.CONVEYOR_CASING.getItemVariant(BlockConveyorCasing.ConveyorCasingTier.MAX)
	};

	public static ItemStack[] emitterCasings = {
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.LV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.MV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.HV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.EV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.IV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.LuV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.ZPM),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.UV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.UHV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.UEV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.UIV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.UXV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.OpV),
			GTQTSMetaBlocks.EMITTER_CASING.getItemVariant(BlockEmitterCasing.EmitterCasingTier.MAX)
	};

	public static ItemStack[] sensorCasings = {
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.LV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.MV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.HV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.EV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.IV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.LuV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.ZPM),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.UV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.UHV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.UEV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.UIV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.UXV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.OpV),
			GTQTSMetaBlocks.SENSOR_CASING.getItemVariant(BlockSensorCasing.SensorCasingTier.MAX)
	};

	public static ItemStack[] fieldGenCasings = {
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.LV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.MV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.HV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.EV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.IV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.LuV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.ZPM),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.UV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.UHV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.UEV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.UIV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.UXV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.OpV),
			GTQTSMetaBlocks.FIELD_GEN_CASING.getItemVariant(BlockFieldGenCasing.FieldGenCasingTier.MAX)
	};

}