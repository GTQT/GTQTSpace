package meowmel.gtqtspace.client;


import meowmel.gtqtspace.GTQTSpace;
import meowmel.gtqtspace.client.control.VehicleKeyHandler;
import meowmel.gtqtspace.client.gui.VehicleHUD;
import meowmel.gtqtspace.client.render.RenderCustomVehicle;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import meowmel.gtqtspace.common.CommonProxy;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = GTQTSpace.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    static {
        MinecraftForge.EVENT_BUS.register(ClientProxy.class); // 显式注册静态处理器
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(GTQTSpace.MODID);
        GTQTSMetaBlocks.registerItemModels();
        GTQTSMetaItems.registerItemModels();

        RenderingRegistry.registerEntityRenderingHandler(EntityCustomVehicle.class,
                RenderCustomVehicle::new);
    }


    @Override
    public void preLoad() {
        super.preLoad();
        GTQTSTextures.init();
        GTQTSTextures.preInit();
        MinecraftForge.EVENT_BUS.register(new VehicleHUD());
    }
}
