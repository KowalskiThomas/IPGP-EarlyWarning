package fr.ipgp.earlywarning.models;

import org.asteriskjava.pbx.DefaultAsteriskSettings;

public class EarlyWarningAsteriskSettings extends DefaultAsteriskSettings {
    String password, username, ip, host;

    public EarlyWarningAsteriskSettings(String password, String username, String ip, String host)
    {
        this.password = password;
        this.username = username;
        this.ip = ip;
        this.host = host;
    }

    public String getManagerPassword() {
        return password;
    }

    public String getManagerUsername() {
        return username;
    }

    public String getAsteriskIP() {
        return ip;
    }

    public String getAgiHost() {
        return host;
    }

    @Override
    public String toString()
    {
        return "fr.ipgp.earlywarning.models.EarlyWarningAsteriskSettings: " + username + ":" + password + ", local = " + host + ", server = " + ip;
    }
}
