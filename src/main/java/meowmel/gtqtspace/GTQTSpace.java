package meowmel.gtqtspace;

import meowmel.gtqtspace.api.GTQTSAPI;
import meowmel.gtqtspace.api.utils.GTQTSLog;
import meowmel.gtqtspace.client.ClientProxy;
import meowmel.gtqtspace.common.CommonProxy;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import meowmel.gtqtspace.integration.GTQTSIntegration;
import meowmel.gtqtspace.world.worldgen.GTQTSDimensionManager;
import meowmel.gtqtspace.world.worldgen.GTQTSDimensionType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;


@Mod(
        modid = "gtqtspace",
        name = "GTQTSpace",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta",
        dependencies = "required-after:gregtech@[2.8.7-beta,) ;"
)
public class GTQTSpace {
    public static final String MODID = "gtqtspace";
    public static final String NAME = "GTQT Space";
    public static final String VERSION = "1.0";

    @Mod.Instance(GTQTSpace.MODID)
    public static GTQTSpace instance;

    @SidedProxy(
            clientSide = "meowmel.gtqtspace.client.ClientProxy",
            serverSide = "meowmel.gtqtspace.common.CommonProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy cproxy;


    public static Block portal;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        proxy.init();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GTQTSIntegration.init();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preLoad();
        GTQTSMetaTileEntities.initialization();
        GTQTSLog.init(event.getModLog());
        GTQTSMetaItems.initialization();
        GTQTSMetaBlocks.init();
        GTQTSAPI.init();
        GTQTSDimensionType.init();
        GTQTSDimensionManager.init();
    }

}
