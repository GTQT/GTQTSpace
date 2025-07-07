package meowmel.gtqtspace.common;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.registry.MTEManager;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import meowmel.gtqtspace.api.unifications.OrePrefixAdditions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(
		modid = "gtqtspace"
)
public class EventLoader {

	public EventLoader() {
	}

	@SubscribeEvent(
			priority = EventPriority.HIGH
	)
	public static void registerMaterials(MaterialEvent event) {
		GTQTSpaceMaterials.register();
		//在此处注册材料
		OrePrefixAdditions.init();
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void registerMTERegistry(MTEManager.MTERegistryEvent event) {
		GregTechAPI.mteManager.createRegistry(GTQTSpace.MODID);
	}
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void createMaterialRegistry(MaterialRegistryEvent event) {
		GregTechAPI.materialManager.createRegistry(GTQTSpace.MODID);
	}
}