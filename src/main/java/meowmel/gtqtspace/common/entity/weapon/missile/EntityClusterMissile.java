package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityClusterMissile extends EntityMissileBase {

    // 集束导弹参数
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;
    private final int subMissileCount;
    private final float splitRange;
    private boolean hasSplit = false;

    // 伞形分裂参数
    private final float umbrellaAngle; // 伞形展开角度（度）
    private final float umbrellaRadius; // 伞形半径

    // 默认参数常量
    private static final float DEFAULT_SPEED = 4.0f;
    private static final float DEFAULT_TURN_RATE = 1.5f;
    private static final float DEFAULT_ACCELERATION = 0.08f;
    private static final int DEFAULT_FUEL = 300;
    private static final float DEFAULT_EXPLOSION_POWER = 3.0f;
    private static final float DEFAULT_DAMAGE_RADIUS = 6.0f;
    private static final float DEFAULT_BASE_DAMAGE = 4.0f;
    private static final int DEFAULT_SUB_MISSILE_COUNT = 12;
    private static final float DEFAULT_SPLIT_RANGE = 20.0f;
    private static final float DEFAULT_UMBRELLA_ANGLE = 60.0f; // 默认60度伞形
    private static final float DEFAULT_UMBRELLA_RADIUS = 2.5f; // 伞形生成半径

    public EntityClusterMissile(World worldIn) {
        this(worldIn, DEFAULT_EXPLOSION_POWER, DEFAULT_DAMAGE_RADIUS,
                DEFAULT_BASE_DAMAGE, DEFAULT_SUB_MISSILE_COUNT, DEFAULT_SPLIT_RANGE,
                DEFAULT_UMBRELLA_ANGLE, DEFAULT_UMBRELLA_RADIUS);
    }

    public EntityClusterMissile(World worldIn, double x, double y, double z) {
        this(worldIn, x, y, z, DEFAULT_EXPLOSION_POWER, DEFAULT_DAMAGE_RADIUS,
                DEFAULT_BASE_DAMAGE, DEFAULT_SUB_MISSILE_COUNT, DEFAULT_SPLIT_RANGE,
                DEFAULT_UMBRELLA_ANGLE, DEFAULT_UMBRELLA_RADIUS);
    }

    public EntityClusterMissile(World worldIn, Entity launcher) {
        this(worldIn, launcher, DEFAULT_EXPLOSION_POWER, DEFAULT_DAMAGE_RADIUS,
                DEFAULT_BASE_DAMAGE, DEFAULT_SUB_MISSILE_COUNT, DEFAULT_SPLIT_RANGE,
                DEFAULT_UMBRELLA_ANGLE, DEFAULT_UMBRELLA_RADIUS);
    }

    // 新增构造器包含伞形参数
    public EntityClusterMissile(World worldIn, float explosionPower, float damageRadius,
                                float baseDamage, int subMissileCount, float splitRange,
                                float umbrellaAngle, float umbrellaRadius) {
        super(worldIn, DEFAULT_SPEED, DEFAULT_TURN_RATE, DEFAULT_ACCELERATION, DEFAULT_FUEL);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.subMissileCount = subMissileCount;
        this.splitRange = splitRange;
        this.umbrellaAngle = umbrellaAngle;
        this.umbrellaRadius = umbrellaRadius;
    }

    public EntityClusterMissile(World worldIn, double x, double y, double z,
                                float explosionPower, float damageRadius, float baseDamage,
                                int subMissileCount, float splitRange,
                                float umbrellaAngle, float umbrellaRadius) {
        super(worldIn, x, y, z, DEFAULT_SPEED, DEFAULT_TURN_RATE, DEFAULT_ACCELERATION, DEFAULT_FUEL);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.subMissileCount = subMissileCount;
        this.splitRange = splitRange;
        this.umbrellaAngle = umbrellaAngle;
        this.umbrellaRadius = umbrellaRadius;
    }

    public EntityClusterMissile(World worldIn, Entity launcher, float explosionPower,
                                float damageRadius, float baseDamage, int subMissileCount,
                                float splitRange, float umbrellaAngle, float umbrellaRadius) {
        super(worldIn, launcher, DEFAULT_SPEED, DEFAULT_TURN_RATE, DEFAULT_ACCELERATION, DEFAULT_FUEL);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.subMissileCount = subMissileCount;
        this.splitRange = splitRange;
        this.umbrellaAngle = umbrellaAngle;
        this.umbrellaRadius = umbrellaRadius;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // 在飞行过程中检测前方障碍物
        if (!this.world.isRemote && !this.hasSplit && !this.hasExploded) {
            checkForSplit();
        }
    }

    @Override
    protected void explode() {
        // 如果没有分裂就爆炸了，执行普通爆炸
        if (!this.hasSplit && !this.hasExploded) {
            this.hasExploded = true;
            performExplosion();
        }
    }

    @Override
    protected EnumParticleTypes getTrailParticleType() {
        return EnumParticleTypes.CLOUD;
    }

    // ==================== 伞形分裂逻辑 ====================

    /**
     * 检测是否需要分裂
     */
    private void checkForSplit() {
        Vec3d lookVec = this.getLookVec();

        // 射线检测前方splitRange格内是否有障碍物
        RayTraceResult result = this.world.rayTraceBlocks(
                new Vec3d(this.posX, this.posY, this.posZ),
                new Vec3d(
                        this.posX + lookVec.x * splitRange,
                        this.posY + lookVec.y * splitRange,
                        this.posZ + lookVec.z * splitRange
                ),
                false, true, false
        );

        // 如果检测到障碍物，在当前位置触发分裂
        if (result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
            splitIntoUmbrellaSubMissiles(lookVec);
        }
    }

    /**
     * 伞形分裂成多个子导弹 - 修正版本
     */
    private void splitIntoUmbrellaSubMissiles(Vec3d umbrellaHandleDirection) {
        this.hasSplit = true;

        if (!this.world.isRemote) {
            playSplitSound();
            spawnUmbrellaSplitParticles(umbrellaHandleDirection);

            // 生成伞形子导弹
            Vec3d splitPos = new Vec3d(this.posX, this.posY, this.posZ);
            for (int i = 0; i < subMissileCount; i++) {
                createUmbrellaSubMissile(splitPos, umbrellaHandleDirection, i);
            }
        }

        // 分裂后原导弹消失
        this.setDead();
    }

    /**
     * 创建单个伞形子导弹 - 修正版本
     */
    private void createUmbrellaSubMissile(Vec3d position, Vec3d umbrellaHandleDirection, int index) {
        // 计算伞骨方向 - 真正的伞形
        Vec3d umbrellaRibDirection = calculateTrueUmbrellaRibDirection(umbrellaHandleDirection, index);

        // 创建并配置子导弹
        EntityExplosiveMissile subMissile = createSubMissileEntity(position, umbrellaRibDirection);
        subMissile.setSpeed(this.speed * 0.8f);

        // 添加到世界并发射
        this.world.spawnEntity(subMissile);
        subMissile.launch();
    }

    /**
     * 计算真正的伞骨方向
     * 伞把方向是母弹前进方向，伞骨围绕伞把呈锥形分布
     */
    private Vec3d calculateTrueUmbrellaRibDirection(Vec3d handleDirection, int index) {
        // 伞把方向（母弹前进方向）
        Vec3d handle = handleDirection.normalize();

        // 计算垂直于伞把的基准向量
        Vec3d up = new Vec3d(0, 1, 0);
        Vec3d right = handle.crossProduct(up).normalize();
        if (right.lengthSquared() < 0.001) {
            // 如果伞把垂直向上/向下，使用X轴作为基准
            right = new Vec3d(1, 0, 0);
        }
        Vec3d forward = right.crossProduct(handle).normalize();

        // 计算当前伞骨在伞面上的角度
        double angle = 2 * Math.PI * index / subMissileCount;

        // 伞骨与伞把的夹角（伞形半角）
        double ribAngle = Math.toRadians(umbrellaAngle / 2);

        // 计算伞骨方向向量
        // 在垂直于伞把的平面上的分量
        Vec3d horizontalComponent = right.scale(Math.cos(angle)).add(forward.scale(Math.sin(angle))).normalize();

        // 伞骨方向 = 伞把方向 * cos(θ) + 水平分量 * sin(θ)
        // 其中θ是伞骨与伞把的夹角
        return handle.scale(Math.cos(ribAngle))
                .add(horizontalComponent.scale(Math.sin(ribAngle)))
                .normalize();
    }

    /**
     * 伞形分裂粒子效果
     */
    private void spawnUmbrellaSplitParticles(Vec3d handleDirection) {
        // 伞把方向
        Vec3d handle = handleDirection.normalize();

        // 计算基准向量
        Vec3d up = new Vec3d(0, 1, 0);
        Vec3d right = handle.crossProduct(up).normalize();
        if (right.lengthSquared() < 0.001) {
            right = new Vec3d(1, 0, 0);
        }
        Vec3d forward = right.crossProduct(handle).normalize();

        double ribAngle = Math.toRadians(umbrellaAngle / 2);

        // 生成沿伞骨方向的粒子
        for (int i = 0; i < subMissileCount; i++) {
            double angle = 2 * Math.PI * i / subMissileCount;
            Vec3d horizontalComponent = right.scale(Math.cos(angle)).add(forward.scale(Math.sin(angle))).normalize();
            Vec3d ribDirection = handle.scale(Math.cos(ribAngle))
                    .add(horizontalComponent.scale(Math.sin(ribAngle)))
                    .normalize();

            // 为每个伞骨生成粒子流
            for (int j = 0; j < 8; j++) {
                double progress = j * 0.5; // 粒子沿伞骨前进的距离
                Vec3d particlePos = new Vec3d(
                        this.posX + ribDirection.x * progress,
                        this.posY + ribDirection.y * progress,
                        this.posZ + ribDirection.z * progress
                );

                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        particlePos.x, particlePos.y, particlePos.z,
                        ribDirection.x * 0.2, ribDirection.y * 0.2, ribDirection.z * 0.2);

                this.world.spawnParticle(EnumParticleTypes.FLAME,
                        particlePos.x, particlePos.y, particlePos.z,
                        ribDirection.x * 0.1, ribDirection.y * 0.1, ribDirection.z * 0.1);
            }
        }
    }

    // ==================== 共用方法 ====================

    /**
     * 创建子导弹实体
     */
    private EntityExplosiveMissile createSubMissileEntity(Vec3d position, Vec3d direction) {
        EntityExplosiveMissile subMissile = new EntityExplosiveMissile(
                this.world, this.launchingEntity,
                this.explosionPower,
                this.damageRadius,
                this.baseDamage
        );

        // 设置子导弹位置和方向
        subMissile.setPositionAndRotation(
                position.x, position.y, position.z,
                (float) Math.toDegrees(Math.atan2(direction.x, direction.z)),
                (float) Math.toDegrees(Math.asin(-direction.y))
        );

        subMissile.calculateInitialMotion();
        return subMissile;
    }

    /**
     * 播放分裂音效
     */
    private void playSplitSound() {
        this.world.playSound(null, this.posX, this.posY, this.posZ,
                net.minecraft.init.SoundEvents.ENTITY_FIREWORK_LARGE_BLAST,
                net.minecraft.util.SoundCategory.BLOCKS, 2.0F,
                (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    /**
     * 执行爆炸逻辑
     */
    private void performExplosion() {
        if (!this.world.isRemote) {
            // 创建爆炸
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 对周围实体造成伤害
            applyDamageToNearbyEntities();

            // 播放爆炸音效
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE,
                    net.minecraft.util.SoundCategory.BLOCKS, 3.0F,
                    (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
        } else {
            // 客户端爆炸粒子效果
            spawnExplosionParticles();
        }
    }

    /**
     * 对周围实体造成伤害
     */
    private void applyDamageToNearbyEntities() {
        List<Entity> affectedEntities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                new AxisAlignedBB(this.posX - damageRadius, this.posY - damageRadius, this.posZ - damageRadius,
                        this.posX + damageRadius, this.posY + damageRadius, this.posZ + damageRadius));

        for (Entity entity : affectedEntities) {
            if (entity != this.launchingEntity && entity != this) {
                double distance = this.getDistance(entity);
                if (distance <= damageRadius) {
                    float damage = (float) ((damageRadius - distance) * baseDamage);
                    entity.attackEntityFrom(createDamageSource(), damage);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnExplosionParticles() {
        for (int i = 0; i < 30; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;
            double offsetY = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;
            double offsetZ = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;

            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1);
        }
    }

    // ==================== Getter 方法 ====================

    public float getExplosionPower() {
        return explosionPower;
    }

    public float getDamageRadius() {
        return damageRadius;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public int getSubMissileCount() {
        return subMissileCount;
    }

    public float getSplitRange() {
        return splitRange;
    }

    public float getUmbrellaAngle() {
        return umbrellaAngle;
    }

    public float getUmbrellaRadius() {
        return umbrellaRadius;
    }

    public boolean hasSplit() {
        return hasSplit;
    }
}