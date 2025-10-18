package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityArmorPiercingMissile extends EntityMissileBase {

    // 穿甲弹参数 - 较小的爆炸范围但高穿透伤害
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;
    private final float armorPenetration; // 穿甲系数

    public EntityArmorPiercingMissile(World worldIn) {
        super(worldIn, 6.0f, 2.5f, 0.12f, 350);
        this.explosionPower = 5.0f;
        this.damageRadius = 5.0f;
        this.baseDamage = 15.0f;
        this.armorPenetration = 1.0f;
    }

    public EntityArmorPiercingMissile(World worldIn, Entity launcher) {
        this(worldIn, launcher, 5.0f, 8.0f, 15.0f, 1.0f);
    }

    public EntityArmorPiercingMissile(World worldIn, Entity launcher, float explosionPower, float damageRadius,
                                      float baseDamage, float armorPenetration) {
        super(worldIn, launcher, 6.0f, 2.5f, 0.12f, 350);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.armorPenetration = armorPenetration;
    }

    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 创建较小的爆炸
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 对周围实体造成真实伤害
            List<Entity> affectedEntities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                    new AxisAlignedBB(this.posX - damageRadius, this.posY - damageRadius, this.posZ - damageRadius,
                            this.posX + damageRadius, this.posY + damageRadius, this.posZ + damageRadius));

            for (Entity entity : affectedEntities) {
                if (entity != this.launchingEntity && entity != this) {
                    double distance = this.getDistance(entity);
                    if (distance <= damageRadius) {
                        // 计算真实伤害，忽略护甲
                        applyTrueDamage(entity, distance);
                    }
                }
            }

            // 播放爆炸音效 - 更高频更尖锐
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE,
                    net.minecraft.util.SoundCategory.BLOCKS, 3.0F,
                    (1.5F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
        } else {
            // 客户端爆炸粒子效果
            spawnArmorPiercingExplosionParticles();
        }

        this.setDead();
    }

    /**
     * 应用真实伤害 - 直接扣除生命值，忽略护甲
     */
    private void applyTrueDamage(Entity entity, double distance) {
        // 计算基础伤害（随距离衰减）
        float damage = calculateDamage(distance);

        if (entity instanceof EntityLivingBase living) {

            // 计算穿甲后的实际伤害
            float finalDamage = applyArmorPenetration(damage, living);

            // 直接设置生命值，绕过护甲计算
            float currentHealth = living.getHealth();
            float newHealth = currentHealth - finalDamage;

            // 确保生命值不会低于0
            if (newHealth <= 0) {
                living.setHealth(0);
                // 如果生命值为0，触发死亡
                if (!living.isDead) {
                    living.onDeath(createDamageSource());
                }
            } else {
                living.setHealth(newHealth);
            }

            // 添加穿甲特效
            spawnPenetrationEffect(living);

        } else {
            // 对于非生物实体，使用普通伤害系统
            entity.attackEntityFrom(createTrueDamageSource(), damage);
        }
    }

    /**
     * 计算伤害（考虑距离衰减）
     */
    private float calculateDamage(double distance) {
        // 线性衰减
        float damageMultiplier = (float) (1.0 - (distance / damageRadius));
        return baseDamage * damageMultiplier;
    }

    /**
     * 应用穿甲效果
     */
    private float applyArmorPenetration(float damage, EntityLivingBase target) {
        // 获取目标的护甲值
        int armorValue = target.getTotalArmorValue();

        if (armorValue <= 0) {
            return damage; // 没有护甲，全额伤害
        }

        // 计算穿甲后的伤害
        // armorPenetration为0-1的值，表示穿透护甲的比例
        float armorReduction = armorValue * 0.04f; // 原版护甲减伤公式的一部分
        float effectiveArmorReduction = armorReduction * (1.0f - armorPenetration);

        return damage * (1.0f - effectiveArmorReduction);
    }

    /**
     * 创建真实伤害源
     */
    private net.minecraft.util.DamageSource createTrueDamageSource() {
        // 使用魔法伤害源，它通常忽略护甲
        net.minecraft.util.DamageSource source;

        if (this.launchingEntity instanceof EntityLivingBase) {
            source = net.minecraft.util.DamageSource.causeIndirectMagicDamage(this, this.launchingEntity);
        } else {
            source = new net.minecraft.util.DamageSource("armorPiercing")
                    .setDamageBypassesArmor()
                    .setMagicDamage();
        }

        return source;
    }

    /**
     * 生成穿甲特效
     */
    private void spawnPenetrationEffect(EntityLivingBase target) {
        // 服务器端播放音效
        this.world.playSound(null, target.posX, target.posY, target.posZ,
                net.minecraft.init.SoundEvents.ENTITY_ITEM_BREAK,
                net.minecraft.util.SoundCategory.NEUTRAL, 0.8F,
                (1.2F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));

        // 客户端粒子效果
        if (this.world.isRemote) {
            spawnClientPenetrationParticles(target);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnClientPenetrationParticles(EntityLivingBase target) {
        // 在目标周围生成金属火花粒子
        for (int i = 0; i < 15; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * target.width;
            double offsetY = this.rand.nextDouble() * target.height;
            double offsetZ = (this.rand.nextDouble() - 0.5) * target.width;

            this.world.spawnParticle(EnumParticleTypes.CRIT,
                    target.posX + offsetX, target.posY + offsetY, target.posZ + offsetZ,
                    (this.rand.nextDouble() - 0.5) * 0.5,
                    this.rand.nextDouble() * 0.5,
                    (this.rand.nextDouble() - 0.5) * 0.5);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnArmorPiercingExplosionParticles() {
        // 较少的爆炸粒子，但更多金属效果
        for (int i = 0; i < 25; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;
            double offsetY = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;
            double offsetZ = (this.rand.nextDouble() - 0.5) * explosionPower * 1.5;

            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1);

            // 金属火花粒子
            if (i % 3 == 0) {
                this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
                        this.posX + offsetX * 0.5, this.posY + offsetY * 0.5, this.posZ + offsetZ * 0.5,
                        offsetX * 0.05, offsetY * 0.05, offsetZ * 0.05);
            }
        }

        // 冲击波效果 - 更集中
        spawnConcentratedShockwave();
    }

    /**
     * 生成集中的冲击波
     */
    @SideOnly(Side.CLIENT)
    private void spawnConcentratedShockwave() {
        for (int ring = 0; ring < 2; ring++) {
            double ringRadius = (ring + 1) * explosionPower * 0.5;

            for (int i = 0; i < 20; i++) {
                double angle = (i / 20.0) * Math.PI * 2;
                double offsetX = Math.cos(angle) * ringRadius;
                double offsetZ = Math.sin(angle) * ringRadius;

                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                        this.posX + offsetX, this.posY, this.posZ + offsetZ,
                        Math.cos(angle) * 0.3, 0.1, Math.sin(angle) * 0.3);
            }
        }
    }

    @Override
    protected EnumParticleTypes getTrailParticleType() {
        // 穿甲弹使用更尖锐的粒子效果
        return EnumParticleTypes.CRIT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void spawnTrailParticles() {
        this.trailCounter++;
        if (this.trailCounter >= 2) {
            this.trailCounter = 0;

            // 尖锐的轨迹粒子
            for (int i = 0; i < 4; i++) {
                double offsetX = (this.rand.nextDouble() - 0.5) * 0.3;
                double offsetY = (this.rand.nextDouble() - 0.5) * 0.3;
                double offsetZ = (this.rand.nextDouble() - 0.5) * 0.3;

                this.world.spawnParticle(EnumParticleTypes.CRIT,
                        this.posX + offsetX,
                        this.posY + offsetY,
                        this.posZ + offsetZ,
                        -this.motionX * 0.1 + (this.rand.nextDouble() - 0.5) * 0.05,
                        -this.motionY * 0.1 + (this.rand.nextDouble() - 0.5) * 0.05,
                        -this.motionZ * 0.1 + (this.rand.nextDouble() - 0.5) * 0.05);
            }

            // 少量火焰粒子
            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX, this.posY, this.posZ,
                    -this.motionX * 0.03, -this.motionY * 0.03, -this.motionZ * 0.03);

            // 偶尔添加金属闪光
            if (this.rand.nextInt(10) == 0) {
                this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
                        this.posX, this.posY, this.posZ,
                        (this.rand.nextDouble() - 0.5) * 0.1,
                        (this.rand.nextDouble() - 0.5) * 0.1,
                        (this.rand.nextDouble() - 0.5) * 0.1);
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

    public float getArmorPenetration() {
        return armorPenetration;
    }
}