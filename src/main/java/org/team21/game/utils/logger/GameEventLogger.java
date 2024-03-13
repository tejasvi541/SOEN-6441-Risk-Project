package org.team21.game.utils.logger;


import org.team21.game.interfaces.observer.Observable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Manages and dispatches game-related event logs. Leverages the Observable pattern
 * for efficient notification of registered observers.
 *
 * @author Kapil Soni
 */
public class GameEventLogger implements Observable {

    private GameLogWriter d_LogEntryWriter;

    /**
     * Initializes a new GameEventLogger instance.
     */
    public GameEventLogger() {
        d_LogEntryWriter = new GameLogWriter();// Automatically register LogEntryWriter
    }

    /**
     * Logs an informational event message and broadcasts it to all observers.
     *
     * @param p_message The event message to log and broadcast.
     */
    public void logEvent(String p_message) {
        // Indicate state change for observers
        notifyObservers(p_message);
    }

    /**
     * Prepares the log file for a new game session.
     *
     * @param p_filenamePrefix The base filename to use for the log file (without extension).
     * @throws IOException If there's an error preparing the log file.
     */
    public void initializeNewLog(String p_filenamePrefix) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + p_filenamePrefix + ".log", false)))) {
            // File cleared on creation
        }
    }

    /**
     * A function to notify to Observer.
     *
     * @param p_s the observable
     */
    @Override
    public void notifyObservers(String p_s) {
        d_LogEntryWriter.update(p_s);
    }
}
