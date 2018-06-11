import java.util.logging.Logger;

public class App {

    public static Logger logger;

    public static void main(final String args[])
    {
        logger = Logger.getLogger("Main");

        EarlyWarningAsteriskSettings settings = SettingsBuilder.fromConfigurationFile();

        logger.info(settings.toString());
        logger.info("Fini !");
    }

}
