package meowmel.gtqtspace.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.api.blocks.IElevatorMotorTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class GTQTSValue {


	public static ResourceLocation gtqtspaceId(String id) {
		return new ResourceLocation(GTQTSpace.MODID, id);
	}

	public static final Object2ObjectMap<IBlockState, IElevatorMotorTier> ELEVATOR_MOTORS = new Object2ObjectOpenHashMap<>();

	public static final int UPDATE_TIER = 114514;
	public static final int UPDATE_TIERA = 456;
	public static final int REQUIRE_DATA_UPDATE = 1919;
	public static final int PARALLEL_NUM = 123;
}
