package fr.ipgp.earlywarning.view;

import fr.ipgp.earlywarning.models.EarlyWarningAsteriskSettings;
import fr.ipgp.earlywarning.utilities.Settings;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.pbx.PBXFactory;
import org.asteriskjava.pbx.internal.core.AsteriskPBX;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static fr.ipgp.earlywarning.utilities.Logging.initLogger;

public class App {

    private static Logger logger;

    /**
     * The app's entry point
     * @param args command line arguments
     */
    public static void main(final String args[])
    {
        logger = initLogger(fr.ipgp.earlywarning.view.App.class, true);

        logger.info("coucuo");

        Settings settings = Settings.getInstance();
        EarlyWarningAsteriskSettings asteriskSettings = settings.getAsteriskSettings();

        /* Initialize a PBX with our current settings, read from the configuration file. */
        try {
            PBXFactory.init(asteriskSettings);
        }
        catch (NullPointerException ex)
        {
            logger.severe("Couldn't connect to the Asterisk server.");
            System.exit(1);
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
