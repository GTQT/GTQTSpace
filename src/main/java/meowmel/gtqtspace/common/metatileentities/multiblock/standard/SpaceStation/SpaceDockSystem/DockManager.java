package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.api.metatileentity.MetaTileEntityBaseWithControl;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing1;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidDrill;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockUtils.getSpeedValue;
import static meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockUtils.getTypeByID;

public class DockManager extends MetaTileEntityBaseWithControl {
    private final ItemStackHandler containerInventory;
    int IDtoDeal;
    int total;
    int[] perTotal = {0, 0, 0, 0, 0, 0};

    boolean deal;
    boolean startWork;
    List<Material> materials;
    //
    int[][] DockList = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    BlockPos ControlPos = new BlockPos(0, 0, 0);

    public DockManager(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.containerInventory = new GTItemStackHandler(this, 8);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 保存容器库存
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        // 写入单个整数
        data.setInteger("IDtoDeal", IDtoDeal);
        data.setIntArray("perTotal", perTotal);
        data.setBoolean("deal", deal);
        data.setBoolean("startWork", startWork);
        data.setInteger("total", total);
        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }
        // 写入二维数组
        for (int i = 0; i < 8; i++) {
            data.setIntArray("DockList" + i, DockList[i]);
        }
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        // 读取容器库存
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));

        // 读取单个整数
        IDtoDeal = data.getInteger("IDtoDeal");
        materials = AsteroidUtils.getMaterialListById(IDtoDeal);
        perTotal = data.getIntArray("perTotal");
        deal = data.getBoolean("deal");
        startWork = data.getBoolean("startWork");
        total = data.getInteger("total");
        // 读取 BlockPos
        if (data.hasKey("ControlPosX") && data.hasKey("ControlPosY") && data.hasKey("ControlPosZ")) {
            int posX = data.getInteger("ControlPosX");
            int posY = data.getInteger("ControlPosY");
            int posZ = data.getInteger("ControlPosZ");
            ControlPos = new BlockPos(posX, posY, posZ);
        } else {
            ControlPos = null;
        }
        // 读取二维数组
        for (int i = 0; i < 8; i++) {
            DockList[i] = data.getIntArray("DockList" + i);
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);

        builder.widget(new SlotWidget(containerInventory, 0, 8, 8, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 1, 8, 38, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 2, 8, 68, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 3, 8, 98, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 4, 8, 128, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 5, 8, 158, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 6, 8, 188, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));
        builder.widget(new SlotWidget(containerInventory, 7, 8, 218, !startWork, !startWork).setBackgroundTexture(GuiTextures.SLOT).setTooltipText("输入槽位"));

        builder.image(36, 4, 340, 120, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(40, 8, this::addShipInfo, 16777215)).setMaxWidthLimit(360).setClickHandler(this::handleDisplayClick));

        builder.image(36, 122, 160, 114, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(40, 126, this::addDisplayText, 16777215)).setMaxWidthLimit(200).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(210, 135, 40, 20, "开始工作", data -> startWork = !startWork).setTooltipText(""));

        builder.widget(new ClickButtonWidget(260, 135, 40, 20, "放弃工作", data -> resetDealState()).setTooltipText(""));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 210, 160);
        return builder;
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("待处理：%s", IDtoDeal));
        if (IDtoDeal != 0) {
            textList.add(new TextComponentTranslation("绑定状态：%s %s %s", ControlPos.getX(), ControlPos.getY(), ControlPos.getZ()));
            textList.add(new TextComponentTranslation("矿脉总数：%s 距离：%s", total, AsteroidUtils.TimeToSolve(IDtoDeal)));
            if (materials == null) return;
            for (int i = 0; i < 6; i++) {
                textList.add(new TextComponentTranslation("：%s %s", materials.get(i).getLocalizedName(), perTotal[i]));
            }
        }
    }

    protected void addShipInfo(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("太空船坞管理器"));
        textList.add(new TextComponentTranslation("工作：%s 预处理：%s", startWork, deal));
        for (int i = 0; i < 8; i++) {
            textList.add(new TextComponentTranslation("船坞-%s=型号：%s 航速：%s 已进行：%s 总耗时：%s 状态：%s", i, getTypeByID(DockList[i][0]), DockList[i][1], DockList[i][2], DockList[i][3], DockList[i][4]));
        }
    }

    public boolean canSetIDtoDeal() {
        return IDtoDeal == 0;
    }

    public void setIDtoDeal(int ID) {
        if (IDtoDeal == 0) IDtoDeal = ID;
    }


    public void setControlPos(BlockPos pos) {
        ControlPos = pos;
    }

    @Override
    protected void updateFormedValid() {
        if (!isStructureFormed()) return;
        if (!startWork) {
            processContainerInventory();
        }
        if (MachineCheck(ControlPos)) {
            processDealLogic();
        }
    }

    private void processContainerInventory() {
        for (int i = 0; i < 8; i++) {
            ItemStack stack = this.containerInventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                Arrays.fill(DockList[i], 0);
                continue;
            }
            if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    int x = compound.getInteger("x");
                    int y = compound.getInteger("y");
                    int z = compound.getInteger("z");
                    Dock mte = getMteFromPos(new BlockPos(x, y, z));
                    if (mte != null && !mte.work) {
                        DockList[i][0] = mte.part[0];
                        DockList[i][1] = getSpeedValue(mte.part);
                        DockList[i][3] = AsteroidUtils.TimeToSolve(IDtoDeal) / DockList[i][1];
                        mte.setControlPos(this.getPos());
                    }
                }
            }
        }
    }

    public void setWork(boolean work) {
        for (int i = 0; i < 8; i++) {
            ItemStack stack = this.containerInventory.getStackInSlot(i);
            if (stack.isEmpty()) break;
            if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    int x = compound.getInteger("x");
                    int y = compound.getInteger("y");
                    int z = compound.getInteger("z");
                    Dock mte = getMteFromPos(new BlockPos(x, y, z));
                    if (mte != null) {
                        mte.setWork(work);
                    }
                }
            }
        }
    }

    private void processDealLogic() {
        if (IDtoDeal == 0 || !deal) {
            if (IDtoDeal != 0) {
                total = AsteroidUtils.getRateById(IDtoDeal);
                perTotal = AsteroidUtils.getPerRateById(IDtoDeal);
                materials = AsteroidUtils.getMaterialListById(IDtoDeal);
                deal = true;
                setWork(true);
            }
            return;
        }

        if (!startWork) return;

        for (int i = 0; i < 8; i++) {
            if (DockList[i][0] == 1) {
                if (DockList[i][2] < DockList[i][3]) {
                    DockList[i][2]++;
                    DockList[i][4] = 0;
                } else {
                    DockList[i][4] = 1;
                }
            } else if (DockList[i][0] == 2) {
                if (DockList[i][4] == 0) {
                    if (DockList[i][2] < DockList[i][3]) {
                        DockList[i][2]++;
                    } else {
                        DockList[i][4] = 1;
                        DockList[i][2] = 0;
                    }
                } else if (DockList[i][4] == 1) {
                    if (DockList[i][2] < DockList[i][3]) {
                        DockList[i][2]++;
                    } else {
                        DockList[i][4] = 0;
                        DockList[i][2] = 0;

                        // 物品
                        for (int j = 0; j < 8; j++) {
                            if (DockList[j][0] == 1 && DockList[j][4] == 1) {
                                for (Material material : materials) {
                                    for (int k = 0; k < 64; k++) {
                                        if (!material.hasProperty(PropertyKey.ORE))
                                            GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(material.getFluid(1000)));
                                        else
                                            GTTransferUtils.insertItem(this.outputInventory, OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)), false);
                                        perTotal[k]--;
                                        total--;
                                        if (total == 0) {
                                            resetDealState();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void resetDealState() {
        IDtoDeal = 0;
        deal = false;
        Arrays.fill(perTotal, 0);
        materials = null;
        startWork = false;
        for (int i = 0; i < 8; i++) {
            DockList[i][2] = 0;
            DockList[i][4] = 0;
        }
        setWork(false);
    }


    public Dock getMteFromPos(BlockPos pos) {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof Dock) {
                return ((Dock) mte);
            }
        }
        return null;
    }

    public boolean MachineCheck(BlockPos pos) {
        MetaTileEntity mt = GTUtility.getMetaTileEntity(this.getWorld(), pos);
        if (mt instanceof MetaTileEntity) {
            TileEntity te = this.getWorld().getTileEntity(pos);
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof AsteroidDrill) {
                    return ((AsteroidDrill) mte).isStructureFormed();
                }
            }
        }
        ControlPos = new BlockPos(0, 0, 0);
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new DockManager(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start().aisle("CCC", "CCC", "CCC").aisle("CCC", "CCC", "CCC").aisle("CMC", "CSC", "CCC").where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH)).where('S', selfPredicate()).where('C', states(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_CASING)).or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(2).setMinGlobalLimited(1)).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2).setMinGlobalLimited(1)).or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1))).build();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(), this.isStructureFormed());
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
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
