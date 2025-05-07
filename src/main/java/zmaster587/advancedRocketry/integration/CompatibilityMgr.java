package zmaster587.advancedRocketry.integration;

import net.minecraftforge.fml.common.Loader;
import zmaster587.advancedRocketry.integration.jei.ARPlugin;

public class CompatibilityMgr {

    public static boolean isSpongeInstalled;

    public CompatibilityMgr() {

    }

    public static void getLoadedMods() {

    }

    public static void reloadRecipes() {
        try {
            Class<?> clazz = Class.forName("mezz.jei.api.BlankModPlugin");
            ARPlugin.reload();
        } catch (ClassNotFoundException e) {
            //Hush
        }
    }
}
