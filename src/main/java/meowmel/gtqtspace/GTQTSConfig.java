package meowmel.gtqtspace;

import net.minecraftforge.common.config.Config;

@Config(modid = GTQTSpace.MODID)
public class GTQTSConfig {
    @Config.Comment("Config options for space")
    public static Render render = new Render();
    public static Space space = new Space();

    public static boolean EnableRocketDitch=true;

    public static class Render {
        @Config.Comment("空间站渲染星星开关")
        @Config.Name("The space station renders the star switch")
        public boolean StarsRender = true;

        @Config.Comment("渲染宇宙背景开关")
        @Config.Name("Render universe background switch")
        public boolean BackgroundRender = true;

        @Config.Comment("更多星星")
        @Config.Name("Render More Stars")
        public boolean moreStars = true;
    }
    public static class Space{

    }
}
