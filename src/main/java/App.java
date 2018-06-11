import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.pbx.PBXFactory;
import org.asteriskjava.pbx.internal.core.AsteriskPBX;

import java.io.IOException;
import java.util.logging.Logger;

public class App {

    public static Logger logger;

    public static void main(final String args[])
    {
        logger = Logger.getLogger("Main");

        Settings settings = Settings.getInstance();
        EarlyWarningAsteriskSettings asteriskSettings = settings.getAsteriskSettings();

        /* Initialize a PBX with our current settings, read from the configuration file. */
        try {
            PBXFactory.init(asteriskSettings);
        }
        catch (NullPointerException ex)
        {
            logger.severe("Couldn't connect to the Asterisk server.");
            return;
        }

        /**
         * Activities utilise an agi entry point in your dial plan.
         * You can create your own entry point in dialplan or have
         * asterisk-java add it automatically as we do here.
         */
        AsteriskPBX asteriskPbx = (AsteriskPBX) PBXFactory.getActivePBX();
        try {
            asteriskPbx.createAgiEntryPoint();
        } catch (IOException e) {
            logger.severe("IOException");
            e.printStackTrace();
        } catch (AuthenticationFailedException e) {
            logger.severe("Authentication failed.");
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.severe("Timeout.");
            e.printStackTrace();
        }

        logger.info("Exiting EarlyWarning.");
    }

}
