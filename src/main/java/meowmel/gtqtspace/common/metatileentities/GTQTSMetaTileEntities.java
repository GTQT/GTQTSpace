package meowmel.gtqtspace.common.metatileentities;

import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem.MetaTileEntityLargeBender;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.common.metatileentities.multiblock.generator.MetaTileEntityWindGenerator;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.MetaTileEntityIndustrialBender;
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
        INDUSTRIAL_BENDER = registerMetaTileEntity(20,  new MetaTileEntityIndustrialBender(gtqtspaceId("industrial_bender")));


        ELEVATOR_PROVIDER_HATCH =  registerMetaTileEntity(95, new MetaTileEntityElevatorProviderHatch(gtqtspaceId("elevator_provider")));
        ELEVATOR_RECEIVER_HATCH =  registerMetaTileEntity(96, new MetaTileEntityElevatorReceiverHatch(gtqtspaceId("elevator_receiver")));

        SPACE_ELEVATOR= registerMetaTileEntity(100, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));

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
