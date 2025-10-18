package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityMachineGunBullet extends EntityMissileBase {

    // 机炮弹参数 - 相比高爆弹威力更小
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;
    private final int penetrationLevel; // 新增：穿甲等级

    public EntityMachineGunBullet(World worldIn) {
        super(worldIn, 8.0f, 3.0f, 0.05f, 200);
        this.explosionPower = 2f;
        this.damageRadius = 3.0f;
        this.baseDamage = 10.0f;
        this.penetrationLevel = 1;
    }

    public EntityMachineGunBullet(World worldIn, Entity launcher) {
        this(worldIn, launcher, 2f, 3.0f, 10.0f, 1);
    }

    public EntityMachineGunBullet(World worldIn, Entity launcher, float explosionPower, float damageRadius, float baseDamage, int penetrationLevel) {
        super(worldIn, launcher, 8.0f, 3.0f, 0.05f, 200);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.penetrationLevel = penetrationLevel;
    }

    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 创建小型爆炸
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 对周围实体造成伤害
            List<Entity> affectedEntities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                    new AxisAlignedBB(this.posX - damageRadius, this.posY - damageRadius, this.posZ - damageRadius,
                            this.posX + damageRadius, this.posY + damageRadius, this.posZ + damageRadius));

            for (Entity entity : affectedEntities) {
                if (entity != this.launchingEntity && entity != this) {
                    double distance = this.getDistance(entity);
                    if (distance <= damageRadius) {
                        // 计算衰减伤害
                        float damage = calculateDamage(distance, entity);
                        if (damage > 0) {
                            entity.attackEntityFrom(createDamageSource(), damage);

                            // 对生物附加击退效果
                            if (entity instanceof EntityLivingBase) {
                                applyKnockback((EntityLivingBase) entity, distance);
                            }
                        }
                    }
                }
            }

            // 播放小型爆炸音效
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_FIREWORK_BLAST,
                    net.minecraft.util.SoundCategory.BLOCKS, 2.0F,
                    (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
        } else {
            // 客户端粒子效果 - 更小的爆炸效果
            spawnBulletImpactParticles();
        }

        this.setDead();
    }

    /**
     * 计算对实体的伤害，考虑穿甲能力
     */
    private float calculateDamage(double distance, Entity target) {
        float damage = (float) ((damageRadius - distance) / damageRadius * baseDamage);

        // 对装甲实体有额外效果
        if (target instanceof EntityLivingBase living) {
            // 如果目标穿着装甲，根据穿甲等级调整伤害
            if (living.getTotalArmorValue() > 0) {
                float armorPenetration = Math.min(penetrationLevel * 0.25f, 0.75f);
                damage *= (1.0f + armorPenetration);
            }
        }

        return Math.max(damage, 0.5f); // 确保最小伤害
    }

    /**
     * 应用击退效果
     */
    private void applyKnockback(EntityLivingBase target, double distance) {
        double knockbackStrength = (damageRadius - distance) / damageRadius * 0.5;
        Vec3d dir = new Vec3d(
                target.posX - this.posX,
                target.posY - this.posY,
                target.posZ - this.posZ
        ).normalize();

        target.knockBack(this, (float) knockbackStrength, dir.x, dir.z);
    }

    @SideOnly(Side.CLIENT)
    private void spawnBulletImpactParticles() {
        // 少量爆炸粒子
        for (int i = 0; i < 15; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * explosionPower;
            double offsetY = (this.rand.nextDouble() - 0.5) * explosionPower;
            double offsetZ = (this.rand.nextDouble() - 0.5) * explosionPower;

            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1);
        }

        // 火花效果
        for (int i = 0; i < 8; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * 0.5;
            double offsetY = (this.rand.nextDouble() - 0.5) * 0.5;
            double offsetZ = (this.rand.nextDouble() - 0.5) * 0.5;

            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.05, offsetY * 0.05, offsetZ * 0.05);
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

    public int getPenetrationLevel() {
        return penetrationLevel;
    }
}