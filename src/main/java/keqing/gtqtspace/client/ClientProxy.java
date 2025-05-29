package keqing.gtqtspace.client;


import keqing.gtqtspace.GTQTSpace;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.CommonProxy;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(GTQTSpace.MODID);
        GTQTSMetaBlocks.registerItemModels();
        GTQTSMetaItems.registerItemModels();
    }


    public void preLoad() {
        super.preLoad();
        GTQTSTextures.init();
        GTQTSTextures.preInit();
    }

}
