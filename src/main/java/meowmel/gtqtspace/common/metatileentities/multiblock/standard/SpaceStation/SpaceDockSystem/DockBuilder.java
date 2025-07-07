package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.api.metatileentity.MetaTileEntityBaseWithControl;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing1;
import meowmel.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.V;

public class DockBuilder extends MetaTileEntityBaseWithControl implements IOpticalComputationReceiver {
    int requestCWUt;
    private IOpticalComputationProvider computationProvider;

    private final ItemStackHandler containerInventory;
    boolean work;
    boolean uninstall;
    int time;
    int totalTime;
    int energyConsume;
    int[] part = {0, 0, 0, 0, 0};
    int[] partToUnistall = {0, 0, 0, 0, 0};

    public DockBuilder(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.containerInventory = new GTItemStackHandler(this, 6);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 保存容器库存
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());

        // 保存布尔值
        data.setBoolean("Work", this.work);
        data.setBoolean("Uninstall", this.uninstall);

        // 保存整数值
        data.setInteger("Time", this.time);
        data.setInteger("TotalTime", this.totalTime);
        data.setInteger("EnergyConsume", this.energyConsume);

        // 保存整数数组
        data.setIntArray("Part", this.part);
        data.setIntArray("PartToUnistall", this.partToUnistall);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        // 读取容器库存
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));

        // 读取布尔值
        this.work = data.getBoolean("Work");
        this.uninstall = data.getBoolean("Uninstall");

        // 读取整数值
        this.time = data.getInteger("Time");
        this.totalTime = data.getInteger("TotalTime");
        this.energyConsume = data.getInteger("EnergyConsume");

        // 读取整数数组
        this.part = data.getIntArray("Part");
        this.partToUnistall = data.getIntArray("PartToUnistall");
    }

    public ItemStack setShip() {

        ItemStack Ship = new ItemStack(GTQTSMetaItems.GTQTS_META_ITEM, 1, 199 + part[0]);


        NBTTagCompound nodeTagCompound = new NBTTagCompound();


        if (part[1] != 0) nodeTagCompound.setInteger("enginModel", part[1]);
        else if(!uninstall&&partToUnistall[1] != 0)nodeTagCompound.setInteger("enginModel", partToUnistall[1]);
        if ((part[1] != 0||uninstall)&&partToUnistall[1] != 0) this.containerInventory.setStackInSlot(1, new ItemStack(GTQTSMetaItems.GTQTS_META_ITEM, 1, 209 + partToUnistall[1]));

        if (part[2] != 0) nodeTagCompound.setInteger("generatorModel", part[2]);
        else if(!uninstall&&partToUnistall[2] != 0)nodeTagCompound.setInteger("generatorModel", partToUnistall[2]);
        if ((part[2] != 0||uninstall)&&partToUnistall[2] != 0) this.containerInventory.setStackInSlot(2, new ItemStack(GTQTSMetaItems.GTQTS_META_ITEM, 1, 219 + partToUnistall[2]));


        if (part[3] != 0) nodeTagCompound.setInteger("countModel", part[3]);
        else if(!uninstall&&partToUnistall[3] != 0)nodeTagCompound.setInteger("countModel", partToUnistall[3]);
        if ((part[3] != 0||uninstall)&&partToUnistall[3] != 0) this.containerInventory.setStackInSlot(3, new ItemStack(GTQTSMetaItems.GTQTS_META_ITEM, 1, 229 + partToUnistall[3]));


        if (part[4] != 0) nodeTagCompound.setInteger("senerModel", part[4]);
        else if(!uninstall&&partToUnistall[4] != 0)nodeTagCompound.setInteger("senerModel", partToUnistall[4]);
        if ((part[4] != 0||uninstall)&&partToUnistall[4] != 0) this.containerInventory.setStackInSlot(4, new ItemStack(GTQTSMetaItems.GTQTS_META_ITEM, 1, 239 + partToUnistall[4]));

        Ship.setTagCompound(nodeTagCompound);
        return Ship;
    }

    public boolean canBuild() {
        return part[0] != 0;
    }

    public void clearAllSlot() {
        for (int i = 0; i < 5; i++) {
            this.containerInventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);

        builder.image(4, 4, 100, 160, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 28, this::addShipInfo, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        builder.image(104, 4, 272, 130, GuiTextures.DISPLAY);
        builder.dynamicLabel(200, 8, () -> "太空船坞装配厂", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(108, 20, this::addInfo, 16777215)).setMaxWidthLimit(280).setClickHandler(this::handleDisplayClick));
        builder.widget((new AdvancedTextWidget(108, 32 , this::addShipDetail, 16777215)).setMaxWidthLimit(280).setClickHandler(this::handleDisplayClick));


        builder.image(4, 160, 200, 76, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 164, this::addTotal1, 16777215)).setMaxWidthLimit(200).setClickHandler(this::handleDisplayClick));
        builder.widget((new AdvancedTextWidget(88, 164, this::addTotal2, 16777215)).setMaxWidthLimit(200).setClickHandler(this::handleDisplayClick));


        builder.widget(new SlotWidget(containerInventory, 0, 8, 8, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("输入槽位"));

        builder.widget(new SlotWidget(containerInventory, 5, 80, 8, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("输出槽位"));

        builder.widget(new SlotWidget(containerInventory, 1, 210, 135, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("部件插槽-引擎模块"));

        builder.widget(new SlotWidget(containerInventory, 2, 230, 135, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("部件插槽-推进器模块"));

        builder.widget(new SlotWidget(containerInventory, 3, 250, 135, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("部件插槽-导航计算机模块"));

        builder.widget(new SlotWidget(containerInventory, 4, 270, 135, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("部件插槽-探测器模块"));

        builder.widget((new ProgressWidget(() -> (double) time / totalTime, 105, 155, 268, 5, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list)));

        builder.widget(new ClickButtonWidget(105, 135, 40, 20, "开始组装", data ->
        {
            if (canBuild()) {
                work = true;
            }
        }).setTooltipText("在确认你需要安装的组建后开始加工"));

        builder.widget(new ClickButtonWidget(150, 135, 40, 20, "拆解模式", data ->
                uninstall=!uninstall).setTooltipText("开启后如何对应槽位无可升级组件则默认拆解原有组件"));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 210, 160);


        return builder;
    }

    public void addBarHoverText(List<ITextComponent> hoverList) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                time + " / " + totalTime + " tick");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "加工进度: %s",
                cwutInfo));
    }
    protected void addTotal1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "解析管理："));
                        tl.add(new TextComponentTranslation("算力提供：%s", requestCWUt));
                        tl.add(new TextComponentTranslation("最大通量：%s", computationProvider.getMaxCWUt()));
                        if(requestCWUt>=1024) tl.add(new TextComponentTranslation("解析速率：%s", 36));
                        else if(requestCWUt>=896) tl.add(new TextComponentTranslation("解析速率：%s", 24));
                        else if(requestCWUt>=768) tl.add(new TextComponentTranslation("解析速率：%s", 16));
                        else if(requestCWUt>=640) tl.add(new TextComponentTranslation("解析速率：%s", 12));
                        else if(requestCWUt>=512) tl.add(new TextComponentTranslation("解析速率：%s", 8));
                        else if(requestCWUt>=384) tl.add(new TextComponentTranslation("解析速率：%s", 4));
                        else if(requestCWUt>=256) tl.add(new TextComponentTranslation("解析速率：%s", 2));
                        else if(requestCWUt>=128) tl.add(new TextComponentTranslation("解析速率：%s", 1));
                    }
                });
    }
    protected void addTotal2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能源管理："));
                        tl.add(new TextComponentTranslation("能量存储上限： %s", this.energyContainer.getEnergyCapacity()));
                        tl.add(new TextComponentTranslation("能量缓存上限： %s", this.energyContainer.getEnergyStored()));
                        tl.add(new TextComponentTranslation("能量输入速率： %s", this.energyContainer.getInputPerSec()));
                    }
                });
    }
    protected void addShipInfo(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "船体预览："));
                        tl.add(new TextComponentTranslation("船体生命： %s", DockUtils.getShipValue(part)));
                        tl.add(new TextComponentTranslation("装甲耐久： %s", DockUtils.getArmorValue(part)));
                        tl.add(new TextComponentTranslation("最大航速： %s", DockUtils.getSpeedValue(part)));
                        tl.add(new TextComponentTranslation("最大加速： %s", DockUtils.getAccelerationValue(part)));
                        tl.add(new TextComponentTranslation("基础闪避： %s", DockUtils.getDodgeValue(part)));
                        tl.add(new TextComponentTranslation("探测范围： %s", DockUtils.getRangeValue(part)));
                        tl.add(new TextComponentTranslation("战力指数： %s", DockUtils.getPowerValue(part)));
                    }
                });
    }
    protected void addInfo(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(new TextComponentTranslation("uninstall： %s", uninstall));
                    }
                });
    }
    protected void addShipDetail(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "部件列表："));
                        tl.add(new TextComponentTranslation("舰船类型:" + DockUtils.getTypeByID(part[0])));
                        tl.add(new TextComponentTranslation("反应堆模块:" + DockUtils.getEnginModelByID(partToUnistall[1]) + "-等级:" + partToUnistall[1] + "->" + DockUtils.getEnginModelByID(part[1]) + "-等级:" + part[1]));
                        tl.add(new TextComponentTranslation("推进器模块:" + DockUtils.getGeneratorModelByID(partToUnistall[2]) + "-等级:" + partToUnistall[2] + "->" + DockUtils.getGeneratorModelByID(part[2]) + "-等级:" + part[2]));
                        tl.add(new TextComponentTranslation("导航计算机模块:" + DockUtils.getCountModelByID(partToUnistall[3]) + "-等级:" + partToUnistall[3] + "->" + DockUtils.getCountModelByID(part[3]) + "-等级:" + part[3]));
                        tl.add(new TextComponentTranslation("探测器模块:" + DockUtils.getSenerModelByID(partToUnistall[4]) + "-等级:" + partToUnistall[4] + "->" + DockUtils.getSenerModelByID(part[4]) + "-等级:" + part[4]));
                    }
                });
    }

    public int getItemFromSlot(int slot) {
        if (this.containerInventory.getStackInSlot(slot) == ItemStack.EMPTY) return 0;
        if (this.containerInventory.getStackInSlot(slot).getItem() != GTQTSMetaItems.GTQTS_META_ITEM) return 0;
        int meta = this.containerInventory.getStackInSlot(slot).getMetadata();
        if (slot == 0 && meta >= 200 && meta < 210) return meta - 199;
        if (slot == 1 && meta >= 210 && meta < 220) return meta - 209;
        if (slot == 2 && meta >= 220 && meta < 230) return meta - 219;
        if (slot == 3 && meta >= 230 && meta < 240) return meta - 229;
        if (slot == 4 && meta >= 240 && meta < 250) return meta - 239;

        return 0;
    }

    @Override
    protected void updateFormedValid() {
        if (work) {
            requestCWUt=computationProvider.requestCWUt(1024, false);

            if(requestCWUt>=1024) time+=36;
            else if(requestCWUt>=896) time+=24;
            else if(requestCWUt>=768) time+=16;
            else if(requestCWUt>=640) time+=12;
            else if(requestCWUt>=512) time+=8;
            else if(requestCWUt>=384) time+=4;
            else if(requestCWUt>=256) time+=2;
            else if(requestCWUt>=128) time++;

            if (time > totalTime) {
                clearAllSlot();
                this.containerInventory.setStackInSlot(5, setShip());
                Arrays.fill(part, 0);
                time = 0;
                work = false;
            }
        } else {
            int totalTimeP = 0;
            for (int j : part) {
                totalTimeP += getItemFromSlot(j);
            }
            if (totalTimeP != totalTime) {
                totalTime = totalTimeP*1000;
                energyConsume = (int) V[5];
            }

            for (int i = 0; i < part.length; i++) {
                if (i == 0) {
                    ItemStack item = this.containerInventory.getStackInSlot(0);
                    NBTTagCompound compound = item.getTagCompound();
                    if (compound != null) {
                        if (compound.hasKey("enginModel")) partToUnistall[1] = compound.getInteger("enginModel");
                        if (compound.hasKey("generatorModel")) partToUnistall[2] = compound.getInteger("generatorModel");
                        if (compound.hasKey("countModel")) partToUnistall[3] = compound.getInteger("countModel");
                        if (compound.hasKey("senerModel")) partToUnistall[4] = compound.getInteger("senerModel");
                    } else {
                        Arrays.fill(partToUnistall, 0);
                    }
                }
                part[i] = getItemFromSlot(i);
            }
        }

    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new DockBuilder(metaTileEntityId);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC","CCC","CCC")
                .aisle("CCC","CCC","CCC")
                .aisle("CMC","CSC","CXC")
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('S', selfPredicate())
                .where('X', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('C', states(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_CASING))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
                .build();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isStructureFormed());
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.SOLAR_PLATE_CASING;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }
    @Override
    public boolean usesMui2() {
        return false;
    }
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
