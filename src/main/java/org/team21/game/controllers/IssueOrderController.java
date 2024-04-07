package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameProgress;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.cards.Card;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.models.order.Order;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.*;

/**
 * Manages the Issue Order phase of the game. Responsible for player turns,
 * command input, validation, and transitioning to the next game phase.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class IssueOrderController implements GameFlowManager {
    /**
     * Holds the current command input by the player.
     */
    public static String d_Commands = null;
    /**
     * Set to track players who have skipped their order-issuing turn.
     */
    private static Set<Player> d_SkippedPlayers = new HashSet<>();
    /**
     * GamePhase instance representing the Execute Order phase.
     */
    GamePhase d_ExecutePhase = GamePhase.ExecuteOrder;

    /**
     * GamePhase instance representing the Map Editor phase.
     */
    GamePhase d_MapEditorPhase = GamePhase.MapEditor;

    /**
     * Tracks the current GamePhase.
     */
    GamePhase d_GamePhase;

    /**
     * Holds the central GameMap object.
     */
    GameMap d_GameMap;

    /**
     * Used for logging game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Initializes the IssueOrderController, obtaining the GameMap instance.
     */

    public IssueOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * A function to map the players and their status for the issuing of the order.
     *
     * @param p_Player The player who has skipped his iteration for the issuing.
     */
    private static void addToSetOfPlayers(Player p_Player) {
        d_SkippedPlayers.add(p_Player);
    }

    /**
     * A function to check the length of each command.
     *
     * @param p_Command The command to be validated.
     * @return true if length is correct else false.
     */
    private static boolean checkLengthOfIssueOrderCommand(String p_Command, int p_Length) {
        if (p_Command.contains(Constants.DEPLOY_COMMAND)) {
            return p_Length == 3;
        } else if (p_Command.contains(Constants.BOMB_COMMAND) || p_Command.contains(Constants.BLOCKADE_COMMAND) || p_Command.contains(Constants.NEGOTIATE_COMMAND) || p_Command.contains(Constants.SAVEGAME_COMMAND)) {
            return (p_Length == 2);
        } else if (p_Command.contains(Constants.AIRLIFT_COMMAND) || p_Command.contains(Constants.ADVANCE_COMMAND)) {
            return (p_Length == 4);
        }
        return false;
    }

    /**
     * A function to start the issue order phase
     *
     * @param p_GamePhase The current phase which is executing
     * @return the next phase to be executed
     * @throws Exception when execution fails
     */
    @Override
    public GamePhase startPhase(GamePhase p_GamePhase) throws Exception {
        if (d_GameMap.getCurrentPlayer() == null) {
            d_GameMap.setCurrentPlayer(d_GameMap.getPlayers().entrySet().iterator().next().getValue());
        }
        d_GamePhase = p_GamePhase;
        while (!(d_SkippedPlayers.size() == d_GameMap.getPlayers().size())) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if ((d_GameMap.getGameLoaded() && !(l_Player.getName().equalsIgnoreCase(d_GameMap.getCurrentPlayer().getName())))) {
                    continue;
                }
                if (!d_SkippedPlayers.isEmpty() && d_SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                d_GameMap.setGameLoaded(false);
                d_GameMap.setCurrentPlayer(l_Player);
                boolean l_IssueCommand = false;
                while (!l_IssueCommand) {
                    showPlayerStatusAndCommands(l_Player);
                    d_Commands = l_Player.readFromPlayer();
                    if (Objects.isNull(d_Commands)) {
                        d_Commands = "";
                    }
                    if (!d_Commands.isEmpty()) {
                        l_IssueCommand = validateIssueOrderCommands(d_Commands, l_Player);
                    }
                    if (d_Commands.equals(Constants.PASS_COMMAND)) {
                        break;
                    }
                    if (d_Commands.split(" ")[0].equals(Constants.SAVEGAME_COMMAND) && l_IssueCommand) {
                        d_GameMap.setGamePhase(d_MapEditorPhase);
                        return d_MapEditorPhase;
                    }
                }
                if (!d_Commands.equals(Constants.PASS_COMMAND)) {
                    d_Logger.log(l_Player.getName() + " has issued this order :- " + d_Commands);
                    l_Player.issueOrder();
                    d_Logger.log(Constants.All_ORDERS_ADDED);
                    d_Logger.log(Constants.EQUAL_SEPERATER);
                }
            }
            d_GameMap.setGameLoaded(false);
        }
        d_SkippedPlayers.clear();
        d_GameMap.setGamePhase(d_ExecutePhase);
        return d_ExecutePhase;
    }

    /**
     * A static function to validate the deploy command
     * It will check all the commands validation
     * as a first entry point validation for Issue Order
     *
     * @param p_CommandArr The string entered by the user
     * @param p_Player     the player object
     * @return true if the command is correct else false
     */
    public boolean validateIssueOrderCommands(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList(Constants.DEPLOY_COMMAND, Constants.ADVANCE_COMMAND, Constants.BOMB_COMMAND, Constants.BLOCKADE_COMMAND, Constants.AIRLIFT_COMMAND, Constants.NEGOTIATE_COMMAND, "savegame");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains("pass")) {
            addToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            d_Logger.log(Constants.THE_COMMAND_SYNTAX_IS_INVALID + p_CommandArr);
            return false;
        }
        if (!checkLengthOfIssueOrderCommand(l_CommandArr[0], l_CommandArr.length)) {
            d_Logger.log(Constants.THE_COMMAND_SYNTAX_IS_INVALID + p_CommandArr);
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()) {
            case Constants.DEPLOY_COMMAND:
                try {
                    Integer.parseInt(l_CommandArr[2]);
                } catch (NumberFormatException l_Exception) {
                    d_Logger.log(Constants.THE_NUMBER_FORMAT_IS_INVALID);
                    return false;
                }
                if (Integer.parseInt(l_CommandArr[2]) < 0) {
                    d_Logger.log(Constants.THE_NUMBER_FORMAT_IS_INVALID);
                    return false;
                }
                break;
            case Constants.ADVANCE_COMMAND:
                try {
                    Integer.parseInt(l_CommandArr[3]);
                } catch (NumberFormatException l_Exception) {
                    d_Logger.log(Constants.THE_NUMBER_FORMAT_IS_INVALID);
                    return false;
                }
                break;
            case Constants.SAVEGAME_COMMAND:
                d_Logger.log("Are you sure you want to save the file? Enter Yes/No.");
                String l_Input = new Scanner(System.in).nextLine();
                if (l_Input.equalsIgnoreCase("Yes")) {
                    GameProgress.SaveGameProgress(d_GameMap, l_CommandArr[1]);
                    return true;
                } else {
                    d_Logger.log("The game has not been saved, continue to play.");
                    return false;
                }
            default:
                break;
        }
        return true;
    }

    /**
     * A function to show the player the status while issuing the order.
     *
     * @param p_Player The current player object.
     */
    public void showPlayerStatusAndCommands(Player p_Player) {
        d_Logger.log("-----------------------------------------------------------------------------------------");
        d_Logger.log("List of game loop commands");
        d_Logger.log("To deploy the armies : deploy countryID numarmies");
        d_Logger.log("To advance/attack the armies : advance countrynamefrom countynameto numarmies");
        d_Logger.log("To airlift the armies : airlift sourcecountryID targetcountryID numarmies");
        d_Logger.log("To blockade the armies : blockade countryID");
        d_Logger.log("To negotiate with player : negotiate playerID");
        d_Logger.log("To bomb the country : bomb countryID");
        d_Logger.log("To skip: pass");
        d_Logger.log("-----------------------------------------------------------------------------------------");
        String l_Table = "|%-15s|%-19s|%-22s|%n";
        System.out.format("+--------------+-----------------------+------------------+%n");
        System.out.format("| Current Player   | Initial Assigned  | Left Armies      | %n");
        System.out.format("+---------------+------------------  +---------------------+%n");
        System.out.format(l_Table, p_Player.getName(), p_Player.getReinforcementArmies(), p_Player.getIssuedArmies());
        System.out.format("+--------------+-----------------------+------------------+%n");

        d_Logger.log("The countries assigned to the player are: ");
        System.out.format("+--------------+-----------------------+------------------+---------+%n");

        System.out.format(
                "|Country name  |Country Armies  | Neighbors                         |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+---------+%n");
        for (Country l_Country : p_Player.getCapturedCountries()) {
            String l_TableCountry = "|%-15s|%-15s|%-35s|%n";
            String l_NeighborList = "";
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                l_NeighborList += l_Neighbor.getName() + "-";
            }
            System.out.format(l_TableCountry, l_Country.getName(), l_Country.getArmies(), l_Country.createANeighborList(l_Country.getNeighbors()));
        }
        System.out.format("+--------------+-----------------------+------------------+---------+\n");

        d_Logger.log(Constants.CARDS_OF_PLAYER);
        if (!p_Player.getPlayerCards().isEmpty()) {
            for (Card l_Card : p_Player.getPlayerCards()) {
                d_Logger.log(l_Card.getCardType().toString());
            }
        }
        if (!p_Player.getOrders().isEmpty()) {
            d_Logger.log("The Orders issued by Player " + p_Player.getName() + " are:");
            for (Order l_Order : p_Player.getOrders()) {
                d_Logger.log(l_Order.getOrderInfo().getCommand());
            }
        }
    }
}