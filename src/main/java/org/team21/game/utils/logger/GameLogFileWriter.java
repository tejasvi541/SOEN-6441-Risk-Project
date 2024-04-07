package org.team21.game.utils.logger;

import org.team21.game.interfaces.observers.Observer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The GameLogFileWriter class implements the Observer interface to observe the LogEntryBuffer and write logs to a file.
 * It maintains a log file for recording game actions and events.
 * This class provides functionality to update the log file with messages received from the subject.
 * It ensures that logs are written to a designated log file with proper formatting.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class GameLogFileWriter implements Observer, Serializable {
    /**
     * The file path for the log file.
     */
    public static final String d_LOG_FILE_PATH = "logfile_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

    /**
     * The directory path where log files are stored.
     */
    private final String d_FilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "logFiles" + File.separator;

    /**
     * Constructs a GameLogFileWriter object and clears existing log files.
     */
    public GameLogFileWriter() {
        clearGameLogs();
    }

    /**
     * Receives updates from the subject and writes the message to the log file.
     *
     * @param p_s The message to be written to the log file.
     */
    public void update(String p_s) {
        writeLogFile(p_s);
    }

    /**
     * Writes the provided message to the log file.
     *
     * @param p_str The message to be written to the log file.
     */
    public void writeLogFile(String p_str) {
        PrintWriter l_WriteData = null;
        try {
            checkDirectory(d_FilePath);
            l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter(d_FilePath + d_LOG_FILE_PATH + ".log", true)));
            l_WriteData.println(p_str);

        } catch (Exception p_Exception) {
            System.out.println(p_Exception.getMessage());
        } finally {
            if (l_WriteData != null) {
                l_WriteData.close();
            }
        }
    }

    /**
     * Checks if the directory exists and creates it if it doesn't.
     *
     * @param path The path to check and create if necessary.
     */
    private void checkDirectory(String path) {
        File l_directory = new File(path);
        if (!l_directory.exists() || !l_directory.isDirectory()) {
            l_directory.mkdirs();
        }
    }

    /**
     * Clears the log file by deleting it before starting a new game.
     */
    @Override
    public void clearGameLogs() {
        try {
            checkDirectory(d_FilePath);
            File l_File = new File(d_FilePath + d_LOG_FILE_PATH + ".log");
            if (l_File.exists()) {
                l_File.delete();
            }
        } catch (Exception ex) {
            // Handle exception
        }
    }
}