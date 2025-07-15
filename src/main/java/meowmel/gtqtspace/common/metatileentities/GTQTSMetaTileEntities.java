package meowmel.gtqtspace.common.metatileentities;

import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.common.metatileentities.multiblock.generator.MetaTileEntityWindGenerator;
import meowmel.gtqtspace.common.metatileentities.multiblock.multiblockpart.MetaTileEntitySpaceMaintenanceHatch;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.*;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidController;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidDrill;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidSearch;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidSolve;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.MetaTileEntityCoreTower;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SolarPlate.MetaTileEntitySolarPlate;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SolarPlate.MetaTileEntitySolarPlateController;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.Dock;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockBuilder;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockManager;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.MetaTileEntitySpaceElevator;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch.MetaTileEntityElevatorProviderHatch;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.hatch.MetaTileEntityElevatorReceiverHatch;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntityAssemblerModule;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntityMiningModule;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules.MetaTileEntityPumpingModule;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static meowmel.gtqtspace.api.GTQTSValue.gtqtspaceId;

public class GTQTSMetaTileEntities {

    public static MetaTileEntityCoreTower TRANSPORT;

    public static MetaTileEntitySolarPlate SOLAR_PLATE;
    public static MetaTileEntitySolarPlateController SOLAR_PLATE_CONTROLLER;

    public static AsteroidController ASTEROID_CONTROLLER;
    public static AsteroidSolve ASTEROID_SOLVE;
    public static AsteroidSearch ASTEROID_SEARCH;
    public static AsteroidDrill ASTEROID_DRILL;

    public static DockManager DOCK_MANGER;
    public static DockBuilder DOCK_BUILDER;
    public static Dock DOCK;
    ///////////////////////////////////////////////////
    public static MetaTileEntityIndustrialBender INDUSTRIAL_BENDER;
    public static MetaTileEntityIndustrialCutter INDUSTRIAL_CUTTER;
    public static MetaTileEntityIndustrialForgeHammer INDUSTRIAL_FORGE_HAMMER;
    public static MetaTileEntityIndustrialExtruder INDUSTRIAL_EXTRUDER;
    public static MetaTileEntityIndustrialWiremill INDUSTRIAL_WIREMILL;
    public static MetaTileEntityIndustrialMixer INDUSTRIAL_MIXER;
    public static MetaTileEntityIndustrialExtractor INDUSTRIAL_EXTRACTOR;
    public static MetaTileEntityIndustrialAssembler INDUSTRIAL_ASSEMBLER;
    public static MetaTileEntityIndustrialEngraver INDUSTRIAL_ENGRAVER;
    public static MetaTileEntityIndustrialFluidSolidifier INDUSTRIAL_FLUID_SOLIDIFIER;
    public static MetaTileEntityIndustrialBrewery INDUSTRIAL_BREWERY;
    public static MetaTileEntityIndustrialAutoclave INDUSTRIAL_AUTOCLAVE;
    public static MetaTileEntityIndustrialArcFurnace INDUSTRIAL_ARC_FURNACE;
    public static MetaTileEntityIndustrialMacerator INDUSTRIAL_MACERATOR;
    public static MetaTileEntityIndustrialCentrifuge INDUSTRIAL_CENTRIFUGE;
    public static MetaTileEntityIndustrialSifter INDUSTRIAL_SIFTER;
    public static MetaTileEntityIndustrialElectrolyzer INDUSTRIAL_ELECTROLYZER;
    public static MetaTileEntityIndustrialOreWasher INDUSTRIAL_ORE_WASHER;
    public static MetaTileEntityIndustrialPacker INDUSTRIAL_PACKER;
    public static MetaTileEntityIndustrialGasCollector INDUSTRIAL_GAS_COLLECTOR;
    public static MetaTileEntityIndustrialChemicalPlant INDUSTRIAL_CHEMICAL_PLANT;

    public static MetaTileEntitySpaceMaintenanceHatch SPACE_MAINTENANCE_HATCH;

    public static MetaTileEntityPumpingModule[] PUMP_MODULE = new MetaTileEntityPumpingModule[3];
    public static MetaTileEntityMiningModule[] MINING_MODULE = new MetaTileEntityMiningModule[3];
    public static MetaTileEntityAssemblerModule[] ASSEMBLER_MODULE = new MetaTileEntityAssemblerModule[3];
    public static MetaTileEntityElevatorProviderHatch ELEVATOR_PROVIDER_HATCH;
    public static MetaTileEntityElevatorReceiverHatch ELEVATOR_RECEIVER_HATCH;
    public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;

    public static MetaTileEntityWindGenerator WIND_GENERATOR;

    public static void initialization() {
        TRANSPORT = registerMetaTileEntity(1, new MetaTileEntityCoreTower(gtqtspaceId("transport")));
        SOLAR_PLATE = registerMetaTileEntity(2, new MetaTileEntitySolarPlate(gtqtspaceId("solar_plate")));
        SOLAR_PLATE_CONTROLLER = registerMetaTileEntity(3, new MetaTileEntitySolarPlateController(gtqtspaceId("solar_plate_controller")));

        ASTEROID_CONTROLLER = registerMetaTileEntity(4, new AsteroidController(gtqtspaceId("asteroid_controller")));
        ASTEROID_SOLVE = registerMetaTileEntity(5, new AsteroidSolve(gtqtspaceId("asteroid_solve")));
        ASTEROID_SEARCH = registerMetaTileEntity(6, new AsteroidSearch(gtqtspaceId("asteroid_search")));
        ASTEROID_DRILL = registerMetaTileEntity(7, new AsteroidDrill(gtqtspaceId("asteroid_drill")));

        DOCK_MANGER = registerMetaTileEntity(8, new DockManager(gtqtspaceId("dock_manager")));
        DOCK_BUILDER = registerMetaTileEntity(9, new DockBuilder(gtqtspaceId("dock_builder")));
        DOCK = registerMetaTileEntity(10, new Dock(gtqtspaceId("dock")));

        WIND_GENERATOR = registerMetaTileEntity(11, new MetaTileEntityWindGenerator(gtqtspaceId("wind_generator")));

        // ------------------ Initialized Multiblock Machines -------------------
        INDUSTRIAL_BENDER = registerMetaTileEntity(20, new MetaTileEntityIndustrialBender(gtqtspaceId("industrial_bender")));
        INDUSTRIAL_CUTTER = registerMetaTileEntity(21, new MetaTileEntityIndustrialCutter(gtqtspaceId("industrial_cutter")));
        INDUSTRIAL_FORGE_HAMMER = registerMetaTileEntity(22, new MetaTileEntityIndustrialForgeHammer(gtqtspaceId("industrial_forge_hammer")));
        INDUSTRIAL_EXTRUDER = registerMetaTileEntity(23, new MetaTileEntityIndustrialExtruder(gtqtspaceId("industrial_extruder")));
        INDUSTRIAL_WIREMILL = registerMetaTileEntity(24, new MetaTileEntityIndustrialWiremill(gtqtspaceId("industrial_wiremill")));
        INDUSTRIAL_MIXER = registerMetaTileEntity(25, new MetaTileEntityIndustrialMixer(gtqtspaceId("industrial_mixer")));
        INDUSTRIAL_EXTRACTOR = registerMetaTileEntity(26, new MetaTileEntityIndustrialExtractor(gtqtspaceId("industrial_extractor")));
        INDUSTRIAL_ASSEMBLER = registerMetaTileEntity(27, new MetaTileEntityIndustrialAssembler(gtqtspaceId("industrial_assembler")));
        INDUSTRIAL_ENGRAVER = registerMetaTileEntity(28, new MetaTileEntityIndustrialEngraver(gtqtspaceId("industrial_laser_engraver")));
        INDUSTRIAL_FLUID_SOLIDIFIER = registerMetaTileEntity(29, new MetaTileEntityIndustrialFluidSolidifier(gtqtspaceId("industrial_fluid_solidifier")));
        INDUSTRIAL_BREWERY = registerMetaTileEntity(30, new MetaTileEntityIndustrialBrewery(gtqtspaceId("industrial_brewery")));
        INDUSTRIAL_AUTOCLAVE = registerMetaTileEntity(31, new MetaTileEntityIndustrialAutoclave(gtqtspaceId("industrial_autoclave")));
        INDUSTRIAL_ARC_FURNACE = registerMetaTileEntity(32, new MetaTileEntityIndustrialArcFurnace(gtqtspaceId("industrial_arc_furnace")));
        INDUSTRIAL_MACERATOR = registerMetaTileEntity(33, new MetaTileEntityIndustrialMacerator(gtqtspaceId("industrial_macerator")));
        INDUSTRIAL_CENTRIFUGE = registerMetaTileEntity(34, new MetaTileEntityIndustrialCentrifuge(gtqtspaceId("industrial_centrifuge")));
        INDUSTRIAL_SIFTER = registerMetaTileEntity(35, new MetaTileEntityIndustrialSifter(gtqtspaceId("industrial_sifter")));
        INDUSTRIAL_ELECTROLYZER = registerMetaTileEntity(36, new MetaTileEntityIndustrialElectrolyzer(gtqtspaceId("industrial_electrolyzer")));
        INDUSTRIAL_ORE_WASHER = registerMetaTileEntity(37, new MetaTileEntityIndustrialOreWasher(gtqtspaceId("industrial_ore_washer")));
        INDUSTRIAL_PACKER = registerMetaTileEntity(38, new MetaTileEntityIndustrialPacker(gtqtspaceId("industrial_packer")));
        INDUSTRIAL_GAS_COLLECTOR = registerMetaTileEntity(39, new MetaTileEntityIndustrialGasCollector(gtqtspaceId("industrial_gas_collector")));
        INDUSTRIAL_CHEMICAL_PLANT = registerMetaTileEntity(40, new MetaTileEntityIndustrialChemicalPlant(gtqtspaceId("industrial_chemical_plant")));
        //高级机器
        //电装 部装 合金
        SPACE_MAINTENANCE_HATCH = registerMetaTileEntity(90, new MetaTileEntitySpaceMaintenanceHatch(gtqtspaceId("space_maintenance_hatch")));

        ELEVATOR_PROVIDER_HATCH = registerMetaTileEntity(95, new MetaTileEntityElevatorProviderHatch(gtqtspaceId("elevator_provider")));
        ELEVATOR_RECEIVER_HATCH = registerMetaTileEntity(96, new MetaTileEntityElevatorReceiverHatch(gtqtspaceId("elevator_receiver")));

        SPACE_ELEVATOR = registerMetaTileEntity(100, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));

        PUMP_MODULE[0] = registerMetaTileEntity(101, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_1"), 1, SpaceModulesType.PUMP_MODULE));
        PUMP_MODULE[1] = registerMetaTileEntity(102, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_2"), 2, SpaceModulesType.PUMP_MODULE));
        PUMP_MODULE[2] = registerMetaTileEntity(103, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_3"), 3, SpaceModulesType.PUMP_MODULE));
        MINING_MODULE[0] = registerMetaTileEntity(104, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_1"), 1, SpaceModulesType.MINING_MODULE));
        MINING_MODULE[1] = registerMetaTileEntity(105, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_2"), 2, SpaceModulesType.MINING_MODULE));
        MINING_MODULE[2] = registerMetaTileEntity(106, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_3"), 3, SpaceModulesType.MINING_MODULE));
        ASSEMBLER_MODULE[0] = registerMetaTileEntity(107, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_1"), 1, SpaceModulesType.ASSEMBLY_MODULE));
        ASSEMBLER_MODULE[1] = registerMetaTileEntity(108, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_2"), 2, SpaceModulesType.ASSEMBLY_MODULE));
        ASSEMBLER_MODULE[2] = registerMetaTileEntity(109, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_3"), 3, SpaceModulesType.ASSEMBLY_MODULE));
    }
}
