package zmaster587.libVulpes.common.block;

import gregtech.common.blocks.MetaBlocks;
import meowmel.gtqtspace.common.block.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.block.BlockAlphaTexture;
import zmaster587.libVulpes.block.BlockPhantom;
import zmaster587.libVulpes.block.BlockTileComparatorOverride;
import zmaster587.libVulpes.block.multiblock.BlockHatch;
import zmaster587.libVulpes.block.multiblock.BlockMultiMachineBattery;
import zmaster587.libVulpes.block.multiblock.BlockMultiblockPlaceHolder;
import zmaster587.libVulpes.inventory.GuiHandler;
import zmaster587.libVulpes.tile.energy.TileCoalGenerator;
import zmaster587.libVulpes.tile.energy.TileCreativePowerInput;
import zmaster587.libVulpes.tile.energy.TileForgePowerInput;
import zmaster587.libVulpes.tile.energy.TileForgePowerOutput;

import static zmaster587.libVulpes.LibVulpes.tabMultiblock;

public class LibVulpesMetaBlocks {

    private LibVulpesMetaBlocks() {}


    public static void InitializeBlocks()
    {
        //Initialize Blocks
        LibVulpesBlocks.blockPhantom = new BlockPhantom(Material.CIRCUITS).setRegistryName("blockPhantom");
        LibVulpesBlocks.blockHatch = new BlockHatch(Material.IRON).setRegistryName("hatch").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockPlaceHolder = new BlockMultiblockPlaceHolder().setRegistryName("placeHolder").setHardness(1f);
        LibVulpesBlocks.blockAdvStructureBlock = new BlockAlphaTexture(Material.IRON).setRegistryName("advstructuremachine").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockStructureBlock = new BlockAlphaTexture(Material.IRON).setRegistryName("structuremachine").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockCreativeInputPlug = new BlockMultiMachineBattery(Material.IRON, TileCreativePowerInput.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("creativePowerBattery").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockForgeInputPlug = new BlockMultiMachineBattery(Material.IRON, TileForgePowerInput.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("forgePowerInput").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockForgeOutputPlug = new BlockMultiMachineBattery(Material.IRON, TileForgePowerOutput.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("forgePowerOutput").setCreativeTab(tabMultiblock).setHardness(3f);
        LibVulpesBlocks.blockCoalGenerator = new BlockTileComparatorOverride(TileCoalGenerator.class, GuiHandler.guiId.MODULAR.ordinal()).setRegistryName("coalGenerator").setCreativeTab(tabMultiblock).setHardness(3f);

    }
}
