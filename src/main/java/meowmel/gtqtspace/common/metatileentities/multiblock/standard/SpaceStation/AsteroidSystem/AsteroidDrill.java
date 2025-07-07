package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.api.metatileentity.MetaTileEntityBaseWithControl;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing1;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsteroidDrill extends MetaTileEntityBaseWithControl {
    int[] IDtoDeal = new int[]{0, 0, 0, 0,0,0,0,0};
    BlockPos ControlPos = new BlockPos(0, 0, 0);
    int x, y, z;
    int[][] DockManagerList = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    boolean autoTrans;
    //按钮选项
    int IDSwitch;
    int DockSwitch;

    /////////////////////////////////////////////////////////////////////////
    public AsteroidDrill(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    //UI
    //分为三列 左一 待处理ID 左二 船坞列表 左三 OP
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);

        //左侧四个线程渲染
        int j = 0;
        //1号 X=0
        builder.image(0, 0, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4, this::addInfoA1, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, 0, 15, 15, "O", data -> IDSwitch=0));
        builder.widget(new ClickButtonWidget(85, 15, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 0)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA2, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=1));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 1)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA3, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=2));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 2)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA4, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=3));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 3)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA5, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=4));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 4)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA6, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=5));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 5)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA7, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=6));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 6)));
        j++;
        builder.image(0, j * 30, 100, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(5, 4 + j * 30, this::addInfoA8, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(85, j * 30, 15, 15, "O", data -> IDSwitch=7));
        builder.widget(new ClickButtonWidget(85, 15 + j * 30, 15, 15, "X", data -> IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, 7)));

        //2号 X=100
        j = 0;
        builder.image(100, 0, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(105, 4 + 0, this::addInfoB1, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(185, 0, 15, 40, "O", data -> DockSwitch=0));
        j++;
        builder.image(100, j * 40, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(105, 4 + j * 40, this::addInfoB2, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(185, j * 40, 15, 40, "O", data -> DockSwitch=1));
        j++;
        builder.image(100, j * 40, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(105, 4 + j * 40, this::addInfoB3, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(185, j * 40, 15, 40, "O", data -> DockSwitch=2));
        j++;
        builder.image(100, j * 40, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(105, 4 + j * 40, this::addInfoB4, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(185, j * 40, 15, 40, "O", data -> DockSwitch=3));

        //能源监控
        builder.image(100, 160, 100, 80, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 164, this::addTotal, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        //3号 X=200
        builder.image(200, 0, 180, 135, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 4, this::addDisplayText, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));

        //按钮
        builder.widget(new ClickButtonWidget(200, 137, 40, 20, "自动分配", data ->autoTrans=true).setTooltipText("由系统自动将待处理的项目分配给空闲船坞"));
        builder.widget(new ClickButtonWidget(240, 137, 40, 20, "手动均分", data ->autoTrans=false).setTooltipText("手动分配项目，需要指定对应解析元与船坞"));
        builder.widget(new ClickButtonWidget(280, 137, 40, 20, "分配项目", this::Trans).setTooltipText("选定完毕对应解析元与船坞后进行项目分配"));
        builder.widget(new ClickButtonWidget(320, 137, 40, 20, "删除目标", this::delet).setTooltipText("删除对应的解析元"));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 200, 160);
        return builder;
    }
    protected void addTotal(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能源管理："));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量存储上限： %s", this.energyContainer.getEnergyCapacity()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量缓存上限： %s", this.energyContainer.getEnergyStored()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量输入速率： %s", this.energyContainer.getInputPerSec()));
                    }
                });
    }
    protected void addInfoA1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[0]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[0])));
                });
    }
    protected void addInfoA2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[1]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[1])));
                });
    }
    protected void addInfoA3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[2]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[2])));
                });
    }
    protected void addInfoA4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[3])));
                });
    }
    protected void addInfoA5(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[4]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[4])));
                });
    }
    protected void addInfoA6(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[5]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[5])));
                });
    }
    protected void addInfoA7(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[6]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[6])));
                });
    }
    protected void addInfoA8(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>解析元：%s",IDtoDeal[7]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "总储量： %s", AsteroidUtils.getRateById(IDtoDeal[7])));
                });
    }

    protected void addInfoB1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>船坞 状态： %s",checkSuitable(new BlockPos(DockManagerList[0][1], DockManagerList[0][2], DockManagerList[0][3]))));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "位置：%s %s %s", DockManagerList[0][1], DockManagerList[0][2], DockManagerList[0][3]));
                });
    }
    protected void addInfoB2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>船坞 状态： %s",checkSuitable(new BlockPos(DockManagerList[1][1], DockManagerList[1][2], DockManagerList[1][3]))));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "位置：%s %s %s", DockManagerList[1][1], DockManagerList[1][2], DockManagerList[1][3]));
                });
    }
    protected void addInfoB3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>船坞 状态： %s",checkSuitable(new BlockPos(DockManagerList[2][1], DockManagerList[2][2], DockManagerList[2][3]))));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "位置：%s %s %s", DockManagerList[2][1], DockManagerList[2][2], DockManagerList[2][3]));
                });
    }
    protected void addInfoB4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>船坞 状态： %s",checkSuitable(new BlockPos(DockManagerList[3][1], DockManagerList[3][2], DockManagerList[3][3]))));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "位置：%s %s %s", DockManagerList[3][1], DockManagerList[3][2], DockManagerList[3][3]));
                });
    }
    //
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("开采管理器"));
        textList.add(new TextComponentTranslation("绑定状态：%s %s %s", ControlPos.getX(),ControlPos.getY(),ControlPos.getZ()));
        textList.add(new TextComponentTranslation("已选定配对：解析元-%s 船坞-%s 自动分配-%s", IDSwitch,DockSwitch,autoTrans));
        textList.add(new TextComponentTranslation("正在预览待处理项目：%s",IDtoDeal[0]));
        textList.add(new TextComponentTranslation("矿脉总数：%s 矿脉距离：%s", AsteroidUtils.getRateById(IDtoDeal[IDSwitch]), AsteroidUtils.TimeToConsume(IDtoDeal[IDSwitch])));
        if(IDtoDeal[IDSwitch]>100000&&IDtoDeal[IDSwitch]<1000000) {
            for (int i = 0; i < 6; i++) {
                textList.add(new TextComponentTranslation("*- %s %s", AsteroidUtils.getMaterialByID(IDtoDeal[IDSwitch], i).getLocalizedName(), AsteroidUtils.getOreNumByID(IDtoDeal[IDSwitch], i)));
            }
        }
    }

    //按钮
    private void Trans(Widget.ClickData clickData) {
        TransIDtoDockManger(DockSwitch, IDSwitch, false);
    }

    private void delet(Widget.ClickData clickData) {
        IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, IDSwitch);
    }

    //转移指令
    public boolean TransIDtoDockManger(int DockListN, int IDtoDealN, boolean autoTrans) {
        if (autoTrans) {
            for (int i = 0; i < IDtoDeal.length; i++) {
                if (IDtoDeal[i] != 0 && tryTransferForAutoMode(i)) {
                    return true;
                }
            }
            return false; // 遍历完所有选项仍未找到合适的组合
        }

        if (DockListN == -1 || IDtoDealN == -1) return false;

        if (DockManagerList[DockListN][0] == 0) return false;

        return checkAndTransfer(DockListN, IDtoDealN);
    }

    private boolean tryTransferForAutoMode(int IDIndex) {
        for (int j = 0; j < DockManagerList.length; j++) {
            if (DockManagerList[j][0] != 0 && checkAndTransfer(j, IDIndex)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAndTransfer(int DockListN, int IDtoDealN) {
        BlockPos pos = new BlockPos(
                DockManagerList[DockListN][1],
                DockManagerList[DockListN][2],
                DockManagerList[DockListN][3]
        );
        if (checkSuitable(pos)) {
            getMteFromPos(pos).setIDtoDeal(IDtoDeal[IDtoDealN]);
            IDtoDeal = IntArrayOperations.deleteAndAppendZero(IDtoDeal, IDtoDealN);
            return true;
        }
        return false;
    }

    public boolean checkSuitable(BlockPos pos) {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof DockManager) {
                return ((DockManager) mte).canSetIDtoDeal();
            }
        }
        return false;
    }

    //常态维护更新
    @Override
    protected void updateFormedValid() {
        if (!isStructureFormed()) return;
        if (!ControlCheck(ControlPos)) return;
        if (autoTrans) TransIDtoDockManger(-1, -1, true);
        if (checkLoacl(true) && MachineCheck(x, y, z, true)) {
            boolean exists = false;
            for (int[] entry : DockManagerList) {
                if (entry[0] != 0 && entry[1] == x && entry[2] == y && entry[3] == z) {
                    exists = true;
                    break;
                }
            }

            // 如果不存在相同的坐标，则插入新坐标
            if (!exists) {
                for (int i = 0; i < DockManagerList.length; i++) {
                    if (DockManagerList[i][0] == 0) {
                        DockManagerList[i][1] = x;
                        DockManagerList[i][2] = y;
                        DockManagerList[i][3] = z;
                        DockManagerList[i][0] = 1; // 标记该条目已使用
                        checkLoacl(false);
                        GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
                        x = 0;
                        y = 0;
                        z = 0;
                        break;
                    }
                }
            }
        }
        checkAndResetInvalidCoordinates();
    }

    //常态更新维护
    private void checkAndResetInvalidCoordinates() {

        for (int[] ints : DockManagerList) {
            if (ints[0] != 0) {
                int x = ints[1];
                int y = ints[2];
                int z = ints[3];
                if (!MachineCheck(x, y, z, false)) {
                    // 如果坐标不合法，将这一列全部置为0
                    Arrays.fill(ints, 0);
                }
            }
        }

    }

    //主控被调用
    public void setControlPos(BlockPos pos) {
        ControlPos = pos;
    }

    public boolean setIDtoDeal(int ID) {
        if (IntArrayOperations.containsZero(IDtoDeal)) {
            IntArrayOperations.replaceFirstZero(IDtoDeal, ID);
            return true;
        }
        return false;
    }

    //输入侧检查
    public DockManager getMteFromPos(BlockPos pos)
    {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof DockManager) {
                return ((DockManager) mte);
            }
        }
        return null;
    }

    public boolean MachineCheck(int x, int y, int z, boolean sim) {
        TileEntity te = this.getWorld().getTileEntity(new BlockPos(x, y, z));
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof DockManager) {
                if (!sim) ((DockManager) mte).setControlPos(this.getPos());
                return true;
            }
        }
        return false;
    }

    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_BINDING_CARD.getMetaItem(), 1, 417);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("x", x);
        nodeTagCompound.setInteger("y", y);
        nodeTagCompound.setInteger("z", z);
        card.setTagCompound(nodeTagCompound);
        return card;
    }

    public boolean checkLoacl(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");
                }
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }

    //控制器检查
    public boolean ControlCheck(BlockPos pos) {
        MetaTileEntity mt = GTUtility.getMetaTileEntity(this.getWorld(), pos);
        if (mt instanceof MetaTileEntity) {
            TileEntity te = this.getWorld().getTileEntity(pos);
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof AsteroidController) {
                    return ((AsteroidController) mte).isStructureFormed();
                }
            }
        }
        ControlPos = new BlockPos(0, 0, 0);
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AsteroidDrill(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "CCC")
                .aisle("CCC", "CCC", "CCC")
                .aisle("CMC", "CSC", "CCC")
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.multiblockCasing1.getState(GTQTSMultiblockCasing1.CasingType.SOLAR_PLATE_CASING))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
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
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
    @Override
    public boolean usesMui2() {
        return false;
    }
    //NBT操作
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 写入单个整数
        data.setIntArray("IDtoDeal", IDtoDeal);
        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }
        for (int i = 0; i < 4; i++) {
            data.setIntArray("DockManager" + i, DockManagerList[i]);
        }

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        // 读取单个整数
        IDtoDeal = data.getIntArray("IDtoDeal");
        // 读取 BlockPos
        if (data.hasKey("ControlPosX") && data.hasKey("ControlPosY") && data.hasKey("ControlPosZ")) {
            int posX = data.getInteger("ControlPosX");
            int posY = data.getInteger("ControlPosY");
            int posZ = data.getInteger("ControlPosZ");
            ControlPos = new BlockPos(posX, posY, posZ);
        } else {
            ControlPos = null;
        }
        for (int i = 0; i < 4; i++) {
            DockManagerList[i] = data.getIntArray("DockManager" + i);
        }
    }
}
