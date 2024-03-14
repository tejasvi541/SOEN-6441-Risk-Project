package org.team21.game.utils.logger;

import org.team21.game.interfaces.observer.Observer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs gameplay events received from an observed subject (e.g., GameEventLogger)
 * into a customizable log file.
 *
 * @author Kapil Soni
 */
public class GameLogFileWriter implements Observer {

    /**
     * Log filename prefix for further usage
     */
    private String d_LogFilenamePrefix = "gamelog"; // Default prefix

    public String getDirectoryPath() {
        return d_Path;
    }

    private String d_Path = "";

    /**
     * Constructor to initialize the GameLogWriter
     */
    public GameLogFileWriter() {
        // You could optionally add code here to load preferences
        // or customize the d_LogFilenamePrefix from a configuration file
        d_Path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Logs" +File.separator;
        File l_MakeDir = new File(d_Path);
        if(!l_MakeDir.exists()){
            l_MakeDir.mkdirs();
        }
    }


    /**
     * Writes a log entry including a timestamp to a  designated log file.
     *
     * @param p_eventMessage The event message to be logged.
     */
    private void writeLogEntry(String p_eventMessage) {
        try (PrintWriter l_Writer = new PrintWriter(new BufferedWriter(new FileWriter(d_Path + d_LogFilenamePrefix + ".log", true)))) {
            LocalDateTime l_Now = LocalDateTime.now();
            DateTimeFormatter l_Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String l_Timestamp = l_Now.format(l_Formatter);

            l_Writer.println("[" + l_Timestamp + "] " + p_eventMessage);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Sets the filename prefix for log files.
     *
     * @param p_Prefix The desired prefix (excluding file extension)
     */
    public void setLogFilenamePrefix(String p_Prefix) {
        this.d_LogFilenamePrefix = p_Prefix;
    }

    /**
     * Function to update the message for the observer
     *
     * @param p_S the message to be updated
     */
    @Override
    public void update(String p_S) {
        writeLogEntry(p_S);
    }
}

