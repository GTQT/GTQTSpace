package zmaster587.libVulpes.common;

import gregtech.api.block.VariantItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.api.LibVulpesItems;
import zmaster587.libVulpes.common.block.LibVulpesMetaBlocks;
import zmaster587.libVulpes.items.ItemBlockMeta;
import zmaster587.libVulpes.network.BasePacket;
import zmaster587.libVulpes.tile.TileInventoriedPointer;
import zmaster587.libVulpes.tile.TilePointer;
import zmaster587.libVulpes.tile.TileSchematic;
import zmaster587.libVulpes.tile.energy.TileCoalGenerator;
import zmaster587.libVulpes.tile.energy.TileCreativePowerInput;
import zmaster587.libVulpes.tile.energy.TileForgePowerInput;
import zmaster587.libVulpes.tile.energy.TileForgePowerOutput;
import zmaster587.libVulpes.tile.multiblock.TilePlaceholder;
import zmaster587.libVulpes.tile.multiblock.hatch.TileFluidHatch;
import zmaster587.libVulpes.tile.multiblock.hatch.TileInputHatch;
import zmaster587.libVulpes.tile.multiblock.hatch.TileOutputHatch;

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

		//注册功能性方块 应该改为GT的 还应该删除部分无用内容
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockPhantom.setTranslationKey("phantomBlock"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockHatch.setTranslationKey("hatch"), ItemBlockMeta.class, false);
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockPlaceHolder.setTranslationKey("placeHolder"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockStructureBlock.setTranslationKey("structureMachine"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockAdvStructureBlock.setTranslationKey("advStructureMachine"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockCreativeInputPlug.setTranslationKey("creativePowerBattery"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockForgeInputPlug.setTranslationKey("forgePowerInput"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockForgeOutputPlug.setTranslationKey("forgePowerOutput"));
		LibVulpesBlocks.registerBlock(LibVulpesBlocks.blockCoalGenerator.setTranslationKey("coalGenerator"));

		//Register Tile
		GameRegistry.registerTileEntity(TileOutputHatch.class, "vulpesoutputHatch");
		GameRegistry.registerTileEntity(TileInputHatch.class, "vulpesinputHatch");
		GameRegistry.registerTileEntity(TilePlaceholder.class, "vulpesplaceHolder");
		GameRegistry.registerTileEntity(TileFluidHatch.class, "vulpesFluidHatch");
		GameRegistry.registerTileEntity(TileSchematic.class, "vulpesTileSchematic");
		GameRegistry.registerTileEntity(TileCreativePowerInput.class, "vulpesCreativeBattery");
		GameRegistry.registerTileEntity(TileForgePowerInput.class, "vulpesForgePowerInput");
		GameRegistry.registerTileEntity(TileForgePowerOutput.class, "vulpesForgePowerOutput");
		GameRegistry.registerTileEntity(TileCoalGenerator.class, "vulpesCoalGenerator");
		//GameRegistry.registerTileEntity(TilePlugInputRF.class, "ARrfBattery");
		//GameRegistry.registerTileEntity(TilePlugOutputRF.class, "ARrfOutputRF");
		GameRegistry.registerTileEntity(TilePointer.class, "vulpesTilePointer");
		GameRegistry.registerTileEntity(TileInventoriedPointer.class, "vulpesTileInvPointer");

		if(FMLCommonHandler.instance().getSide().isClient()) {
			//Register Block models
			Item blockItem = Item.getItemFromBlock(LibVulpesBlocks.blockHatch);
			ModelLoader.setCustomModelResourceLocation(blockItem, 0, new ModelResourceLocation("libvulpes:inputhatch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(blockItem, 1, new ModelResourceLocation("libvulpes:outputhatch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(blockItem, 2, new ModelResourceLocation("libvulpes:fluidinputhatch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(blockItem, 3, new ModelResourceLocation("libvulpes:fluidoutputhatch", "inventory"));
		}

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

		//Register Items
		LibVulpesBlocks.registerItem(LibVulpesItems.itemLinker);
		LibVulpesBlocks.registerItem(LibVulpesItems.itemBattery);
		LibVulpesBlocks.registerItem(LibVulpesItems.itemHoloProjector);

		OreDictionary.registerOre("itemBattery", new ItemStack(LibVulpesItems.itemBattery,1,0));
	}

	private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
		ItemBlock itemBlock = producer.apply(block);
		itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
		return itemBlock;
	}
}
