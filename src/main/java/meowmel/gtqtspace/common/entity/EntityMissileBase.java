package meowmel.gtqtspace.common.entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
public abstract class EntityMissileBase extends Entity {

    // 导弹通用参数
    protected final float maxSpeed;
    protected float speed;
    protected final float acceleration;
    protected int lifeTime;
    protected Entity launchingEntity;
    protected boolean hasExploded = false;

    // 轨迹粒子效果
    protected int trailCounter = 0;

    // 运动方向
    protected double initialMotionX;
    protected double initialMotionY;
    protected double initialMotionZ;

    public EntityMissileBase(World worldIn, float maxSpeed, float initialSpeed, float acceleration, int lifeTime) {
        super(worldIn);
        this.maxSpeed = maxSpeed;
        this.speed = initialSpeed;
        this.acceleration = acceleration;
        this.lifeTime = lifeTime;
        this.setSize(0.5f, 0.5f);
        this.setNoGravity(true);
    }

    public EntityMissileBase(World worldIn, double x, double y, double z,
                             float maxSpeed, float initialSpeed, float acceleration, int lifeTime) {
        this(worldIn, maxSpeed, initialSpeed, acceleration, lifeTime);
        this.setPosition(x, y, z);
    }

    public EntityMissileBase(World worldIn, Entity launcher,
                             float maxSpeed, float initialSpeed, float acceleration, int lifeTime) {
        this(worldIn, maxSpeed, initialSpeed, acceleration, lifeTime);
        this.launchingEntity = launcher;
        this.setInitialPositionAndRotation();
    }

    @Override
    protected void entityInit() {
        // 不需要特殊的数据监视器
    }

    protected void setInitialPositionAndRotation() {
        if (launchingEntity != null) {
            float yaw = launchingEntity.rotationYaw;
            float pitch = launchingEntity.rotationPitch;

            double offsetX = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            double offsetY = -MathHelper.sin(pitch * 0.017453292F);
            double offsetZ = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);

            double offsetDistance = 2.0;
            this.setPosition(
                    launchingEntity.posX + offsetX * offsetDistance,
                    launchingEntity.posY + launchingEntity.getEyeHeight() + offsetY * offsetDistance,
                    launchingEntity.posZ + offsetZ * offsetDistance
            );

            this.rotationYaw = yaw;
            this.rotationPitch = pitch;

            calculateInitialMotion();
        }
    }

    /**
     * 计算初始运动方向
     */
    protected void calculateInitialMotion() {
        float yawRad = this.rotationYaw * 0.017453292F;
        float pitchRad = this.rotationPitch * 0.017453292F;

        this.initialMotionX = -MathHelper.sin(yawRad) * MathHelper.cos(pitchRad);
        this.initialMotionY = -MathHelper.sin(pitchRad);
        this.initialMotionZ = MathHelper.cos(yawRad) * MathHelper.cos(pitchRad);

        double length = Math.sqrt(initialMotionX * initialMotionX + initialMotionY * initialMotionY + initialMotionZ * initialMotionZ);
        if (length > 0) {
            this.initialMotionX /= length;
            this.initialMotionY /= length;
            this.initialMotionZ /= length;
        }
    }

    /**
     * 发射导弹
     */
    public void launch() {
        updateMotion();

        if (!this.world.isRemote) {
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_FIREWORK_LAUNCH,
                    net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 0.8F);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        updateMotion();
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

        checkCollision();
        updateLifeTime();
        spawnTrailParticles();
    }

    protected void updateMotion() {
        this.speed = Math.min(this.speed + this.acceleration, this.maxSpeed);

        this.motionX = initialMotionX * this.speed;
        this.motionY = initialMotionY * this.speed;
        this.motionZ = initialMotionZ * this.speed;
    }

    protected void checkCollision() {
        if (this.world.isRemote || this.hasExploded) return;

        // 方向变化检测
        Vec3d expectedMotion = new Vec3d(
                initialMotionX * this.speed,
                initialMotionY * this.speed,
                initialMotionZ * this.speed
        );

        Vec3d actualMotion = new Vec3d(this.motionX, this.motionY, this.motionZ);

        double dotProduct = expectedMotion.x * actualMotion.x +
                expectedMotion.y * actualMotion.y +
                expectedMotion.z * actualMotion.z;

        double expectedLength = expectedMotion.length();
        double actualLength = actualMotion.length();

        if (expectedLength > 0 && actualLength > 0) {
            double cosAngle = dotProduct / (expectedLength * actualLength);
            if (cosAngle < 0.9659) {
                explode();
                return;
            }
        }

        // 实体碰撞检测
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this,
                this.getEntityBoundingBox().grow(0.5));

        for (Entity entity : entities) {
            if (entity == this.launchingEntity || entity == this) continue;
            if (!entity.canBeCollidedWith()) continue;

            AxisAlignedBB entityBB = entity.getEntityBoundingBox();
            if (this.getEntityBoundingBox().intersects(entityBB)) {
                explode();
                return;
            }
        }
    }

    protected void updateLifeTime() {
        this.lifeTime--;
        if (this.lifeTime <= 0 && !this.hasExploded) {
            explode();
        }
    }

    @SideOnly(Side.CLIENT)
    protected void spawnTrailParticles() {
        this.trailCounter++;
        if (this.trailCounter >= 2) {
            this.trailCounter = 0;

            for (int i = 0; i < 3; i++) {
                double offsetX = (this.rand.nextDouble() - 0.5) * 0.2;
                double offsetY = (this.rand.nextDouble() - 0.5) * 0.2;
                double offsetZ = (this.rand.nextDouble() - 0.5) * 0.2;

                this.world.spawnParticle(getTrailParticleType(),
                        this.posX + offsetX,
                        this.posY + offsetY,
                        this.posZ + offsetZ,
                        -this.motionX * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1,
                        -this.motionY * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1,
                        -this.motionZ * 0.1 + (this.rand.nextDouble() - 0.5) * 0.1);
            }

            // 额外火焰粒子
            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX, this.posY, this.posZ,
                    -this.motionX * 0.05, -this.motionY * 0.05, -this.motionZ * 0.05);
        }
    }

    /**
     * 获取轨迹粒子类型（子类可以重写）
     */
    protected EnumParticleTypes getTrailParticleType() {
        return EnumParticleTypes.SMOKE_LARGE;
    }

    /**
     * 抽象爆炸方法，子类必须实现
     */
    protected abstract void explode();

    /**
     * 创建爆炸伤害源
     */
    protected net.minecraft.util.DamageSource createDamageSource() {
        if (this.launchingEntity instanceof net.minecraft.entity.EntityLivingBase) {
            return net.minecraft.util.DamageSource.causeExplosionDamage(
                    (net.minecraft.entity.EntityLivingBase) this.launchingEntity);
        } else {
            return new net.minecraft.util.DamageSource("explosion")
                    .setDifficultyScaled()
                    .setExplosion();
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.speed = compound.getFloat("Speed");
        this.lifeTime = compound.getInteger("LifeTime");
        this.hasExploded = compound.getBoolean("HasExploded");
        this.initialMotionX = compound.getDouble("InitialMotionX");
        this.initialMotionY = compound.getDouble("InitialMotionY");
        this.initialMotionZ = compound.getDouble("InitialMotionZ");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setFloat("Speed", this.speed);
        compound.setInteger("LifeTime", this.lifeTime);
        compound.setBoolean("HasExploded", this.hasExploded);
        compound.setDouble("InitialMotionX", this.initialMotionX);
        compound.setDouble("InitialMotionY", this.initialMotionY);
        compound.setDouble("InitialMotionZ", this.initialMotionZ);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    // Getter 和 Setter 方法
    public float getSpeed() { return this.speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public int getLifeTime() { return this.lifeTime; }
    public Entity getLaunchingEntity() { return this.launchingEntity; }
}