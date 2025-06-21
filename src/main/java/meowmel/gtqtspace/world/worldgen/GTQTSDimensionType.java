package meowmel.gtqtspace.world.worldgen;

import meowmel.gtqtspace.world.dims.*;
import net.minecraft.world.DimensionType;

public class GTQTSDimensionType {
    public static DimensionType SPACE_STATION_TYPE;
    public static void init() {
        SPACE_STATION_TYPE = DimensionType.register("SpaceStationWorld", "_void", 50, SpaceStationdProvider.class, false);
    }
}
