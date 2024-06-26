package keqing.gtqtspace.common.metatileentities;

import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySatelliteAssembler;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySentryArray;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySpaceElevator;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.suvery.MetaTileEntityCosmicRayDetector;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.suvery.MetaTileEntitySatelliteSuvery;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtspace.api.GTQTSValue.gtqtspaceId;

public class GTQTSMetaTileEntities {
	public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;
	public static MetaTileEntityCosmicRayDetector COSMIC_RAY_DETECTOR;
	public static MetaTileEntitySatelliteAssembler SATELLITE_ASSEMBLER;
	public static MetaTileEntitySentryArray SENTRY_ARRAY;
	public static MetaTileEntitySatelliteSuvery SATELLITE_SUVERY;

	public static void initialization() {
		//GTQTSLog.logger.info("Registering MetaTileEntities");
		SPACE_ELEVATOR = registerMetaTileEntity(5000, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));
		COSMIC_RAY_DETECTOR = registerMetaTileEntity(5001, new MetaTileEntityCosmicRayDetector(gtqtspaceId("cosmic_ray_detector")));
		SATELLITE_SUVERY = registerMetaTileEntity(5002, new MetaTileEntitySatelliteSuvery(gtqtspaceId("satellite_suvery")));
		SATELLITE_ASSEMBLER = registerMetaTileEntity(5003, new MetaTileEntitySatelliteAssembler(gtqtspaceId("satellite_assembler")));
		SENTRY_ARRAY = registerMetaTileEntity(5004, new MetaTileEntitySentryArray(gtqtspaceId("sentry_array")));
	}
}
