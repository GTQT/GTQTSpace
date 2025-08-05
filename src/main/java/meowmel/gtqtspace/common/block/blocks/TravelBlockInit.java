package meowmel.gtqtspace.common.block.blocks;

import meowmel.gtqtspace.common.block.BlockTeleporterDeepDank;
import meowmel.gtqtspace.common.block.TileEntityTeleporterDeepDank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = "gtqtspace")
public class TravelBlockInit {

    public static final BlockTeleporterDeepDank TELEPORTER = new BlockTeleporterDeepDank();
    public static final Item ITEM_TELEPORTER_BLOCK = new ItemBlock(TELEPORTER).setRegistryName(TELEPORTER.getRegistryName().getPath());

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        // 和物品一样，每一个方块都有唯一一个注册名，不能使用大写字母。
        event.getRegistry().register(TELEPORTER);
        GameRegistry.registerTileEntity(TileEntityTeleporterDeepDank.class, "teleporter_spacestation");
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        // 注意这个 ItemBlock 使用了和它对应的方块一样的注册名。
        // 对于所有有物品形态的方块，其物品的注册名和它自己的注册名需要保持一致。
        event.getRegistry().register(ITEM_TELEPORTER_BLOCK);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelReg(ModelRegistryEvent event) {
        //材质注册，最后的参数为空表示同时注册了世界中的材质和物品栏中的材质
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TELEPORTER), 0, new ModelResourceLocation(TELEPORTER.getRegistryName(), ""));


    }

}
