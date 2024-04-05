package org.team21.game.controllers;

import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameProgress;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.Scanner;

/**
 * Loadgame controller
 * @author Meet Boghani
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
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
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
     * function to parse the command
     *
     * @param command string to be parsed
     * @return command
     */
    private String parseCommand(String command) {
        String[] l_Commands = command.split(" ");
        if (l_Commands.length == 2 && l_Commands[0].equals("loadgame")) {
            return l_Commands[1];
        }
        return "";
    }


}