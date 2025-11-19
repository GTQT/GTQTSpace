package zmaster587.advancedRocketry.dimension;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Materials;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import zmaster587.advancedRocketry.AdvancedRocketry;
import zmaster587.advancedRocketry.api.*;
import zmaster587.advancedRocketry.api.dimension.IDimensionProperties;
import zmaster587.advancedRocketry.api.dimension.solar.IGalaxy;
import zmaster587.advancedRocketry.api.dimension.solar.StellarBody;
import zmaster587.advancedRocketry.api.satellite.SatelliteBase;
import zmaster587.advancedRocketry.api.stations.ISpaceObject;
import zmaster587.advancedRocketry.dimension.DimensionProperties.AtmosphereTypes;
import zmaster587.advancedRocketry.network.PacketDimInfo;
import zmaster587.advancedRocketry.network.PacketSatellitesUpdate;
import zmaster587.advancedRocketry.stations.SpaceObjectManager;
import zmaster587.advancedRocketry.util.AstronomicalBodyHelper;
import zmaster587.advancedRocketry.util.FluidGasGiantGas;
import zmaster587.advancedRocketry.util.PlanetaryTravelHelper;
import zmaster587.advancedRocketry.util.XMLPlanetLoader;
import zmaster587.advancedRocketry.world.provider.WorldProviderAsteroid;
import zmaster587.advancedRocketry.world.provider.WorldProviderPlanet;
import zmaster587.advancedRocketry.world.provider.WorldProviderSpace;
import zmaster587.libVulpes.network.PacketHandler;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static meowmel.gtqtspace.common.items.GTQTSMetaItems.*;


public class DimensionManager implements IGalaxy {

    public static final String workingPath = "advRocketry";
    public static final String tempFile = "/temp.dat";
    public static final String worldXML = "/planetDefs.xml";
    public static final DimensionType PlanetDimensionType = DimensionType.register("planet", "planet", 2, WorldProviderPlanet.class, false);
    public static final DimensionType spaceDimensionType = DimensionType.register("space", "space", 3, WorldProviderSpace.class, false);
    public static final DimensionType AsteroidDimensionType = DimensionType.register("asteroid", "asteroid", 4, WorldProviderAsteroid.class, false);
    public static final int GASGIANT_DIMID_OFFSET = 0x100; //Offset by 256
    public static Logger logger = AdvancedRocketry.logger;
    public static int dimOffset = 0;
    public static String prevBuild;
    //Stat tracking
    public static boolean hasReachedMoon;
    public static boolean hasReachedWarp;
    //Reference to the worldProvider for any dimension created through this system, normally WorldProviderPlanet, set in AdvancedRocketry.java in preinit
    public static Class<? extends WorldProvider> planetWorldProvider;
    //The default properties belonging to the overworld
    public static DimensionProperties overworldProperties;
    //the default property for any dimension created in space, normally, space over earth
    public static DimensionProperties defaultSpaceDimensionProperties;
    private static final DimensionManager instance = (DimensionManager) (AdvancedRocketryAPI.dimensionManager = new DimensionManager());
    private static long nextSatelliteId;
    public Set<Integer> knownPlanets;
    private final Random random;
    private boolean hasBeenInitialized = false;
    private final HashMap<Integer, DimensionProperties> dimensionList;
    private final HashMap<Integer, StellarBody> starList;

    public DimensionManager() {
        dimensionList = new HashMap<>();
        starList = new HashMap<>();
        StellarBody sol = new StellarBody();
        sol.setTemperature(100);
        sol.setId(0);
        sol.setName("Sol");

        overworldProperties = new DimensionProperties(0);
        overworldProperties.setAtmosphereDensityDirect(100);
        //Temperature in Kelvin, 286 is 13 Degrees C
        overworldProperties.averageTemperature = 286;
        overworldProperties.gravitationalMultiplier = 1f;
        overworldProperties.orbitalDist = 100;
        overworldProperties.skyColor = new float[]{1f, 1f, 1f};
        overworldProperties.setName("Earth");
        overworldProperties.isNativeDimension = false;
        overworldProperties.setStar(sol);

        defaultSpaceDimensionProperties = new DimensionProperties(SpaceObjectManager.WARPDIMID, false);
        defaultSpaceDimensionProperties.setAtmosphereDensityDirect(0);
        defaultSpaceDimensionProperties.averageTemperature = 0;
        defaultSpaceDimensionProperties.gravitationalMultiplier = 0.1f;
        defaultSpaceDimensionProperties.orbitalDist = 100;
        defaultSpaceDimensionProperties.skyColor = new float[]{0f, 0f, 0f};
        defaultSpaceDimensionProperties.setName("Space");
        defaultSpaceDimensionProperties.fogColor = new float[]{0f, 0f, 0f};
        //defaultSpaceDimensionProperties.setParentPlanet(overworldProperties,false);

        random = new Random(System.currentTimeMillis());
        knownPlanets = new HashSet<>();
    }

    public static DimensionManager getInstance() {
        return AdvancedRocketry.proxy.getDimensionManager(); //instance;
    }

    public static DimensionProperties getEffectiveDimId(int dimId, BlockPos pos) {

        if (dimId == ARConfiguration.getCurrentConfig().spaceDimId) {
            ISpaceObject spaceObject = SpaceObjectManager.getSpaceManager().getSpaceStationFromBlockCoords(pos);
            if (spaceObject != null) return (DimensionProperties) spaceObject.getProperties().getParentProperties();
            else return defaultSpaceDimensionProperties;
        } else return getInstance().getDimensionProperties(dimId);
    }

    public static DimensionProperties getEffectiveDimId(World world, BlockPos pos) {
        int dimId = world.provider.getDimension();

        if (dimId == ARConfiguration.getCurrentConfig().spaceDimId) {
            ISpaceObject spaceObject = SpaceObjectManager.getSpaceManager().getSpaceStationFromBlockCoords(pos);
            if (spaceObject != null) return (DimensionProperties) spaceObject.getProperties().getParentProperties();
            else return defaultSpaceDimensionProperties;
        } else return getInstance().getDimensionProperties(dimId);
    }

    /**
     * @return an Integer array of dimensions registered with this DimensionManager
     */
    public Integer[] getRegisteredDimensions() {
        Integer[] ret = new Integer[dimensionList.size()];
        return dimensionList.keySet().toArray(ret);
    }

    /**
     * @return List of dimensions registered with this manager that are currently loaded on the server/integrated server
     */
    public Integer[] getLoadedDimensions() {
        return getRegisteredDimensions();
    }

    //TODO: fix naming system

    /**
     * Increments the nextAvalible satellite ID and returns one
     *
     * @return next avalible id for satellites
     */
    public long getNextSatelliteId() {
        return nextSatelliteId++;
    }

    /**
     * @param satId long id of the satellite
     * @return a reference to the satellite object with the supplied ID
     */
    public SatelliteBase getSatellite(long satId) {

        //Hack to allow monitoring stations to properly reload after a server restart
        //Because there should never be a tile in the world where no planets have been generated load file first
        //Worst thing that can happen is there is no file and it gets genned later and the monitor does not reconnect
        if (!hasBeenInitialized && FMLCommonHandler.instance().getSide().isServer()) {
            zmaster587.advancedRocketry.dimension.DimensionManager.getInstance().loadDimensions(zmaster587.advancedRocketry.dimension.DimensionManager.workingPath);
        }

        SatelliteBase satellite = overworldProperties.getSatellite(satId);

        if (satellite != null) return satellite;

        for (int i : DimensionManager.getInstance().getLoadedDimensions()) {
            if ((satellite = DimensionManager.getInstance().getDimensionProperties(i).getSatellite(satId)) != null)
                return satellite;
        }
        return null;
    }

    /**
     * @param dimId id to register the planet with
     * @return the name for the next planet
     */
    private String getNextName(int starId, int dimId) {
        return getStar(starId).getName() + " " + dimId;
    }

    /**
     * Called every tick to tick satellites
     */
    public void tickDimensions() {
        //Tick satellites
        for (int i : DimensionManager.getInstance().getLoadedDimensions()) {
            DimensionProperties prop = DimensionManager.getInstance().getDimensionProperties(i);
            prop.tick();

            //THIS CODE NEEDS TO BE MADE MORE EFFICIENT FOR MINING ROCKETS!!!!!
            if (net.minecraftforge.common.DimensionManager.getWorld(0).getTotalWorldTime() % 100 == 0)
                PacketHandler.sendToAll(new PacketSatellitesUpdate(i, prop));


        }
    }

    public void tickDimensionsClient() {
        //Tick satellites
        for (int i : DimensionManager.getInstance().getLoadedDimensions()) {
            DimensionManager.getInstance().getDimensionProperties(i).updateOrbit();
        }
    }

    /**
     * Sets the properies supplied for the supplied dimensionID, if the dimension does not exist, it is added to the list but not registered with minecraft
     *
     * @param dimId      id to set the properties of
     * @param properties to set for that dimension
     */
    public void setDimProperties(int dimId, DimensionProperties properties) {
        dimensionList.put(dimId, properties);
    }

    /**
     * Iterates though the list of existing dimIds, and returns the closest free id greater than two
     *
     * @return next free id
     */
    public int getNextFreeDim(int startingValue) {
        for (int i = startingValue; i < 10000; i++) {
            if (!net.minecraftforge.common.DimensionManager.isDimensionRegistered(i) && !dimensionList.containsKey(i))
                return i;
        }
        return Constants.INVALID_PLANET;
    }

    public int getNextFreeStarId() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!starList.containsKey(i)) return i;
        }
        return -1;
    }

    public DimensionProperties generateRandom(int starId, int atmosphereFactor, int distanceFactor, int gravityFactor) {
        return generateRandom(starId, 100, 100, 100, atmosphereFactor, distanceFactor, gravityFactor);
    }

    public DimensionProperties generateRandom(int starId, String name, int atmosphereFactor, int distanceFactor, int gravityFactor) {
        return generateRandom(starId, name, 100, 100, 100, atmosphereFactor, distanceFactor, gravityFactor);
    }

    /**
     * Creates and registers a planet with the given properties, Xfactor is the amount of variance from the supplied base property; ie: base - (factor/2) <= generated property value <= base - (factor/2)
     *
     * @param name             name of the planet
     * @param baseAtmosphere
     * @param baseDistance
     * @param baseGravity
     * @param atmosphereFactor
     * @param distanceFactor
     * @param gravityFactor
     * @return the new dimension properties created for this planet
     */
    public DimensionProperties generateRandom(int starId, String name, int baseAtmosphere, int baseDistance, int baseGravity, int atmosphereFactor, int distanceFactor, int gravityFactor) {
        DimensionProperties properties = new DimensionProperties(getNextFreeDim(dimOffset));

        if (properties.getId() == Constants.INVALID_PLANET) return null;

        if (name.equals("")) properties.setName(getNextName(starId, properties.getId()));
        else {
            properties.setName(name);
        }
        properties.setAtmosphereDensityDirect(MathHelper.clamp(baseAtmosphere + random.nextInt(atmosphereFactor) - atmosphereFactor / 2, DimensionProperties.MIN_ATM_PRESSURE, DimensionProperties.MAX_ATM_PRESSURE));
        int newDist = properties.orbitalDist = MathHelper.clamp(baseDistance + random.nextInt(distanceFactor), DimensionProperties.MIN_DISTANCE, DimensionProperties.MAX_DISTANCE);

        properties.gravitationalMultiplier = Math.min(Math.max(0.05f, (baseGravity + random.nextInt(gravityFactor) - gravityFactor / 2f) / 100f), 1.3f);

        double minDistance;
        int walkDist = 0;

        do {
            minDistance = Double.MAX_VALUE;

            for (IDimensionProperties properties2 : getStar(starId).getPlanets()) {
                int dist = Math.abs(((DimensionProperties) properties2).orbitalDist - newDist);
                if (minDistance > dist) minDistance = dist;
            }

            newDist = properties.orbitalDist + walkDist;
            if (walkDist > -1) walkDist = -walkDist - 1;
            else walkDist = -walkDist;

        } while (minDistance < 4);

        properties.orbitalDist = newDist;
        properties.baseOrbitTheta = random.nextInt(360) * Math.PI / 180d;

        properties.orbitalPhi = (random.nextGaussian() - 0.5d) * 180;
        properties.rotationalPhi = (random.nextGaussian() - 0.5d) * 180;

        //Get Star Color
        properties.setStar(getStar(starId));

        //Linear is easier. Earth is nominal!
        properties.averageTemperature = AstronomicalBodyHelper.getAverageTemperature(properties.getStar(), properties.getSolarOrbitalDistance(), properties.getAtmosphereDensity());


        if (AtmosphereTypes.getAtmosphereTypeFromValue(properties.getAtmosphereDensity()) == AtmosphereTypes.NONE && random.nextInt() % 5 == 0 && !AdvancedRocketryFluids.fluidOxygen.isGaseous()) {
            properties.setOceanBlock(AdvancedRocketryBlocks.blockOxygenFluid.getDefaultState());
            properties.setSeaLevel(random.nextInt(6) + 72);
        }

        if (random.nextInt() % 10 == 0) {
            properties.setSeaLevel(random.nextInt(40) + 43);
        }

        properties.skyColor[0] *= 1 - MathHelper.clamp(random.nextFloat() * 0.1f + (70 - (properties.averageTemperature / 3f)) / 100f, 0.2f, 1);
        properties.skyColor[1] *= 1 - (random.nextFloat() * .5f);
        properties.skyColor[2] *= 1 - MathHelper.clamp(random.nextFloat() * 0.1f + ((properties.averageTemperature / 3f) - 70) / 100f, 0, 1);

        if (random.nextInt() % 50 == 0) {
            properties.setHasRings(true);
            properties.ringColor[0] = properties.skyColor[0];
            properties.ringColor[1] = properties.skyColor[1];
            properties.ringColor[2] = properties.skyColor[2];
        }

        properties.rotationalPeriod = (int) (Math.pow((1 / properties.gravitationalMultiplier), 3) * 24000);

        properties.addBiomes(properties.getViableBiomes(true));
        properties.initDefaultAttributes();

        registerDim(properties, true);
        return properties;
    }

    public DimensionProperties generateRandom(int starId, int baseAtmosphere, int baseDistance, int baseGravity, int atmosphereFactor, int distanceFactor, int gravityFactor) {
        return generateRandom(starId, "", baseAtmosphere, baseDistance, baseGravity, atmosphereFactor, distanceFactor, gravityFactor);
    }

    public DimensionProperties generateRandomGasGiant(int starId, String name, int baseAtmosphere, int baseDistance, int baseGravity, int atmosphereFactor, int distanceFactor, int gravityFactor) {
        DimensionProperties properties = new DimensionProperties(getNextFreeDim(dimOffset));

        if (name.isEmpty()) properties.setName(getNextName(starId, properties.getId()));
        else {
            properties.setName(name);
        }
        properties.setAtmosphereDensityDirect(MathHelper.clamp(baseAtmosphere + random.nextInt(atmosphereFactor) - atmosphereFactor / 2, DimensionProperties.MIN_ATM_PRESSURE, DimensionProperties.MAX_ATM_PRESSURE));
        properties.orbitalDist = MathHelper.clamp(baseDistance + random.nextInt(distanceFactor), DimensionProperties.MIN_DISTANCE, 800);
        //System.out.println(properties.orbitalDist);
        properties.gravitationalMultiplier = Math.min(Math.max(0.05f, (baseGravity + random.nextInt(gravityFactor) - gravityFactor / 2f) / 100f), 1.3f);

        double minDistance;

        do {
            minDistance = Double.MAX_VALUE;

            properties.orbitTheta = random.nextInt(360) * (2f * Math.PI) / 360f;

            for (IDimensionProperties properties2 : getStar(starId).getPlanets()) {
                double dist = Math.abs(((DimensionProperties) properties2).orbitTheta - properties.orbitTheta);
                if (dist < minDistance) minDistance = dist;
            }

        } while (minDistance < (Math.PI / 40f));

        //Get Star Color
        properties.setStar(getStar(starId));

        //Linear is easier. Earth is nominal!
        properties.averageTemperature = AstronomicalBodyHelper.getAverageTemperature(properties.getStar(), properties.getSolarOrbitalDistance(), properties.getAtmosphereDensity());
        properties.setGasGiant(true);

        // Add all gasses for the default world
        for (FluidGasGiantGas gas : AdvancedRocketryFluids.getGasGiantGasses()) {
            if (((properties.gravitationalMultiplier * 100) >= gas.getMinGravity()) && (gas.getMaxGravity() >= (properties.gravitationalMultiplier * 100)) && 0 > (Math.random() - gas.getChance())) {
                properties.getHarvestableGasses().add(gas.getFluid());
            }
        }

        registerDim(properties, true);
        return properties;
    }

    /**
     * @param dimId dimension id to check
     * @return true if it can be traveled to, in general if it has a surface
     */
    public boolean canTravelTo(int dimId) {
        return net.minecraftforge.common.DimensionManager.isDimensionRegistered(dimId) && dimId != Constants.INVALID_PLANET && getDimensionProperties(dimId).hasSurface();
    }

    /**
     * Attempts to register a dimension with {@link DimensionProperties}, if the dimension has not yet been registered, sends a packet containing the dimension information to all connected clients
     *
     * @param properties {@link DimensionProperties} to register
     * @return false if the dimension has not been registered, true if it is being newly registered
     */
    public boolean registerDim(@Nonnull DimensionProperties properties, boolean registerWithForge) {
        boolean bool = registerDimNoUpdate(properties, registerWithForge);

        if (bool) PacketHandler.sendToAll(new PacketDimInfo(properties.getId(), properties));
        return bool;
    }

    /**
     * Attempts to register a dimension without sending an update to the client
     *
     * @param properties        {@link DimensionProperties} to register
     * @param registerWithForge if true also registers the dimension with forge
     * @return true if the dimension has NOT been registered before, false if the dimension IS registered exist already
     */
    public boolean registerDimNoUpdate(@Nonnull DimensionProperties properties, boolean registerWithForge) {
        int dimId = properties.getId();

        if (dimensionList.containsKey(dimId)) return false;

        //Avoid registering gas giants as dimensions
        if (registerWithForge && properties.hasSurface() && !net.minecraftforge.common.DimensionManager.isDimensionRegistered(dimId)) {

            if (properties.isAsteroid())
                net.minecraftforge.common.DimensionManager.registerDimension(dimId, AsteroidDimensionType);
            else net.minecraftforge.common.DimensionManager.registerDimension(dimId, PlanetDimensionType);
        }
        dimensionList.put(dimId, properties);

        return true;
    }

    /**
     * Unregisters all dimensions associated with this DimensionManager from both Minecraft and this DimnensionManager
     */
    public void unregisterAllDimensions() {
        for (Entry<Integer, DimensionProperties> dimSet : dimensionList.entrySet()) {
            if (dimSet.getValue().isNativeDimension && dimSet.getValue().hasSurface() && net.minecraftforge.common.DimensionManager.isDimensionRegistered(dimSet.getKey())) {
                net.minecraftforge.common.DimensionManager.unregisterDimension(dimSet.getKey());
            }
        }
        dimensionList.clear();
        starList.clear();
    }

    /**
     * Deletes and unregisters the dimensions, as well as all child dimensions, from the game
     *
     * @param dimId the dimensionId to delete
     */
    public void deleteDimension(int dimId) {

        if (net.minecraftforge.common.DimensionManager.getWorld(dimId) != null) {
            AdvancedRocketry.logger.warn("Cannot delete dimension " + dimId + " it is still loaded");
            return;
        }

        DimensionProperties properties = dimensionList.get(dimId);

        //Can happen in some rare cases
        if (properties == null) return;

        if (properties.getStar() != null) properties.getStar().removePlanet(properties);
        if (properties.isMoon()) {
            properties.getParentProperties().removeChild(properties.getId());
        }

        if (properties.hasChildren()) {

            Iterator<Integer> iterator = properties.getChildPlanets().iterator();
            while (iterator.hasNext()) {
                Integer child = iterator.next();
                iterator.remove(); //Avoid CME
                deleteDimension(child);

                PacketHandler.sendToAll(new PacketDimInfo(child, null));
            }
        }

        //TODO: check for world loaded
        // If not native to AR let the mod it's registered to handle it
        if (properties.isNativeDimension) {
            if (net.minecraftforge.common.DimensionManager.isDimensionRegistered(dimId)) {
                if (net.minecraftforge.common.DimensionManager.getWorld(dimId) != null)
                    net.minecraftforge.common.DimensionManager.unloadWorld(dimId);

                net.minecraftforge.common.DimensionManager.unregisterDimension(dimId);
            }
        }
        dimensionList.remove(dimId);

        //Delete World Folder
        File file = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), workingPath + "/DIM" + dimId);

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitialized() {

        return hasBeenInitialized;
    }

    public void onServerStopped() {
        unregisterAllDimensions();
        knownPlanets.clear();
        overworldProperties.resetProperties();
        hasBeenInitialized = false;
    }

    /**
     * @param dimId id of the dimention of which to get the properties
     * @return DimensionProperties representing the dimId given
     */
    public DimensionProperties getDimensionProperties(int dimId) {

        //If we're trying to get star properties for orbit and such, this is a proxy
        if (dimId >= Constants.STAR_ID_OFFSET) {
            StellarBody star = getStar(dimId - Constants.STAR_ID_OFFSET);
            if (star == null) return overworldProperties;

            DimensionProperties newprops = new DimensionProperties(dimId);
            newprops.setName(star.getName());
            return newprops;
        }

        DimensionProperties properties = dimensionList.get(dimId);
        if (dimId == ARConfiguration.getCurrentConfig().spaceDimId || dimId == Integer.MIN_VALUE) {
            return defaultSpaceDimensionProperties;
        }
        return properties == null ? overworldProperties : properties;
    }

    /**
     * @param id star id for which to get the object
     * @return the {@link StellarBody} object
     */
    public StellarBody getStar(int id) {
        return starList.get(id);
    }

    /**
     * @return a list of star ids
     */
    public Set<Integer> getStarIds() {
        return starList.keySet();
    }

    public Collection<StellarBody> getStars() {

        return starList.values();
    }

    /**
     * Adds a star to the handler
     *
     * @param star star to add
     */
    public void addStar(StellarBody star) {
        starList.put(star.getId(), star);
    }

    /**
     * Removes the star from the handler
     *
     * @param id id of the star to remove
     */
    public void removeStar(int id) {
        //TODO: actually remove subPlanets et
        starList.remove(id);
    }

    /**
     * Saves all dimension data, satellites, and space stations to disk, SHOULD NOT BE CALLED OUTSIDE OF WORLDSAVEEVENT
     *
     * @param filePath file path to which to save the data
     */
    public void saveDimensions(String filePath) throws Exception {

        if (starList.isEmpty() || dimensionList.isEmpty()) {
            throw new Exception("Missing Stars");
        }

        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagCompound dimListnbt = new NBTTagCompound();


        //Save SolarSystems first
        NBTTagCompound solarSystem = new NBTTagCompound();
        for (Entry<Integer, StellarBody> stars : starList.entrySet()) {
            NBTTagCompound solarNBT = new NBTTagCompound();
            stars.getValue().writeToNBT(solarNBT);
            solarSystem.setTag(stars.getKey().toString(), solarNBT);
        }

        nbt.setTag("starSystems", solarSystem);

        //Save satelliteId
        nbt.setLong("nextSatelliteId", nextSatelliteId);

        //Save Overworld
        for (Entry<Integer, DimensionProperties> dimSet : dimensionList.entrySet()) {

            NBTTagCompound dimNbt = new NBTTagCompound();
            dimSet.getValue().writeToNBT(dimNbt);

            dimListnbt.setTag(dimSet.getKey().toString(), dimNbt);
        }

        nbt.setTag("dimList", dimListnbt);

        //Stats
        NBTTagCompound stats = new NBTTagCompound();
        stats.setBoolean("hasReachedMoon", hasReachedMoon);
        stats.setBoolean("hasReachedWarp", hasReachedWarp);
        nbt.setTag("stat", stats);

        NBTTagCompound nbtTag = new NBTTagCompound();
        SpaceObjectManager.getSpaceManager().writeToNBT(nbtTag);
        nbt.setTag("spaceObjects", nbtTag);

        String xmlOutput = XMLPlanetLoader.writeXML(this);

        try {
            File planetXMLOutput = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), filePath + worldXML);
            planetXMLOutput.createNewFile();

            File tmpFileXml = File.createTempFile("ARXMLdata_", ".DAT", net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
            FileOutputStream bufOutStream = new FileOutputStream(tmpFileXml);
            bufOutStream.write(xmlOutput.getBytes());

            //Commit to OS, tell OS to commit to disk, release and close stream
            bufOutStream.flush();
            bufOutStream.getFD().sync();
            bufOutStream.close();

            //Temp file was written OK, commit
            Files.copy(tmpFileXml.toPath(), planetXMLOutput.toPath(), REPLACE_EXISTING);
            tmpFileXml.delete();

            File file = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), filePath + tempFile);
            file.createNewFile();

            //Getting real sick of my planet file getting toasted during debug...
            File tmpFile = File.createTempFile("dimprops", ".DAT", net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
            FileOutputStream tmpFileOut = new FileOutputStream(tmpFile);
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(tmpFileOut)));
            try {
                //Closes output stream internally without flush... why tho...
                CompressedStreamTools.write(nbt, outStream);

                //Open in append mode to make sure the file syncs, hacky AF
                outStream.flush();
                tmpFileOut.getFD().sync();
                outStream.close();

                Files.copy(tmpFile.toPath(), file.toPath(), REPLACE_EXISTING);
                tmpFile.delete();

            } catch (Exception e) {
                AdvancedRocketry.logger.error("Cannot save advanced rocketry planet file, you may be able to find backups in " + net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
                e.printStackTrace();
            }


        } catch (IOException e) {
            AdvancedRocketry.logger.error("Cannot save advanced rocketry planet files, you may be able to find backups in " + net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
            e.printStackTrace();
        }
    }

    /**
     * @param dimId integer id of the dimension
     * @return true if the dimension exists and is registered
     */
    public boolean isDimensionCreated(int dimId) {
        return dimensionList.containsKey(dimId) || dimId == ARConfiguration.getCurrentConfig().spaceDimId;
    }

    private List<DimensionProperties> generateRandomPlanets(StellarBody star, int numRandomGeneratedPlanets, int numRandomGeneratedGasGiants) {
        List<DimensionProperties> dimPropList = new LinkedList<>();

        Random random = new Random(System.currentTimeMillis());


        for (int i = 0; i < numRandomGeneratedGasGiants; i++) {
            int baseAtm = 180;
            int baseDistance = 100;

            DimensionProperties properties = DimensionManager.getInstance().generateRandomGasGiant(star.getId(), "", baseDistance + 50, baseAtm, 125, 100, 100, 75);

            dimPropList.add(properties);
            if (properties.gravitationalMultiplier >= 1f) {
                int numMoons = random.nextInt(8);

                for (int ii = 0; ii < numMoons; ii++) {
                    DimensionProperties moonProperties = DimensionManager.getInstance().generateRandom(star.getId(), properties.getName() + ": " + ii, 25, 100, (int) (properties.gravitationalMultiplier / .02f), 25, 100, 50);
                    if (moonProperties == null) continue;

                    dimPropList.add(moonProperties);

                    moonProperties.setParentPlanet(properties);
                    star.removePlanet(moonProperties);
                }
            }
        }

        for (int i = 0; i < numRandomGeneratedPlanets; i++) {
            int baseAtm = 75;
            int baseDistance = 100;

            if (i % 4 == 0) {
                baseAtm = 0;
            } else if (i != 6 && (i + 2) % 4 == 0) baseAtm = 120;

            if (i % 3 == 0) {
                baseDistance = 170;
            } else if ((i + 1) % 3 == 0) {
                baseDistance = 30;
            }

            DimensionProperties properties = DimensionManager.getInstance().generateRandom(star.getId(), baseDistance, baseAtm, 125, 100, 100, 75);

            if (properties == null) continue;

            dimPropList.add(properties);

            if (properties.gravitationalMultiplier >= 1f) {
                int numMoons = random.nextInt(4);

                for (int ii = 0; ii < numMoons; ii++) {
                    DimensionProperties moonProperties = DimensionManager.getInstance().generateRandom(star.getId(), properties.getName() + ": " + ii, 25, 100, (int) (properties.gravitationalMultiplier / .02f), 25, 100, 50);

                    if (moonProperties == null) continue;

                    dimPropList.add(moonProperties);
                    moonProperties.setParentPlanet(properties);
                    star.removePlanet(moonProperties);
                }
            }
        }

        return dimPropList;
    }

    public void createAndLoadDimensions() {
        // 创建太阳系
        StellarBody sol = new StellarBody();
        sol.setTemperature(100);
        sol.setId(0);
        sol.setName("Sol");
        DimensionManager.getInstance().addStar(sol);

        // 添加主世界
        DimensionManager.getInstance().registerDimNoUpdate(DimensionManager.overworldProperties, false);
        sol.addPlanet(DimensionManager.overworldProperties);


        // 注册月球 T1
        DimensionProperties moonProperties = new DimensionProperties(51);
        moonProperties.setAtmosphereDensityDirect(0);//大气0
        moonProperties.averageTemperature = 20;//温度20k
        moonProperties.rotationalPeriod = 128000;//自传周期 128000tick
        moonProperties.setGravitationalMultiplier(0.166f);
        moonProperties.setName("Moon");
        moonProperties.orbitalDist = 150;//卫星算法 假设月球到地球为150
        moonProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        moonProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        moonProperties.addBiome(AdvancedRocketryBiomes.moonBiomeDark);
        moonProperties.setParentPlanet(DimensionManager.overworldProperties);
        moonProperties.setStar(sol);//恒星是太阳
        moonProperties.isNativeDimension = true;
        moonProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(moonProperties, true);

        //注册火星 T2
        DimensionProperties marsProperties = new DimensionProperties(52);
        marsProperties.setRequiredArtifacts(COMPUTERTIER1.getStackForm());
        marsProperties.setAtmosphereDensityDirect(6);
        marsProperties.averageTemperature = 220;
        marsProperties.rotationalPeriod = 24660;
        marsProperties.setGravitationalMultiplier(0.38f);
        marsProperties.fogColor = new float[]{1.0f, 0.4f, 0.3f};
        marsProperties.skyColor = new float[]{0.8f, 0.5f, 0.3f};
        marsProperties.setName("Mars");
        marsProperties.orbitalDist = 152;//100为一个地日距离
        marsProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MARS_STONE)); // 替换为火星石头
        marsProperties.addBiome(AdvancedRocketryBiomes.marsBiome);
        //marsProperties.setParentPlanet(null); // 火星是行星，无父行星
        marsProperties.setStar(sol);
        marsProperties.isNativeDimension = true;
        marsProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(marsProperties, true);

        //注册金星 T3
        DimensionProperties venusProperties = new DimensionProperties(53);
        marsProperties.setRequiredArtifacts(COMPUTERTIER2.getStackForm());
        venusProperties.setAtmosphereDensityDirect(920);
        venusProperties.averageTemperature = 464;
        venusProperties.rotationalPeriod = 20995200;
        venusProperties.setGravitationalMultiplier(0.904f);
        venusProperties.fogColor = new float[]{0.9f, 0.8f, 0.6f};
        venusProperties.skyColor = new float[]{0.8f, 0.7f, 0.5f};
        venusProperties.setName("Venus");
        venusProperties.orbitalDist = 72;
        venusProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.VENUS_STONE));
        venusProperties.addBiome(AdvancedRocketryBiomes.venusBiome);
        venusProperties.addBiome(AdvancedRocketryBiomes.volcanicBarren);
        venusProperties.setStar(sol);
        venusProperties.isNativeDimension = true;
        venusProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(venusProperties, true);

        //注册谷神星 T3
        DimensionProperties ceresProperties = new DimensionProperties(69);
        marsProperties.setRequiredArtifacts(COMPUTERTIER2.getStackForm());
        ceresProperties.setAtmosphereDensityDirect(0);
        ceresProperties.averageTemperature = 167;
        ceresProperties.rotationalPeriod = 326400;
        ceresProperties.setGravitationalMultiplier(0.029f);
        ceresProperties.setName("Ceres");
        ceresProperties.orbitalDist = 260;
        ceresProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                .getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        ceresProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        ceresProperties.addBiome(AdvancedRocketryBiomes.alienForest);
        ceresProperties.setStar(sol);
        ceresProperties.isNativeDimension = true;
        ceresProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(ceresProperties, true);

        //注册小行星带 T3
        DimensionProperties asteroidProperties = new DimensionProperties(54);
        marsProperties.setRequiredArtifacts(COMPUTERTIER2.getStackForm());
        asteroidProperties.setAtmosphereDensityDirect(0);
        asteroidProperties.averageTemperature = 30;
        asteroidProperties.rotationalPeriod = 0;
        asteroidProperties.gravitationalMultiplier = 0;
        asteroidProperties.skyColor = new float[]{0.0f, 0.0f, 0.0f};
        asteroidProperties.setName("Asteroid");
        asteroidProperties.orbitalDist = 300;
        asteroidProperties.setGenType(Constants.GENTYPE_ASTEROID);
        asteroidProperties.setStar(sol);
        asteroidProperties.isNativeDimension = true;
        asteroidProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(asteroidProperties, true);

        // 注册木星 T4 (气态巨行星)
        DimensionProperties jupiterProperties = new DimensionProperties(55);
        jupiterProperties.setAtmosphereDensityDirect(220);
        jupiterProperties.averageTemperature = 165;
        jupiterProperties.setHasRings(true);
        jupiterProperties.ringColor = new float[]{0.9f, 0.8f, 0.7f};
        jupiterProperties.rotationalPeriod = 35000;
        jupiterProperties.setGravitationalMultiplier(2.5f);
        jupiterProperties.fogColor = new float[]{0.9f, 0.8f, 0.7f};
        jupiterProperties.skyColor = new float[]{0.8f, 0.7f, 0.6f};
        jupiterProperties.setName("Jupiter");
        jupiterProperties.orbitalDist = 520;
        jupiterProperties.setStar(sol);
        jupiterProperties.setGasGiant(true);
        jupiterProperties.isNativeDimension = true;
        // 添加木星特有气体

        jupiterProperties.getHarvestableGasses().add(Materials.Hydrogen.getFluid());
        jupiterProperties.getHarvestableGasses().add(Materials.Helium.getFluid());
        jupiterProperties.getHarvestableGasses().add(Materials.Helium3.getFluid());

        jupiterProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(jupiterProperties, true);

        // 木卫一 (Io)
        DimensionProperties ioProperties = new DimensionProperties(56);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        ioProperties.setAtmosphereDensityDirect(0);
        ioProperties.averageTemperature = 130;
        ioProperties.rotationalPeriod = 152853;
        ioProperties.setGravitationalMultiplier(0.18f);
        ioProperties.setName("Io");
        ioProperties.orbitalDist = 25;
        ioProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.IO_STONE));
        ioProperties.addBiome(AdvancedRocketryBiomes.hotDryBiome);
        ioProperties.addBiome(AdvancedRocketryBiomes.stormLandsBiome);
        ioProperties.addBiome(AdvancedRocketryBiomes.volcanic);
        ioProperties.setParentPlanet(jupiterProperties);
        ioProperties.setStar(sol);
        ioProperties.isNativeDimension = true;
        ioProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(ioProperties, true);

        // 木卫二 (Europa)
        DimensionProperties europaProperties = new DimensionProperties(57);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        europaProperties.setAtmosphereDensityDirect(1);
        europaProperties.averageTemperature = 102;
        europaProperties.rotationalPeriod = 306000;
        europaProperties.setGravitationalMultiplier(0.13f);
        europaProperties.setName("Europa");
        europaProperties.orbitalDist = 35;
        europaProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        europaProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        europaProperties.addBiome(AdvancedRocketryBiomes.crystalChasms);
        europaProperties.setParentPlanet(jupiterProperties);
        europaProperties.setStar(sol);
        europaProperties.isNativeDimension = true;
        europaProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(europaProperties, true);

        // 木卫三 (Ganymede) - 木星最大卫星
        DimensionProperties ganymedeProperties = new DimensionProperties(70);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        ganymedeProperties.setAtmosphereDensityDirect(0);
        ganymedeProperties.averageTemperature = 110;
        ganymedeProperties.rotationalPeriod = 618240;
        ganymedeProperties.setGravitationalMultiplier(0.146f);
        ganymedeProperties.setName("Ganymede");
        ganymedeProperties.orbitalDist = 60;
        ganymedeProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        ganymedeProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        ganymedeProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        ganymedeProperties.setParentPlanet(jupiterProperties);
        ganymedeProperties.setStar(sol);
        ganymedeProperties.isNativeDimension = true;
        ganymedeProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(ganymedeProperties, true);

        // 木卫四 (Callisto) - 木星另一主要卫星
        DimensionProperties callistoProperties = new DimensionProperties(71);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        callistoProperties.setAtmosphereDensityDirect(0);
        callistoProperties.averageTemperature = 134;
        callistoProperties.rotationalPeriod = 1442880;
        callistoProperties.setGravitationalMultiplier(0.126f);
        callistoProperties.setName("Callisto");
        callistoProperties.orbitalDist = 120;
        callistoProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        callistoProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        callistoProperties.addBiome(AdvancedRocketryBiomes.moonBiomeDark);
        callistoProperties.setParentPlanet(jupiterProperties);
        callistoProperties.setStar(sol);
        callistoProperties.isNativeDimension = true;
        callistoProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(callistoProperties, true);

        // 注册土星 T4 (气态巨行星)
        DimensionProperties saturnProperties = new DimensionProperties(58);
        saturnProperties.setAtmosphereDensityDirect(180);
        saturnProperties.averageTemperature = 134;
        saturnProperties.rotationalPeriod = 38500;
        saturnProperties.setGravitationalMultiplier(1.1f);
        saturnProperties.fogColor = new float[]{0.95f, 0.9f, 0.8f};
        saturnProperties.skyColor = new float[]{0.9f, 0.85f, 0.75f};
        saturnProperties.setName("Saturn");
        saturnProperties.orbitalDist = 950;
        saturnProperties.setStar(sol);
        saturnProperties.setGasGiant(true);
        saturnProperties.isNativeDimension = true;

        saturnProperties.getHarvestableGasses().add(Materials.Hydrogen.getFluid());
        saturnProperties.getHarvestableGasses().add(Materials.Methane.getFluid());

        saturnProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(saturnProperties, true);

        // 土卫1 (Mimas) - 死亡之星小卫星
        DimensionProperties mimasProperties = new DimensionProperties(72);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        mimasProperties.setAtmosphereDensityDirect(0);
        mimasProperties.averageTemperature = 64;
        mimasProperties.rotationalPeriod = 81480;
        mimasProperties.setGravitationalMultiplier(0.0006f);
        mimasProperties.setName("Mimas");
        mimasProperties.orbitalDist = 5;
        mimasProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        mimasProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        mimasProperties.setParentPlanet(saturnProperties);
        mimasProperties.setStar(sol);
        mimasProperties.isNativeDimension = true;
        mimasProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(mimasProperties, true);

        // 土卫3 (Tethys) - 土星卫星
        DimensionProperties tethysProperties = new DimensionProperties(73);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        tethysProperties.setAtmosphereDensityDirect(0);
        tethysProperties.averageTemperature = 86;
        tethysProperties.rotationalPeriod = 261888;
        tethysProperties.setGravitationalMultiplier(0.0145f);
        tethysProperties.setName("Tethys");
        tethysProperties.orbitalDist = 12;
        tethysProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        tethysProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        tethysProperties.addBiome(AdvancedRocketryBiomes.crystalChasms);
        tethysProperties.setParentPlanet(saturnProperties);
        tethysProperties.setStar(sol);
        tethysProperties.isNativeDimension = true;
        tethysProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(tethysProperties, true);

        // 土卫4 (Dione) - 土星卫星
        DimensionProperties dioneProperties = new DimensionProperties(74);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        dioneProperties.setAtmosphereDensityDirect(0);
        dioneProperties.averageTemperature = 87;
        dioneProperties.rotationalPeriod = 273120;
        dioneProperties.setGravitationalMultiplier(0.023f);
        dioneProperties.setName("Dione");
        dioneProperties.orbitalDist = 15;
        dioneProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        dioneProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        dioneProperties.setParentPlanet(saturnProperties);
        dioneProperties.setStar(sol);
        dioneProperties.isNativeDimension = true;
        dioneProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(dioneProperties, true);

        // 土卫5 (Rhea) - 土星第二大卫星
        DimensionProperties rheaProperties = new DimensionProperties(75);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        rheaProperties.setAtmosphereDensityDirect(0);
        rheaProperties.averageTemperature = 81;
        rheaProperties.rotationalPeriod = 1086240;
        rheaProperties.setGravitationalMultiplier(0.0269f);
        rheaProperties.setName("Rhea");
        rheaProperties.orbitalDist = 18;
        rheaProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        rheaProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        rheaProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        rheaProperties.setParentPlanet(saturnProperties);
        rheaProperties.setStar(sol);
        rheaProperties.isNativeDimension = true;
        rheaProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(rheaProperties, true);

        // 土卫六 (Titan)
        DimensionProperties titanProperties = new DimensionProperties(59);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        titanProperties.setAtmosphereDensityDirect(146);
        titanProperties.averageTemperature = 94;
        titanProperties.rotationalPeriod = 1370000;
        titanProperties.setGravitationalMultiplier(0.14f);
        titanProperties.fogColor = new float[]{0.9f, 0.85f, 0.8f};
        titanProperties.setName("Titan");
        titanProperties.orbitalDist = 30;
        titanProperties.setSeaLevel(50);
        titanProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                .getState(GTQTSStoneVariantBlock.StoneType.METHANE_ICE));
        titanProperties.setOceanBlock(Materials.Methane.getFluid(FluidStorageKeys.LIQUID).getBlock().getDefaultState());
        titanProperties.setParentPlanet(saturnProperties);
        titanProperties.setStar(sol);
        titanProperties.isNativeDimension = true;
        titanProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(titanProperties, true);

        // 土卫二 (Enceladus)
        DimensionProperties enceladusProperties = new DimensionProperties(60);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        enceladusProperties.setAtmosphereDensityDirect(0);
        enceladusProperties.averageTemperature = 75;
        enceladusProperties.rotationalPeriod = 118000;
        enceladusProperties.setGravitationalMultiplier(0.01f);
        enceladusProperties.setName("Enceladus");
        enceladusProperties.orbitalDist = 40;
        enceladusProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        enceladusProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        enceladusProperties.addBiome(AdvancedRocketryBiomes.alienForest);
        enceladusProperties.setParentPlanet(saturnProperties);
        enceladusProperties.setStar(sol);
        enceladusProperties.isNativeDimension = true;
        enceladusProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(enceladusProperties, true);

        // 土卫八 (Iapetus)
        DimensionProperties iapetusProperties = new DimensionProperties(61);
        marsProperties.setRequiredArtifacts(COMPUTERTIER3.getStackForm());
        iapetusProperties.setAtmosphereDensityDirect(0);
        iapetusProperties.averageTemperature = 98;
        iapetusProperties.rotationalPeriod = 1900000;
        iapetusProperties.setGravitationalMultiplier(0.024f);
        iapetusProperties.fogColor = new float[]{0.2f, 0.2f, 0.2f};
        iapetusProperties.skyColor = new float[]{0.1f, 0.1f, 0.1f};
        iapetusProperties.setName("Iapetus");
        iapetusProperties.orbitalDist = 200;
        iapetusProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                .getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        iapetusProperties.addBiome(AdvancedRocketryBiomes.moonBiome);

        // 土卫八著名的两面性：一面明亮，一面黑暗

        iapetusProperties.setParentPlanet(saturnProperties);
        iapetusProperties.setStar(sol);
        iapetusProperties.isNativeDimension = true;
        iapetusProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(iapetusProperties, true);

        // 水星 T5
        DimensionProperties mercuryProperties = new DimensionProperties(62);
        marsProperties.setRequiredArtifacts(COMPUTERTIER4.getStackForm());
        mercuryProperties.setAtmosphereDensityDirect(0);
        mercuryProperties.averageTemperature = 475;
        mercuryProperties.rotationalPeriod = 1416240;
        mercuryProperties.setGravitationalMultiplier(0.38f);
        mercuryProperties.fogColor = new float[]{0.8f, 0.6f, 0.4f};
        mercuryProperties.skyColor = new float[]{0.7f, 0.5f, 0.3f};
        mercuryProperties.setName("Mercury");
        mercuryProperties.orbitalDist = 39;
        mercuryProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MARS_STONE)); // 重用火星石头
        mercuryProperties.addBiome(AdvancedRocketryBiomes.hotDryBiome);
        mercuryProperties.setStar(sol);
        mercuryProperties.isNativeDimension = true;
        mercuryProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(mercuryProperties, true);

        //天王星 T6 (冰巨行星)
        DimensionProperties uranusProperties = new DimensionProperties(63);
        marsProperties.setRequiredArtifacts(COMPUTERTIER5.getStackForm());
        uranusProperties.setAtmosphereDensityDirect(160);
        uranusProperties.averageTemperature = 59;
        uranusProperties.rotationalPeriod = 62066;
        uranusProperties.setGravitationalMultiplier(0.886f);
        uranusProperties.fogColor = new float[]{0.6f, 0.8f, 0.9f};
        uranusProperties.skyColor = new float[]{0.3f, 0.5f, 0.7f};
        uranusProperties.setName("Uranus");
        uranusProperties.orbitalDist = 2870;
        uranusProperties.setStar(sol);
        uranusProperties.setGasGiant(true);
        uranusProperties.isNativeDimension = true;

        uranusProperties.getHarvestableGasses().add(Materials.Hydrogen.getFluid());
        uranusProperties.getHarvestableGasses().add(Materials.Methane.getFluid());
        uranusProperties.getHarvestableGasses().add(Materials.CarbonDioxide.getFluid());

        uranusProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(uranusProperties, true);

        // 天卫三 (Oberon 乌兰诺斯 Furnaces) - 天王星的主要卫星
        DimensionProperties oberonProperties = new DimensionProperties(64);
        marsProperties.setRequiredArtifacts(COMPUTERTIER5.getStackForm());
        oberonProperties.setAtmosphereDensityDirect(0);
        oberonProperties.averageTemperature = 89;
        oberonProperties.rotationalPeriod = 362044;
        oberonProperties.setGravitationalMultiplier(0.035f);
        oberonProperties.setName("Oberon");
        oberonProperties.orbitalDist = 30;
        oberonProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        oberonProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        oberonProperties.addBiome(AdvancedRocketryBiomes.moonBiomeDark);
        oberonProperties.setParentPlanet(uranusProperties);
        oberonProperties.setStar(sol);
        oberonProperties.isNativeDimension = true;
        oberonProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(oberonProperties, true);

        // 天卫四 (Titania 泰坦妮娅 Furnaces) - 天王星的主要卫星
        DimensionProperties titaniaProperties = new DimensionProperties(65);
        marsProperties.setRequiredArtifacts(COMPUTERTIER5.getStackForm());
        titaniaProperties.setAtmosphereDensityDirect(0);
        titaniaProperties.averageTemperature = 89;
        titaniaProperties.rotationalPeriod = 208488;
        titaniaProperties.setGravitationalMultiplier(0.038f);
        titaniaProperties.setName("Titania");
        titaniaProperties.orbitalDist = 50;
        titaniaProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        titaniaProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        titaniaProperties.addBiome(AdvancedRocketryBiomes.crystalChasms);
        titaniaProperties.setParentPlanet(uranusProperties);
        titaniaProperties.setStar(sol);
        titaniaProperties.isNativeDimension = true;
        titaniaProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(titaniaProperties, true);

        //海王星 T6 (冰巨行星)
        DimensionProperties neptuneProperties = new DimensionProperties(66);
        neptuneProperties.setAtmosphereDensityDirect(170);
        neptuneProperties.averageTemperature = 55;
        neptuneProperties.rotationalPeriod = 57996;
        neptuneProperties.setGravitationalMultiplier(1.14f);
        neptuneProperties.fogColor = new float[]{0.4f, 0.6f, 0.9f};
        neptuneProperties.skyColor = new float[]{0.2f, 0.4f, 0.8f};
        neptuneProperties.setName("Neptune");
        neptuneProperties.orbitalDist = 4504;
        neptuneProperties.setStar(sol);
        neptuneProperties.setGasGiant(true);
        neptuneProperties.isNativeDimension = true;

        neptuneProperties.getHarvestableGasses().add(Materials.Hydrogen.getFluid());
        neptuneProperties.getHarvestableGasses().add(Materials.Helium.getFluid());
        neptuneProperties.getHarvestableGasses().add(Materials.Methane.getFluid());

        neptuneProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(neptuneProperties, true);

        // 海卫一 (Triton 特赖顿 Furnaces) - 海王星的主要卫星
        DimensionProperties tritonProperties = new DimensionProperties(67);
        tritonProperties.setAtmosphereDensityDirect(2);
        tritonProperties.averageTemperature = 38;
        tritonProperties.rotationalPeriod = 254880;
        tritonProperties.setGravitationalMultiplier(0.079f);
        tritonProperties.setName("Triton");
        tritonProperties.orbitalDist = 40;
        tritonProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        tritonProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        tritonProperties.addBiome(AdvancedRocketryBiomes.alienForest);
        tritonProperties.setParentPlanet(neptuneProperties);
        tritonProperties.setStar(sol);
        tritonProperties.isNativeDimension = true;
        tritonProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(tritonProperties, true);

        // 海卫八 (Nereid 尼里德 Furnaces) - 海王星的远距离卫星
        DimensionProperties nereidProperties = new DimensionProperties(68);
        nereidProperties.setAtmosphereDensityDirect(0);
        nereidProperties.averageTemperature = 35;
        nereidProperties.rotationalPeriod = 864000;
        nereidProperties.setGravitationalMultiplier(0.003f);
        nereidProperties.setName("Nereid");
        nereidProperties.orbitalDist = 5500;
        nereidProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                .getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        nereidProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        nereidProperties.setParentPlanet(neptuneProperties);
        nereidProperties.setStar(sol);
        nereidProperties.isNativeDimension = true;
        nereidProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(nereidProperties, true);
        
        // 其他统一 T7
        // 阋神星 (Eris) - 最遥远的矮行星，争议中的9号行星
        DimensionProperties erisProperties = new DimensionProperties(78);
        erisProperties.setAtmosphereDensityDirect(0);
        erisProperties.averageTemperature = 42;
        erisProperties.rotationalPeriod = 1117440;
        erisProperties.setGravitationalMultiplier(0.080f);
        erisProperties.setName("Eris");
        erisProperties.orbitalDist = 7600;
        erisProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        erisProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        erisProperties.addBiome(AdvancedRocketryBiomes.moonBiomeDark);
        erisProperties.setStar(sol);
        erisProperties.isNativeDimension = true;
        erisProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(erisProperties, true);

        // 赛德娜 (Sedna) - 内奥尔特云天体的代表
        DimensionProperties sednaProperties = new DimensionProperties(79);
        sednaProperties.setAtmosphereDensityDirect(0);
        sednaProperties.averageTemperature = 30;
        sednaProperties.rotationalPeriod = 18000000;
        sednaProperties.setGravitationalMultiplier(0.042f);
        sednaProperties.setName("Sedna");
        sednaProperties.orbitalDist = 15200;
        sednaProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        sednaProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        sednaProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        sednaProperties.setStar(sol);
        sednaProperties.isNativeDimension = true;
        sednaProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(sednaProperties, true);

        // 妊神星 (Haumea) - 快速自转的椭球形矮行星
        DimensionProperties haumeaProperties = new DimensionProperties(80);
        haumeaProperties.setAtmosphereDensityDirect(0);
        haumeaProperties.averageTemperature = 50;
        haumeaProperties.rotationalPeriod = 10800;
        haumeaProperties.setGravitationalMultiplier(0.25f);
        haumeaProperties.setName("Haumea");
        haumeaProperties.orbitalDist = 6500;
        haumeaProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        haumeaProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        haumeaProperties.addBiome(AdvancedRocketryBiomes.crystalChasms);
        haumeaProperties.setStar(sol);
        haumeaProperties.isNativeDimension = true;
        haumeaProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(haumeaProperties, true);

        // 注册柯伊伯带 T7 - 太阳系遥远边缘的冰质小行星带
        DimensionProperties kuiperBeltProperties = new DimensionProperties(81);
        kuiperBeltProperties.setAtmosphereDensityDirect(0);
        kuiperBeltProperties.averageTemperature = 25;
        kuiperBeltProperties.rotationalPeriod = 0;
        kuiperBeltProperties.gravitationalMultiplier = 0;
        kuiperBeltProperties.skyColor = new float[]{0.0f, 0.0f, 0.0f};
        kuiperBeltProperties.setName("Kuiper Belt");
        kuiperBeltProperties.orbitalDist = 9000;
        kuiperBeltProperties.setGenType(Constants.GENTYPE_ASTEROID);
        kuiperBeltProperties.setStar(sol);
        kuiperBeltProperties.isNativeDimension = true;
        kuiperBeltProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(kuiperBeltProperties, true);

        // 冥王星 (Pluto) - 矮行星
        DimensionProperties plutoProperties = new DimensionProperties(76);
        plutoProperties.setAtmosphereDensityDirect(2);
        plutoProperties.averageTemperature = 40;
        plutoProperties.rotationalPeriod = 551880;
        plutoProperties.setGravitationalMultiplier(0.063f);
        plutoProperties.setName("Pluto");
        plutoProperties.orbitalDist = 5900;
        plutoProperties.setStoneBlock(Blocks.PACKED_ICE.getDefaultState());
        plutoProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        plutoProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        plutoProperties.setStar(sol);
        plutoProperties.isNativeDimension = true;
        plutoProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(plutoProperties, true);

        // 卡戎 (Charon) - 冥王星的卫星
        DimensionProperties charonProperties = new DimensionProperties(77);
        charonProperties.setAtmosphereDensityDirect(0);
        charonProperties.averageTemperature = 40;
        charonProperties.rotationalPeriod = 551880; // 与冥王星同步自转
        charonProperties.setGravitationalMultiplier(0.022f);
        charonProperties.setName("Charon");
        charonProperties.orbitalDist = 25;
        charonProperties.setStoneBlock(GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH)
                .getState(GTQTSStoneVariantBlock.StoneType.MOON_STONE));
        charonProperties.addBiome(AdvancedRocketryBiomes.moonBiome);
        charonProperties.addBiome(AdvancedRocketryBiomes.iceBiome);
        charonProperties.setParentPlanet(plutoProperties);
        charonProperties.setStar(sol);
        charonProperties.isNativeDimension = true;
        charonProperties.initDefaultAttributes();
        DimensionManager.getInstance().registerDimNoUpdate(charonProperties, true);



        // 为太阳生成随机行星
        //generateRandomPlanets(sol, numRandomGeneratedPlanets, numRandomGeneratedGasGiants);

        /*
        // 添加其他恒星系统
        StellarBody star = new StellarBody();
        star.setTemperature(10);
        star.setPosX(300);
        star.setPosZ(-200);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Wolf 12");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 5, 0);

        star = new StellarBody();
        star.setTemperature(170);
        star.setPosX(-200);
        star.setPosZ(80);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Epsilon ire");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 7, 0);

        star = new StellarBody();
        star.setTemperature(200);
        star.setPosX(-150);
        star.setPosZ(250);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Proxima Centaurs");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 3, 0);

        star = new StellarBody();
        star.setTemperature(70);
        star.setPosX(-150);
        star.setPosZ(-250);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Magnis Vulpes");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 2, 0);

        star = new StellarBody();
        star.setTemperature(200);
        star.setPosX(50);
        star.setPosZ(-250);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Ma-Roo");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 6, 0);

        star = new StellarBody();
        star.setTemperature(120);
        star.setPosX(75);
        star.setPosZ(200);
        star.setId(DimensionManager.getInstance().getNextFreeStarId());
        star.setName("Alykitt");
        DimensionManager.getInstance().addStar(star);
        generateRandomPlanets(star, 3, 1);

         */


        // 添加初始已知行星
        DimensionManager.getInstance().knownPlanets.addAll(zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().initiallyKnownPlanets);

        // 执行完整性检查
        for (ISpaceObject spaceObject : SpaceObjectManager.getSpaceManager().getSpaceObjects()) {
            int orbitingId = spaceObject.getOrbitingPlanetId();
            if (!isDimensionCreated(orbitingId) && orbitingId != 0 && orbitingId != SpaceObjectManager.WARPDIMID && orbitingId < Constants.STAR_ID_OFFSET) {
                AdvancedRocketry.logger.warn("Dimension ID " + spaceObject.getOrbitingPlanetId() + " is not registered and a space station is orbiting it, moving to dimid 0");
                SpaceObjectManager.getSpaceManager().moveStationToBody(spaceObject, 0);
                spaceObject.setDestOrbitingBody(0);
                spaceObject.setOrbitingBody(0);
            }
        }
    }


    /**
     * Loads all information to rebuild the galaxy and solar systems from disk into the current instance of DimensionManager
     *
     * @param filePath file path from which to load the information
     */
    public Map<Integer, IDimensionProperties> loadDimensions(String filePath) {
        hasBeenInitialized = true;
        Map<Integer, IDimensionProperties> loadedDimProps = new HashMap<>();

        FileInputStream inStream;
        NBTTagCompound nbt;
        try {
            File file = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), filePath + tempFile);

            if (!file.exists()) {
                new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())).mkdirs();


                file.createNewFile();
                return loadedDimProps;
            }

            inStream = new FileInputStream(file);
            nbt = CompressedStreamTools.readCompressed(inStream);
            inStream.close();
        } catch (EOFException e) {
            //Silence you fool!
            //Patch to fix JEI printing when trying to load planets too early
            return loadedDimProps;
        } catch (IOException e) {
            e.printStackTrace();
            return loadedDimProps;
        }//TODO: try not to obliterate planets in the future


        //Load SolarSystems first
        NBTTagCompound solarSystem = nbt.getCompoundTag("starSystems");

        if (solarSystem.isEmpty()) return loadedDimProps;

        NBTTagCompound stats = nbt.getCompoundTag("stat");
        hasReachedMoon = stats.getBoolean("hasReachedMoon");
        hasReachedWarp = stats.getBoolean("hasReachedWarp");

        for (String key : solarSystem.getKeySet()) {

            NBTTagCompound solarNBT = solarSystem.getCompoundTag(key);
            StellarBody star = new StellarBody();
            star.readFromNBT(solarNBT);
            starList.put(star.getId(), star);
        }

        nbt.setTag("starSystems", solarSystem);

        nextSatelliteId = nbt.getLong("nextSatelliteId");

        NBTTagCompound dimListNbt = nbt.getCompoundTag("dimList");


        for (String key : dimListNbt.getKeySet()) {
            DimensionProperties properties = DimensionProperties.createFromNBT(Integer.parseInt(key), dimListNbt.getCompoundTag(key));

            int keyInt = Integer.parseInt(key);
				/*if(!net.minecraftforge.common.DimensionManager.isDimensionRegistered(keyInt) && properties.isNativeDimension && !properties.isGasGiant()) {
					if(properties.isAsteroid())
						net.minecraftforge.common.DimensionManager.registerDimension(keyInt, AsteroidDimensionType);
					else
						net.minecraftforge.common.DimensionManager.registerDimension(keyInt, PlanetDimensionType);
				}*/

            loadedDimProps.put(keyInt, properties);
            //TODO: print unable to register world
        }


        //Check for tag in case old version of Adv rocketry is in use
        if (nbt.hasKey("spaceObjects")) {
            NBTTagCompound nbtTag = nbt.getCompoundTag("spaceObjects");
            SpaceObjectManager.getSpaceManager().readFromNBT(nbtTag);
        }

        prevBuild = nbt.getString("prevVersion");
        nbt.setString("prevVersion", AdvancedRocketry.version);

        return loadedDimProps;
    }

    /**
     * @param destinationDimId
     * @param dimension
     * @return true if the two dimensions are in the same planet/moon system
     */
    public boolean areDimensionsInSamePlanetMoonSystem(int destinationDimId, int dimension) {
        return PlanetaryTravelHelper.isTravelAnywhereInPlanetarySystem(destinationDimId, dimension);
    }
}
