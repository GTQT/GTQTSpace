package meowmel.gtqtspace;

import net.minecraftforge.common.config.Config;

@Config(modid = GTQTSpace.MODID)
public class GTQTSConfig {
    @Config.Comment("Config options for space")
    public static Render render = new Render();
    public static Space space = new Space();

    public static boolean EnableRocketDitch=true;

    public static class Render {
        @Config.Comment("空间站渲染 星星 开关")
        @Config.Name("Render More Stars")
        public static boolean StarsRender = true;

        @Config.Comment("空间站渲染 宇宙背景 开关")
        @Config.Name("Render More Stars")
        public static boolean BackgroundRender = true;

        @Config.Comment("更多星星")
        @Config.Name("Render More Stars")
        public static boolean moreStars = true;
    }
    public static class Space{

    }
}
