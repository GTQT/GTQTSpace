package keqing.gtqtspace.common.metatileentities.multiblock.standard.oldElevator.elevatormodules;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import keqing.gtqtspace.loaders.recipes.categories.SpaceMiningRecipes;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.eclipse.xtext.xbase.lib.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BooleanSupplier;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;
import static keqing.gtqtspace.loaders.recipes.categories.SpaceMiningRecipes.*;
/*
public class MetaTileEntityMiningModule extends MetaTileEntityModuleBase implements IOpticalComputationReceiver {

    private IItemHandlerModifiable inputInventory;
    private IItemHandlerModifiable outputInventory;
    private IMultipleTankHandler inputFluidInventory;
    private IOpticalComputationProvider computationProvider;
    private int minDistance = 0;
    private int maxDistance = 0;
    private int distance = 0;
    private int range = 0;
    private final int MAX_PARALLEL;
    private final int MAX_DISTANCE = 300;
    private final int MAX_RANGE = 150;
    private boolean cycleMode = false;
    private int step = 0;
    private int parallel;

    private SpaceMiningRecipes.SpaceMiningRecipePartTwo randomRecipe;
    private List<ItemStack> randomOutput = new ArrayList<>();

    private List<String> whitelist = new ArrayList<>();
    private boolean isWhitelist = true;

    private long totalEUt = 0;
    private int totalComputation = 0;

    public MetaTileEntityMiningModule(ResourceLocation metaTileEntityId, int tier, int moduleTier, int minMotorTier, int maxParallel) {
        super(metaTileEntityId, tier, moduleTier, minMotorTier);
        this.MAX_PARALLEL = maxParallel;
        this.parallel = maxParallel;
    }

    @Override
    public void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.IMPORT_FLUIDS));

        try {
            this.computationProvider = getSpaceElevator().getComputationProvider();
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    protected TraceabilityPredicate abilities() {
        return abilities(MultiblockAbility.IMPORT_ITEMS, MultiblockAbility.EXPORT_ITEMS, MultiblockAbility.IMPORT_FLUIDS);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMiningModule(this.metaTileEntityId, this.tier, this.moduleTier, this.minMotorTier, this.MAX_PARALLEL);
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
    public void updateFormedValid() {
        super.updateFormedValid();
        if (!this.isWorkingEnabled()) {
            return;
        }


        if (!drainEnergy(true, this.totalEUt) || this.computationProvider.requestCWUt(this.totalComputation, true) != this.totalComputation) {
            if (this.progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) this.progressTime = 1;
                else this.progressTime = Math.max(1, this.progressTime - 2);
            }
            return;
        }
        if (progressTime == 0 && checkFluidInventory(true) == null) {
            setActive(false);
            return;
        }
        if (progressTime == 0 && checkRecipes(true) == null) {
            setActive(false);
            if (this.cycleMode) {
                setDistance(String.valueOf(this.distance + this.step));
            }
        } else {
            Random rand = this.getWorld().rand;

            setActive(true);

            if (progressTime == 0) {

                List<SpaceMiningRecipes.SpaceMiningRecipePartTwo> recipes = checkRecipes(false);

                Fluid fluid = checkFluidInventory(false);

                int averageProgress = 0;

                for (int x = 0; x < this.parallel; x++) {
                    int totalWeight = 0;
                    for (SpaceMiningRecipes.SpaceMiningRecipePartTwo i : recipes) {
                        totalWeight += i.getWeight();
                    }

                    int idx = 0;
                    for (int r = rand.nextInt(totalWeight); idx < recipes.size() - 1; ++idx) {
                        r -= recipes.get(idx).getWeight();
                        if (r <= 0) break;
                    }

                    this.randomRecipe = recipes.get(idx);

                    averageProgress += this.randomRecipe.getDuration();
                    this.totalEUt += this.randomRecipe.getEUt();
                    this.totalComputation += this.randomRecipe.getComputation();


                    int randomStackSize = rand.nextInt(this.randomRecipe.getMaxSize() - this.randomRecipe.getMinSize()) + this.randomRecipe.getMinSize();

                    int totalWeight2 = 0;
                    for (Pair<Material, Integer> i : this.randomRecipe.getOutputs()) {
                        totalWeight2 += i.getValue();
                    }

                    int idx2 = 0;
                    for (int r = rand.nextInt(totalWeight2); idx2 < this.randomRecipe.getOutputs().size() - 1; ++idx2) {
                        r -= this.randomRecipe.getOutputs().get(idx2).getValue();
                        if (r <= 0) break;
                    }

                    if (!this.whitelist.isEmpty()) {
                        for (int i = 0; i < this.whitelist.size(); i++) {
                            if (this.whitelist.get(i).equalsIgnoreCase(this.randomRecipe.getOutputs().get(idx2).getKey().getLocalizedName())) {
                                if (this.isWhitelist) {
                                    this.randomOutput.add(OreDictUnifier.get(OrePrefix.ore, this.randomRecipe.getOutputs().get(idx2).getKey(), randomStackSize));
                                    break;
                                } else continue;
                            }

                            if (i == this.whitelist.size() - 1 && !this.isWhitelist) {
                                this.randomOutput.add(OreDictUnifier.get(OrePrefix.ore, this.randomRecipe.getOutputs().get(idx2).getKey(), randomStackSize));
                            }
                        }
                    } else {
                        this.randomOutput.add(OreDictUnifier.get(OrePrefix.ore, this.randomRecipe.getOutputs().get(idx2).getKey(), randomStackSize));
                    }

                    if (x == this.parallel - 1) {
                        if (fluid == RocketFuel.getFluid()) {
                            setMaxProgress(averageProgress / this.parallel);
                        } else if (fluid == RP1RocketFuel.getFluid()) {
                            setMaxProgress(averageProgress / this.parallel / 2);
                        } else if (fluid == DenseHydrazineMixtureFuel.getFluid()) {
                            setMaxProgress(averageProgress / this.parallel / 4);
                        } else if (fluid == MethylhydrazineNitrateRocketFuel.getFluid()) {
                            setMaxProgress(averageProgress / this.parallel / 8);
                        }
                    }
                }
            }

            if (this.randomRecipe == null || this.randomOutput == null) {
                progressTime = 0;
                return;
            }

            this.computationProvider.requestCWUt(this.totalComputation, false);
            drainEnergy(false, this.totalEUt);

            progressTime++;
            if (progressTime % getMaxProgress() != 0) return;
            progressTime = 0;

            if (this.cycleMode) {
                setDistance(String.valueOf(this.distance + this.step));
            }

            GTTransferUtils.addItemsToItemHandler(this.outputInventory, false, this.randomOutput);

            this.randomOutput.clear();
            this.totalEUt = 0;
            this.totalComputation = 0;

        }
    }

    public List<SpaceMiningRecipes.SpaceMiningRecipePartTwo> checkRecipes(boolean simulate) {

        ItemStack item = null;

        Material matStick = null;
        Material matDrill = null;

        int stickCount = 0;
        int drillCount = 0;

        final int neededSticks = STICK_INPUT_STACK_SIZE * this.parallel;
        final int neededDrills = DRILL_HEAD_INPUT_STACK_SIZE * this.parallel;


        if(getInputInventory()==null) {
            return null;
        }
        MetaItem<?>.MetaValueItem[] Drone={MINING_DRONE_LV,MINING_DRONE_MV,MINING_DRONE_HV,MINING_DRONE_EV,MINING_DRONE_IV,MINING_DRONE_LuV,MINING_DRONE_ZPM,MINING_DRONE_UV,MINING_DRONE_UHV,MINING_DRONE_UEV,MINING_DRONE_UIV,MINING_DRONE_UXV,MINING_DRONE_OpV,MINING_DRONE_MAX};
        for (int i = 0; i < getInputInventory().getSlots(); i++)
        {

            ItemStack slot = getInputInventory().getStackInSlot(i);

            for (MetaItem<?>.MetaValueItem metaValueItem : Drone) {

                if (slot !=null&&slot.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && slot.getMetadata() == metaValueItem.getMetaValue()) {
                    item = slot;
                    break;
                }
            }

            if (OreDictUnifier.getPrefix(slot) == OrePrefix.stick) {
                if (matStick != null && matStick != Objects.requireNonNull(OreDictUnifier.getMaterial(slot)).material) {
                    return null;
                }
                matStick = Objects.requireNonNull(OreDictUnifier.getMaterial(slot)).material;
                stickCount += slot.getCount();
                continue;
            }
            if (OreDictUnifier.getPrefix(slot) == OrePrefix.toolHeadDrill) {
                if (matDrill != null && matDrill != Objects.requireNonNull(OreDictUnifier.getMaterial(slot)).material) {
                    return null;
                }
                matDrill = Objects.requireNonNull(OreDictUnifier.getMaterial(slot)).material;
                drillCount += slot.getCount();
            }

            if (item != null && stickCount >= neededSticks && drillCount >= neededDrills)
                break;
        }


        if (stickCount < neededSticks || drillCount < neededDrills) {
            return null;
        } else {
            stickCount = neededSticks;
            drillCount = neededDrills;
        }

        if (matStick != matDrill || matStick == null)
            return null;


        List<SpaceMiningRecipes.SpaceMiningRecipePartTwo> recipesBeforeCheck = SPACE_MINING_RECIPES.get(new SpaceMiningRecipes.SpaceMiningRecipePartOne(item, matStick).hashCode());

        if (recipesBeforeCheck == null) return null;

        List<SpaceMiningRecipes.SpaceMiningRecipePartTwo> recipesAfterCheck = new ArrayList<>();


        for (SpaceMiningRecipes.SpaceMiningRecipePartTwo recipe : recipesBeforeCheck) {
            if (recipe != null) {

                if (this.moduleTier < recipe.getMinModuleTier()) {
                    continue;
                }
                if (!(this.minDistance <= recipe.getMaxDistance() && recipe.getMinDistance() <= this.maxDistance)) {
                    continue;
                }
                if (!drainEnergy(true, recipe.getEUt() * this.parallel)) {
                    continue;
                }
                if (this.computationProvider.requestCWUt(recipe.getComputation() * this.parallel, true) != recipe.getComputation() * this.parallel) {
                    continue;
                }

                recipesAfterCheck.add(recipe);

                if (simulate)
                    return recipesAfterCheck;
            }
        }

        if (recipesAfterCheck.isEmpty())
            return null;
        else {
            int currentStickCount = 0;
            int currentDrillCount = 0;
            for (int i = 0; i < getInputInventory().getSlots(); i++) {
                ItemStack item2 = getInputInventory().getStackInSlot(i);

                try {
                    if (currentStickCount != stickCount && OreDictUnifier.getPrefix(item2) == OrePrefix.stick && OreDictUnifier.getMaterial(item2).material == matStick) {
                        currentStickCount += item2.getCount();
                        getInputInventory().extractItem(i, Math.min(item2.getCount(), stickCount), false);
                    }

                    if (currentDrillCount != drillCount && OreDictUnifier.getPrefix(item2) == OrePrefix.toolHeadDrill && OreDictUnifier.getMaterial(item2).material == matDrill) {
                        currentDrillCount += item2.getCount();
                        getInputInventory().extractItem(i, Math.min(item2.getCount(), drillCount), false);
                    }
                } catch (NullPointerException ignored) {
                }

                if (currentStickCount >= stickCount && currentDrillCount >= drillCount)
                    break;
            }
            return recipesAfterCheck;
        }
    }

    private Fluid checkFluidInventory(boolean simulate) {

        FluidStack canDrainFuel1 = getInputFluidInventory().drain(RocketFuel.getFluid(1000 * this.parallel), false);
        FluidStack canDrainFuel2 = getInputFluidInventory().drain(RP1RocketFuel.getFluid(500 * this.parallel), false);
        FluidStack canDrainFuel3 = getInputFluidInventory().drain(DenseHydrazineMixtureFuel.getFluid(300 * this.parallel), false);
        FluidStack canDrainFuel4 = getInputFluidInventory().drain(MethylhydrazineNitrateRocketFuel.getFluid(100 * this.parallel), false);

        if (canDrainFuel1 != null || canDrainFuel2 != null || canDrainFuel3 != null || canDrainFuel4 != null) {

            if (canDrainFuel1 != null && canDrainFuel1.isFluidStackIdentical(RocketFuel.getFluid(1000 * this.parallel))) {
                if (!simulate) {
                    getInputFluidInventory().drain(RocketFuel.getFluid(1000 * this.parallel), true);
                }
                return RocketFuel.getFluid();
            }

            if (canDrainFuel2 != null && canDrainFuel2.isFluidStackIdentical(RP1RocketFuel.getFluid(500 * this.parallel))) {
                if (!simulate) {
                    getInputFluidInventory().drain(RP1RocketFuel.getFluid(500 * this.parallel), true);
                }

                return RP1RocketFuel.getFluid();
            }

            if (canDrainFuel3 != null && canDrainFuel3.isFluidStackIdentical(DenseHydrazineMixtureFuel.getFluid(300 * this.parallel))) {
                if (!simulate) {
                    getInputFluidInventory().drain(DenseHydrazineMixtureFuel.getFluid(300 * this.parallel), true);
                }
                return DenseHydrazineMixtureFuel.getFluid();
            }

            if (canDrainFuel4 != null && canDrainFuel4.isFluidStackIdentical(MethylhydrazineNitrateRocketFuel.getFluid(100 * this.parallel))) {
                if (!simulate) {
                    getInputFluidInventory().drain(MethylhydrazineNitrateRocketFuel.getFluid(100 * this.parallel), true);
                }
                return MethylhydrazineNitrateRocketFuel.getFluid();
            }

        }
        return null;
    }

    private IMultipleTankHandler getInputFluidInventory() {
        return this.inputFluidInventory;
    }

    private IItemHandlerModifiable getInputInventory() {
        return this.inputInventory;
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 238);
        ;
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



        builder.widget(new TextFieldWidget2(9, (size + padding) + 11 * 5, size * size, size, this::getBlankName, this::addToWhiteList).setMaxLength(21).setAllowedChars(TextFieldWidget2.LETTERS));

        builder.widget(new TextFieldWidget2(9, (size + padding) + 11 * 7, size * size, size, this::getBlankRemoveName, this::removeFromWhitelist).setMaxLength(21).setAllowedChars(TextFieldWidget2.LETTERS));



        builder.widget((new AdvancedTextWidget(9, (size + padding), this::addDisplayText, 16777215)).setMaxWidthLimit(181).setClickHandler(this::handleDisplayClick));
        IControllable controllable = (IControllable) this.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, (EnumFacing) null);
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

        //clear or retrieve list //TODO change the texture of this
        builder.widget(new ClickButtonWidget(173, 155, 18, 18, "", data -> printWhitelistOrClear(data, entityPlayer)).setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID).setTooltipText("gtqtspace.gui.mining_module.print_whitelist_or_clear"));

        //whitelist or blacklist
        builder.widget(new ImageCycleButtonWidget(173, 173, 18, 18, GTQTSTextures.BUTTON_WHITE_BLACK_LIST, this::getWhitelistMode, this::setWhitelistMode).setTooltipHoverString("gtqtspace.gui.mining_module.change_whitelist_mode"));

        //Cycle mode
        builder.widget(new ImageCycleButtonWidget(173, 191, 18, 18, GTQTSTextures.BUTTON_CYCLE, this::getCycleMode, this::setCycleMode).setTooltipHoverString("gtqtspace.gui.mining_module.cycle"));


        builder.bindPlayerInventory(entityPlayer.inventory, 155);
        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(isWorkingEnabled(), isActive())
                .addWorkingStatusLine()
                .addParallelsLine(this.MAX_PARALLEL)
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtspace.gui.mining_module.min_distance", this.minDistance));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gtqtspace.gui.mining_module.max_distance", this.maxDistance));
                })
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(this.isWhitelist ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED, this.isWhitelist ? "gtqtspace.gui.mining_module.whitelist" : "gtqtspace.gui.mining_module.blacklist"));
                })
                .addEmptyLine()
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gtqtspace.gui.mining_module.remove"));
                })
                .addEmptyLine()
                .addProgressLine(getProgressPercent() / 100.0)
                .addEnergyUsageExactLine(this.totalEUt)
                .addComputationUsageExactLine(this.totalComputation);

    }

    private void printWhitelistOrClear(Widget.ClickData data, EntityPlayer player) {

        if(this.whitelist.isEmpty()) {
            player.sendStatusMessage(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist_empty" : "blacklist_empty")), false);
            return;
        }

        if(data.isShiftClick) {
            this.whitelist.clear();
            player.sendStatusMessage(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist_cleared" : "blacklist_cleared")), false);
        }
        else {

            StringBuilder sb = new StringBuilder(I18n.format("gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist" : "blacklist")) + "\n");

            for(int i = 0; i < this.whitelist.size(); i++) {
                sb.append(this.whitelist.get(i));
                if(i != this.whitelist.size() - 1) {
                    sb.append("\n");
                }
            }

            player.sendStatusMessage(TextComponentUtil.stringWithColor(TextFormatting.WHITE, sb.toString()), false);
        }
    }

    private void addToWhiteList(String name) {
        if (!this.whitelist.contains(name) && !Objects.equals(name, I18n.format("gtqtspace.gui.mining_module.blank_name"))) {
            this.whitelist.add(name);
        }
    }

    private void removeFromWhitelist(String name) {
        this.whitelist.remove(name);
    }

    private String getBlankName() {
        return I18n.format("gtqtspace.gui.mining_module.blank_name");
    }

    private String getBlankRemoveName() {
        return I18n.format("gtqtspace.gui.mining_module.blank_name_remove");
    }

    private void setWhitelistMode(boolean bool) {
        this.isWhitelist = bool;
    }

    private boolean getWhitelistMode() {
        return this.isWhitelist;
    }

    private void setCycleMode(boolean bool) {
        this.cycleMode = bool;
    }

    private boolean getCycleMode() {
        return this.cycleMode;
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

    private String getDistance() {
        return String.valueOf(this.distance);
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

    private String getRange() {
        return String.valueOf(this.range);
    }

    private void setStep(String step) {
        try {
            this.step = Integer.parseInt(step);
        } catch (NumberFormatException e) {
            this.step = 0;
        }
    }

    private String getStep() {
        return String.valueOf(this.step);
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
    }

    private String getParallel() {
        return String.valueOf(this.parallel);
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
        data.setBoolean("cycle", this.cycleMode);
        data.setBoolean("isWhitelist", this.isWhitelist);


        NBTTagList nbtTagList = new NBTTagList();

        for(String s : this.whitelist) {
            NBTTagCompound tag = new NBTTagCompound();

            tag.setString("material", s);
            nbtTagList.appendTag(tag);
        }

        data.setInteger("whitelistSize", this.whitelist.size());
        data.setTag("whitelist", nbtTagList);

        return super.writeToNBT(data);
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
        this.cycleMode = data.getBoolean("cycle");
        this.isWhitelist = data.getBoolean("isWhitelist");

        if (data.getInteger("whitelistSize") > 0) {
            NBTTagList nbtTagList = data.getTagList("whitelist", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < data.getInteger("whitelistSize"); i++) {
                NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
                this.whitelist.add(tag.getString("material"));
            }
        }
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
        buf.writeBoolean(this.isWhitelist);
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
        this.isWhitelist = buf.readBoolean();
    }
    @Override
    public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁家虚空矿机", new Object[0]));
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("舱室要求：输入仓 输入总线 输出总线"));
        tooltip.add(I18n.format("分别在范围 距离 步长内设置好参数后，在输入总线放入耗材与无人机便可工作"));
        tooltip.add(I18n.format("也可在在范围 距离 步长内设置好参数后，使用循环模式自动分配"));
        tooltip.add(I18n.format("可设置配方白名单黑名单"));
        tooltip.add(I18n.format("并行数量：%s",parallel));
    }
}

 */
