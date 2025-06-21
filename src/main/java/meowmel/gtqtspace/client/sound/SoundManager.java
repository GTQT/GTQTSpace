package meowmel.gtqtspace.client.sound;

import meowmel.gtqtspace.GTQTSpace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = GTQTSpace.MODID)
public class SoundManager {
    public static SoundEvent shuttle = new SoundEvent(new ResourceLocation(GTQTSpace.MODID, "shuttle"));

    @SubscribeEvent
    public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().register(shuttle.setRegistryName("shuttle"));
    }
}
