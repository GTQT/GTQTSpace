package zmaster587.libVulpes;


import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.api.LibVulpesItems;
import zmaster587.libVulpes.api.material.AllowedProducts;
import zmaster587.libVulpes.api.material.MaterialRegistry;
import zmaster587.libVulpes.block.BlockMeta;
import zmaster587.libVulpes.common.CommonProxy;
import zmaster587.libVulpes.common.block.LibVulpesMetaBlocks;
import zmaster587.libVulpes.event.BucketHandler;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.inventory.GuiHandler;
import zmaster587.libVulpes.network.PacketChangeKeyState;
import zmaster587.libVulpes.network.PacketEntity;
import zmaster587.libVulpes.network.PacketHandler;
import zmaster587.libVulpes.network.PacketMachine;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.tile.multiblock.TileMultiBlock;
import zmaster587.libVulpes.util.TeslaCapabilityProvider;
import zmaster587.libVulpes.util.XMLRecipeLoader;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static zmaster587.libVulpes.common.block.LibVulpesMetaBlocks.InitializeBlocks;
import static zmaster587.libVulpes.common.item.LibVulpesMetaItems.InitializeItems;

@Mod(modid = "libvulpes", name = "Vulpes library", version = "0.5.0", useMetadata = true)

public class LibVulpes {
    public static org.apache.logging.log4j.Logger logger = LogManager.getLogger("libVulpes");
    public static int time = 0;
    @Instance(value = "libvulpes")
    public static LibVulpes instance;
    @SidedProxy(clientSide = "zmaster587.libVulpes.client.ClientProxy", serverSide = "zmaster587.libVulpes.common.CommonProxy")
    public static CommonProxy proxy;
    public static CreativeTabs tabMultiblock = new CreativeTabs("multiBlock") {
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(LibVulpesItems.itemLinker);
        }
    };
    public static CreativeTabs tabLibVulpesOres = new CreativeTabs("advancedRocketryOres") {
        @Nonnull
        public ItemStack createIcon() {
            return OreDictUnifier.get(OrePrefix.ore, Materials.Copper);
        }
    };
    public static MaterialRegistry materialRegistry = new MaterialRegistry();
    private static final HashMap<Class, String> userModifiableRecipes = new HashMap<>();

    public LibVulpes() {
        MinecraftForge.EVENT_BUS.register(this);
        InitializeBlocks();
        InitializeItems();
    }

    public static void registerRecipeHandler(Class clazz, String fileName) {
        userModifiableRecipes.put(clazz, fileName);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        proxy.preInitItems();
        proxy.preInitBlocks();
        LibVulpesMetaBlocks.registerItemModels();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerBlocks(RegistryEvent.Register<Block> evt) {
        materialRegistry.registerOres(tabLibVulpesOres);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void missingMappings(RegistryEvent.MissingMappings<Item> evt) {
        for (Mapping<Item> mapping : evt.getAllMappings()) {
            if (mapping.key.compareTo(new ResourceLocation("libvulpes:productcrystal")) == 0)
                mapping.remap(MaterialRegistry.getItemStackFromMaterialAndType("Dilithium", AllowedProducts.getProductByName("GEM")).getItem());

        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        LibVulpesMetaBlocks.init();

        //Configuration
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        zmaster587.libVulpes.Configuration.EUMult = (float) config.get(Configuration.CATEGORY_GENERAL, "EUPowerMultiplier", 4, "How many FE one EU makes").getDouble();
        zmaster587.libVulpes.Configuration.powerMult = (float) config.get(Configuration.CATEGORY_GENERAL, "PowerMultiplier", 1, "Power multiplier on machines").getDouble();

        config.save();

        TeslaCapabilityProvider.registerCap();


        //Register allowedProducts
        AllowedProducts.registerProduct("DUST");
        AllowedProducts.registerProduct("INGOT");
        AllowedProducts.registerProduct("GEM");
        AllowedProducts.registerProduct("BOULE");
        AllowedProducts.registerProduct("NUGGET");
        AllowedProducts.registerProduct("COIL", true);
        AllowedProducts.registerProduct("PLATE");
        AllowedProducts.registerProduct("STICK");
        AllowedProducts.registerProduct("BLOCK", true);
        AllowedProducts.registerProduct("ORE", true);
        AllowedProducts.registerProduct("FAN");
        AllowedProducts.registerProduct("SHEET");
        AllowedProducts.registerProduct("GEAR");

        //Register Ores
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Dilithium", "pickaxe", 3, 0xddcecb, AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("GEM").getFlagValue()));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Iron", "pickaxe", 1, 0xafafaf, AllowedProducts.getProductByName("SHEET").getFlagValue() | AllowedProducts.getProductByName("STICK").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue(), false));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Gold", "pickaxe", 1, 0xffff5d, AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("COIL").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue(), false));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Silicon", "pickaxe", 1, 0x2c2c2b, AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("BOULE").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue(), false));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Copper", "pickaxe", 1, 0xd55e28, AllowedProducts.getProductByName("COIL").getFlagValue() | AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("STICK").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("SHEET").getFlagValue()));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Tin", "pickaxe", 1, 0xcdd5d8, AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue()));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Steel", "pickaxe", 1, 0x55555d, AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("FAN").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("STICK").getFlagValue() | AllowedProducts.getProductByName("GEAR").getFlagValue() | AllowedProducts.getProductByName("SHEET").getFlagValue(), false));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Titanium", "pickaxe", 1, 0xccc8fa, AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("COIL").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("STICK").getFlagValue() | AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("GEAR").getFlagValue() | AllowedProducts.getProductByName("SHEET").getFlagValue(), false));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Rutile", "pickaxe", 1, 0xbf936a, AllowedProducts.getProductByName("ORE").getFlagValue(), new String[]{"Rutile", "Titanium"}));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Aluminum", "pickaxe", 1, 0xb3e4dc, AllowedProducts.getProductByName("COIL").getFlagValue() | AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("SHEET").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("SHEET").getFlagValue()));
        materialRegistry.registerMaterial(new zmaster587.libVulpes.api.material.Material("Iridium", "pickaxe", 2, 0xdedcce, AllowedProducts.getProductByName("COIL").getFlagValue() | AllowedProducts.getProductByName("BLOCK").getFlagValue() | AllowedProducts.getProductByName("DUST").getFlagValue() | AllowedProducts.getProductByName("INGOT").getFlagValue() | AllowedProducts.getProductByName("NUGGET").getFlagValue() | AllowedProducts.getProductByName("PLATE").getFlagValue() | AllowedProducts.getProductByName("STICK").getFlagValue()));

        //
        PacketHandler.INSTANCE.addDiscriminator(PacketMachine.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketEntity.class);
        PacketHandler.INSTANCE.addDiscriminator(PacketChangeKeyState.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        PacketHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        proxy.registerEventHandlers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BucketHandler());

        //Init TileMultiblock
        //Item output
        List<BlockMeta> list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 1));
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 9));
        TileMultiBlock.addMapping('O', list);

        //Item Inputs
        list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 0));
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 8));
        TileMultiBlock.addMapping('I', list);

        //Power input
        list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockCreativeInputPlug, BlockMeta.WILDCARD));
        list.add(new BlockMeta(LibVulpesBlocks.blockForgeInputPlug, BlockMeta.WILDCARD));
        if (LibVulpesBlocks.blockRFBattery != null)
            list.add(new BlockMeta(LibVulpesBlocks.blockRFBattery, BlockMeta.WILDCARD));
        if (LibVulpesBlocks.blockIC2Plug != null)
            list.add(new BlockMeta(LibVulpesBlocks.blockIC2Plug, BlockMeta.WILDCARD));
        TileMultiBlock.addMapping('P', list);

        //Power output
        list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockForgeOutputPlug, BlockMeta.WILDCARD));
        if (LibVulpesBlocks.blockRFOutput != null)
            list.add(new BlockMeta(LibVulpesBlocks.blockRFOutput, BlockMeta.WILDCARD));
        TileMultiBlock.addMapping('p', list);

        //Liquid input
        list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 2));
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 10));
        TileMultiBlock.addMapping('L', list);

        //Liquid output
        list = new LinkedList<>();
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 3));
        list.add(new BlockMeta(LibVulpesBlocks.blockHatch, 11));
        TileMultiBlock.addMapping('l', list);

        //User Recipes


    }

    public void loadXMLRecipe(Class clazz) {
        File file = new File(userModifiableRecipes.get(clazz));
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/libvulpes/defaultrecipe.xml")));

                BufferedWriter stream2 = new BufferedWriter(new FileWriter(file));


                while (inputStream.ready()) {
                    stream2.write(inputStream.readLine() + "\n");
                }


                //Write recipes

                stream2.write("<Recipes useDefault=\"true\">\n");
                for (IRecipe recipe : RecipesMachine.getInstance().getRecipes(clazz)) {
                    boolean writeable = true;
                    for (ItemStack stack : recipe.getOutput()) {
                        if (stack.hasTagCompound()) {
                            writeable = false;
                            break;
                        }
                    }

                    if (((RecipesMachine.Recipe) recipe).outputToOnlyEmptySlots())
                        writeable = false;

                    if (writeable)
                        stream2.write(XMLRecipeLoader.writeRecipe(recipe) + "\n");
                }
                stream2.write("</Recipes>");
                stream2.close();

                inputStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            XMLRecipeLoader loader = new XMLRecipeLoader();
            try {
                loader.loadFile(file);
                loader.registerRecipes(clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        time++;
    }
}

