package meowmel.gtqtspace.api.utils;

import org.apache.logging.log4j.Logger;

public class GTQTSLog {
    public static Logger logger;

    public GTQTSLog() {}

    public static void init(Logger modLogger) {
        logger = modLogger;
    }
}
