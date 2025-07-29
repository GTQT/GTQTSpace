package meowmel.gtqtspace.common;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.registry.MTEManager;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import keqing.gtqtcore.api.unification.ore.GTQTStoneTypes;
import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials;
import meowmel.gtqtspace.api.unifications.OrePrefixAdditions;
import meowmel.gtqtspace.api.unifications.ore.GTQTSStoneTypes;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(
		modid = "gtqtspace"
)
public class EventLoader {

	public EventLoader() {
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void registerMaterials(MaterialEvent event) {
		GTQTSpaceMaterials.register();
		OrePrefixAdditions.init();
	}
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		GTQTSStoneTypes.init();
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