package meowmel.gtqtgalaxy;

import meowmel.gtqtgalaxy.cilent.ClientProxy;
import meowmel.gtqtgalaxy.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(
        modid = "gtqtgalaxy",
        name = "GTQT Galaxy",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta",
        dependencies = "required-after:gregtech@[2.9.0-beta,);"
)
public class GTQTGalaxy {
    public static final String PACK = "1.8.0";

    public static final String MODID = "gtqtgalaxy";
    public static final String NAME = "GTQT Galaxy";
    public static final String VERSION = "0601(2025/6/1)";

    @Mod.Instance(GTQTGalaxy.MODID)
    public static GTQTGalaxy instance;

    @SidedProxy(
            clientSide = "meowmel.gtqtgalaxy.client.ClientProxy",
            serverSide = "meowmel.gtqtgalaxy.common.CommonProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy cproxy;
}
