package meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import meowmel.gtqtspace.common.block.GTQTSMetaBlocks;
import meowmel.gtqtspace.common.block.blocks.GTQTSMultiblockCasing1;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Random;

import static meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.IntArrayOperations.containsZero;
import static meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.AsteroidSystem.IntArrayOperations.replaceFirstZero;

public class AsteroidSearch extends MultiblockWithDisplayBase {
    int IDResolve;
    BlockPos ControlPos=new BlockPos(0,0,0);
    @Override
    protected void addDisplayText (List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("待处理：%s",IDResolve));
        textList.add(new TextComponentTranslation("处理进度：%s /200", time));
        textList.add(new TextComponentTranslation("绑定状态：%s %s %s", ControlPos.getX(),ControlPos.getY(),ControlPos.getZ()));
        textList.add(new TextComponentTranslation("======================"));
        textList.add(new TextComponentTranslation("探测等级：%s",1));
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 写入单个整数
        data.setInteger("IDResolve", IDResolve);

        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }

        return super.writeToNBT(data);
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        // 读取单个整数
        IDResolve = data.getInteger("IDResolve");

        // 读取 BlockPos
        if (data.hasKey("ControlPosX") && data.hasKey("ControlPosY") && data.hasKey("ControlPosZ")) {
            int posX = data.getInteger("ControlPosX");
            int posY = data.getInteger("ControlPosY");
            int posZ = data.getInteger("ControlPosZ");
            ControlPos = new BlockPos(posX, posY, posZ);
        } else {
            ControlPos = null;
        }
    }

    public void setControlPos(BlockPos pos)
    {
        ControlPos=pos;
    }
    int time=0;
    @Override
    protected void updateFormedValid() {
        if(!isStructureFormed())return;
        if(MachineCheck(ControlPos))
        {
            if(IDResolve!=0) {
                AsteroidController control = getMteFromPos(ControlPos);
                if(containsZero(control.WaitToDeal))
                {
                    replaceFirstZero(control.WaitToDeal,IDResolve);
                    IDResolve=0;
                }
            }
            if(IDResolve==0)
            {
                time++;
                if(time==200)
                {
                    IDResolve =generateUniqueID();
                    time=0;
                }
            }
        }
    }
    public static int generateUniqueID() {
        Random random = new Random();
        int id;
        do {
            id = 100000 + random.nextInt(900000);
        } while (containsZeroID(id));
        return id;
    }
    private static boolean containsZeroID(int number) {
        while (number > 0) {
            if (number % 10 == 0) {
                return true;
            }
            number /= 10;
        }
        return false;
    }
    public AsteroidController getMteFromPos(BlockPos pos)
    {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof AsteroidController) {
                return ((AsteroidController) mte);
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
                if (mte instanceof AsteroidController) {
                    return ((AsteroidController) mte).isStructureFormed();
                }
            }
        }
        ControlPos=new BlockPos(0,0,0);
        return false;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AsteroidSearch(metaTileEntityId);
    }
    public AsteroidSearch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC","CCC","CCC")
                .aisle("CCC","CCC","CCC")
                .aisle("CMC","CSC","CCC")
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('S', selfPredicate())
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
}
