package zmaster587.libVulpes.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import zmaster587.libVulpes.LibVulpes;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.api.LibVulpesItems;
import zmaster587.libVulpes.common.CommonProxy;
import zmaster587.libVulpes.common.block.LibVulpesMetaBlocks;
import zmaster587.libVulpes.entity.fx.FxErrorBlock;
import zmaster587.libVulpes.network.BasePacket;

import java.util.LinkedList;
@Mod.EventBusSubscriber({Side.CLIENT})
public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	@Override
	public String getLocalizedString(String str) {
		return I18n.format(str);
	}

	@Override
	public void init() {

	}
	
	@Override
	public void preInit() {
		OBJLoader.INSTANCE.addDomain("libvulpes");
	}
	
	@Override
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(LibVulpesItems.itemHoloProjector);
	}
	@Override
	public void spawnParticle(String particle, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {

		if(particle.equals("errorBox")) {
			FxErrorBlock fx = new FxErrorBlock(world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}
	@Override
	public void addScheduledTask(BasePacket packet) {
		//Minecraft.getMinecraft().addScheduledTask(new ExecutorClient(packet, Minecraft.getMinecraft().thePlayer, Side.CLIENT));
	}
	
	@Override
	public void preInitItems()
	{
        //Register Item models
        ModelLoader.setCustomModelResourceLocation(LibVulpesItems.itemLinker, 0, new ModelResourceLocation("libvulpes:linker", "inventory"));
        ModelLoader.setCustomModelResourceLocation(LibVulpesItems.itemHoloProjector, 0, new ModelResourceLocation("libvulpes:holoprojector", "inventory"));
        ModelLoader.setCustomModelResourceLocation(LibVulpesItems.itemBattery, 0, new ModelResourceLocation("libvulpes:smallbattery", "inventory"));
        ModelLoader.setCustomModelResourceLocation(LibVulpesItems.itemBattery, 1, new ModelResourceLocation("libvulpes:small2xbattery", "inventory"));
	}
	
	@Override
	public void preInitBlocks()
	{
		LinkedList<Item> blockItems = new LinkedList<>();
		

		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockAdvStructureBlock));
		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockCoalGenerator));
		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockCreativeInputPlug));

		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockForgeInputPlug));
		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockForgeOutputPlug));
		if(LibVulpesBlocks.blockIC2Plug != null)
			blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockIC2Plug));

		blockItems.add(Item.getItemFromBlock(LibVulpesBlocks.blockStructureBlock));
		
		for(Item blockItem2 : blockItems)
			ModelLoader.setCustomModelResourceLocation(blockItem2, 0, new ModelResourceLocation(blockItem2.getRegistryName(), "inventory"));
		
		for(Block block : LibVulpesBlocks.itemBlocks)
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	@Override
	public void playSound(Object sound) {
		if(sound instanceof ITickableSound) {
			ITickableSound sound2 = (ITickableSound)sound;
			//does not work - game crashes sometimes...
			if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound2)) {
				Minecraft.getMinecraft().getSoundHandler().playSound(sound2);
			}

		}
	}
	
	@Override
	public void playSound(World worldObj, BlockPos pos, SoundEvent event, SoundCategory cat, float volume, float pitch) {
		worldObj.playSound(Minecraft.getMinecraft().player, pos, event, SoundCategory.BLOCKS, Minecraft.getMinecraft().gameSettings.getSoundLevel(cat)*volume,  0.975f + worldObj.rand.nextFloat()*0.05f);
		
	}
}
