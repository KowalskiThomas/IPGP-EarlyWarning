package fr.ipgp.earlywarning.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static Logger logger = initLogger(Logging.class, false);

    /** The folder that will contain logs. */
    private static String logPath = "/home/kowalski/stage/logs";

    /**
     * Verifies the log directory (logPath) exists or doesn't exist and is creatable. If so, creates it.
     */
    private static void verifyLogDirectory()
    {
        File logDir = new File(logPath);
        if (!logDir.isDirectory() && logDir.exists())
        {
            System.err.println("Can't use '" + logPath + "' as log path since it's already a file.");
            System.exit(1);
        }
        else if (!logDir.isDirectory()) {
           if (!logDir.mkdirs())
           {
               System.err.println("Can't create '" + logPath + "' to store logs.");
               System.exit(1);
           }
        }
    }

    /**
     * Configures the logger to write to a file.
     * @param logger the logger to configure
     * @param toFile whether or not the Logger should also write to a file.
     */
    public static void initLogger(Logger logger, boolean toFile)
    {
        if (toFile) {
            verifyLogDirectory();

            logger.info("Setting up file output for logger '" + logger.getName() + "'");
            FileHandler fileHandler;
            String loggerName = logger.getName();
            try {
                fileHandler = new FileHandler(logPath + "/" + loggerName);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
            } catch (IOException e) {
                logger.severe("Can't create file handler for logger '" + logger.get)
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes and configures a logger
     * @param o the Object for which we need a logger
     * @param toFile whether or not the log should also be written to a file
     * @return an initialized and configured logger for o
     */
    public static Logger initLogger(Object o, boolean toFile)
    {
        Logger logger = Logger.getLogger(o.getClass().toString());
        initLogger(logger, toFile);
        return logger;
    }

    /**
     * Initializes and configures a logger
     * @param s the name of the Logger
     * @param toFile whether or not the log should also be written to a file
     * @return an initialized and configured logger with name s
     */
    public static Logger initLogger(String s, boolean toFile)
    {
        Logger logger = Logger.getLogger(s);
        initLogger(logger, toFile);
        return logger;
    }

    public static Logger initLogger(Class c, boolean toFile)
    {
        Logger logger = Logger.getLogger(c.toString());
        initLogger(logger, toFile);
        return logger;
    }

    /**
     * Initializes and configures a logger
     * @param s the name of the Logger
     * @return an initialized and configured logger with name s
     */
    public static Logger initLogger(String s)
    {
        Logger logger = Logger.getLogger(s);
        initLogger(logger, false);
        return logger;
    }
}
