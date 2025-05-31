package keqing.gtqtspace.integration;

import keqing.gtqtspace.integration.provider.SpaceModulerInfoProvider;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class GTQTSIntegration {
    public GTQTSIntegration() {
    }

    public static void init() {

        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new SpaceModulerInfoProvider());
    }
}

