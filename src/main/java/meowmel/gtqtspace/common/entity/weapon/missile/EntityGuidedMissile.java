package meowmel.gtqtspace.common.entity.weapon.missile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityGuidedMissile extends EntityMissileBase {

    // 制导导弹参数
    private final float explosionPower;
    private final float damageRadius;
    private final float baseDamage;
    private final float lockRange; // 锁定范围 - 延长至128格
    private final float lockAngle; // 锁定锥形角度
    private final int lockTimeRequired = 20; // 需要20 tick锁定时间
    // 制导相关变量
    private Entity targetEntity;
    private boolean hasLockedTarget = false;
    private int lockCounter = 0;
    private boolean hasSentLockMessage = false; // 防止重复发送锁定消息

    // 调试相关
    private boolean debugMode = false; // 设置为true可启用调试粒子

    public EntityGuidedMissile(World worldIn) {
        super(worldIn, 4.0f, 1.5f, 0.1f, 400);
        this.explosionPower = 10.0f;
        this.damageRadius = 8.0f;
        this.baseDamage = 15.0f;
        this.lockRange = 128.0f;
        this.lockAngle = 45.0f;
    }

    public EntityGuidedMissile(World worldIn, Entity launcher) {
        this(worldIn, launcher, 10.0f, 8.0f, 15.0f, 128.0f, 45.0f);
    }

    public EntityGuidedMissile(World worldIn, Entity launcher, float explosionPower, float damageRadius,
                               float baseDamage, float lockRange, float lockAngle) {
        super(worldIn, launcher, 4.0f, 1.5f, 0.1f, 400);
        this.explosionPower = explosionPower;
        this.damageRadius = damageRadius;
        this.baseDamage = baseDamage;
        this.lockRange = lockRange;
        this.lockAngle = lockAngle;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote && !this.hasExploded) {
            // 如果没有锁定目标，尝试锁定
            if (!this.hasLockedTarget) {
                acquireTarget();
            } else {
                // 如果已经锁定目标，进行跟踪
                trackTarget();

                // 检查目标距离，如果小于5格则爆炸
                if (this.targetEntity != null && !this.targetEntity.isDead) {
                    double distance = this.getDistance(this.targetEntity);
                    if (distance <= 5.0f) {
                        explode();
                    }
                } else {
                    // 目标丢失，转为普通导弹
                    loseTarget();
                }
            }
        }

        // 客户端调试显示
        if (this.world.isRemote && this.debugMode) {
            debugDrawLockingCone();
        }
    }

    /**
     * 检查目标是否有效
     */
    private boolean isValidTarget(Entity entity) {
        // 1. 不是发射者自己
        if (entity == this.launchingEntity || entity == this)
            return false;

        // 2. 实体存活
        if (!entity.isEntityAlive())
            return false;

        // 3. 是生物或玩家
        if (!(entity instanceof EntityLivingBase))
            return false;

        // 4. 在锁定范围内
        double distance = this.getDistance(entity);
        if (distance > lockRange)
            return false;

        // 5. 在锁定锥形角度内
        double angle = calculateAngleToEntity(entity);
        if (angle > lockAngle)
            return false;

        // 6. 有视线（可选，增加真实性）
        return hasLineOfSight(entity);
    }

    /**
     * 检查是否有视线（无遮挡）
     */
    private boolean hasLineOfSight(Entity entity) {
        RayTraceResult result = this.world.rayTraceBlocks(
                new Vec3d(this.posX, this.posY, this.posZ),
                new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ),
                false, true, false
        );

        return result == null || result.typeOfHit == RayTraceResult.Type.MISS;
    }

    /**
     * 在锥形范围内捕获目标
     */
    private void acquireTarget() {
        // 获取导弹前方锥形范围内的所有实体
        List<Entity> potentialTargets = this.world.getEntitiesWithinAABBExcludingEntity(this,
                this.getEntityBoundingBox().grow(lockRange));

        Entity bestTarget = null;
        double bestScore = Double.MAX_VALUE;

        for (Entity entity : potentialTargets) {
            if (!isValidTarget(entity)) continue;

            // 计算综合评分（角度权重更高）
            double distance = this.getDistance(entity);
            double angle = calculateAngleToEntity(entity);

            // 角度越小、距离越近，评分越好
            double score = angle * 0.7 + distance * 0.3;

            if (score < bestScore) {
                bestScore = score;
                bestTarget = entity;
            }
        }

        // 如果找到目标，开始锁定
        if (bestTarget != null) {
            this.lockCounter++;

            // 添加锁定进度反馈
            if (this.lockCounter % 5 == 0 && getLaunchingPlayer(launchingEntity) instanceof EntityPlayer player) {
                int progress = (this.lockCounter * 100 / this.lockTimeRequired);
                player.sendMessage(new TextComponentString(
                        TextFormatting.YELLOW + "锁定中... " + progress + "%"
                ));
            }

            if (this.lockCounter >= this.lockTimeRequired) {
                completeLockOn(bestTarget);
            }
        } else {
            // 没有找到目标，重置锁定计数
            if (this.lockCounter > 0) {
                this.lockCounter = Math.max(0, this.lockCounter - 2);
            }
        }
    }

    /**
     * 完成锁定目标
     */
    private void completeLockOn(Entity target) {
        this.targetEntity = target;
        this.hasLockedTarget = true;

        // 播放锁定音效
        this.world.playSound(null, this.posX, this.posY, this.posZ,
                net.minecraft.init.SoundEvents.BLOCK_NOTE_PLING,
                net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 2.0F);

        // 发送锁定成功消息
        sendTargetLockedMessage(target);
    }

    /**
     * 发送目标锁定成功消息
     */
    private void sendTargetLockedMessage(Entity target) {
        if (!this.hasSentLockMessage && getLaunchingPlayer(launchingEntity) instanceof EntityPlayer player) {

            String targetName = target.getName();
            String targetType = target.getClass().getSimpleName();
            double distance = this.getDistance(target);

            TextComponentString message = new TextComponentString(
                    TextFormatting.GREEN + "✓ 目标锁定! " +
                            TextFormatting.YELLOW + targetName +
                            TextFormatting.GRAY + " [" + targetType + "] " +
                            TextFormatting.WHITE + "距离: " + String.format("%.1f", distance) + "m"
            );

            player.sendMessage(message);
            this.hasSentLockMessage = true;
        }
    }

    /**
     * 丢失目标
     */
    private void loseTarget() {
        this.hasLockedTarget = false;
        this.targetEntity = null;
        this.lockCounter = 0;
        this.hasSentLockMessage = false;

        // 发送目标丢失消息
        sendTargetLostMessage();
    }

    /**
     * 发送目标丢失消息
     */
    private void sendTargetLostMessage() {
        if (getLaunchingPlayer(launchingEntity) instanceof EntityPlayer player) {

            TextComponentString message = new TextComponentString(
                    TextFormatting.RED + "⚠ 目标丢失，转为常规飞行模式 ⚠"
            );

            player.sendMessage(message);
        }
    }

    /**
     * 计算实体相对于导弹前方的角度
     */
    private double calculateAngleToEntity(Entity entity) {
        // 导弹前进方向
        Vec3d missileDirection = this.getLookVec();

        // 导弹到实体的方向
        Vec3d toTarget = new Vec3d(
                entity.posX - this.posX,
                entity.posY - this.posY,
                entity.posZ - this.posZ
        ).normalize();

        // 计算角度（弧度）
        double dotProduct = missileDirection.dotProduct(toTarget);

        // 确保在有效范围内
        dotProduct = Math.max(-1.0, Math.min(1.0, dotProduct));

        // 返回角度（度）
        return Math.toDegrees(Math.acos(dotProduct));
    }

    /**
     * 跟踪目标
     */
    private void trackTarget() {
        if (this.targetEntity == null || this.targetEntity.isDead || !this.targetEntity.isEntityAlive()) {
            loseTarget();
            return;
        }

        // 检查目标是否还在有效范围内
        double distance = this.getDistance(this.targetEntity);
        if (distance > lockRange * 1.5f) { // 允许稍微超出原锁定范围
            loseTarget();
            return;
        }

        // 计算预测位置（提前量预测）
        Vec3d targetPos = calculatePredictedTargetPosition();

        Vec3d missilePos = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d directionToTarget = targetPos.subtract(missilePos).normalize();

        // 动态转向系数（距离越近转向越灵敏）
        double turnFactor = calculateTurnFactor(distance);

        // 平滑转向
        Vec3d currentDirection = this.getLookVec();
        Vec3d newDirection = currentDirection.scale(1.0 - turnFactor)
                .add(directionToTarget.scale(turnFactor))
                .normalize();

        // 更新导弹方向
        updateMissileDirection(newDirection);

        // 动态速度控制
        controlSpeed(distance);

        // 偶尔播放跟踪音效
        if (this.ticksExisted % 40 == 0) {
            this.world.playSound(null, this.posX, this.posY, this.posZ,
                    net.minecraft.init.SoundEvents.BLOCK_NOTE_HAT,
                    net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.5F);
        }
    }

    /**
     * 计算预测目标位置（提前量）
     */
    private Vec3d calculatePredictedTargetPosition() {
        // 基础目标位置
        Vec3d basePos = new Vec3d(
                this.targetEntity.posX,
                this.targetEntity.posY + this.targetEntity.getEyeHeight() * 0.5,
                this.targetEntity.posZ
        );

        // 如果是移动实体，计算提前量
        if (this.targetEntity instanceof EntityLivingBase) {
            double missileToTargetTime = this.getDistance(this.targetEntity) / Math.max(this.speed, 0.1);

            // 预测目标位置（简单的线性预测）
            basePos = basePos.add(
                    this.targetEntity.motionX * missileToTargetTime * 0.5,
                    this.targetEntity.motionY * missileToTargetTime * 0.5,
                    this.targetEntity.motionZ * missileToTargetTime * 0.5
            );
        }

        return basePos;
    }

    /**
     * 计算转向系数
     */
    private double calculateTurnFactor(double distance) {
        // 距离越近，转向越灵敏
        double baseTurnRate = 0.3;
        double proximityBoost = Math.max(0, 1.0 - distance / 20.0) * 0.4;
        return Math.min(baseTurnRate + proximityBoost, 0.7);
    }

    /**
     * 更新导弹方向
     */
    private void updateMissileDirection(Vec3d newDirection) {
        this.rotationYaw = (float) Math.toDegrees(Math.atan2(newDirection.x, newDirection.z));
        this.rotationPitch = (float) Math.toDegrees(Math.asin(-newDirection.y));

        // 更新运动方向
        this.initialMotionX = newDirection.x;
        this.initialMotionY = newDirection.y;
        this.initialMotionZ = newDirection.z;
    }

    /**
     * 控制速度
     */
    private void controlSpeed(double distance) {
        if (distance > 20.0) {
            // 远距离时加速追击
            this.speed = Math.min(this.speed + this.acceleration * 2.0f, this.maxSpeed * 1.5f);
        } else if (distance < 5.0) {
            // 近距离时稍微减速以提高命中率
            this.speed = Math.max(this.speed * 0.98f, this.maxSpeed * 0.8f);
        } else {
            // 正常加速
            this.speed = Math.min(this.speed + this.acceleration, this.maxSpeed);
        }
    }

    @Override
    protected void updateMotion() {
        // 如果有锁定目标，使用跟踪逻辑（在trackTarget中处理）
        if (!this.hasLockedTarget) {
            super.updateMotion();
        } else {
            // 跟踪时的运动在trackTarget中已经更新了方向
            this.motionX = this.initialMotionX * this.speed;
            this.motionY = this.initialMotionY * this.speed;
            this.motionZ = this.initialMotionZ * this.speed;
        }
    }

    @Override
    protected void explode() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        if (!this.world.isRemote) {
            // 发送命中消息（如果命中目标）
            if (this.targetEntity != null && this.targetEntity.isEntityAlive() &&
                    this.launchingEntity instanceof EntityPlayer) {
                sendTargetHitMessage();
            }

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
                        float damage = (float) ((damageRadius - distance) / damageRadius * baseDamage);
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

    /**
     * 发送目标命中消息
     */
    private void sendTargetHitMessage() {
        if (this.launchingEntity instanceof EntityPlayer player) {

            String targetName = this.targetEntity.getName();

            TextComponentString message = new TextComponentString(
                    TextFormatting.RED + "✸ 命中目标: " +
                            TextFormatting.YELLOW + targetName +
                            TextFormatting.RED + " ✸"
            );

            player.sendMessage(message);
        }
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

    @Override
    protected EnumParticleTypes getTrailParticleType() {
        // 如果锁定目标，使用不同的粒子效果
        return this.hasLockedTarget ? EnumParticleTypes.VILLAGER_ANGRY : EnumParticleTypes.SMOKE_LARGE;
    }

    // 添加锁定目标时的特殊轨迹粒子
    @Override
    @SideOnly(Side.CLIENT)
    protected void spawnTrailParticles() {
        super.spawnTrailParticles();

        // 如果锁定目标，添加额外的锁定粒子
        if (this.hasLockedTarget && this.targetEntity != null) {
            if (this.trailCounter % 5 == 0) {
                // 从导弹指向目标的粒子
                Vec3d missilePos = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d targetPos = new Vec3d(
                        this.targetEntity.posX,
                        this.targetEntity.posY + this.targetEntity.getEyeHeight(),
                        this.targetEntity.posZ
                );

                Vec3d direction = targetPos.subtract(missilePos).normalize();
                double distance = missilePos.distanceTo(targetPos);

                // 在路径上生成多个粒子
                for (int i = 0; i < 3; i++) {
                    double progress = this.rand.nextDouble();
                    double particleX = this.posX + direction.x * distance * progress;
                    double particleY = this.posY + direction.y * distance * progress;
                    double particleZ = this.posZ + direction.z * distance * progress;

                    this.world.spawnParticle(EnumParticleTypes.REDSTONE,
                            particleX, particleY, particleZ,
                            0, 0, 0);
                }
            }
        }
    }

    /**
     * 调试方法：绘制锁定锥形区域
     */
    @SideOnly(Side.CLIENT)
    private void debugDrawLockingCone() {
        if (!this.hasLockedTarget) {
            // 绘制锁定锥形区域的粒子效果
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(this.lockAngle * (i / 4.0));
                double length = this.lockRange;

                // 计算锥形边界点
                Vec3d forward = this.getLookVec();
                Vec3d right = forward.crossProduct(new Vec3d(0, 1, 0)).normalize();
                Vec3d up = forward.crossProduct(right).normalize();

                Vec3d coneEdge = forward.scale(Math.cos(angle))
                        .add(right.scale(Math.sin(angle)))
                        .normalize()
                        .scale(length);

                // 生成粒子
                for (int j = 1; j <= 10; j++) {
                    double progress = j / 10.0;
                    this.world.spawnParticle(EnumParticleTypes.REDSTONE,
                            this.posX + coneEdge.x * progress,
                            this.posY + coneEdge.y * progress,
                            this.posZ + coneEdge.z * progress,
                            0, 1, 0); // 绿色粒子
                }
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

    public float getLockRange() {
        return lockRange;
    }

    public float getLockAngle() {
        return lockAngle;
    }

    public Entity getTargetEntity() {
        return targetEntity;
    }

    public boolean hasLockedTarget() {
        return hasLockedTarget;
    }

    // 设置调试模式
    public void setDebugMode(boolean debug) {
        this.debugMode = debug;
    }
}