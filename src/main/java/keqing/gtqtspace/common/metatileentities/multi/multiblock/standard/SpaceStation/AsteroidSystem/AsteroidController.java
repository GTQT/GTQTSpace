package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSSolarPlate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsteroidController extends MetaTileEntityBaseWithControl {
    int[] WaitToDeal = new int[]{0, 0, 0, 0};
    int[] WaitToDrill = new int[]{0, 0, 0, 0};
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("WaitToDeal: %s", Arrays.toString(WaitToDeal)));
        textList.add(new TextComponentTranslation("WaitToDrill: %s", Arrays.toString(WaitToDrill)));
    }
    int x, y, z;
    int[][] AsteroidSearchList = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    int[][] AsteroidSolveList = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    int[][] AsteroidDrillList = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 写入一维数组
        data.setIntArray("WaitToDeal", WaitToDeal);
        data.setIntArray("WaitToDrill", WaitToDrill);

        // 写入单个整数
        data.setInteger("x", x);
        data.setInteger("y", y);
        data.setInteger("z", z);

        // 写入二维数组
        for (int i = 0; i < 4; i++) {
            data.setIntArray("AsteroidSearchList" + i, AsteroidSearchList[i]);
            data.setIntArray("AsteroidSolveList" + i, AsteroidSolveList[i]);
            data.setIntArray("AsteroidDrillList" + i, AsteroidDrillList[i]);
        }

        return super.writeToNBT(data);
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        // 读取一维数组
        WaitToDeal = data.getIntArray("WaitToDeal");
        WaitToDrill = data.getIntArray("WaitToDrill");

        // 读取单个整数
        x = data.getInteger("x");
        y = data.getInteger("y");
        z = data.getInteger("z");

        // 读取二维数组
        for (int i = 0; i < 4; i++) {
            AsteroidSearchList[i] = data.getIntArray("AsteroidSearchList" + i);
            AsteroidSolveList[i] = data.getIntArray("AsteroidSolveList" + i);
            AsteroidDrillList[i] = data.getIntArray("AsteroidDrillList" + i);
        }
    }
    public AsteroidController(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    public AsteroidSolve getAsteroidSolveFromPos(BlockPos pos)
    {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof AsteroidSolve) {
                return ((AsteroidSolve) mte);
            }
        }
        return null;
    }
    public AsteroidDrill getAsteroidDrillFromPos(BlockPos pos)
    {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof AsteroidDrill) {
                return ((AsteroidDrill) mte);
            }
        }
        return null;
    }

    public void processWaitToDeal() {
        // 检查WaitToDeal中是否存在不为0的元素
        boolean hasNonZero = false;
        for (int id : WaitToDeal) {
            if (id != 0) {
                hasNonZero = true;
                break;
            }
        }
        if (!hasNonZero) {
            return;
        }

        // 遍历AsteroidSolveList
        for (int[] row : AsteroidSolveList) {
            if (row[0] != 0) {
                int x = row[1];
                int y = row[2];
                int z = row[3];
                // 获取AsteroidSolve对象
                AsteroidSolve asteroidSolve = getAsteroidSolveFromPos(new BlockPos(x, y, z));

                // 遍历WaitToDeal中的每个元素
                for (int i = 0; i < WaitToDeal.length; i++) {
                    if (WaitToDeal[i] != 0) {
                        int id = WaitToDeal[i];
                        if (asteroidSolve.setIDtoDeal(id)) {
                            // 如果setIDtoDeal返回true，将WaitToDeal中的元素设置为0
                            WaitToDeal=IntArrayOperations.deleteAndAppendZero(WaitToDeal,i);
                        }
                    }
                }
            }
        }
    }

    public void processWaitToDrill() {
        // 检查WaitToDrill中是否存在不为0的元素
        boolean hasNonZero = false;
        for (int id : WaitToDrill) {
            if (id != 0) {
                hasNonZero = true;
                break;
            }
        }
        if (!hasNonZero) {
            return;
        }

        // 遍历AsteroidDrillList
        for (int[] row : AsteroidDrillList) {
            if (row[0] != 0) {
                int x = row[1];
                int y = row[2];
                int z = row[3];
                // 获取AsteroidDrill对象
                AsteroidDrill asteroidDrill = getAsteroidDrillFromPos(new BlockPos(x, y, z));

                // 遍历WaitToDrill中的每个元素
                for (int i = 0; i < WaitToDrill.length; i++) {
                    if (WaitToDrill[i] != 0) {
                        int id = WaitToDrill[i];
                        if (asteroidDrill.setIDtoDeal(id)) {
                            // 如果setIDtoDeal返回true，将WaitToDrill中的元素设置为0
                            WaitToDrill=IntArrayOperations.deleteAndAppendZero(WaitToDrill,i);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void updateFormedValid() {
        if(!isStructureFormed())return;
        ////////////////////////////////////
        if (checkLoacl(true)) {
            int machineType = MachineCheck(x, y, z,true);

            // 根据机器类型选择对应的数组
            int[][] asteroidList;
            switch (machineType) {
                case 1:
                    asteroidList = AsteroidSearchList;
                    break;
                case 2:
                    asteroidList = AsteroidSolveList;
                    break;
                case 3:
                    asteroidList = AsteroidDrillList;
                    break;
                default:
                    return; // 如果机器类型不匹配，直接返回
            }

            // 检查是否已存在相同的坐标
            boolean exists = false;
            for (int[] entry : asteroidList) {
                if (entry[0] != 0 && entry[1] == x && entry[2] == y && entry[3] == z) {
                    exists = true;
                    break;
                }
            }

            // 如果不存在相同的坐标，则插入新坐标
            if (!exists) {
                for (int i = 0; i < asteroidList.length; i++) {
                    if (asteroidList[i][0] == 0) {
                        asteroidList[i][1] = x;
                        asteroidList[i][2] = y;
                        asteroidList[i][3] = z;
                        asteroidList[i][0] = 1; // 标记该条目已使用
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
        //在这里完成：检查三个二维数组存入的坐标是否合法（及使用MachineCheck(int x, int y, int z)返回的值不为0），如果不合法则将这一列全部置为0
        checkAndResetInvalidCoordinates();

        processWaitToDeal();
        processWaitToDrill();
    }

    private void checkAndResetInvalidCoordinates() {
        int[][][] lists = {AsteroidSearchList, AsteroidSolveList, AsteroidDrillList};

        for (int[][] list : lists) {
            for (int[] ints : list) {
                if (ints[0] != 0) {
                    int x = ints[1];
                    int y = ints[2];
                    int z = ints[3];
                    if (MachineCheck(x, y, z,false) == 0) {
                        // 如果坐标不合法，将这一列全部置为0
                        Arrays.fill(ints, 0);
                    }
                }
            }
        }
    }
    public int MachineCheck(int x, int y, int z,boolean sim) {
        TileEntity te = this.getWorld().getTileEntity(new BlockPos(x, y, z));
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            //找到加速器
            if (mte instanceof AsteroidSearch) {
                if(!sim)((AsteroidSearch) mte).setControlPos(this.getPos());
                return 1;
            }
            if (mte instanceof AsteroidSolve) {
                if(!sim)((AsteroidSolve) mte).setControlPos(this.getPos());
                return 2;
            }
            if (mte instanceof AsteroidDrill) {
                if(!sim)((AsteroidDrill) mte).setControlPos(this.getPos());
                return 3;
            }
        }
        return 0;
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

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AsteroidController(metaTileEntityId);
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
                .where('C', states(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_CASING))
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
}
