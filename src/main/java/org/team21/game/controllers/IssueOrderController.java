package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * Constructor to get the GameMap instance
     */
    public IssueOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * findCommandLength will check and find the length of each command
     *
     * @param p_Command the command to be validated
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
     * @param p_CurrentGamePhase :  The current phase which is executing
     * @return : to return the next phase to be executed
     */

    @Override
    public GamePhase start(GamePhase p_CurrentGamePhase) {
        return run(p_CurrentGamePhase);
    }

    /**
     * run is entry method of Execute Order and it will run Issue order
     *
     * @param p_CurrentGamePhase : Current phase of game.
     * @return : It will return game phase to go next
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
                    System.out.println(Constants.SEPERATER);
                    return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
                }
                if (l_Player.getReinforcementArmies() != 0) {
                    System.out.println(Constants.SEPERATER);
                    System.out.println("Player: " + l_Player.getName() + "; armies assigned are: " + l_Player.getReinforcementArmies());
                    System.out.println(Constants.ELIGIBLE_NATIONS_ARMY);
                    for (Country l_CapturedCountry : l_Player.getCapturedCountries()) {
                        System.out.println(l_CapturedCountry.getCountryId() + " ");
                    }
                    System.out.println(Constants.SEPERATER);
                    String l_Commands = getCommandFromPlayer(l_Player);
                    l_Player.issueOrder(l_Commands);
                }
            }
        }
        System.out.println(Constants.ARMY_DEPLETED);
        System.out.println(Constants.SEPERATER);
        return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
    }

    /**
     * A function to read all the commands from player
     *
     * @return command entered by the player
     */
    private String getCommandFromPlayer(Player p_CurrentPlayer) {
        String l_Command = "";
        System.out.println(Constants.ISSUE_COMMAND_MESSAGE);
        Constants.showIssueOrderCommand();
        while (!l_Command.equals(Constants.EXIT)) {
            l_Command = d_Scanner.nextLine();
            if (Constants.LIST_COMMANDS.contains(l_Command.split(" ")[0])) {
                if (checkIfCommandIsContainsDeploy(l_Command.toLowerCase(), p_CurrentPlayer)) {
                    // Split the string based on consecutive whitespaces
                    String[] l_StringParts = l_Command.trim().split("\\s+");
                    return String.join(" ", l_StringParts);
                }else{
                    //Todo add validation of other commands
                    return l_Command;
                }
            } else {
                System.out.println(Constants.DEPLOY_COMMAND_MESSAGE);
            }
        }
        return l_Command;
    }

    /**
     * A function to validate if the command is correct
     *
     * @param p_Command The command entered by player
     * @return true if the format is valid else false
     */
    private boolean checkIfCommandIsContainsDeploy(String p_Command, Player p_CurrentPlayer) {
        boolean l_CapturedCountry = false;
        String[] l_CommandList;
        String commandString = p_Command.trim();

        // Split the string based on consecutive whitespaces
        l_CommandList = commandString.split("\\s+");

        if (l_CommandList.length == 3) {
            try {
                int l_Number = Integer.parseInt(l_CommandList[2].trim());
                if (l_Number <= 0) {
                    System.out.println(Constants.ARMIES_NON_ZERO);
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.ARMIES_NON_ZERO);
                return false;
            }
            for (Country l_Country : p_CurrentPlayer.getCapturedCountries()) {
                if (Objects.equals(l_CommandList[1].trim(), l_Country.getCountryId().toLowerCase())) {
                    l_CapturedCountry = true;
                    break;
                }
            }
            if (!l_CapturedCountry) {
                System.out.println(Constants.COUNTRIES_DOES_NOT_BELONG);
            }
            return (l_CommandList[0].equals(Constants.DEPLOY_COMMAND) && l_CapturedCountry);
        } else
            return false;
    }
}
