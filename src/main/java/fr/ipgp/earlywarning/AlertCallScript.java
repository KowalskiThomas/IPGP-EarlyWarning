package fr.ipgp.earlywarning;

import fr.ipgp.earlywarning.utilities.Settings;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.util.logging.Logger;

public class AlertCallScript extends BaseAgiScript {

    Logger logger = Logger.getLogger("AlertCall");

    public void service(AgiRequest request, AgiChannel channel) throws AgiException
    {
        // Stream an alert file
        streamFile("alerte.mp3");

        // Asks for a confirmation code (that ends with #)
        String code = getData("demande-code.mp3");

        if (code.equals(Settings.getInstance().getConfirmationCode()))
            logger.info("Correct confirmation code entered.");
        else
            logger.info("Wrong confirmation code entered.");

        // ...and hangup.
        hangup();
    }

}
