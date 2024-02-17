package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.models.game_play.Player;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;
import org.team25.game.utils.Constants;

import java.util.Scanner;

/**
 * The Issue order controller will execute orders and passes to {@linkplain ExecuteOrderController}
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
    private final GamePhase d_UpcomingGamePhase = GamePhase.Reinforcement;
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
         * The d_CurrentGamePhase is used to know current game phase.
         */
        int l_PlayerCounts = 0;
        while (l_PlayerCounts < d_GameMap.getPlayers().size()) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if (l_Player.getReinforcementArmies() <= 0) {
                    l_PlayerCounts++;
                    continue;
                }
                System.out.println("Player:" + l_Player.getName() + "; armies assigned are: " + l_Player.getReinforcementArmies());
                System.out.println(Constants.ELIGIBLE_NATIONS_ARMY);
                for (Country l_CapturedCountry : l_Player.getCapturedCountries()) {
                    System.out.println(l_CapturedCountry.get_countryId() + " ");
                }
                System.out.println(Constants.SEPERATER);
                String l_DeployCommands = getCommandFromPlayer();
                l_Player.issueOrder(l_DeployCommands);
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
    private String getCommandFromPlayer() {
        String l_DeployCommand;
        System.out.println(Constants.ISSUE_COMMAND_MESSAGE);
        while (true) {
            l_DeployCommand = d_Scanner.nextLine();
            if (Constants.DEPLOY_COMMAND.equalsIgnoreCase(l_DeployCommand.split(" ")[0])) {
                if (checkIfCommandIsContainsDeploy(l_DeployCommand.toLowerCase())) {
                    return l_DeployCommand;
                }
            } else {
                System.out.println(Constants.DEPLOY_COMMAND_MESSAGE);
            }
        }
    }

    /**
     * A function to validate if the command is correct
     *
     * @param p_Command The command entered by player
     * @return true if the format is valid else false
     */
    private boolean checkIfCommandIsContainsDeploy(String p_Command) {
        String[] l_CommandList = p_Command.split(" ");
        if (l_CommandList.length == 3) {
            return l_CommandList[0].equals(Constants.DEPLOY_COMMAND);
        } else
            return false;
    }

}
