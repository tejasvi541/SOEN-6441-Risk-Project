package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.cards.Card;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The Issue order controller will execute orders and passes to {ExecuteOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class IssueOrderController implements GameFlowManager {
    /**
     * To check current issued order commands
     */
    public static String d_IssueOrderCommand = "";
    /**
     * This will indicate who has skipped the list
     */
    private static Set<Player> d_SkippedPlayers = new HashSet<>();
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
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @param p_Player     the player object
     * @return true if the command is correct else false
     */
    public static boolean validateCommand(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Constants.LIST_COMMANDS;
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains(Constants.PASS_COMMAND)) {
            addToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            System.out.println(Constants.INVALID_COMMAND);
            return false;
        }
        if (!findCommandLength(l_CommandArr[0], l_CommandArr.length)) {
            System.out.println(Constants.INVALID_COMMAND);
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()) {
            case Constants.DEPLOY_COMMAND:
                try {
                    Integer.parseInt(l_CommandArr[2]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println(Constants.INVALID_COMMAND);
                    return false;
                }
                break;
            case Constants.ADVANCE_COMMAND:
                if (l_CommandArr.length < 4) {
                    System.out.println(Constants.INVALID_COMMAND);
                    return false;
                }
                try {
                    Integer.parseInt(l_CommandArr[3]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println(Constants.INVALID_COMMAND);
                    return false;
                }

            default:
                break;

        }
        return true;
    }

    /**
     * A function to map the players and their status for the issuing of the order
     *
     * @param p_Player The player who has skipped his iteration for the issuing
     */
    private static void addToSetOfPlayers(Player p_Player) {
        d_SkippedPlayers.add(p_Player);
    }

    /**
     * A function to start the issue order phase
     *
     * @param p_CurrentPhase :  The current phase which is executing
     * @return : to return the next phase to be executed
     */

    @Override
    public GamePhase start(GamePhase p_CurrentPhase) {
        return run(p_CurrentPhase);
    }

    /**
     * Run method will execute IssueOrder logic
     *
     * @param p_CurrentGamePhase : Based on current gamephase next phase will come
     * @return : It will return next gamephase
     */
    private GamePhase run(GamePhase p_CurrentGamePhase) {
        d_GameEventLogger.logEvent(Constants.ISSUE_ORDER_PHASE);
        while (!(d_SkippedPlayers.size() == d_GameMap.getPlayers().size())) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if (!d_SkippedPlayers.isEmpty() && d_SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                System.out.println(Constants.ASSIGNED_COUNTRIES);
                for (Country l_Country : l_Player.getCapturedCountries()) {
                    System.out.println(l_Country.getCountryId() + " ");
                }
                if (!l_Player.getPlayerCards().isEmpty()) {

                    System.out.println(Constants.CARDS_OF_PLAYER);
                    for (Card l_Card : l_Player.getPlayerCards()) {
                        System.out.println(l_Card.getCard());
                    }
                }
                System.out.println(Constants.SEPERATER);
                boolean l_IssueCommand = false;

                while (!l_IssueCommand) {
                    d_IssueOrderCommand = getCommandFromPlayer();
                    l_IssueCommand = validateCommand(d_IssueOrderCommand, l_Player);
                    //Todo add logger
                    if (d_IssueOrderCommand.equals(Constants.PASS_COMMAND)) {
                        break;
                    }
                }
                if (!d_IssueOrderCommand.equals(Constants.PASS_COMMAND)) {
                    l_Player.issueOrder(d_IssueOrderCommand);

                    System.out.println(Constants.All_ORDERS_ADDED);
                    System.out.println(Constants.SEPERATER);
                }
            }
        }
        d_SkippedPlayers.clear();
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
        Constants.showIssueOrderCommand();
        l_Command = d_Scanner.nextLine();
        //Todo add validatio
        return l_Command;
    }

}
