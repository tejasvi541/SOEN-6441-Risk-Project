package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Issue order controller will execute orders and passes to {ExecuteOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class IssueOrderController implements GameFlowManager {
    /**
     * Scanner will scan the inputs from the user
     */
    private final Scanner d_Scanner = new Scanner(System.in);
    /**
     * The d_UpcomingGamePhase is used to get next game phase.
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.ExecuteOrder;
    /**
     * The d_GameMap is game map.
     */
    private final GameMap d_GameMap;
    /**
     * Created object d_GameEventLogger of GameEventLogger.
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();

    /**
     * To check current issued order commands
     */
    public static String d_IssueOrderCommand = "";
    /**
     * Constructor to get the GameMap instance
     */
    public IssueOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * findCommandLength will check and find the length of each command
     *
     * @param p_Command the command to be validated
     * @param p_Length  required length to be validated
     * @return : true if length is same as required command
     */
    private static boolean findCommandLength(String p_Command, int p_Length) {
        if (p_Command.contains(Constants.DEPLOY_COMMAND)) {
            return p_Length == 3;
        } else if (p_Command.contains(Constants.BOMB_COMMAND) || p_Command.contains(Constants.BLOCKADE_COMMAND) || p_Command.contains(Constants.NEGOTIATE_COMMAND)) {
            return (p_Length == 2);
        } else if (p_Command.contains(Constants.AIRLIFT_COMMAND) || p_Command.contains(Constants.ADVANCE_COMMAND)) {
            return (p_Length == 4);
        }
        return false;
    }

    /**
     * A function to start the issue order phase
     *
     * @param p_CurrentPhase :  The current phase which is executing
     * @return : to return the next phase to be executed
     */

    @Override
    public GamePhase start(GamePhase p_CurrentPhase) {
        d_GameEventLogger.logEvent(Constants.ISSUE_ORDER_PHASE);
        return run(p_CurrentPhase);
    }


    /**
     * Run method will execute IssueOrder logic
     *
     * @param p_CurrentGamePhase : Based on current gamePhase next phase will come
     * @return : It will return next gamePhase
     */
    private GamePhase run(GamePhase p_CurrentGamePhase) {
        /**
         * The p_CurrentGamePhase is used to know current game phase.
         */
        int l_PlayerCounts = 0;
        List<String> l_ZeroReinforcementPlayers = new ArrayList<>();
        while (l_PlayerCounts != d_GameMap.getPlayers().size()) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if (l_Player.getReinforcementArmies() <= 0 && !(l_ZeroReinforcementPlayers.contains(l_Player.getName()))) {
                    l_ZeroReinforcementPlayers.add(l_Player.getName());
                    l_PlayerCounts++;
                    continue;
                }
                if (l_PlayerCounts == d_GameMap.getPlayers().size()) {
                    System.out.println(Constants.ARMY_DEPLETED);
                    d_GameEventLogger.logEvent(Constants.ARMY_DEPLETED);
                    System.out.println(Constants.SEPERATER);
                    return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
                }
                if (l_Player.getReinforcementArmies() != 0) {
                    System.out.println(Constants.SEPERATER);
                    System.out.println("Player: " + l_Player.getName() + "; armies assigned are: " + l_Player.getReinforcementArmies());
                    d_GameEventLogger.logEvent("Player: " + l_Player.getName() + "; armies assigned are: " + l_Player.getReinforcementArmies());
                    System.out.println(Constants.ELIGIBLE_NATIONS_ARMY);
                    d_GameEventLogger.logEvent(Constants.ELIGIBLE_NATIONS_ARMY);
                    for (Country l_CapturedCountry : l_Player.getCapturedCountries()) {
                        System.out.println(l_CapturedCountry.getCountryId() + " ");
                    }
                    System.out.println(Constants.SEPERATER);
                    d_IssueOrderCommand = getCommandFromPlayer();
                    l_Player.issueOrder(d_IssueOrderCommand);
                }
            }
        }
        System.out.println(Constants.ARMY_DEPLETED);
        d_GameEventLogger.logEvent(Constants.ARMY_DEPLETED);
        System.out.println(Constants.SEPERATER);
        return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
    }

    /**
     * It will get command from CMD
     *
     * @return : Command
     */
    private String getCommandFromPlayer() {
        String l_Command = "";
        System.out.println(Constants.ISSUE_COMMAND_MESSAGE);
        d_GameEventLogger.logEvent(Constants.ISSUE_COMMAND_MESSAGE);
        Constants.showIssueOrderCommand();
        while (!l_Command.equals(Constants.EXIT)) {
            l_Command = d_Scanner.nextLine();
            if (Constants.LIST_COMMANDS.contains(l_Command.split(" ")[0])) {
                return l_Command;
            } else {
                System.out.println(Constants.INVALID_COMMAND);
                d_GameEventLogger.logEvent(Constants.INVALID_COMMAND);
            }
        }
        return l_Command;
    }

}
