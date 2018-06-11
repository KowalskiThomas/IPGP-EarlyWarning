import org.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class SettingsBuilder {

    public static Logger logger = Logger.getLogger("Settings");

    public static String getDefaultPath()
    {
        return "/home/kowalski/.earlywarning/settings.json";
    }

    public static EarlyWarningAsteriskSettings fromJson(String json)
    {
        JSONObject config = new JSONObject(json);

        config = config.getJSONObject("connection");

        return new EarlyWarningAsteriskSettings(
                config.getString("password"),
                config.getString("username"),
                config.getString("server"),
                config.getString("host"));

    }

    public static EarlyWarningAsteriskSettings fromConfigurationFile(String configurationFile)
    {
        String content = "";
        try
        {
            File file = new File(configurationFile);
            FileReader reader = new FileReader(file);
            int c = reader.read();
            while (c != -1)
            {
                content += (char) c;
                c =reader.read();
            }
            reader.close();
        }
        catch (IOException ex)
        {
            logger.severe("Can't find configuration file '" + configurationFile + "'");
            logger.severe(ex.getMessage());
            return null;
        }

        try
        {
            return fromJson(content);
        }
        catch (JSONException ex)
        {
            logger.severe("The configuration file '" + configurationFile + "' is invalid.");
            return null;
        }
    }

    public static EarlyWarningAsteriskSettings fromConfigurationFile()
    {
        return fromConfigurationFile(getDefaultPath());
    }

}
