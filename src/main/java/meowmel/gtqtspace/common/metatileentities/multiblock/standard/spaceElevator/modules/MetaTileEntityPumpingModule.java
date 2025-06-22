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
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.items.MetaItems;
import meowmel.gtqtspace.api.multiblock.SpaceModulesType;
import meowmel.gtqtspace.api.recipes.properties.DimProperty;
import meowmel.gtqtspace.client.textures.GTQTSGuiTextures;
import meowmel.gtqtspace.client.textures.GTQTSTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing;
import meowmel.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class MetaTileEntityPumpingModule extends MetaTileEntitySpaceElevatorModules {

    //Dim轮询表
    private final int[] planet = {0, 0, 0, 0};
    //电路板轮询表
    private final int[] fluidNumber = {0, 0, 0, 0};
    //距离轮询系统 只在没有配方工作时开始循环
    boolean cycleMode;
    //这个是直接设定的维度
    int dim;
    int circuit;
    int i = 0;

    public MetaTileEntityPumpingModule(ResourceLocation metaTileEntityId, int tier, SpaceModulesType type) {
        super(metaTileEntityId, tier, type);
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
        if (cycleMode) {
            if (i > 3) i = 0;
            dim = planet[i];
            circuit = fluidNumber[i];

            if (getInputInventory() != null) {
                for (int slot = 0; slot < getInputInventory().getSlots(); slot++) {
                    ItemStack stack = getInputInventory().getStackInSlot(slot);
                    if (stack.isItemEqual(MetaItems.INTEGRATED_CIRCUIT.getStackForm())) {
                        setCircuitConfiguration(stack, circuit);
                    }
                }
            }

            i++;
        }
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.recipeMapWorkable.setParallelLimit((int) Math.pow(4, this.tier));
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (!super.checkRecipe(recipe, consumeIfSuccess)) return false;
        return this.dim == recipe.getProperty(DimProperty.getInstance(), 0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPumpingModule(this.metaTileEntityId, this.tier, this.type);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("H", "C", "C", "C", "C")
                .aisle("C", "C", "C", "S", "C")
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.spaceElevatorCasing.getState(GTQTSpaceElevatorCasing.ElevatorCasingType.BASIC_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
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
                GTQTSTextures.PUMP_MODULE_OVERLAY.renderSided(renderSide, renderState, translation, pipeline);
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


        builder.widget(new LabelWidget(10, 39 + (size + padding), "gtqtspace.multiblock.pump_module.planet", 0x55FF55));
        builder.widget(new TextFieldWidget2(50, 39 + (size + padding), size * size, size, () -> this.getPlanetValue(0), s -> this.setPlanetValue(s, 0)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));
        builder.widget(new LabelWidget(10, 39 + 2 * (size + padding), "gtqtspace.multiblock.pump_module.fluid", 0xFF55FF));
        builder.widget(new TextFieldWidget2(50, 39 + 2 * (size + padding), size * size, size, () -> this.getFluidValue(0), s -> this.setFluidValue(s, 0)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));


        builder.widget(new LabelWidget(100, 39 + (size + padding), "gtqtspace.multiblock.pump_module.planet", 0x55FF55));
        builder.widget(new TextFieldWidget2(140, 39 + (size + padding), size * size, size, () -> this.getPlanetValue(1), s -> this.setPlanetValue(s, 1)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));
        builder.widget(new LabelWidget(100, 39 + 2 * (size + padding), "gtqtspace.multiblock.pump_module.fluid", 0xFF55FF));
        builder.widget(new TextFieldWidget2(140, 39 + 2 * (size + padding), size * size, size, () -> this.getFluidValue(1), s -> this.setFluidValue(s, 1)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));

        builder.widget(new LabelWidget(10, 39 + 3 * (size + padding), "gtqtspace.multiblock.pump_module.planet", 0x55FF55));
        builder.widget(new TextFieldWidget2(50, 39 + 3 * (size + padding), size * size, size, () -> this.getPlanetValue(2), s -> this.setPlanetValue(s, 2)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));
        builder.widget(new LabelWidget(10, 39 + 4 * (size + padding), "gtqtspace.multiblock.pump_module.fluid", 0xFF55FF));
        builder.widget(new TextFieldWidget2(50, 39 + 4 * (size + padding), size * size, size, () -> this.getFluidValue(2), s -> this.setFluidValue(s, 2)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));

        builder.widget(new LabelWidget(100, 39 + 3 * (size + padding), "gtqtspace.multiblock.pump_module.planet", 0x55FF55));
        builder.widget(new TextFieldWidget2(140, 39 + 3 * (size + padding), size * size, size, () -> this.getPlanetValue(3), s -> this.setPlanetValue(s, 3)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));
        builder.widget(new LabelWidget(100, 39 + 4 * (size + padding), "gtqtspace.multiblock.pump_module.fluid", 0xFF55FF));
        builder.widget(new TextFieldWidget2(140, 39 + 4 * (size + padding), size * size, size, () -> this.getFluidValue(3), s -> this.setFluidValue(s, 3)).setMaxLength(3).setAllowedChars(TextFieldWidget2.WHOLE_NUMS));

        builder.widget((new AdvancedTextWidget(9, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(181).setClickHandler(this::handleDisplayClick));
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

        builder.widget((new ImageWidget(173, 173, 18, 18, GuiTextures.BUTTON_VOID_NONE)).setTooltip("gregtech.gui.multiblock_voiding_not_supported"));
        //Cycle mode
        builder.widget(new ImageCycleButtonWidget(173, 191, 18, 18, GTQTSGuiTextures.BUTTON_CYCLE, this::getCycleMode, this::setCycleMode).setTooltipHoverString("gtqtspace.gui.mining_module.cycle"));

        builder.bindPlayerInventory(entityPlayer.inventory, 155);
        return builder;
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(this.recipeMapWorkable.isWorkingEnabled(), this.recipeMapWorkable.isActive())
                .addCustom(tl -> {
                    if (spaceElevatorProvider != null) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtspace.gui.mining_module.tier", this.spaceElevatorProvider.getMotorTier()));
                    }
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtspace.gui.pumping_module.target_dimension", this.dim));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gtqtspace.gui.pumping_module.liquid_preference", this.circuit));
                })
                .addWorkingStatusLine().addProgressLine(this.recipeMapWorkable.getProgressPercent())
                .addComputationUsageExactLine(getRecipeMapWorkable().getCurrentDrawnCWUt());
    }

    private String getPlanetValue(int index) {
        return String.valueOf(this.planet[index]);
    }

    private void setPlanetValue(String val, int index) {
        try {
            this.planet[index] = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            this.planet[index] = 0;
        }

    }

    private String getFluidValue(int index) {
        return String.valueOf(this.fluidNumber[index]);
    }

    private void setFluidValue(String val, int index) {
        try {
            this.fluidNumber[index] = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            this.fluidNumber[index] = 0;
        }
    }

    private boolean getCycleMode() {
        return this.cycleMode;
    }

    private void setCycleMode(boolean bool) {
        this.cycleMode = bool;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("cycleMode", this.cycleMode);
        NBTTagList nbtTagPlanetList = new NBTTagList();
        NBTTagList nbtTagFluidList = new NBTTagList();
        for (int i = 0; i < 4; i++) {
            NBTTagCompound planetTag = new NBTTagCompound();
            NBTTagCompound fluidTag = new NBTTagCompound();

            planetTag.setInteger("planet", this.planet[i]);
            nbtTagPlanetList.appendTag(planetTag);

            fluidTag.setInteger("fluid", this.fluidNumber[i]);
            nbtTagFluidList.appendTag(fluidTag);
        }
        data.setTag("planets", nbtTagPlanetList);
        data.setTag("fluids", nbtTagFluidList);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.cycleMode = data.getBoolean("cycleMode");
        NBTTagList nbtTagPlanetList = data.getTagList("planets", Constants.NBT.TAG_COMPOUND);
        NBTTagList nbtTagFluidList = data.getTagList("fluids", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < 4; i++) {
            NBTTagCompound planetTag = nbtTagPlanetList.getCompoundTagAt(i);
            NBTTagCompound fluidTag = nbtTagFluidList.getCompoundTagAt(i);

            this.planet[i] = planetTag.getInteger("planet");
            this.fluidNumber[i] = fluidTag.getInteger("fluid");
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.cycleMode);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.cycleMode = buf.readBoolean();
    }

    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("漂浮的流体钻机", new Object[0]));
        tooltip.add(I18n.format("可指定四种流体，需要设置其维度（表示为流体星球）与虚拟电路值（表示为流体种类）。"));
        tooltip.add(I18n.format("开启轮询模式后，将会在指定的四种流体内循环运行配方。"));
        super.addInformation(stack, world, tooltip, advanced);
    }
}
