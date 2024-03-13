package org.team21.game.utils.logger;

import org.team21.game.interfaces.observer.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs gameplay events received from an observed subject (e.g., GameEventLogger)
 * into a customizable log file.
 *
 * @author Kapil Soni
 */
public class GameLogWriter implements Observer {

    private String logFilenamePrefix = "gamelog"; // Default prefix

    /**
     *  Constructor to initialize the GameLogWriter
     */
    public GameLogWriter() {
        // You could optionally add code here to load preferences
        // or customize the logFilenamePrefix from a configuration file
    }


    /**
     * Writes a log entry including a timestamp to a  designated log file.
     *
     * @param p_eventMessage The event message to be logged.
     */
    private void writeLogEntry(String p_eventMessage) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + logFilenamePrefix + ".log", true)))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            writer.println("[" + timestamp + "] " + p_eventMessage);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Sets the filename prefix for log files.
     *
     * @param prefix The desired prefix (excluding file extension)
     */
    public void setLogFilenamePrefix(String prefix) {
        this.logFilenamePrefix = prefix;
    }

    /**
     * Function to update the message for the observer
     *
     * @param p_s the message to be updated
     */
    @Override
    public void update(String p_s) {
        writeLogEntry(p_s);
    }
}

