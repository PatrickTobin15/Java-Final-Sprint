package com.gym.util;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {

    private static final Logger logger = Logger.getLogger("GymApp");
    private static boolean initialized = false;

    private AppLogger() {}

    public static Logger getLogger() {
        if (!initialized) {
            try {
                // Creates a file handler to which the logs are ritten to gym_app.log
                FileHandler fileHandler = new FileHandler("gym_app.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.ALL);

                // Console handler
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.WARNING);

                logger.addHandler(fileHandler);
                logger.addHandler(consoleHandler);
                logger.setLevel(Level.ALL);
                logger.setUseParentHandlers(false);

                initialized = true;
                logger.info("=== Gym Management System Started ===");
            } catch (IOException e) {
                System.err.println("Could not initialize logger: " + e.getMessage());
            }
        }
        return logger;
    }
}
