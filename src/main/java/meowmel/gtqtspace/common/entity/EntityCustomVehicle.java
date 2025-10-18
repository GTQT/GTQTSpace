package meowmel.gtqtspace.common.entity;

import meowmel.gtqtspace.common.network.MessageVehicleInput;
import meowmel.gtqtspace.common.network.PacketDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityCustomVehicle extends Entity {

    // 数据监视器参数
    public static final DataParameter<Boolean> ACCELERATING = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> PITCH_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> YAW_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> ROLL_INPUT = EntityDataManager.createKey(EntityCustomVehicle.class, DataSerializers.FLOAT);

    // 物理参数
    private final float acceleration = 0.1f;
    private final float rotationSpeed = 3.0f;
    private final float maxSpeed = 3.0f;
    private final float drag = 0.98f;
    private final float airResistance = 0.995f;

    // 输入状态
    private boolean accelerating = false;
    private float pitchInput = 0;
    private float yawInput = 0;
    private float rollInput = 0;

    // 输入平滑处理
    private float smoothedPitch = 0;
    private float smoothedYaw = 0;
    private float smoothedRoll = 0;
    private final float smoothFactor = 0.7f;

    // 输入超时保护
    private long lastInputTime = 0;
    private static final long INPUT_TIMEOUT_MS = 500;

    // 调试计数
    private AtomicInteger debugCounter = new AtomicInteger(0);

    public EntityCustomVehicle(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.0F);
        this.stepHeight = 1.0F;
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
        this.setNoGravity(true); // 太空环境无重力
    }

    public EntityCustomVehicle(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(ACCELERATING, false);
        this.dataManager.register(PITCH_INPUT, 0F);
        this.dataManager.register(YAW_INPUT, 0F);
        this.dataManager.register(ROLL_INPUT, 0F);
    }

    public float prevRotationPitch;
    public float prevRotationYaw;

    @Override
    public void onUpdate() {
        // 保存上一tick的旋转角度
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;

        // 更新乘客位置
        this.updatePassengerPositions();

        // 处理输入超时
        handleInputTimeout();

        // 平滑输入处理
        smoothInputs();

        // 处理运动
        updateMotion();

        // 调试输出
        if (debugCounter.incrementAndGet() % 20 == 0) { // 每秒钟输出一次
            printDebugInfo();
        }
    }

    private void handleInputTimeout() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastInputTime > INPUT_TIMEOUT_MS) {
            // 超时后逐渐清零输入
            accelerating = false;
            pitchInput *= 0.5f;
            yawInput *= 0.5f;
            rollInput *= 0.5f;

            // 如果输入值很小，直接清零
            if (Math.abs(pitchInput) < 0.01f) pitchInput = 0;
            if (Math.abs(yawInput) < 0.01f) yawInput = 0;
            if (Math.abs(rollInput) < 0.01f) rollInput = 0;
        }
    }

    private void smoothInputs() {
        smoothedPitch = lerp(smoothedPitch, pitchInput, smoothFactor);
        smoothedYaw = lerp(smoothedYaw, yawInput, smoothFactor);
        smoothedRoll = lerp(smoothedRoll, rollInput, smoothFactor);
    }

    private float lerp(float a, float b, float factor) {
        return a + (b - a) * factor;
    }

    private void updateMotion() {
        // 应用加速
        if (accelerating) {
            applyAcceleration();
        }

        // 应用旋转
        applyRotation();

        // 应用阻力和空气阻力
        applyResistance();

        // 限制最大速度
        limitMaxSpeed();

        // 应用运动
        if (!this.world.isRemote) {
            // 服务端：实际移动并同步到客户端
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        } else {
            // 客户端：预测移动（服务端会校正）
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        }
    }

    private void applyAcceleration() {
        // 基于当前朝向计算前进方向
        float yawRad = (float) Math.toRadians(-this.rotationYaw);
        float pitchRad = (float) Math.toRadians(-this.rotationPitch);

        double forwardX = Math.sin(yawRad) * Math.cos(pitchRad);
        double forwardY = Math.sin(pitchRad);
        double forwardZ = Math.cos(yawRad) * Math.cos(pitchRad);

        // 应用加速度
        this.motionX += forwardX * acceleration;
        this.motionY += forwardY * acceleration;
        this.motionZ += forwardZ * acceleration;
    }

    // 在 EntityCustomVehicle 类中修改 applyRotation 方法
    private void applyRotation() {
        // 应用平滑后的旋转输入
        this.rotationPitch += smoothedPitch * rotationSpeed;
        this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);

        this.rotationYaw += smoothedYaw * rotationSpeed;
        // 确保偏航角在合理范围内，但不限制持续旋转
        this.rotationYaw = this.rotationYaw % 360.0F;

        // 可选：滚转控制（如果需要）
        // this.rotationRoll += smoothedRoll * rotationSpeed;
        // this.rotationRoll %= 360.0F;
    }

    private void applyResistance() {
        // 基础阻力
        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;

        // 空气阻力（在太空环境中可能较小）
        this.motionX *= airResistance;
        this.motionY *= airResistance;
        this.motionZ *= airResistance;
    }

    private void limitMaxSpeed() {
        double currentSpeed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        if (currentSpeed > maxSpeed) {
            double ratio = maxSpeed / currentSpeed;
            this.motionX *= ratio;
            this.motionY *= ratio;
            this.motionZ *= ratio;
        }
    }

    private void updatePassengerPositions() {
        for (Entity passenger : this.getPassengers()) {
            updatePassenger(passenger);
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float offsetY = (float) (this.getMountedYOffset() + passenger.getYOffset());

            passenger.setPosition(
                    this.posX,
                    this.posY + offsetY,
                    this.posZ
            );

            // 乘客视角与飞船同步
            passenger.rotationYaw = this.rotationYaw;
            passenger.rotationPitch = this.rotationPitch;
            passenger.setRotationYawHead(this.rotationYaw);
        }
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!this.world.isRemote && this.getPassengers().isEmpty()) {
            player.startRiding(this);
            return true;
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public double getMountedYOffset() {
        return 0.3D;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("MotionX")) {
            this.motionX = compound.getDouble("MotionX");
            this.motionY = compound.getDouble("MotionY");
            this.motionZ = compound.getDouble("MotionZ");
        }
        if (compound.hasKey("RotationYaw")) {
            this.rotationYaw = compound.getFloat("RotationYaw");
            this.rotationPitch = compound.getFloat("RotationPitch");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setDouble("MotionX", this.motionX);
        compound.setDouble("MotionY", this.motionY);
        compound.setDouble("MotionZ", this.motionZ);
        compound.setFloat("RotationYaw", this.rotationYaw);
        compound.setFloat("RotationPitch", this.rotationPitch);
    }

    // 客户端输入处理方法
    public void updateInputs(boolean accelerating, float pitch, float yaw) {
        // 更新输入状态
        this.accelerating = accelerating;
        this.pitchInput = pitch;
        this.yawInput = yaw;
        this.lastInputTime = System.currentTimeMillis();

        // 同步到数据监视器（用于渲染和预测）
        this.dataManager.set(ACCELERATING, accelerating);
        this.dataManager.set(PITCH_INPUT, pitch);
        this.dataManager.set(YAW_INPUT, yaw);

        // 发送网络包到服务端
        if (this.world.isRemote) {
            PacketDispatcher.sendToServer(new MessageVehicleInput(
                    this.getEntityId(), accelerating, pitch, yaw
            ));

            // 调试输出
            System.out.println("Client input sent - Accel: " + accelerating + ", Pitch: " + pitch + ", Yaw: " + yaw);
        }
    }

    // 服务端输入更新方法 - 由网络包调用
    public void updateInputsFromServer(boolean accelerating, float pitch, float yaw) {
        // 直接更新输入状态
        this.accelerating = accelerating;
        this.pitchInput = pitch;
        this.yawInput = yaw;
        this.lastInputTime = System.currentTimeMillis();

        // 同步到数据监视器，以便在客户端显示
        this.dataManager.set(ACCELERATING, accelerating);
        this.dataManager.set(PITCH_INPUT, pitch);
        this.dataManager.set(YAW_INPUT, yaw);

        // 立即应用运动更新以确保响应性
        if (!this.world.isRemote) {
            this.updateMotion();
        }

        // 调试输出
        System.out.println("Server received input - Accel: " + accelerating + ", Pitch: " + pitch + ", Yaw: " + yaw);
    }

    private void printDebugInfo() {
        String side = this.world.isRemote ? "Client" : "Server";
        System.out.println("[" + side + "] " + getMotionState() + " | " + getInputState());
    }

    // 获取当前输入状态（用于调试）
    public String getInputState() {
        return String.format("Input - Accel: %s, Pitch: %.1f, Yaw: %.1f",
                accelerating, pitchInput, yawInput);
    }

    // 获取运动状态（用于调试）
    public String getMotionState() {
        double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        return String.format("Pos: %.1f,%.1f,%.1f | Motion: %.2f | Speed: %.2f",
                posX, posY, posZ,
                Math.sqrt(motionX * motionX + motionZ * motionZ), speed);
    }

    // 强制停止飞船
    public void stopVehicle() {
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.accelerating = false;
        this.pitchInput = 0;
        this.yawInput = 0;
        this.rollInput = 0;
    }

    // 获取当前速度
    public double getCurrentSpeed() {
        return Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
    }

    // 是否在移动
    public boolean isMoving() {
        return getCurrentSpeed() > 0.01;
    }
}