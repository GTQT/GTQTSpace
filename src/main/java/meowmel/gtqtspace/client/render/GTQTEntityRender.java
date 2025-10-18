package meowmel.gtqtspace.client.render;

import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import meowmel.gtqtspace.common.entity.weapon.missile.EntityMissileBase;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class GTQTEntityRender {
    public static void register() {

        RenderingRegistry.registerEntityRenderingHandler(EntityCustomVehicle.class,
                RenderCustomVehicle::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityMissileBase.class,
                RenderMissile::new);
    }
}
