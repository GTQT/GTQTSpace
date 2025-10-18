package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityTacticalNuke extends EntityMissileBase {

    // 核弹参数 - 比高爆弹威力更大
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;
    private final int tntCount; // 生成的TNT数量

    public EntityTacticalNuke(World worldIn) {
        super(worldIn, 4.0f, 1.0f, 0.05f, 500);
        this.explosionPower = 25.0f;
        this.damageRadius = 25.0f;
        this.baseDamage = 50.0f;
        this.tntCount = 12;
    }

    public EntityTacticalNuke(World worldIn, Entity launcher) {
        this(worldIn, launcher, 100.0f, 50.0f, 50.0f, 12);
    }

    public EntityTacticalNuke(World worldIn, Entity launcher, float explosionPower, float damageRadius,
                              float baseDamage, int tntCount) {
        super(worldIn, launcher, 4.0f, 1.0f, 0.05f, 500);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.tntCount = tntCount;
    }


    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 创建主爆炸 - 威力巨大
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 生成多个TNT增强爆炸效果
            spawnAdditionalTNT();

            // 对周围实体造成伤害 - 范围更大
            List<Entity> affectedEntities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                    new AxisAlignedBB(this.posX - damageRadius, this.posY - damageRadius, this.posZ - damageRadius,
                            this.posX + damageRadius, this.posY + damageRadius, this.posZ + damageRadius));

            for (Entity entity : affectedEntities) {
                if (entity != this.launchingEntity && entity != this) {
                    double distance = this.getDistance(entity);
                    if (distance <= damageRadius) {
                        // 核弹伤害衰减更平缓，确保大范围内都有伤害
                        float damage = calculateNuclearDamage(distance);
                        entity.attackEntityFrom(createDamageSource(), damage);

                        // 对生物附加击退和燃烧效果
                        applyNuclearEffects(entity, distance);
                    }
                }
            }

            // 播放更强的爆炸音效
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE,
                    net.minecraft.util.SoundCategory.BLOCKS, 8.0F,  // 音量更大
                    (0.8F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.5F); // 音调更低

        } else {
            // 客户端爆炸粒子效果 - 更壮观
            spawnNuclearExplosionParticles();
        }

        this.setDead();
    }

    /**
     * 生成额外的TNT来增强爆炸效果
     */
    private void spawnAdditionalTNT() {
        for (int i = 0; i < tntCount; i++) {
            // 在爆炸范围内随机位置生成TNT
            double offsetX = (this.rand.nextDouble() - 0.5) * damageRadius * 0.8;
            double offsetY = this.rand.nextDouble() * damageRadius * 0.5;
            double offsetZ = (this.rand.nextDouble() - 0.5) * damageRadius * 0.8;

            double tntX = this.posX + offsetX;
            double tntY = this.posY + offsetY;
            double tntZ = this.posZ + offsetZ;

            // 确保TNT生成在可放置的位置
            if (this.world.isAirBlock(new BlockPos(tntX, tntY, tntZ)) ||
                    this.world.getBlockState(new BlockPos(tntX, tntY, tntZ)).getMaterial().isReplaceable()) {

                EntityTNTPrimed tnt = new EntityTNTPrimed(this.world,
                        tntX, tntY, tntZ, (EntityLivingBase) getLaunchingPlayer(this.launchingEntity));

                // 设置较短的引信时间，让TNT快速爆炸
                tnt.setFuse(10 + this.rand.nextInt(10));

                this.world.spawnEntity(tnt);
            }
        }
    }

    /**
     * 计算核弹伤害 - 使用不同的衰减曲线
     */
    private float calculateNuclearDamage(double distance) {
        // 使用平方反比衰减，中心伤害极高，但衰减较快
        double normalizedDistance = distance / damageRadius;
        double damageMultiplier = 1.0 / (1.0 + normalizedDistance * normalizedDistance * 4.0);

        return (float) (baseDamage * damageMultiplier);
    }

    /**
     * 应用核弹特殊效果
     */
    private void applyNuclearEffects(Entity entity, double distance) {
        if (entity instanceof EntityLivingBase living) {

            // 近距离实体受到燃烧效果
            if (distance < damageRadius * 0.3) {
                living.setFire(10); // 10秒燃烧
            }

            // 所有命中实体都受到击退
            double knockbackStrength = (damageRadius - distance) / damageRadius * 2.0;
            Vec3d dir = new Vec3d(
                    entity.posX - this.posX,
                    entity.posY - this.posY,
                    entity.posZ - this.posZ
            ).normalize();

            living.knockBack(this, (float) knockbackStrength, dir.x, dir.z);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnNuclearExplosionParticles() {
        // 第一阶段：巨大的爆炸云
        for (int i = 0; i < 100; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * explosionPower * 3;
            double offsetY = (this.rand.nextDouble() - 0.5) * explosionPower * 2;
            double offsetZ = (this.rand.nextDouble() - 0.5) * explosionPower * 3;

            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.05, offsetY * 0.05, offsetZ * 0.05);

            // 添加大量火焰粒子
            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX + offsetX * 0.3, this.posY + offsetY * 0.3, this.posZ + offsetZ * 0.3,
                    offsetX * 0.02, offsetY * 0.02, offsetZ * 0.02);
        }

        // 第二阶段：上升的蘑菇云
        spawnMushroomCloud();

        // 第三阶段：冲击波粒子
        spawnShockwaveParticles();
    }

    /**
     * 生成蘑菇云效果
     */
    @SideOnly(Side.CLIENT)
    private void spawnMushroomCloud() {
        // 蘑菇云茎部
        for (int i = 0; i < 50; i++) {
            double radius = this.rand.nextDouble() * explosionPower * 0.5;
            double angle = this.rand.nextDouble() * Math.PI * 2;

            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double offsetY = this.rand.nextDouble() * explosionPower * 2;

            this.world.spawnParticle(EnumParticleTypes.CLOUD,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    0, 0.1 + this.rand.nextDouble() * 0.2, 0);
        }

        // 蘑菇云顶部
        for (int i = 0; i < 80; i++) {
            double radius = this.rand.nextDouble() * explosionPower * 1.5;
            double angle = this.rand.nextDouble() * Math.PI * 2;

            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double offsetY = explosionPower * 1.5 + this.rand.nextDouble() * explosionPower;

            this.world.spawnParticle(EnumParticleTypes.CLOUD,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    (this.rand.nextDouble() - 0.5) * 0.1,
                    (this.rand.nextDouble() - 0.5) * 0.1,
                    (this.rand.nextDouble() - 0.5) * 0.1);
        }
    }

    /**
     * 生成冲击波粒子
     */
    @SideOnly(Side.CLIENT)
    private void spawnShockwaveParticles() {
        for (int ring = 0; ring < 3; ring++) {
            double ringRadius = (ring + 1) * explosionPower * 0.8;

            for (int i = 0; i < 30; i++) {
                double angle = (i / 30.0) * Math.PI * 2;
                double offsetX = Math.cos(angle) * ringRadius;
                double offsetZ = Math.sin(angle) * ringRadius;

                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        this.posX + offsetX, this.posY, this.posZ + offsetZ,
                        Math.cos(angle) * 0.5, 0.2, Math.sin(angle) * 0.5);
            }
        }
    }

    @Override
    protected EnumParticleTypes getTrailParticleType() {
        // 核弹使用更壮观的轨迹粒子
        return EnumParticleTypes.FLAME;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void spawnTrailParticles() {
        this.trailCounter++;
        if (this.trailCounter >= 2) {
            this.trailCounter = 0;

            // 更多的轨迹粒子
            for (int i = 0; i < 8; i++) {
                double offsetX = (this.rand.nextDouble() - 0.5) * 0.4;
                double offsetY = (this.rand.nextDouble() - 0.5) * 0.4;
                double offsetZ = (this.rand.nextDouble() - 0.5) * 0.4;

                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        this.posX + offsetX,
                        this.posY + offsetY,
                        this.posZ + offsetZ,
                        -this.motionX * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1,
                        -this.motionY * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1,
                        -this.motionZ * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1);
            }

            // 强烈的火焰轨迹
            for (int i = 0; i < 5; i++) {
                this.world.spawnParticle(EnumParticleTypes.FLAME,
                        this.posX, this.posY, this.posZ,
                        -this.motionX * 0.05 + (this.rand.nextDouble() - 0.5) * 0.05,
                        -this.motionY * 0.05 + (this.rand.nextDouble() - 0.5) * 0.05,
                        -this.motionZ * 0.05 + (this.rand.nextDouble() - 0.5) * 0.05);
            }

            // 偶尔添加爆炸粒子预示巨大威力
            if (this.rand.nextInt(20) == 0) {
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                        this.posX, this.posY, this.posZ,
                        (this.rand.nextDouble() - 0.5) * 0.2,
                        (this.rand.nextDouble() - 0.5) * 0.2,
                        (this.rand.nextDouble() - 0.5) * 0.2);
            }
        }
    }

    // Getter 方法
    public float getExplosionPower() {
        return explosionPower;
    }

    public float getDamageRadius() {
        return damageRadius;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public int getTntCount() {
        return tntCount;
    }
}