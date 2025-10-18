package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityExplosiveMissile extends EntityMissileBase {

    // 爆炸参数
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;

    public EntityExplosiveMissile(World worldIn) {
        super(worldIn, 5.0f, 2.0f, 0.1f, 400);
        this.explosionPower = 10.0f;
        this.damageRadius = 8.0f;
        this.baseDamage = 20.0f;
    }

    public EntityExplosiveMissile(World worldIn, Entity launcher) {
        this(worldIn, launcher, 10.0f, 8.0f, 20.0f);
    }

    public EntityExplosiveMissile(World worldIn, Entity launcher, float explosionPower, float damageRadius, float baseDamage) {
        super(worldIn, launcher, 5.0f, 2.0f, 0.1f, 400);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
    }


    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 创建爆炸
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 对周围实体造成伤害
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

            // 播放爆炸音效
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE,
                    net.minecraft.util.SoundCategory.BLOCKS, 4.0F,
                    (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
        } else {
            // 客户端爆炸粒子效果
            spawnExplosionParticles();
        }

        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    private void spawnExplosionParticles() {
        for (int i = 0; i < 50; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * explosionPower * 2;
            double offsetY = (this.rand.nextDouble() - 0.5) * explosionPower * 2;
            double offsetZ = (this.rand.nextDouble() - 0.5) * explosionPower * 2;

            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1);

            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX + offsetX * 0.5, this.posY + offsetY * 0.5, this.posZ + offsetZ * 0.5,
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
}