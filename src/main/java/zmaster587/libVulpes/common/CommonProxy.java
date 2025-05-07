package zmaster587.libVulpes.common;

import gregtech.api.block.VariantItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import zmaster587.libVulpes.common.block.LibVulpesMetaBlocks;
import zmaster587.libVulpes.network.BasePacket;

import java.util.Objects;
import java.util.function.Function;
@Mod.EventBusSubscriber(
		modid = "libvulpes"
)
public class CommonProxy {
	public String getLocalizedString(String str) {
		return str;
	}

	public void registerEventHandlers() {
		
	}

	public void spawnParticle(String particle, World world, double x, double y,
			double z, double motionX, double motionY, double motionZ) {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		
	}

	public void addScheduledTask(BasePacket packet) {
		// TODO Auto-generated method stub
		
	}

	public void playSound(World world, BlockPos pos, SoundEvent event,
			SoundCategory cat, float volume, float pitch) {
		
		
		
	}

	public void playSound(Object sound) {
		
	}

	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	public void preInitBlocks() {
		// TODO Auto-generated method stub
		
	}

	public void preInitItems() {
		// TODO Auto-generated method stub
		
	}
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(LibVulpesMetaBlocks.MOTOR_CASING);
		registry.register(LibVulpesMetaBlocks.PISTON_CASING);
		registry.register(LibVulpesMetaBlocks.PUMP_CASING);
		registry.register(LibVulpesMetaBlocks.CONVEYOR_CASING);
		registry.register(LibVulpesMetaBlocks.ROBOT_ARM_CASING);
		registry.register(LibVulpesMetaBlocks.EMITTER_CASING);
		registry.register(LibVulpesMetaBlocks.SENSOR_CASING);
		registry.register(LibVulpesMetaBlocks.FIELD_GEN_CASING);
	}
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(createItemBlock(LibVulpesMetaBlocks.MOTOR_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.PISTON_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.PUMP_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.CONVEYOR_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.ROBOT_ARM_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.EMITTER_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.SENSOR_CASING, VariantItemBlock::new));
		registry.register(createItemBlock(LibVulpesMetaBlocks.FIELD_GEN_CASING, VariantItemBlock::new));
	}

	private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
		ItemBlock itemBlock = producer.apply(block);
		itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
		return itemBlock;
	}
}
