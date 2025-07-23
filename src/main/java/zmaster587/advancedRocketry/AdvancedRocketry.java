package zmaster587.advancedRocketry;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zmaster587.advancedRocketry.advancements.ARAdvancements;
import zmaster587.advancedRocketry.api.*;
import zmaster587.advancedRocketry.api.capability.CapabilitySpaceArmor;
import zmaster587.advancedRocketry.api.satellite.SatelliteProperties;
import zmaster587.advancedRocketry.armor.ItemSpaceArmor;
import zmaster587.advancedRocketry.armor.ItemSpaceChest;
import zmaster587.advancedRocketry.block.*;
import zmaster587.advancedRocketry.block.multiblock.BlockARHatch;
import zmaster587.advancedRocketry.block.plant.BlockLightwoodLeaves;
import zmaster587.advancedRocketry.block.plant.BlockLightwoodPlanks;
import zmaster587.advancedRocketry.block.plant.BlockLightwoodSapling;
import zmaster587.advancedRocketry.block.plant.BlockLightwoodWood;
import zmaster587.advancedRocketry.capability.CapabilityProtectiveArmor;
import zmaster587.advancedRocketry.command.WorldCommand;
import zmaster587.advancedRocketry.common.CommonProxy;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.dimension.DimensionProperties.AtmosphereTypes;
import zmaster587.advancedRocketry.dimension.DimensionProperties.Temps;
import zmaster587.advancedRocketry.dimension.biome.BiomeManager;
import zmaster587.advancedRocketry.enchant.EnchantmentSpaceBreathing;
import zmaster587.advancedRocketry.entity.*;
import zmaster587.advancedRocketry.event.CableTickHandler;
import zmaster587.advancedRocketry.event.PlanetEventHandler;
import zmaster587.advancedRocketry.event.WorldEvents;
import zmaster587.advancedRocketry.integration.CompatibilityMgr;
import zmaster587.advancedRocketry.item.*;
import zmaster587.advancedRocketry.item.components.ItemJetpack;
import zmaster587.advancedRocketry.item.components.ItemPressureTank;
import zmaster587.advancedRocketry.item.components.ItemUpgrade;
import zmaster587.advancedRocketry.item.tools.ItemBasicLaserGun;
import zmaster587.advancedRocketry.mission.MissionGasCollection;
import zmaster587.advancedRocketry.mission.MissionOreMining;
import zmaster587.advancedRocketry.network.*;
import zmaster587.advancedRocketry.satellite.*;
import zmaster587.advancedRocketry.stations.SpaceObjectManager;
import zmaster587.advancedRocketry.stations.SpaceStationObject;
import zmaster587.advancedRocketry.tile.*;
import zmaster587.advancedRocketry.tile.atmosphere.*;
import zmaster587.advancedRocketry.tile.cables.TileDataPipe;
import zmaster587.advancedRocketry.tile.cables.TileEnergyPipe;
import zmaster587.advancedRocketry.tile.cables.TileLiquidPipe;
import zmaster587.advancedRocketry.tile.cables.TileWirelessTransciever;
import zmaster587.advancedRocketry.tile.hatch.TileDataBus;
import zmaster587.advancedRocketry.tile.hatch.TileSatelliteHatch;
import zmaster587.advancedRocketry.tile.infrastructure.*;
import zmaster587.advancedRocketry.tile.multiblock.*;
import zmaster587.advancedRocketry.tile.multiblock.energy.TileBlackHoleGenerator;
import zmaster587.advancedRocketry.tile.multiblock.energy.TileMicrowaveReciever;
import zmaster587.advancedRocketry.tile.multiblock.energy.TileSolarArray;
import zmaster587.advancedRocketry.tile.multiblock.machine.*;
import zmaster587.advancedRocketry.tile.multiblock.orbitallaserdrill.TileOrbitalLaserDrill;
import zmaster587.advancedRocketry.tile.satellite.TileSatelliteBuilder;
import zmaster587.advancedRocketry.tile.satellite.TileSatelliteTerminal;
import zmaster587.advancedRocketry.tile.satellite.TileTerraformingTerminal;
import zmaster587.advancedRocketry.tile.station.*;
import zmaster587.advancedRocketry.util.*;
import zmaster587.advancedRocketry.world.decoration.MapGenLander;
import zmaster587.advancedRocketry.world.provider.WorldProviderPlanet;
import zmaster587.advancedRocketry.world.type.WorldTypePlanetGen;
import zmaster587.advancedRocketry.world.type.WorldTypeSpace;
import zmaster587.libVulpes.LibVulpes;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.api.LibVulpesItems;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.block.*;
import zmaster587.libVulpes.block.multiblock.BlockMultiBlockComponentVisible;
import zmaster587.libVulpes.block.multiblock.BlockMultiBlockComponentVisibleAlphaTexture;
import zmaster587.libVulpes.block.multiblock.BlockMultiblockMachine;
import zmaster587.libVulpes.inventory.GuiHandler;
import zmaster587.libVulpes.items.ItemBlockMeta;
import zmaster587.libVulpes.items.ItemIngredient;
import zmaster587.libVulpes.items.ItemProjector;
import zmaster587.libVulpes.network.PacketHandler;
import zmaster587.libVulpes.network.PacketItemModifcation;
import zmaster587.libVulpes.tile.multiblock.TileMultiBlock;
import zmaster587.libVulpes.util.FluidUtils;
import zmaster587.libVulpes.util.HashedBlockPosition;
import zmaster587.libVulpes.util.InputSyncHandler;
import zmaster587.libVulpes.util.SingleEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static gregtech.api.unification.material.Materials.Gold;
import static gregtech.api.unification.material.Materials.Titanium;


@Mod(modid = "advancedrocketry",
        dependencies = "required-after:libvulpes;")
public class AdvancedRocketry {

    public static final RecipeHandler machineRecipes = new RecipeHandler();
    public static final Logger logger = LogManager.getLogger(Constants.modId);

    @SidedProxy(
            clientSide = "zmaster587.advancedRocketry.client.ClientProxy",
            serverSide = "zmaster587.advancedRocketry.common.CommonProxy"
    )

    public static CommonProxy proxy;
    public static String version;

    @Instance(value = Constants.modId)
    public static AdvancedRocketry instance;

    public static WorldType planetWorldType;
    public static WorldType spaceWorldType;

    public static CompatibilityMgr compat = new CompatibilityMgr();
    public static HashMap<AllowedProducts, HashSet<String>> modProducts = new HashMap<>();

    private static Configuration config;

    static {
        FluidRegistry.enableUniversalBucket(); // Must be called before preInit
    }

    //Biome registry.
    @SubscribeEvent
    public void register(RegistryEvent.Register<Biome> evt) {
        System.out.println("REGISTERING BIOMES");
        BiomeManager.registerBiomes(evt);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        version = event.getModMetadata().version;

        //Init API
        DimensionManager.planetWorldProvider = WorldProviderPlanet.class;
        AdvancedRocketryAPI.atomsphereSealHandler = SealableBlockHandler.INSTANCE;
        ((SealableBlockHandler) AdvancedRocketryAPI.atomsphereSealHandler).loadDefaultData();


        //Configuration  ---------------------------------------------------------------------------------------------

        config = new Configuration(new File(event.getModConfigurationDirectory(), "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/advancedRocketry.cfg"));
        zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().config = config;
        config.load();

        ARConfiguration.loadPreInit();
        //Load client and UI positioning stuff
        proxy.loadUILayout(config);

        config.save();

        //Register cap events
        MinecraftForge.EVENT_BUS.register(new CapabilityProtectiveArmor());

        //Register Packets
        PacketHandler.INSTANCE.addDiscriminator(PacketDimInfo.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketSatellite.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketStellarInfo.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketItemModifcation.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketOxygenState.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketStationUpdate.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketSpaceStationInfo.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketAtmSync.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketBiomeIDChange.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketStorageTileUpdate.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketLaserGun.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketAsteroidInfo.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketAirParticle.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketInvalidLocationNotify.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketConfigSync.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketFluidParticle.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketSatellitesUpdate.class);


        //if(zmaster587.advancedRocketry.api.Configuration.allowMakingItemsForOtherMods)
        MinecraftForge.EVENT_BUS.register(this);

        //Satellites ---------------------------------------------------------------------------------------------
        SatelliteRegistry.registerSatellite("optical", SatelliteOptical.class);
        SatelliteRegistry.registerSatellite("density", SatelliteDensity.class);
        SatelliteRegistry.registerSatellite("composition", SatelliteComposition.class);
        SatelliteRegistry.registerSatellite("mass", SatelliteMassScanner.class);
        SatelliteRegistry.registerSatellite("asteroidMiner", MissionOreMining.class);
        SatelliteRegistry.registerSatellite("gasMining", MissionGasCollection.class);
        SatelliteRegistry.registerSatellite("solarEnergy", SatelliteMicrowaveEnergy.class);
        SatelliteRegistry.registerSatellite("oreScanner", SatelliteOreMapping.class);
        SatelliteRegistry.registerSatellite("biomeChanger", SatelliteBiomeChanger.class);
        SatelliteRegistry.registerSatellite("weatherController", SatelliteWeatherController.class);


        //Entity Registration ---------------------------------------------------------------------------------------------
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "mountDummy"), EntityDummy.class, "mountDummy", 0, this, 16, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "rocket"), EntityRocket.class, "rocket", 1, this, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "laserNode"), EntityLaserNode.class, "laserNode", 2, instance, 256, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "deployedRocket"), EntityStationDeployedRocket.class, "deployedRocket", 3, this, 256, 600, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARAbductedItem"), EntityItemAbducted.class, "ARAbductedItem", 4, this, 127, 600, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARPlanetUIItem"), EntityUIPlanet.class, "ARPlanetUIItem", 5, this, 64, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARPlanetUIButton"), EntityUIButton.class, "ARPlanetUIButton", 6, this, 64, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARStarUIButton"), EntityUIStar.class, "ARStarUIButton", 7, this, 64, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARSpaceElevatorCapsule"), EntityElevatorCapsule.class, "ARSpaceElevatorCapsule", 8, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId, "ARHoverCraft"), EntityHoverCraft.class, "hovercraft", 9, this, 64, 3, true);

        //TileEntity Registration ---------------------------------------------------------------------------------------------
        GameRegistry.registerTileEntity(TileBrokenPart.class, "ARbrokenPart");
        GameRegistry.registerTileEntity(TileRocketAssemblingMachine.class, "ARrocketBuilder");
        GameRegistry.registerTileEntity(TileWarpCore.class, "ARwarpCore");
        //GameRegistry.registerTileEntity(TileModelRender.class, "ARmodelRenderer");
        GameRegistry.registerTileEntity(TileFuelingStation.class, "ARfuelingStation");
        GameRegistry.registerTileEntity(TileRocketMonitoringStation.class, "ARmonitoringStation");
        //GameRegistry.registerTileEntity(TileMissionController.class, "ARmissionControlComp");
        GameRegistry.registerTileEntity(TileOrbitalLaserDrill.class, "ARspaceLaser");
        GameRegistry.registerTileEntity(TilePrecisionAssembler.class, "ARprecisionAssembler");
        GameRegistry.registerTileEntity(TileObservatory.class, "ARobservatory");
        GameRegistry.registerTileEntity(TileCrystallizer.class, "ARcrystallizer");
        GameRegistry.registerTileEntity(TileCuttingMachine.class, "ARcuttingmachine");
        GameRegistry.registerTileEntity(TileDataBus.class, "ARdataBus");
        GameRegistry.registerTileEntity(TileSatelliteHatch.class, "ARsatelliteHatch");
        GameRegistry.registerTileEntity(TileGuidanceComputerAccessHatch.class, "ARguidanceComputerHatch");
        GameRegistry.registerTileEntity(TileSatelliteBuilder.class, "ARsatelliteBuilder");
        GameRegistry.registerTileEntity(TileSatelliteTerminal.class, "ARTileEntitySatelliteControlCenter");
        GameRegistry.registerTileEntity(TileTerraformingTerminal.class, "ARTileEntityTerraformingTerminal");
        GameRegistry.registerTileEntity(TileAstrobodyDataProcessor.class, "ARplanetAnalyser");
        GameRegistry.registerTileEntity(TileGuidanceComputer.class, "ARguidanceComputer");
        GameRegistry.registerTileEntity(TileElectricArcFurnace.class, "ARelectricArcFurnace");
        GameRegistry.registerTileEntity(TilePlanetSelector.class, "ARTilePlanetSelector");
        GameRegistry.registerTileEntity(TileLathe.class, "ARTileLathe");
        GameRegistry.registerTileEntity(TileRollingMachine.class, "ARTileMetalBender");
        GameRegistry.registerTileEntity(TileStationAssembler.class, "ARStationBuilder");
        GameRegistry.registerTileEntity(TileElectrolyser.class, "ARElectrolyser");
        GameRegistry.registerTileEntity(TileChemicalReactor.class, "ARChemicalReactor");
        GameRegistry.registerTileEntity(TileOxygenVent.class, "AROxygenVent");
        GameRegistry.registerTileEntity(TileGasChargePad.class, "AROxygenCharger");
        GameRegistry.registerTileEntity(TileCO2Scrubber.class, "ARCO2Scrubber");
        GameRegistry.registerTileEntity(TileWarpController.class, "ARStationMonitor");
        GameRegistry.registerTileEntity(TileAtmosphereDetector.class, "AROxygenDetector");
        GameRegistry.registerTileEntity(TileStationOrientationController.class, "AROrientationControl");
        GameRegistry.registerTileEntity(TileStationGravityController.class, "ARGravityControl");
        GameRegistry.registerTileEntity(TileLiquidPipe.class, "ARLiquidPipe");
        GameRegistry.registerTileEntity(TileDataPipe.class, "ARDataPipe");
        GameRegistry.registerTileEntity(TileEnergyPipe.class, "AREnergyPipe");
        GameRegistry.registerTileEntity(TileMicrowaveReciever.class, "ARMicrowaveReciever");
        GameRegistry.registerTileEntity(TileSuitWorkStation.class, "ARSuitWorkStation");
        GameRegistry.registerTileEntity(TileRocketLoader.class, "ARRocketLoader");
        GameRegistry.registerTileEntity(TileRocketUnloader.class, "ARRocketUnloader");
        GameRegistry.registerTileEntity(TileBiomeScanner.class, "ARBiomeScanner");
        GameRegistry.registerTileEntity(TileAtmosphereTerraformer.class, "ARAttTerraformer");
        GameRegistry.registerTileEntity(TileLandingPad.class, "ARLandingPad");
        GameRegistry.registerTileEntity(TileUnmannedVehicleAssembler.class, "ARStationDeployableRocketAssembler");
        GameRegistry.registerTileEntity(TileFluidTank.class, "ARFluidTank");
        GameRegistry.registerTileEntity(TileRocketFluidUnloader.class, "ARFluidUnloader");
        GameRegistry.registerTileEntity(TileRocketFluidLoader.class, "ARFluidLoader");
        GameRegistry.registerTileEntity(TileSolarPanel.class, "ARSolarGenerator");
        GameRegistry.registerTileEntity(TileDockingPort.class, "ARDockingPort");
        GameRegistry.registerTileEntity(TileStationAltitudeController.class, "ARStationAltitudeController");
        GameRegistry.registerTileEntity(TileRailgun.class, "ARRailgun");
        GameRegistry.registerTileEntity(TileHolographicPlanetSelector.class, "ARplanetHoloSelector");
        GameRegistry.registerTileEntity(TileForceFieldProjector.class, "ARForceFieldProjector");
        GameRegistry.registerTileEntity(TileSeal.class, "ARBlockSeal");
        GameRegistry.registerTileEntity(TileSpaceElevator.class, "ARSpaceElevator");
        GameRegistry.registerTileEntity(TileBeacon.class, "ARBeacon");
        GameRegistry.registerTileEntity(TileWirelessTransciever.class, "ARTransciever");
        GameRegistry.registerTileEntity(TileBlackHoleGenerator.class, "ARblackholegenerator");
        GameRegistry.registerTileEntity(TilePump.class, new ResourceLocation(Constants.modId, "ARpump"));
        GameRegistry.registerTileEntity(TileCentrifuge.class, new ResourceLocation(Constants.modId, "ARCentrifuge"));
        GameRegistry.registerTileEntity(TilePrecisionLaserEtcher.class, new ResourceLocation(Constants.modId, "ARPrecisionLaserEtcher"));
        GameRegistry.registerTileEntity(TileSolarArray.class, new ResourceLocation(Constants.modId, "ARSolarArray"));

        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableGravityController)
            GameRegistry.registerTileEntity(TileAreaGravityController.class, "ARGravityMachine");


        //Register machine recipes
        LibVulpes.registerRecipeHandler(TileCuttingMachine.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/CuttingMachine.xml");
        LibVulpes.registerRecipeHandler(TilePrecisionAssembler.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/PrecisionAssembler.xml");
        LibVulpes.registerRecipeHandler(TileChemicalReactor.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/ChemicalReactor.xml");
        LibVulpes.registerRecipeHandler(TileCrystallizer.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/Crystallizer.xml");
        LibVulpes.registerRecipeHandler(TileElectrolyser.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/Electrolyser.xml");
        LibVulpes.registerRecipeHandler(TileElectricArcFurnace.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/ElectricArcFurnace.xml");
        LibVulpes.registerRecipeHandler(TileLathe.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/Lathe.xml");
        LibVulpes.registerRecipeHandler(TileRollingMachine.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/RollingMachine.xml");
        LibVulpes.registerRecipeHandler(BlockSmallPlatePress.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/SmallPlatePress.xml");
        LibVulpes.registerRecipeHandler(TileCentrifuge.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/Centrifuge.xml");
        LibVulpes.registerRecipeHandler(TilePrecisionLaserEtcher.class, event.getModConfigurationDirectory().getAbsolutePath() + "/" + zmaster587.advancedRocketry.api.ARConfiguration.configFolder + "/PrecisionLaserEtcher.xml");


        //AUDIO

        //MOD-SPECIFIC ENTRIES --------------------------------------------------------------------------------------------------------------------------


        //Register Space Objects
        SpaceObjectManager.getSpaceManager().registerSpaceObjectType("genericObject", SpaceStationObject.class);


        //Register item/block crap
        proxy.preinit();

        //Register machines
        machineRecipes.registerMachine(TileElectrolyser.class);
        machineRecipes.registerMachine(TileCuttingMachine.class);
        machineRecipes.registerMachine(TileLathe.class);
        machineRecipes.registerMachine(TilePrecisionAssembler.class);
        machineRecipes.registerMachine(TileElectricArcFurnace.class);
        machineRecipes.registerMachine(TileChemicalReactor.class);
        machineRecipes.registerMachine(TileRollingMachine.class);
        machineRecipes.registerMachine(TileCrystallizer.class);
        machineRecipes.registerMachine(TileCentrifuge.class);
        machineRecipes.registerMachine(TilePrecisionLaserEtcher.class);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerEnchants(RegistryEvent.Register<Enchantment> evt) {
        //Enchantments
        AdvancedRocketryAPI.enchantmentSpaceProtection = new EnchantmentSpaceBreathing();
        AdvancedRocketryAPI.enchantmentSpaceProtection.setRegistryName(new ResourceLocation("advancedrocketry:spacebreathing"));
        evt.getRegistry().register(AdvancedRocketryAPI.enchantmentSpaceProtection);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerItems(RegistryEvent.Register<Item> evt) {
        //Items -------------------------------------------------------------------------------------
        AdvancedRocketryItems.itemWafer = new ItemIngredient(1).setRegistryName("advancedrocketry:wafer").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemCircuitPlate = new ItemIngredient(2).setRegistryName("advancedrocketry:circuitplate").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemIC = new ItemIngredient(6).setRegistryName("advancedrocketry:circuitIC").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemMisc = new ItemIngredient(2).setRegistryName("advancedrocketry:miscpart").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSawBlade = new ItemIngredient(1).setRegistryName("advancedrocketry:sawBladeIron").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSpaceStationChip = new ItemStationChip().setRegistryName("stationChip").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSpaceElevatorChip = new ItemSpaceElevatorChip().setRegistryName("elevatorChip").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemAsteroidChip = new ItemAsteroidChip().setRegistryName("asteroidChip").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSpaceStation = new ItemPackedStructure().setRegistryName("station");
        AdvancedRocketryItems.itemSmallAirlockDoor = new ItemDoor(AdvancedRocketryBlocks.blockAirLock).setRegistryName("smallAirlock").setCreativeTab(CommonProxy.tabAdvRocketry);
        //Short.MAX_VALUE is forge's wildcard, don't use it
        AdvancedRocketryItems.itemCarbonScrubberCartridge = new Item().setMaxDamage(Short.MAX_VALUE - 1).setRegistryName("carbonScrubberCartridge").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemLens = new ItemIngredient(1).setRegistryName("advancedrocketry:itemLens").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSatellitePowerSource = new ItemIngredient(2).setRegistryName("advancedrocketry:satellitePowerSource").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSatellitePrimaryFunction = new ItemIngredient(7).setRegistryName("advancedrocketry:satellitePrimaryFunction").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemThermite = new ItemThermite().setRegistryName("thermite").setCreativeTab(CommonProxy.tabAdvRocketry);

        //TODO: move registration in the case we have more than one chip type
        AdvancedRocketryItems.itemDataUnit = new ItemData().setRegistryName("advancedrocketry:dataUnit").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemOreScanner = new ItemOreScanner().setRegistryName("OreScanner").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemQuartzCrucible = new ItemBlock(AdvancedRocketryBlocks.blockQuartzCrucible).setRegistryName("qcrucible").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemSatellite = new ItemSatellite().setRegistryName("satellite").setCreativeTab(CommonProxy.tabAdvRocketry).setMaxStackSize(1);
        AdvancedRocketryItems.itemSatelliteIdChip = new ItemSatelliteIdentificationChip().setRegistryName("satelliteIdChip").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemPlanetIdChip = new ItemPlanetIdentificationChip().setRegistryName("planetIdChip").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemBiomeChanger = new ItemBiomeChanger().setRegistryName("biomeChanger").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemWeatherController = new ItemWeatherController().setRegistryName("weatherController").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemBasicLaserGun = new ItemBasicLaserGun().setRegistryName("basicLaserGun").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemHovercraft = new ItemHovercraft().setRegistryName("hovercraft").setCreativeTab(CommonProxy.tabAdvRocketry);

        //Suit Component Registration
        AdvancedRocketryItems.itemJetpack = new ItemJetpack().setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("jetPack");
        AdvancedRocketryItems.itemPressureTank = new ItemPressureTank(4, (int) (1000 * ARConfiguration.getCurrentConfig().suitTankCapacity)).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("advancedrocketry:pressureTank");
        AdvancedRocketryItems.itemUpgrade = new ItemUpgrade(6).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("advancedrocketry:itemUpgrade");
        AdvancedRocketryItems.itemAtmAnalyser = new ItemAtmosphereAnalzer().setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("atmAnalyser");
        AdvancedRocketryItems.itemBeaconFinder = new ItemBeaconFinder().setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("beaconFinder");

        //Armor registration
        AdvancedRocketryItems.itemSpaceSuit_Helmet = new ItemSpaceArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, 4).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("spaceHelmet");
        AdvancedRocketryItems.itemSpaceSuit_Chest = new ItemSpaceChest(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST, 6).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("spaceChest");
        AdvancedRocketryItems.itemSpaceSuit_Leggings = new ItemSpaceArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, 4).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("spaceLeggings");
        AdvancedRocketryItems.itemSpaceSuit_Boots = new ItemSpaceArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, 4).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("spaceBoots");
        AdvancedRocketryItems.itemSealDetector = new ItemSealDetector().setMaxStackSize(1).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("sealDetector");

        //Tools
        AdvancedRocketryItems.itemJackhammer = new ItemJackHammer(ToolMaterial.DIAMOND).setRegistryName("jackhammer").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryItems.itemJackhammer.setHarvestLevel("jackhammer", 3);
        AdvancedRocketryItems.itemJackhammer.setHarvestLevel("pickaxe", 3);

        //Register Satellite Properties
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 0), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteOptical.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 1), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteComposition.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 2), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteMassScanner.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 3), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteMicrowaveEnergy.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 4), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteOreMapping.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 5), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteBiomeChanger.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePrimaryFunction, 1, 6), new SatelliteProperties().setSatelliteType(SatelliteRegistry.getKey(SatelliteWeatherController.class)));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePowerSource, 1, 0), new SatelliteProperties().setPowerGeneration(4));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemSatellitePowerSource, 1, 1), new SatelliteProperties().setPowerGeneration(40));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(LibVulpesItems.itemBattery, 1, 0), new SatelliteProperties().setPowerStorage(10000));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(LibVulpesItems.itemBattery, 1, 1), new SatelliteProperties().setPowerStorage(40000));
        SatelliteRegistry.registerSatelliteProperty(new ItemStack(AdvancedRocketryItems.itemDataUnit, 1, 0), new SatelliteProperties().setMaxData(1000));


        //Item Registration
        //Circuit pieces
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemWafer.setTranslationKey("wafer"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemCircuitPlate.setTranslationKey("circuitPlate"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemIC.setTranslationKey("circuitIC"));
        //Chips
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSatelliteIdChip.setTranslationKey("satelliteIdChip"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemPlanetIdChip.setTranslationKey("planetIdChip"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemAsteroidChip.setTranslationKey("asteroidChip"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceElevatorChip.setTranslationKey("elevatorChip"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceStationChip.setTranslationKey("stationChip"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemDataUnit.setTranslationKey("dataUnit"));
        //Satellite bits
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSatellite.setTranslationKey("satellite"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSatellitePowerSource.setTranslationKey("satellitePowerSource"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSatellitePrimaryFunction.setTranslationKey("satellitePrimaryFunction"));
        //Spacesuit
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceSuit_Helmet.setTranslationKey("spaceHelmet"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceSuit_Chest.setTranslationKey("spaceChest"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceSuit_Leggings.setTranslationKey("spaceLeggings"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceSuit_Boots.setTranslationKey("spaceBoots"));
        //Space suit modifiers
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemPressureTank.setTranslationKey("pressureTank"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemUpgrade.setTranslationKey("itemUpgrade"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemBeaconFinder.setTranslationKey("beaconFinder"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemJetpack.setTranslationKey("jetPack"));
        //Handheld tools
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemAtmAnalyser.setTranslationKey("atmAnalyser"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSealDetector.setTranslationKey("sealDetector"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemOreScanner.setTranslationKey("oreScanner"));
        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableTerraforming) {
            LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemBiomeChanger.setTranslationKey("biomeChanger"));
            LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemWeatherController.setTranslationKey("weatherController"));
        }
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemJackhammer.setTranslationKey("jackHammer"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemBasicLaserGun.setTranslationKey("basicLaserGun"));
        //Misc
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemMisc.setTranslationKey("misc"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSawBlade.setTranslationKey("sawBladeIron"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemQuartzCrucible.setTranslationKey("qcrucible"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemLens.setTranslationKey("lens"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemThermite.setTranslationKey("thermite"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemCarbonScrubberCartridge.setTranslationKey("carbonScrubberCartridge"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSmallAirlockDoor.setTranslationKey("smallAirlockDoor"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemHovercraft.setTranslationKey("hoverCraft"));
        LibVulpesBlocks.registerItem(AdvancedRocketryItems.itemSpaceStation.setTranslationKey("spaceStation"));


        OreDictionary.registerOre("waferSilicon", new ItemStack(AdvancedRocketryItems.itemWafer, 1, 0));
        OreDictionary.registerOre("ingotCarbon", new ItemStack(AdvancedRocketryItems.itemMisc, 1, 1));
        OreDictionary.registerOre("itemLens", AdvancedRocketryItems.itemLens);
        OreDictionary.registerOre("lensPrecisionLaserEtcher", AdvancedRocketryItems.itemLens);
        OreDictionary.registerOre("itemSilicon", OreDictUnifier.get(OrePrefix.block, GTQTMaterials.Polysilicon));
        OreDictionary.registerOre("dustThermite", new ItemStack(AdvancedRocketryItems.itemThermite));
        OreDictionary.registerOre("slab", new ItemStack(Blocks.STONE_SLAB));
        OreDictionary.registerOre("blockWarpCoreCore", OreDictUnifier.get(OrePrefix.block, Gold));
        OreDictionary.registerOre("blockWarpCoreRim", OreDictUnifier.get(OrePrefix.block, Titanium));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerBlocks(RegistryEvent.Register<Block> evt) {
        //Blocks -------------------------------------------------------------------------------------
        //Machines
        //Machine parts
        AdvancedRocketryBlocks.blockConcrete = new Block(Material.ROCK).setRegistryName("concrete").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setResistance(16f);
        AdvancedRocketryBlocks.blockBlastBrick = new BlockMultiBlockComponentVisible(Material.ROCK).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("blastBrick").setHardness(3F).setResistance(15F);
        AdvancedRocketryBlocks.blockStructureTower = new BlockAlphaTexture(Material.IRON).setRegistryName("structuretower").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockLens = new BlockLens().setRegistryName("blockLens").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.3f);
        AdvancedRocketryBlocks.blockSolarPanel = new Block(Material.IRON).setRegistryName("solarPanel").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockSolarArrayPanel = new BlockMultiBlockComponentVisibleAlphaTexture(Material.IRON).setRegistryName("solararraypanel").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(1).setResistance(1f);
        AdvancedRocketryBlocks.blockQuartzCrucible = new BlockQuartzCrucible().setRegistryName("quartzcrucible").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockSawBlade = new BlockMotor(Material.IRON, 1f).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("sawBlade").setHardness(2f);
        //Singleblock machines
        AdvancedRocketryBlocks.blockPlatePress = new BlockSmallPlatePress().setRegistryName("platepress").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockForceFieldProjector = new BlockForceFieldProjector(Material.IRON).setRegistryName("forceFieldProjector").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockForceField = new BlockForceField(Material.BARRIER).setBlockUnbreakable().setResistance(6000000.0F).setRegistryName("forceField");
        AdvancedRocketryBlocks.blockVacuumLaser = new BlockFullyRotatable(Material.IRON).setRegistryName("vacuumLaser").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(4f);
        AdvancedRocketryBlocks.blockPump = new BlockTile(TilePump.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("blockPump").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockSuitWorkStation = new BlockSuitWorkstation(TileSuitWorkStation.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("suitWorkStation").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockPressureTank = new BlockPressurizedFluidTank(Material.IRON).setRegistryName("liquidTank").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockSolarGenerator = new BlockSolarGenerator(TileSolarPanel.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("solarGenerator");
        AdvancedRocketryBlocks.blockTransciever = new BlockTransciever(TileWirelessTransciever.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("wirelessTransciever").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //Multiblock machines
        //T1 processing
        AdvancedRocketryBlocks.blockArcFurnace = new BlockMultiblockMachine(TileElectricArcFurnace.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("arcfurnace").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockRollingMachine = new BlockMultiblockMachine(TileRollingMachine.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("rollingMachine").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockLathe = new BlockMultiblockMachine(TileLathe.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("lathe").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockCrystallizer = new BlockMultiblockMachine(TileCrystallizer.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("Crystallizer").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockCuttingMachine = new BlockMultiblockMachine(TileCuttingMachine.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("cuttingMachine").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockPrecisionAssembler = new BlockMultiblockMachine(TilePrecisionAssembler.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("precisionAssemblingMachine").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockElectrolyser = new BlockMultiblockMachine(TileElectrolyser.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("electrolyser").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockChemicalReactor = new BlockMultiblockMachine(TileChemicalReactor.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("chemicalReactor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //T2 processing
        AdvancedRocketryBlocks.blockPrecisionLaserEngraver = new BlockMultiblockMachine(TilePrecisionLaserEtcher.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("precisionlaseretcher").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockObservatory = new BlockMultiblockMachine(TileObservatory.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("observatory").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockPlanetAnalyser = new BlockMultiblockMachine(TileAstrobodyDataProcessor.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("planetanalyser").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockCentrifuge = new BlockMultiblockMachine(TileCentrifuge.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("centrifuge");
        AdvancedRocketryBlocks.blockSatelliteBuilder = new BlockMultiblockMachine(TileSatelliteBuilder.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("satelliteBuilder");
        //Energy
        AdvancedRocketryBlocks.blockBlackHoleGenerator = new BlockMultiblockMachine(TileBlackHoleGenerator.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("blackholegenerator").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockMicrowaveReciever = new BlockMultiblockMachine(TileMicrowaveReciever.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("microwaveReciever");
        AdvancedRocketryBlocks.blockSolarArray = new BlockMultiblockMachine(TileSolarArray.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("solararray").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //Aux/huge
        AdvancedRocketryBlocks.blockWarpCore = new BlockWarpCore(TileWarpCore.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("warpCore").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockBeacon = new BlockBeacon(TileBeacon.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("beacon").setHardness(3f);
        AdvancedRocketryBlocks.blockBiomeScanner = new BlockMultiblockMachine(TileBiomeScanner.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("biomeScanner").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockRailgun = new BlockMultiblockMachine(TileRailgun.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("railgun").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockSpaceElevatorController = new BlockMultiblockMachine(TileSpaceElevator.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("spaceElevatorController").setHardness(3f);
        //Configurable stuff
        if (ARConfiguration.getCurrentConfig().enableTerraforming)
            AdvancedRocketryBlocks.blockAtmosphereTerraformer = new BlockMultiblockMachine(TileAtmosphereTerraformer.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("terraformer").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        if (ARConfiguration.getCurrentConfig().enableGravityController)
            AdvancedRocketryBlocks.blockGravityMachine = new BlockMultiblockMachine(TileAreaGravityController.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("gravityMachine").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        if (ARConfiguration.getCurrentConfig().enableLaserDrill)
            AdvancedRocketryBlocks.blockSpaceLaser = new BlockOrbitalLaserDrill().setHardness(2f).setCreativeTab(CommonProxy.tabAdvRocketry);
        //Docking blocks
        AdvancedRocketryBlocks.blockLaunchpad = new BlockLinkedHorizontalTexture(Material.ROCK).setRegistryName("launchPad").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f).setResistance(10f);
        AdvancedRocketryBlocks.blockLandingPad = new BlockLandingPad(Material.ROCK).setRegistryName("landingPad").setHardness(3f).setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockDockingPort = new BlockStationModuleDockingPort(Material.IRON).setRegistryName("stationMarker").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //Rocket blocks
        AdvancedRocketryBlocks.blockGenericSeat = new BlockSeat(Material.CLOTH).setRegistryName("seat").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.5f);
        AdvancedRocketryBlocks.blockEngine = new BlockRocketMotor(Material.IRON).setRegistryName("rocketmotor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockBipropellantEngine = new BlockBipropellantRocketMotor(Material.IRON).setRegistryName("bipropellantrocketmotor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockAdvEngine = new BlockAdvancedRocketMotor(Material.IRON).setRegistryName("advRocketmotor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockAdvBipropellantEngine = new BlockAdvancedBipropellantRocketMotor(Material.IRON).setRegistryName("advbipropellantRocketmotor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockNuclearEngine = new BlockNuclearRocketMotor(Material.IRON).setRegistryName("nuclearrocketmotor").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockFuelTank = new BlockFuelTank(Material.IRON).setRegistryName("fuelTank").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockBipropellantFuelTank = new BlockBipropellantFuelTank(Material.IRON).setRegistryName("bipropellantfueltank").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockOxidizerFuelTank = new BlockOxidizerFuelTank(Material.IRON).setRegistryName("oxidizerfueltank").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockNuclearFuelTank = new BlockNuclearFuelTank(Material.IRON).setRegistryName("nuclearfueltank").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockNuclearCore = new BlockNuclearCore(Material.IRON).setRegistryName("nuclearcore").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockGuidanceComputer = new BlockTile(TileGuidanceComputer.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("guidanceComputer").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockIntake = new BlockIntake(Material.IRON).setRegistryName("intake").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockDrill = new BlockMiningDrill().setRegistryName("drill").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockLandingFloat = new Block(Material.IRON).setRegistryName("landingfloat").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(1).setResistance(1f);
        //Assembly machines
        AdvancedRocketryBlocks.blockRocketBuilder = new BlockTileWithMultitooltip(TileRocketAssemblingMachine.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("rocketBuilder").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockStationBuilder = new BlockTileWithMultitooltip(TileStationAssembler.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("stationBuilder").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockDeployableRocketBuilder = new BlockTileWithMultitooltip(TileUnmannedVehicleAssembler.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setRegistryName("deployableRocketBuilder").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //Infrastructure machines
        AdvancedRocketryBlocks.blockLoader = new BlockARHatch(Material.IRON).setRegistryName("loader").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockFuelingStation = new BlockTileRedstoneEmitter(TileFuelingStation.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("fuelingStation").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockMonitoringStation = new BlockTileNeighborUpdate(TileRocketMonitoringStation.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("monitoringstation");
        AdvancedRocketryBlocks.blockSatelliteControlCenter = new BlockTile(TileSatelliteTerminal.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("satelliteControlCenter");
        AdvancedRocketryBlocks.blockTerraformingTerminal = new BlockTileTerraformer(TileTerraformingTerminal.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("terraformingTerminal");

        //Station machines
        AdvancedRocketryBlocks.blockWarpShipMonitor = new BlockWarpController(TileWarpController.class, GuiHandler.guiId.MODULARNOINV.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f).setRegistryName("warpMonitor");
        AdvancedRocketryBlocks.blockOrientationController = new BlockTile(TileStationOrientationController.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("orientationController").setHardness(3f);
        AdvancedRocketryBlocks.blockGravityController = new BlockTileComparatorOverride(TileStationGravityController.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("gravityController").setHardness(3f);
        AdvancedRocketryBlocks.blockAltitudeController = new BlockTileComparatorOverride(TileStationAltitudeController.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("altitudeController").setHardness(3f);
        AdvancedRocketryBlocks.blockPlanetSelector = new BlockTile(TilePlanetSelector.class, GuiHandler.guiId.MODULARFULLSCREEN.ordinal()).setRegistryName("planetSelector").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockPlanetHoloSelector = new BlockHalfTile(TileHolographicPlanetSelector.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("planetHoloSelector").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        //Oxygen machines
        AdvancedRocketryBlocks.blockCO2Scrubber = new BlockTileComparatorOverride(TileCO2Scrubber.class, GuiHandler.guiId.MODULAR.ordinal()).setCreativeTab(CommonProxy.tabAdvRocketry).setRegistryName("oxygenScrubber").setHardness(3f);
        AdvancedRocketryBlocks.blockOxygenVent = new BlockTile(TileOxygenVent.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("oxygenVent").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockOxygenCharger = new BlockHalfTile(TileGasChargePad.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("oxygenCharger").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockOxygenDetection = new BlockRedstoneEmitter(Material.IRON, "advancedrocketry:oxygenDetection_active").setRegistryName("oxygenDetection").setHardness(3f).setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockPipeSealer = new BlockSeal(Material.IRON).setRegistryName("pipeSealer").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.5f);
        AdvancedRocketryBlocks.blockAirLock = new BlockDoor2(Material.IRON).setRegistryName("smallAirlockDoor").setHardness(3f).setResistance(8f);
        //Light sources
        AdvancedRocketryBlocks.blockUnlitTorch = new BlockTorchUnlit().setHardness(0.0F).setRegistryName("unlittorch");
        AdvancedRocketryBlocks.blockThermiteTorch = new BlockThermiteTorch().setRegistryName("thermiteTorch").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.1f).setLightLevel(1f);
        AdvancedRocketryBlocks.blockCircleLight = new Block(Material.IRON).setRegistryName("circleLight").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f).setLightLevel(1f);
        AdvancedRocketryBlocks.blockLightSource = new BlockLightSource();
        AdvancedRocketryBlocks.blockRocketFire = new BlockRocketFire();
        //Worldgen
        AdvancedRocketryBlocks.blockMoonTurf = new BlockRegolith().setMapColor(MapColor.SNOW).setHardness(0.5F).setRegistryName("moonturf").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockMoonTurfDark = new BlockRegolith().setMapColor(MapColor.CLAY).setHardness(0.5F).setRegistryName("moonturf_dark").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockHotTurf = new BlockRegolith().setMapColor(MapColor.NETHERRACK).setHardness(0.5F).setRegistryName("hotTurf").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockBasalt = new Block(Material.ROCK).setRegistryName("basalt").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(5f).setResistance(15f);
        AdvancedRocketryBlocks.blocksGeode = new Block(MaterialGeode.geode).setRegistryName("geode").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(6f).setResistance(2000F);
        AdvancedRocketryBlocks.blocksGeode.setHarvestLevel("jackhammer", 2);
        AdvancedRocketryBlocks.blockCrystal = new BlockCrystal().setRegistryName("crystal").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(2f);
        AdvancedRocketryBlocks.blockVitrifiedSand = new Block(Material.SAND).setRegistryName("vitrifiedSand").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.5F);
        AdvancedRocketryBlocks.blockCharcoalLog = new BlockCharcoalLog().setRegistryName("charcoallog").setCreativeTab(CommonProxy.tabAdvRocketry);
        AdvancedRocketryBlocks.blockElectricMushroom = new BlockElectricMushroom().setRegistryName("electricMushroom").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(0.0F);
        AdvancedRocketryBlocks.blockLightwoodWood = new BlockLightwoodWood().setRegistryName("alienwood").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.sblockLightwoodLeaves = new BlockLightwoodLeaves().setRegistryName("alienleaves").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockLightwoodSapling = new BlockLightwoodSapling().setRegistryName("alienSapling").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);
        AdvancedRocketryBlocks.blockLightwoodPlanks = new BlockLightwoodPlanks().setRegistryName("alienPlanks").setCreativeTab(CommonProxy.tabAdvRocketry).setHardness(3f);


        //Fluid definitions
        final ResourceLocation notFlowing = new ResourceLocation("advancedrocketry:blocks/fluid/oxygen_still");
        final ResourceLocation flowing = new ResourceLocation("advancedrocketry:blocks/fluid/oxygen_flow");
        AdvancedRocketryFluids.fluidOxygen = new Fluid("oxygen", notFlowing, flowing).setUnlocalizedName("oxygen").setGaseous(true).setDensity(-1000).setViscosity(1000).setColor(0xFF6CE2FF);
        AdvancedRocketryFluids.fluidHydrogen = new Fluid("hydrogen", notFlowing, flowing).setUnlocalizedName("hydrogen").setGaseous(true).setDensity(-1000).setViscosity(1000).setColor(0xFFDBC1C1);
        AdvancedRocketryFluids.fluidNitrogen = new Fluid("nitrogen", notFlowing, flowing).setUnlocalizedName("nitrogen").setGaseous(true).setDensity(-1000).setViscosity(1000).setColor(0xFFDFE5FE);
        AdvancedRocketryFluids.fluidRocketFuel = new Fluid("rocketFuel", notFlowing, flowing).setUnlocalizedName("rocketFuel").setGaseous(false).setLuminosity(2).setDensity(800).setViscosity(1500).setColor(0xFFE5D884);
        AdvancedRocketryFluids.fluidEnrichedLava = new Fluid("enrichedLava", new ResourceLocation("advancedrocketry:blocks/fluid/lava_still"), new ResourceLocation("advancedrocketry:blocks/fluid/lava_flow")).setUnlocalizedName("enrichedLava").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setColor(0xFFFFFFFF);

        //Fluid Registration
        if (!FluidRegistry.registerFluid(AdvancedRocketryFluids.fluidOxygen))
            AdvancedRocketryFluids.fluidOxygen = FluidRegistry.getFluid("oxygen");
        if (!FluidRegistry.registerFluid(AdvancedRocketryFluids.fluidHydrogen))
            AdvancedRocketryFluids.fluidHydrogen = FluidRegistry.getFluid("hydrogen");
        if (!FluidRegistry.registerFluid(AdvancedRocketryFluids.fluidNitrogen))
            AdvancedRocketryFluids.fluidNitrogen = FluidRegistry.getFluid("nitrogen");
        if (!FluidRegistry.registerFluid(AdvancedRocketryFluids.fluidRocketFuel))
            AdvancedRocketryFluids.fluidRocketFuel = FluidRegistry.getFluid("rocketFuel");
        if (!FluidRegistry.registerFluid(AdvancedRocketryFluids.fluidEnrichedLava))
            AdvancedRocketryFluids.fluidEnrichedLava = FluidRegistry.getFluid("enrichedLava");

        // For all intents and purposes, they're the same -- Mekanism compat
        FluidUtils.addFluidMapping(AdvancedRocketryFluids.fluidOxygen, "liquidoxygen");
        FluidUtils.addFluidMapping(AdvancedRocketryFluids.fluidOxygen, "oxynitrogenmix");
        FluidUtils.addFluidMapping(AdvancedRocketryFluids.fluidHydrogen, "liquidhydrogen");

        AdvancedRocketryBlocks.blockOxygenFluid = new BlockFluid(AdvancedRocketryFluids.fluidOxygen, Material.WATER).setRegistryName("oxygenFluidBlock").setCreativeTab(CreativeTabs.MISC);
        AdvancedRocketryBlocks.blockHydrogenFluid = new BlockFluid(AdvancedRocketryFluids.fluidHydrogen, Material.WATER).setRegistryName("hydrogenFluidBlock").setCreativeTab(CreativeTabs.MISC);
        AdvancedRocketryBlocks.blockNitrogenFluid = new BlockFluid(AdvancedRocketryFluids.fluidNitrogen, Material.WATER).setRegistryName("nitrogenFluidBlock").setCreativeTab(CreativeTabs.MISC);
        AdvancedRocketryBlocks.blockFuelFluid = new BlockFluid(AdvancedRocketryFluids.fluidRocketFuel, new MaterialLiquid(MapColor.YELLOW)).setRegistryName("rocketFuelBlock").setCreativeTab(CreativeTabs.MISC);
        AdvancedRocketryBlocks.blockEnrichedLavaFluid = new BlockEnrichedLava(AdvancedRocketryFluids.fluidEnrichedLava, Material.LAVA).setRegistryName("enrichedLavaBlock").setCreativeTab(CreativeTabs.MISC).setLightLevel(15);

        //Fluids
        FluidRegistry.addBucketForFluid(AdvancedRocketryFluids.fluidHydrogen);
        FluidRegistry.addBucketForFluid(AdvancedRocketryFluids.fluidNitrogen);
        FluidRegistry.addBucketForFluid(AdvancedRocketryFluids.fluidOxygen);
        FluidRegistry.addBucketForFluid(AdvancedRocketryFluids.fluidRocketFuel);
        FluidRegistry.addBucketForFluid(AdvancedRocketryFluids.fluidEnrichedLava);

        //Cables
        //TODO: add back after fixing the cable network
        //AdvancedRocketryBlocks.blockFluidPipe = new BlockLiquidPipe(Material.IRON).setRegistryName("liquidPipe").setCreativeTab(tabAdvRocketry).setHardness(1f);
        //AdvancedRocketryBlocks.blockDataPipe = new BlockDataCable(Material.IRON).setUnlocalizedName("dataPipe").setCreativeTab(tabAdvRocketry).setHardness(1f);
        //AdvancedRocketryBlocks.blockEnergyPipe = new BlockEnergyCable(Material.IRON).setRegistryName("energyPipe").setCreativeTab(tabAdvRocketry).setHardness(1f);
        //LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockDataPipe.setRegistryName("dataPipe"));
        //LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockEnergyPipe.setRegistryName("energyPipe"));
        //LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockFluidPipe.setRegistryName("liquidPipe"));


        //Machines
        //Machine parts
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockConcrete.setTranslationKey("concrete"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBlastBrick.setTranslationKey("blastBrick"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockStructureTower.setTranslationKey("structureTower"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLens.setTranslationKey("blockLens"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSolarPanel.setTranslationKey("solarPanel"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSolarArrayPanel.setTranslationKey("solararraypanel"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockQuartzCrucible.setTranslationKey("quartzcrucible"), null, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSawBlade.setTranslationKey("sawBlade"));
        //Singleblock machines
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPlatePress.setTranslationKey("platepress"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockForceFieldProjector.setTranslationKey("forceFieldProjector"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockForceField.setTranslationKey("forceField"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockVacuumLaser.setTranslationKey("vacuumLaser"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPump.setTranslationKey("blockPump"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSuitWorkStation.setTranslationKey("suitWorkStation"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPressureTank.setTranslationKey("liquidTank"), ItemBlockFluidTank.class, true);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSolarGenerator.setTranslationKey("solarGenerator"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockTransciever.setTranslationKey("wirelessTransciever"));
        //Multiblock machines
        //T1 processing
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockArcFurnace.setTranslationKey("arcfurnace"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockRollingMachine.setTranslationKey("rollingMachine"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLathe.setTranslationKey("lathe"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCrystallizer.setTranslationKey("crystallizer"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCuttingMachine.setTranslationKey("cuttingMachine"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPrecisionAssembler.setTranslationKey("precisionAssemblingMachine"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockElectrolyser.setTranslationKey("electrolyser"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockChemicalReactor.setTranslationKey("chemicalReactor"));
        //T2 processing
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPrecisionLaserEngraver.setTranslationKey("precisionlaseretcher"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockObservatory.setTranslationKey("observatory"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPlanetAnalyser.setTranslationKey("planetAnalyser"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCentrifuge.setTranslationKey("centrifuge"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSatelliteBuilder.setTranslationKey("satelliteBuilder"));
        //Energy
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBlackHoleGenerator.setTranslationKey("blackholegenerator"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockMicrowaveReciever.setTranslationKey("microwaveReciever"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSolarArray.setTranslationKey("solararray"));
        //Aux/huge
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockWarpCore.setTranslationKey("warpCore"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBeacon.setTranslationKey("beacon"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBiomeScanner.setTranslationKey("biomeScanner"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockRailgun.setTranslationKey("railgun"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSpaceElevatorController.setTranslationKey("spaceElevatorController"));
        //Configurable stuff
        if (ARConfiguration.getCurrentConfig().enableTerraforming)
            LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockAtmosphereTerraformer.setTranslationKey("terraformer"));
        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableGravityController)
            LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockGravityMachine.setTranslationKey("gravityMachine"));
        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableLaserDrill)
            LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSpaceLaser.setRegistryName("spaceLaser"));
        //Docking blocks
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLaunchpad.setTranslationKey("launchpad"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLandingPad.setTranslationKey("landingPad"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockDockingPort.setTranslationKey("stationMarker"));
        //Rocket blocks
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockGenericSeat.setTranslationKey("seat"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockEngine.setTranslationKey("rocketmotor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBipropellantEngine.setTranslationKey("bipropellantrocketmotor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockAdvEngine.setTranslationKey("advRocketmotor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockAdvBipropellantEngine.setTranslationKey("advbipropellantRocketmotor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockNuclearEngine.setTranslationKey("nuclearrocketmotor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockFuelTank.setTranslationKey("fuelTank"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBipropellantFuelTank.setTranslationKey("bipropellantfueltank"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOxidizerFuelTank.setTranslationKey("oxidizerfueltank"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockNuclearFuelTank.setTranslationKey("nuclearfueltank"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockNuclearCore.setTranslationKey("nuclearcore"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockGuidanceComputer.setTranslationKey("guidanceComputer"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockIntake.setTranslationKey("intake"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockDrill.setTranslationKey("drill"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLandingFloat.setTranslationKey("landingfloat"));
        //Assembly machines
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockRocketBuilder.setTranslationKey("rocketBuilder"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockStationBuilder.setTranslationKey("stationBuilder"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockDeployableRocketBuilder.setTranslationKey("deployableRocketBuilder"));
        //Infrastructure machines
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLoader.setTranslationKey("loader"), ItemBlockMeta.class, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockFuelingStation.setTranslationKey("fuelingStation"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockMonitoringStation.setTranslationKey("monitoringStation"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockSatelliteControlCenter.setTranslationKey("satelliteControlCenter"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockTerraformingTerminal.setTranslationKey("terraformingTerminal"));
        //Station machines
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockWarpShipMonitor.setTranslationKey("warpMonitor"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOrientationController.setTranslationKey("orientationController"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockGravityController.setTranslationKey("gravityController"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockAltitudeController.setTranslationKey("altitudeController"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPlanetSelector.setTranslationKey("planetSelector"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPlanetHoloSelector.setTranslationKey("planetHoloSelector"));
        //Oxygen machines
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCO2Scrubber.setTranslationKey("oxygenScrubber"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOxygenVent.setTranslationKey("oxygenVent"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOxygenCharger.setTranslationKey("oxygenCharger"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOxygenDetection.setTranslationKey("oxygenDetection"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockPipeSealer.setTranslationKey("pipeSealer"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockAirLock.setTranslationKey("airlock_door"));
        //Light sources
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockUnlitTorch.setTranslationKey("unlitTorch"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockThermiteTorch.setTranslationKey("thermiteTorch"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCircleLight.setTranslationKey("circleLight"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLightSource.setRegistryName("lightSource"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockRocketFire.setRegistryName("rocketfire"), null, false);
        //Worldgen
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockMoonTurf.setTranslationKey("moonturf"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockMoonTurfDark.setTranslationKey("moonturf_dark"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockHotTurf.setTranslationKey("hotTurf"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockBasalt.setTranslationKey("basalt"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blocksGeode.setTranslationKey("geode"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCrystal.setTranslationKey("crystal"), ItemBlockCrystal.class, true);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockVitrifiedSand.setTranslationKey("vitrifiedSand"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockCharcoalLog.setTranslationKey("charcoalLog"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockElectricMushroom.setTranslationKey("electricMushroom"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLightwoodWood.setTranslationKey("alienWood"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.sblockLightwoodLeaves.setTranslationKey("alienLeaves"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLightwoodSapling.setTranslationKey("alienSapling"));
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockLightwoodPlanks.setTranslationKey("alienPlanks"));
        //Fluids
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockOxygenFluid.setTranslationKey("oxygenFluid"), null, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockHydrogenFluid.setTranslationKey("hydrogenFluid"), null, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockNitrogenFluid.setTranslationKey("nitrogenFluid"), null, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockFuelFluid.setTranslationKey("rocketFuel"), null, false);
        LibVulpesBlocks.registerBlock(AdvancedRocketryBlocks.blockEnrichedLavaFluid.setTranslationKey("enrichedLavaFluid"), null, false);

        //OreDict stuff
        OreDictionary.registerOre("turfMoon", new ItemStack(AdvancedRocketryBlocks.blockMoonTurf));
        OreDictionary.registerOre("turfMoon", new ItemStack(AdvancedRocketryBlocks.blockMoonTurfDark));
        OreDictionary.registerOre("logWood", new ItemStack(AdvancedRocketryBlocks.blockLightwoodWood));
        OreDictionary.registerOre("plankWood", new ItemStack(AdvancedRocketryBlocks.blockLightwoodPlanks));
        OreDictionary.registerOre("treeLeaves", new ItemStack(AdvancedRocketryBlocks.sblockLightwoodLeaves));
        OreDictionary.registerOre("treeSapling", new ItemStack(AdvancedRocketryBlocks.blockLightwoodSapling));
        OreDictionary.registerOre("concrete", new ItemStack(AdvancedRocketryBlocks.blockConcrete));
        OreDictionary.registerOre("casingCentrifuge", new ItemStack(LibVulpesBlocks.blockAdvStructureBlock));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        proxy.preInitItems();
        proxy.preInitBlocks();
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent<IRecipe> evt) {
        //Register the machine recipes
        machineRecipes.registerAllMachineRecipes();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        ARAdvancements.register();
        proxy.init();

        zmaster587.advancedRocketry.cable.NetworkRegistry.registerFluidNetwork();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new zmaster587.advancedRocketry.inventory.GuiHandler());
        planetWorldType = new WorldTypePlanetGen("PlanetCold");
        spaceWorldType = new WorldTypeSpace("Space");

        //Biomes --------------------------------------------------------------------------------------

        String[] biomeBlackList = config.getStringList("BlacklistedBiomes", "Planet",
                new String[]{
                        Biomes.SKY.getRegistryName().toString(),
                        Biomes.HELL.getRegistryName().toString(),
                        Biomes.VOID.getRegistryName().toString(),
                },
                "List of Biomes to be blacklisted from spawning as BiomeIds during terraforming");
        String[] biomeHighPressure = config.getStringList("HighPressureBiomes", "Planet", new String[]{AdvancedRocketryBiomes.swampDeepBiome.getRegistryName().toString(), AdvancedRocketryBiomes.stormLandsBiome.getRegistryName().toString()}, "Biomes that only spawn on worlds with pressures over 125, will override blacklist.  Defaults: StormLands, DeepSwamp");
        String[] biomeSingle = config.getStringList("SingleBiomes", "Planet", new String[]{AdvancedRocketryBiomes.volcanicBarren.getRegistryName().toString(), AdvancedRocketryBiomes.swampDeepBiome.getRegistryName().toString(), AdvancedRocketryBiomes.crystalChasms.getRegistryName().toString(), AdvancedRocketryBiomes.alienForest.getRegistryName().toString(), Biomes.DESERT_HILLS.getRegistryName().toString(),
                Biomes.MUSHROOM_ISLAND.getRegistryName().toString(), Biomes.EXTREME_HILLS.getRegistryName().toString(), Biomes.ICE_PLAINS.getRegistryName().toString()}, "Some worlds have a chance of spawning single biomes contained in this list.  Defaults: deepSwamp, crystalChasms, alienForest, desert hills, mushroom island, extreme hills, ice plains");

        config.save();

        //Prevent these biomes from spawning normally
        AdvancedRocketryBiomes.instance.registerBlackListBiome(AdvancedRocketryBiomes.moonBiome);
        AdvancedRocketryBiomes.instance.registerBlackListBiome(AdvancedRocketryBiomes.moonBiomeDark);
        AdvancedRocketryBiomes.instance.registerBlackListBiome(AdvancedRocketryBiomes.hotDryBiome);
        AdvancedRocketryBiomes.instance.registerBlackListBiome(AdvancedRocketryBiomes.spaceBiome);
        AdvancedRocketryBiomes.instance.registerBlackListBiome(AdvancedRocketryBiomes.volcanic);

        //Read BlackList from config and register Blacklisted biomes
        for (String string : biomeBlackList) {
            try {
                Biome biome = AdvancedRocketryBiomes.getBiome(string);

                if (biome == null)
                    logger.warn(String.format("Error blackListing biome  \"%s\", a biome with that ID does not exist!", string));
                else
                    AdvancedRocketryBiomes.instance.registerBlackListBiome(biome);
            } catch (NumberFormatException e) {
                logger.warn("Error blackListing \"" + string + "\".  It is not a valid number or Biome ResourceLocation");
            }
        }

        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().blackListAllVanillaBiomes) {
            AdvancedRocketryBiomes.instance.blackListVanillaBiomes();
        }


        //Read and Register High Pressure biomes from config
        for (String string : biomeHighPressure) {
            try {
                Biome biome = AdvancedRocketryBiomes.getBiome(string);

                if (biome == null)
                    logger.warn(String.format("Error registering high pressure biome \"%s\", a biome with that ID does not exist!", string));
                else
                    AdvancedRocketryBiomes.instance.registerHighPressureBiome(biome);
            } catch (NumberFormatException e) {
                logger.warn("Error registering high pressure biome \"" + string + "\".  It is not a valid number or Biome ResourceLocation");
            }
        }

        //Read and Register Single biomes from config
        for (String string : biomeSingle) {
            try {
                Biome biome = AdvancedRocketryBiomes.getBiome(string);

                if (biome == null)
                    logger.warn(String.format("Error registering single biome \"%s\", a biome with that ID does not exist!", string));
                else
                    AdvancedRocketryBiomes.instance.registerSingleBiome(biome);
            } catch (NumberFormatException e) {
                logger.warn("Error registering single biome \"" + string + "\".  It is not a valid number or Biome ResourceLocation");
            }
        }


        //Data mapping 'D'

        List<BlockMeta> list = new LinkedList<>();
        list.add(new BlockMeta(AdvancedRocketryBlocks.blockLoader, 0));
        list.add(new BlockMeta(AdvancedRocketryBlocks.blockLoader, 8));
        TileMultiBlock.addMapping('D', list);

        machineRecipes.createAutoGennedRecipes(modProducts);
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        CapabilitySpaceArmor.register();
        //Need to raise the Max Entity Radius to allow player interaction with rockets
        World.MAX_ENTITY_RADIUS = 20;

        //Register multiblock items with the projector
        //Basic processing machines
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileElectricArcFurnace(), (BlockTile) AdvancedRocketryBlocks.blockArcFurnace);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileRollingMachine(), (BlockTile) AdvancedRocketryBlocks.blockRollingMachine);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileLathe(), (BlockTile) AdvancedRocketryBlocks.blockLathe);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileCrystallizer(), (BlockTile) AdvancedRocketryBlocks.blockCrystallizer);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileCuttingMachine(), (BlockTile) AdvancedRocketryBlocks.blockCuttingMachine);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TilePrecisionAssembler(), (BlockTile) AdvancedRocketryBlocks.blockPrecisionAssembler);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileElectrolyser(), (BlockTile) AdvancedRocketryBlocks.blockElectrolyser);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileChemicalReactor(), (BlockTile) AdvancedRocketryBlocks.blockChemicalReactor);
        //T2 processing machines
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TilePrecisionLaserEtcher(), (BlockTile) AdvancedRocketryBlocks.blockPrecisionLaserEngraver);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileObservatory(), (BlockTile) AdvancedRocketryBlocks.blockObservatory);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileAstrobodyDataProcessor(), (BlockTile) AdvancedRocketryBlocks.blockPlanetAnalyser);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileCentrifuge(), (BlockTile) AdvancedRocketryBlocks.blockCentrifuge);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileSatelliteBuilder(), (BlockTile) AdvancedRocketryBlocks.blockSatelliteBuilder);
        //Power generation
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileBlackHoleGenerator(), (BlockTile) AdvancedRocketryBlocks.blockBlackHoleGenerator);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileMicrowaveReciever(), (BlockTile) AdvancedRocketryBlocks.blockMicrowaveReciever);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileSolarArray(), (BlockTile) AdvancedRocketryBlocks.blockSolarArray);
        //Auxillary machines
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileWarpCore(), (BlockTile) AdvancedRocketryBlocks.blockWarpCore);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileBeacon(), (BlockTile) AdvancedRocketryBlocks.blockBeacon);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileBiomeScanner(), (BlockTile) AdvancedRocketryBlocks.blockBiomeScanner);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileRailgun(), (BlockTile) AdvancedRocketryBlocks.blockRailgun);
        ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileSpaceElevator(), (BlockTile) AdvancedRocketryBlocks.blockSpaceElevatorController);
        //Config-controlled machines
        if (ARConfiguration.getCurrentConfig().enableTerraforming)
            ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileAtmosphereTerraformer(), (BlockTile) AdvancedRocketryBlocks.blockAtmosphereTerraformer);
        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableGravityController)
            ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileAreaGravityController(), (BlockTile) AdvancedRocketryBlocks.blockGravityMachine);
        if (zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().enableLaserDrill)
            ((ItemProjector) LibVulpesItems.itemHoloProjector).registerMachine(new TileOrbitalLaserDrill(), (BlockTile) AdvancedRocketryBlocks.blockSpaceLaser);

        proxy.registerEventHandlers();
        proxy.registerKeyBindings();
        //TODO: debug
        //ClientCommandHandler.instance.registerCommand(new Debugger());

        PlanetEventHandler handle = new PlanetEventHandler();
        MinecraftForge.EVENT_BUS.register(handle);
        MinecraftForge.ORE_GEN_BUS.register(handle);

        CableTickHandler cable = new CableTickHandler();
        MinecraftForge.EVENT_BUS.register(cable);

        InputSyncHandler inputSync = new InputSyncHandler();
        MinecraftForge.EVENT_BUS.register(inputSync);

        MinecraftForge.EVENT_BUS.register(new MapGenLander());
        AdvancedRocketryAPI.gravityManager = new GravityHandler();

        CompatibilityMgr.isSpongeInstalled = Loader.isModLoaded("sponge");
        // End compat stuff

        MinecraftForge.EVENT_BUS.register(SpaceObjectManager.getSpaceManager());

        PacketHandler.init();

        ForgeChunkManager.setForcedChunkLoadingCallback(instance, new WorldEvents());

        //Register space dimension
        net.minecraftforge.common.DimensionManager.registerDimension(zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().spaceDimId, DimensionManager.spaceDimensionType);

        ARConfiguration.loadPostInit();

        //Add the overworld as a discovered planet
        zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().initiallyKnownPlanets.add(0);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        for (int dimId : DimensionManager.getInstance().getLoadedDimensions()) {
            DimensionProperties properties = DimensionManager.getInstance().getDimensionProperties(dimId);
            if (!properties.isNativeDimension && properties.getId() == zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().MoonId && !Loader.isModLoaded("GalacticraftCore")) {
                properties.isNativeDimension = true;
            }
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new WorldCommand());

        //Regenerate Chemical Reactor armor recipes
        TileChemicalReactor.reloadRecipesSpecial();

        DimensionManager.getInstance().createAndLoadDimensions();
    }


    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        zmaster587.advancedRocketry.dimension.DimensionManager.getInstance().onServerStopped();
        //zmaster587.advancedRocketry.cable.NetworkRegistry.clearNetworks();
        SpaceObjectManager.getSpaceManager().onServerStopped();
        zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().MoonId = Constants.INVALID_PLANET;
        ((BlockSeal) AdvancedRocketryBlocks.blockPipeSealer).clearMap();
        DimensionManager.dimOffset = config.getInt("minDimension", "Planet", 2, -127, 8000, "Dimensions including and after this number are allowed to be made into planets");
        zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().spaceDimId = config.get(Configuration.CATEGORY_GENERAL, "spaceStationId", -2, "Dimension ID to use for space stations").getInt();
        WeightEngine.INSTANCE.save();
    }

    @SubscribeEvent
    public void registerOre(OreRegisterEvent event) {
        //Register ore products
        if (!zmaster587.advancedRocketry.api.ARConfiguration.getCurrentConfig().allowMakingItemsForOtherMods)
            return;

        for (AllowedProducts product : AllowedProducts.getAllAllowedProducts()) {
            if (event.getName().startsWith(product.name().toLowerCase(Locale.ENGLISH))) {
                HashSet<String> list = modProducts.computeIfAbsent(product, k -> new HashSet<>());
                list.add(event.getName().substring(product.name().length()));
            }
        }
    }
}
