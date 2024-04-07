package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameProgress;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.Scanner;

/**
 * LoadGameController to load the game from the files
 * and parsing the command
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public class LoadGameController implements GameFlowManager {

    /**
     * logger to print
     */
    private final GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * a method to execute the phase
     *
     * @param p_GamePhase holding the current game phase
     * @return gamephase
     * @throws Exception exception
     */
    @Override
    public GamePhase startPhase(GamePhase p_GamePhase) throws Exception {
        GameProgress.showFiles();
        Scanner l_Scanner = new Scanner(System.in);
        String l_Command = l_Scanner.nextLine();
        String l_FileName = parseCommand(l_Command);
        GamePhase l_GameLoaded = GameProgress.LoadGameProgress(l_FileName);
        if (l_GameLoaded.equals(GamePhase.StartUp)) {
            d_Logger.log("Loading Old game failed, Check the file name");
            return GamePhase.StartUp;
        }
        return l_GameLoaded;
    }

    /**
     * This is the function to parse the command for the
     * load game controller. It will check all the commands validation
     * as a first entry point validation for
     * loadgame
     *
     * @param command string to be parsed
     * @return command
     */
    private String parseCommand(String command) {
        String[] l_Commands = command.split(" ");
        if (l_Commands.length == 2 && l_Commands[0].equals(Constants.LOADGAME_COMMAND)) {
            return l_Commands[1];
        }
        return "";
    }


}