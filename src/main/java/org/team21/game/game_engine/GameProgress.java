package org.team21.game.game_engine;

import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.ValidationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * A class to save and load game progress
 *
 * @author Tejasvi
 * @version 1.0.0
 */
public class GameProgress {
    /**
     * constant path
     */
    static final String d_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "savedGames" + File.separator;
    /**
     * LogEntry Buffer Instance
     */
    private static GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * A function to save the game progress
     *
     * @param p_GameMap instance of the game
     * @param p_Name    file name
     * @return true is successful else false
     */
    public static boolean SaveGameProgress(GameMap p_GameMap, String p_Name) {
        try {
            checkDirectory(d_PATH);
            FileOutputStream l_Fs = new FileOutputStream(d_PATH + p_Name + ".bin");
            ObjectOutputStream l_Os = new ObjectOutputStream(l_Fs);
            l_Os.writeObject(p_GameMap);
            d_Logger.log("The game has been saved successfully to file ./savedGames/" + p_Name + ".bin");
            l_Os.flush();
            l_Fs.close();
            p_GameMap.flushGameMap();
            return true;
        } catch (Exception p_Exception) {
            d_Logger.log(p_Exception.toString());
            return false;
        }
    }

    /**
     * A file to load the game progress
     *
     * @param p_Filename the file name string
     * @return Gamephase instance
     */
    public static GamePhase LoadGameProgress(String p_Filename) {
        FileInputStream l_Fs;
        GameMap l_LoadedGameMap;
        try {
            l_Fs = new FileInputStream(d_PATH + p_Filename+ ".bin");
            ObjectInputStream l_Os = new ObjectInputStream(l_Fs);
            l_LoadedGameMap = (GameMap) l_Os.readObject();
            d_Logger.log("The game is loaded successfully will continue from where it last stopped.");
            l_Os.close();
            return GameMap.getInstance().gamePlayBuilder(l_LoadedGameMap);
        } catch (IOException | ClassNotFoundException | ValidationException p_Exception) {
            d_Logger.log("The file could not be loaded.");
            return GamePhase.StartUp;
        }
    }

    /**
     * A function to show the files
     *
     * @throws IOException File exception
     */
    public static void showFiles() throws IOException {
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        d_Logger.log("\t\t\t Warzone");
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        d_Logger.log("\t\t\t Load Game");
        d_Logger.log("\t=======================\n");
        if (new File(d_PATH).exists()) {
            Files.walk(Path.of(d_PATH))
                    .filter(path -> path.toFile().isFile())
                    .forEach(path -> {
                        d_Logger.log("\t\t " + path.getFileName());
                    });
        } else {
            d_Logger.log("\t\t " + "no load files found");
        }
        d_Logger.log("");
        d_Logger.log("\t=======================");
        d_Logger.log("\t use file name to load");
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        d_Logger.log("example command: loadgame");
    }

    /**
     * Checks if the directory exists and creates it if it doesn't.
     *
     * @param path The path to check and create if necessary.
     */
    private static void checkDirectory(String path) {
        File l_directory = new File(path);
        if (!l_directory.exists() || !l_directory.isDirectory()) {
            l_directory.mkdirs();
        }
    }
}
