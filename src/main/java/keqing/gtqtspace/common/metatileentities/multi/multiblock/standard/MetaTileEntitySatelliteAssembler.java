package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController.tieredCasing;
import static gregtech.api.util.RelativeDirection.*;
import static java.lang.Math.min;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.BASIC_SATELLITE;
import static net.minecraft.util.math.MathHelper.abs;
import static net.minecraft.util.math.MathHelper.ceil;

public class MetaTileEntitySatelliteAssembler extends MetaTileEntityBaseWithControl {

    int value1;
    String value2="null" ;

    String value3="null" ;

    public MetaTileEntitySatelliteAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySatelliteAssembler(this.metaTileEntityId);
    }

    //怎么写
    //塞入卫星
    //塞入部件
    //检查
    //执行安装 如果有就强制替换
    @Override
    protected void updateFormedValid() {

        //首先自检

        if (checkSatellite(true)) {
            GTTransferUtils.insertItem(this.outputInventory, setSatellite(checkSolar(), checkSenior(), checkGenerator()), false);
            checkSatellite(false);
            value1=0;value2="null";value3="null";
        }
    }
    public boolean checkSatellite(boolean sim)
    {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getDisplayName().equals("基础卫星")) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null) {
                    compound.getKeySet().forEach(key -> { // 遍历NBT数据的键
                        if (Objects.equals(key, "SolarTier")) {
                            value1 = compound.getInteger(key); // 获取键对应的值的字符串表示
                        } else if (Objects.equals(key, "SeniorTier")) {
                            value2 = compound.getString(key); // 获取键对应的值的字符串表示
                        } else if (Objects.equals(key, "GeneratorTier")) {
                            value3 = compound.getString(key); // 获取键对应的值的字符串表示
                        }
                    });
                }
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }
    public int checkSolar()
    {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getDisplayName().equals("基础卫星太阳能板")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 1;
            }
            if (item.getDisplayName().equals("进阶卫星太阳能板")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 2;
            }
            if (item.getDisplayName().equals("高级卫星太阳能板")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 3;
            }
            if (item.getDisplayName().equals("精英卫星太阳能板")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 4;
            }
            if (item.getDisplayName().equals("终极卫星太阳能板")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 5;
            }
        }
        return 0;
    }
    public int checkSenior()
    {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getDisplayName().equals("矿脉传感器")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 1;
            }
            if (item.getDisplayName().equals("大气传感器")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 2;
            }
            if (item.getDisplayName().equals("轨道数据采集器")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 3;
            }
            if (item.getDisplayName().equals("深空数据采集器")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 4;
            }
            if (item.getDisplayName().equals("宇宙粒子采集器")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 5;
            }
        }
        return 0;
    }
    public int checkGenerator()
    {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getDisplayName().equals("基础化学能引擎")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 1;
            }
            if (item.getDisplayName().equals("高级化学能引擎")) {
                this.getInputInventory().extractItem(i, 1, false);
                return 2;
            }
        }
        return 0;
    }
    public String getSenior(int i)
    {
        switch (i)
        {
            case (1):return "矿脉传感器";
            case (2):return "大气传感器";
            case (3):return "轨道数据采集器";
            case (4):return "深空数据采集器";
            default:return "宇宙粒子采集器";
        }
    }
    public String getGenerator(int i)
    {
        switch (i)
        {
            case (1):return "基础化学能引擎";
            default:return "高级化学能引擎";
        }
    }
    public ItemStack setSatellite(int solar,int senior,int generator){
        ItemStack Satellite = new ItemStack(BASIC_SATELLITE.getMetaItem(), 1, 100);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("SolarTier", solar==0?value1:solar);
        nodeTagCompound.setString("SeniorTier", senior==0?value2:getSenior(senior));
        nodeTagCompound.setString("GeneratorTier",generator==0?value3:getGenerator(generator));
        Satellite.setTagCompound(nodeTagCompound);
        return Satellite;
    }
    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "CAX", "CCX").setRepeatable(3)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XAX", "#XX")
                .aisle("XXX", "STX", "#XX")
                .aisle("XXX", "XAX", "#XX")
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(40)
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
                .where('C', states(getCasingState2()))
                .where('T', tieredCasing().or(air()))
                .where('A', air())
                .where('#', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ASSEMBLING_CASING);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.ASSEMBLING_CASING;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.LARGE_ASSEMBLER_OVERLAY;
    }


    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
