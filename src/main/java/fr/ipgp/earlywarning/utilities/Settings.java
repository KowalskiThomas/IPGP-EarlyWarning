package fr.ipgp.earlywarning.utilities;

import fr.ipgp.earlywarning.models.EarlyWarningAsteriskSettings;
import org.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Settings {

    public class ManagerSettings {
        public String ip;
        public String password;
        public String port;

        public ManagerSettings(String ip, String password, String port)
        {
            this.ip = ip;
            this.password = password;
            this.port = port;
        }
    }

    public static Logger logger = Logger.getLogger("Settings");

    private static Settings singleton;
    private static String path = null;
    private static EarlyWarningAsteriskSettings asteriskSettings;
    private static ManagerSettings managerSettings;
    private String confirmationCode;

    private Settings()
    {
        if (path == null)
            path = getDefaultPath();

        fromConfigurationFile();
        singleton = this;
    }

    public static Settings getInstance()
    {
        if (singleton == null)
            singleton = new Settings();

        return singleton;
    }

    public String getConfirmationCode()
    {
        return confirmationCode;
    }

    public ManagerSettings getManagerSettings()
    {
        return managerSettings;
    }

    public EarlyWarningAsteriskSettings getAsteriskSettings()
    {
        return asteriskSettings;
    }

    public static String getDefaultPath()
    {
        return "/home/kowalski/stage/config/settings.json";
    }

    public static void setDefaultPath(String path)
    {
        Settings.path = path;
    }

    private void fromJson(String json)
    {
        JSONObject config = new JSONObject(json);

        String ip = config.getJSONObject("server").getString("ip");
        String managerPassword = config.getJSONObject("manager").getString("password");
        String managerPort = config.getJSONObject("manager").getString("port");

        String asteriskPassword = config.getJSONObject("connection").getString("password");
        String asteriskUsername = config.getJSONObject("connection").getString("username");
        String asteriskHost = config.getJSONObject("connection").getString("host");

        managerSettings = new ManagerSettings(ip, managerPassword, managerPort);
        asteriskSettings = new EarlyWarningAsteriskSettings(asteriskPassword, asteriskUsername, ip, asteriskHost);
    }

    private void fromConfigurationFile()
    {
        String content = "";
        try
        {
            File file = new File(path);
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
            logger.severe("Can't find configuration file '" + path + "'");
            logger.severe(ex.getMessage());
            System.exit(1);
        }

        try
        {
            fromJson(content);
        }
        catch (JSONException ex)
        {
            logger.severe("The configuration file '" + path + "' is invalid.");
            logger.severe(ex.getMessage());
            System.exit(1);
        }
    }
}
