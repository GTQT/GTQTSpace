package meowmel.gtqtspace.common.entity.weapon.missile;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityIncendiaryMissile extends EntityMissileBase {

    // 燃烧弹参数
    private final float explosionPower;
    private final float fireRadius;
    private final int burnDuration;

    public EntityIncendiaryMissile(World worldIn) {
        super(worldIn, 4.5f, 1.8f, 0.08f, 350);
        this.explosionPower = 2.0f;
        this.fireRadius = 6.0f;
        this.burnDuration = 100; // 5秒燃烧时间
    }

    public EntityIncendiaryMissile(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z, 4.5f, 1.8f, 0.08f, 350);
        this.explosionPower = 2.0f;
        this.fireRadius = 6.0f;
        this.burnDuration = 100;
    }

    public EntityIncendiaryMissile(World worldIn, Entity launcher) {
        super(worldIn, launcher, 4.5f, 1.8f, 0.08f, 350);
        this.explosionPower = 2.0f;
        this.fireRadius = 6.0f;
        this.burnDuration = 100;
    }

    public EntityIncendiaryMissile(World worldIn, Entity launcher, float explosionPower, float fireRadius, int burnDuration) {
        super(worldIn, launcher, 4.5f, 1.8f, 0.08f, 350);
        this.explosionPower = explosionPower;
        this.fireRadius = fireRadius;
        this.burnDuration = burnDuration;
    }

    @Override
    protected EnumParticleTypes getTrailParticleType() {
        // 燃烧弹使用火焰粒子作为轨迹
        return EnumParticleTypes.FLAME;
    }

    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 较小威力的爆炸
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true);

            // 点燃周围实体
            List<Entity> affectedEntities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                    new AxisAlignedBB(this.posX - fireRadius, this.posY - fireRadius, this.posZ - fireRadius,
                            this.posX + fireRadius, this.posY + fireRadius, this.posZ + fireRadius));

            for (Entity entity : affectedEntities) {
                if (entity != this.launchingEntity && entity != this) {
                    double distance = this.getDistance(entity);
                    if (distance <= fireRadius) {
                        // 对实体造成火焰伤害
                        entity.attackEntityFrom(net.minecraft.util.DamageSource.IN_FIRE, 2.0f);

                        // 点燃实体
                        if (entity instanceof net.minecraft.entity.EntityLivingBase) {
                            entity.setFire(burnDuration / 20); // 转换为秒
                        }
                    }
                }
            }

            // 在周围生成火焰
            spawnFireBlocks();

            // 播放特殊音效
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ITEM_FIRECHARGE_USE,
                    net.minecraft.util.SoundCategory.BLOCKS, 3.0F,
                    (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);

        } else {
            // 客户端火焰爆炸粒子效果
            spawnFireExplosionParticles();
        }

        this.setDead();
    }

    private void spawnFireBlocks() {
        int fireBlocks = 15 + this.rand.nextInt(10); // 15-25个火焰方块

        for (int i = 0; i < fireBlocks; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * fireRadius * 1.5;
            double offsetY = this.rand.nextDouble() * fireRadius * 0.8;
            double offsetZ = (this.rand.nextDouble() - 0.5) * fireRadius * 1.5;

            net.minecraft.util.math.BlockPos firePos = new net.minecraft.util.math.BlockPos(
                    this.posX + offsetX,
                    this.posY + offsetY,
                    this.posZ + offsetZ
            );

            // 检查是否可以放置火焰
            if (this.world.isAirBlock(firePos) && Blocks.FIRE.canPlaceBlockAt(this.world, firePos)) {
                this.world.setBlockState(firePos, net.minecraft.init.Blocks.FIRE.getDefaultState());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnFireExplosionParticles() {
        for (int i = 0; i < 80; i++) {
            double offsetX = (this.rand.nextDouble() - 0.5) * fireRadius * 1.5;
            double offsetY = (this.rand.nextDouble() - 0.5) * fireRadius * 1.5;
            double offsetZ = (this.rand.nextDouble() - 0.5) * fireRadius * 1.5;

            // 大量火焰粒子
            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ,
                    offsetX * 0.05, offsetY * 0.05, offsetZ * 0.05);

            // 烟雾粒子
            if (i % 3 == 0) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        this.posX + offsetX * 0.7, this.posY + offsetY * 0.7, this.posZ + offsetZ * 0.7,
                        offsetX * 0.02, offsetY * 0.02, offsetZ * 0.02);
            }

            // 熔岩粒子效果
            if (i % 5 == 0) {
                this.world.spawnParticle(EnumParticleTypes.LAVA,
                        this.posX + offsetX * 0.5, this.posY + offsetY * 0.5, this.posZ + offsetZ * 0.5,
                        0, 0, 0);
            }
        }
    }

    // Getter 方法
    public float getExplosionPower() { return explosionPower; }
    public float getFireRadius() { return fireRadius; }
    public int getBurnDuration() { return burnDuration; }
}