package meowmel.gtqtspace.world.worldgen;

import meowmel.gtqtspace.world.dims.*;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GTQTSDimensionManager {

    public static int SPACE_STATION_ID;
    public static int MOON_ID;
    public static int MARS_ID;
    public static int VENUS_ID;
    public static int ASTEROIDS_ID;
    public static void init() {
        SPACE_STATION_ID=50;
        DimensionManager.registerDimension(SPACE_STATION_ID, DimensionType.register("SpaceStationWorld", "_void", SPACE_STATION_ID, SpaceStationdProvider.class, false));
    }
}
