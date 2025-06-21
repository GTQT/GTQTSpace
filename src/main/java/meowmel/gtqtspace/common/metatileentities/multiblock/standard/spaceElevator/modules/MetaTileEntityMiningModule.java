package meowmel.gtqtspace.common.metatileentities.multiblock.standard.spaceElevator.modules;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.items.MetaItems;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.api.recipes.properties.MaxDistenceProperty;
import meowmel.gtqtspace.api.recipes.properties.MinDistenceProperty;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing;
import meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class MetaTileEntityMiningModule extends MetaTileEntitySpaceElevatorModules {

    private final int MAX_PARALLEL;
    private final int MAX_DISTANCE = 300;
    private final int MAX_RANGE = 150;
    //距离轮询系统 只在没有配方工作时开始循环
    boolean cycleMode;
    boolean cycleCircuit;
    int distance;
    int minDistance;
    int maxDistance;
    int range;//搜索范围 为maxDistance-minDistance的长度
    int i = 1;
    private int step = 1;//搜索步长 为每次增加多少
    private int parallel;
    private int updateCounter = 0; // 新增计数器，用于跟踪update调用次数

    public MetaTileEntityMiningModule(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, tier, type);
        this.MAX_PARALLEL = (int) Math.pow(4, this.tier);
        this.parallel = MAX_PARALLEL;
    }

    public static void setCircuitConfiguration(ItemStack itemStack, int configuration) {
        if (!MetaItems.INTEGRATED_CIRCUIT.isItemEqual(itemStack))
            throw new IllegalArgumentException("Given item stack is not an integrated circuit!");
        if (configuration < 0 || configuration > 33)
            throw new IllegalArgumentException("Given configuration number is out of range!");
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            itemStack.setTagCompound(tagCompound);
        }
        tagCompound.setInteger("Configuration", configuration);
    }
    @Override
    public void doCycle()
    {
        // 先执行cycleCircuit逻辑（每次update都执行）
        if (cycleCircuit) {
            if (i > 10) i = 1;
            if (getInputInventory() != null) {
                for (int slot = 0; slot < getInputInventory().getSlots(); slot++) {
                    ItemStack stack = getInputInventory().getStackInSlot(slot);
                    if (stack.isItemEqual(MetaItems.INTEGRATED_CIRCUIT.getStackForm())) {
                        setCircuitConfiguration(stack, i);
                    }
                }
            }
            i++;
        }

        // 每10次update执行一次cycleMode
        if (cycleMode) {
            if (!cycleCircuit || updateCounter % 10 == 9) {
                if (distance < minDistance || distance > maxDistance) {
                    distance = Math.max(minDistance, Math.min(maxDistance, distance));
                }
                distance += step;
                if (distance > maxDistance) {
                    distance = minDistance + (distance - maxDistance - 1);
                } else if (distance < minDistance) {
                    distance = maxDistance;
                }
            }
        }

        // 更新计数器（每次update都计数）
        updateCounter = (updateCounter + 1) % 10;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (!super.checkRecipe(recipe, consumeIfSuccess)) return false;
        return this.distance >= recipe.getProperty(MinDistenceProperty.getInstance(), 0) && this.distance <= recipe.getProperty(MaxDistenceProperty.getInstance(), 0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMiningModule(this.metaTileEntityId, this.tier, this.type);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("H", "C", "C", "C", "C")
                .aisle("C", "C", "C", "S", "C")
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.BASIC_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                )
                .where('H', metaTileEntities(GTQTSMetaTileEntities.ELEVATOR_RECEIVER_HATCH))
                .build();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        for (EnumFacing renderSide : EnumFacing.HORIZONTALS) {
            if (renderSide == getFrontFacing()) {
                getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                        true);
            } else
                GTQTSTextures.MINING_MODULE_OVERLAY.renderSided(renderSide, renderState, translation, pipeline);
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 238);
        builder.image(4, 4, 190, 147, GuiTextures.DISPLAY);
        builder.widget((new IndicatorImageWidget(174, 131, 17, 17, this.getLogo())).setWarningStatus(this.getWarningLogo(), this::addWarningText).setErrorStatus(this.getErrorLogo(), this::addErrorText));

        builder.label(9, 9, this.getMetaFullName(), 16777215);

        int size = 18;
        int padding = 3;


        builder.widget(new LabelWidget(120, 9 + (size + padding), "gtqtspace.gui.mining_module.distance", 0xFF55FF));
        builder.widget(new TextFieldWidget2(170, 9 + (size + padding), size * size, size, this::getDistance, this::setDistance).setMaxLength(3).setAllowedChars(TextFieldWidget2.NATURAL_NUMS));

        builder.widget(new LabelWidget(120, 9 + 2 * (size + padding), "gtqtspace.gui.mining_module.range", 0xFF55FF));
        builder.widget(new TextFieldWidget2(170, 9 + 2 * (size + padding), size * size, size, this::getRange, this::setRange).setMaxLength(3).setAllowedChars(TextFieldWidget2.NATURAL_NUMS));

        builder.widget(new LabelWidget(120, 9 + 3 * (size + padding), "gtqtspace.gui.mining_module.step", 0xFF55FF));
        builder.widget(new TextFieldWidget2(170, 9 + 3 * (size + padding), size * size, size, this::getStep, this::setStep).setMaxLength(3).setAllowedChars(TextFieldWidget2.NATURAL_NUMS));

        builder.widget(new LabelWidget(120, 9 + 4 * (size + padding), "gtqtspace.gui.mining_module.parallel", 0xFF55FF));
        builder.widget(new TextFieldWidget2(170, 9 + 4 * (size + padding), size * size, size, this::getParallel, this::setParallel).setMaxLength(1).setAllowedChars(TextFieldWidget2.NATURAL_NUMS));


        builder.widget((new AdvancedTextWidget(9, (size + padding), this::addDisplayText, 16777215)).setMaxWidthLimit(181).setClickHandler(this::handleDisplayClick));
        IControllable controllable = this.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);
        TextureArea var10007;
        BooleanSupplier var10008;
        if (controllable != null) {
            var10007 = GuiTextures.BUTTON_POWER;
            Objects.requireNonNull(controllable);
            var10008 = controllable::isWorkingEnabled;
            Objects.requireNonNull(controllable);
            builder.widget(new ImageCycleButtonWidget(173, 213, 18, 18, var10007, var10008, controllable::setWorkingEnabled));
            builder.widget(new ImageWidget(173, 231, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));
        }

        /*
        //clear or retrieve list //TODO change the texture of this
        builder.widget(new ClickButtonWidget(173, 155, 18, 18, "", data -> printWhitelistOrClear(data, entityPlayer)).setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID).setTooltipText("gtqtspace.gui.mining_module.print_whitelist_or_clear"));

        //whitelist or blacklist
        builder.widget(new ImageCycleButtonWidget(173, 173, 18, 18, GTQTSTextures.BUTTON_WHITE_BLACK_LIST, this::getWhitelistMode, this::setWhitelistMode).setTooltipHoverString("gtqtspace.gui.mining_module.change_whitelist_mode"));


         */
        //Cycle mode
        builder.widget(new ImageCycleButtonWidget(173, 173, 18, 18, GTQTSTextures.BUTTON_CYCLE, this::getCycleCircuit, this::setCycleCircuit).setTooltipHoverString("gtqtspace.gui.mining_module.circuit"));
        builder.widget(new ImageCycleButtonWidget(173, 191, 18, 18, GTQTSTextures.BUTTON_CYCLE, this::getCycleMode, this::setCycleMode).setTooltipHoverString("gtqtspace.gui.mining_module.cycle"));


        builder.bindPlayerInventory(entityPlayer.inventory, 155);
        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), isActive())
                .addWorkingStatusLine()
                .addParallelsLine(this.parallel)
                .addCustom(tl -> {
                    if (spaceElevatorProvider != null) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtspace.gui.mining_module.tier", this.spaceElevatorProvider.getMotorTier()));
                    }
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtspace.gui.mining_module.min_distance", this.minDistance));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gtqtspace.gui.mining_module.max_distance", this.maxDistance));
                })
                .addEmptyLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent())
                .addEnergyUsageExactLine(recipeMapWorkable.getRecipeEUt())
                .addComputationUsageExactLine(getRecipeMapWorkable().getCurrentDrawnCWUt());

    }

    private boolean getCycleMode() {
        return this.cycleMode;
    }

    private void setCycleMode(boolean bool) {
        this.cycleMode = bool;
    }

    private boolean getCycleCircuit() {
        return this.cycleCircuit;
    }

    private void setCycleCircuit(boolean bool) {
        this.cycleCircuit = bool;
    }

    private String getDistance() {
        return String.valueOf(this.distance);
    }

    private void setDistance(String distance) {
        try {
            int real = Integer.parseInt(distance);

            if (real > this.MAX_DISTANCE) {
                while (real > this.MAX_DISTANCE) {
                    real -= this.MAX_DISTANCE;
                }
            }
            this.distance = real;
            this.minDistance = Math.max(this.distance - this.range / 2, 0);
            this.maxDistance = Math.min(this.distance + this.range / 2, this.MAX_DISTANCE);
        } catch (NumberFormatException e) {
            this.distance = 0;
            this.minDistance = Math.max(this.distance - this.range / 2, 0);
            this.maxDistance = Math.min(this.distance + this.range / 2, this.MAX_DISTANCE);
        }
    }

    private String getRange() {
        return String.valueOf(this.range);
    }

    private void setRange(String range) {
        try {
            if (Integer.parseInt(range) > this.MAX_RANGE) {
                this.range = 0;
                this.minDistance = this.distance;
                this.maxDistance = this.distance;
            } else {
                this.range = Integer.parseInt(range);
                this.minDistance = Math.max(this.distance - this.range / 2, 0);
                this.maxDistance = Math.min(this.distance + this.range / 2, this.MAX_DISTANCE);
            }
        } catch (NumberFormatException e) {
            this.range = 0;
            this.minDistance = this.distance;
            this.maxDistance = this.distance;
        }
    }

    private String getStep() {
        return String.valueOf(this.step);
    }

    private void setStep(String step) {
        try {
            this.step = Integer.parseInt(step);
        } catch (NumberFormatException e) {
            this.step = 1;
        }
    }

    private String getParallel() {
        return String.valueOf(this.parallel);
    }

    private void setParallel(String parallel) {
        try {
            int real = Integer.parseInt(parallel);
            if (real < 1)
                this.parallel = 1;
            else this.parallel = Math.min(real, this.MAX_PARALLEL);
        } catch (NumberFormatException e) {
            this.parallel = 1;
        }
        this.recipeMapWorkable.setParallelLimit(this.parallel);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("distance", this.distance);
        data.setInteger("minDistance", this.minDistance);
        data.setInteger("maxDistance", this.maxDistance);
        data.setInteger("range", this.range);
        data.setInteger("step", this.step);
        data.setInteger("parallel", this.parallel);
        data.setBoolean("cycleMode", this.cycleMode);
        data.setBoolean("cycleCircuit", this.cycleCircuit);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.distance = data.getInteger("distance");
        this.minDistance = data.getInteger("minDistance");
        this.maxDistance = data.getInteger("maxDistance");
        this.range = data.getInteger("range");
        this.step = data.getInteger("step");
        this.parallel = data.getInteger("parallel");
        this.cycleMode = data.getBoolean("cycleMode");
        this.cycleCircuit = data.getBoolean("cycleCircuit");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.distance);
        buf.writeInt(this.minDistance);
        buf.writeInt(this.maxDistance);
        buf.writeInt(this.range);
        buf.writeInt(this.step);
        buf.writeInt(this.parallel);
        buf.writeBoolean(this.cycleMode);
        buf.writeBoolean(this.cycleCircuit);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.distance = buf.readInt();
        this.minDistance = buf.readInt();
        this.maxDistance = buf.readInt();
        this.range = buf.readInt();
        this.step = buf.readInt();
        this.parallel = buf.readInt();
        this.cycleMode = buf.readBoolean();
        this.cycleCircuit = buf.readBoolean();
    }

    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("银河系资源开发", new Object[0]));
        tooltip.add(I18n.format("挖掘指定距离区域的矿物，可通过轮询模式自动挖掘某一范围内的矿物"));
        tooltip.add(I18n.format("并行：矿机所并行执行的配方量，总耗电、所需等离子体量和算力均为单个耗电或算力乘以并行数。"));
        tooltip.add(I18n.format("范围：开启轮询模式后，指定该模块将会在 实际距离 ∈ [距离-范围，距离+范围] 处挖矿，每次增加一个步长。"));
        tooltip.add(I18n.format("步长：开启轮询模式后，每次挖完一个配方，则实际距离将增加Step的设定值；如果实际距离超出了 距离+范围，则会将实际距离 减少到 距离-范围。"));
        super.addInformation(stack, world, tooltip, advanced);
    }

}
