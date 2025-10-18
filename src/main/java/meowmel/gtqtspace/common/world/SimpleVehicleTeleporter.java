package meowmel.gtqtspace.common.world;


import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * 简单的载具传送器
 */
public class SimpleVehicleTeleporter extends Teleporter {

    private final WorldServer world;
    private final BlockPos targetPos;

    public SimpleVehicleTeleporter(WorldServer world, BlockPos targetPos) {
        super(world);
        this.world = world;
        this.targetPos = targetPos;
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
        // 直接设置实体位置到目标位置
        entity.setPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        this.placeInPortal(entity, rotationYaw);
        return true;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }
}