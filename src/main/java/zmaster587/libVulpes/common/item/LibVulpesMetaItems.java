package zmaster587.libVulpes.common.item;

import zmaster587.libVulpes.api.LibVulpesItems;
import zmaster587.libVulpes.items.ItemIngredient;
import zmaster587.libVulpes.items.ItemLinker;
import zmaster587.libVulpes.items.ItemProjector;

import static zmaster587.libVulpes.LibVulpes.tabMultiblock;

public class LibVulpesMetaItems {
    public static void InitializeItems()
    {
        //Initialize Items
        LibVulpesItems.itemLinker = new ItemLinker().setRegistryName("libvulpes:linker").setCreativeTab(tabMultiblock).setTranslationKey("Linker");
        LibVulpesItems.itemBattery = new ItemIngredient(2).setRegistryName("libvulpes:battery").setCreativeTab(tabMultiblock).setTranslationKey("battery");
        LibVulpesItems.itemHoloProjector = new ItemProjector().setRegistryName("libvulpes:holoprojector").setCreativeTab(tabMultiblock).setTranslationKey("holoProjector");
    }
}
