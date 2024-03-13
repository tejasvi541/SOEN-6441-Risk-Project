package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.orders.Order;
import org.team21.game.utils.Constants;

import java.util.HashMap;

import static org.team21.game.models.game_play.Player.d_PlayerOrderList;

/**
 * TheExecute order controller will execute orders based on {IssueOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class ExecuteOrderController implements GameFlowManager {

    /**
     * GameMap instance
     */
    GameMap d_GameMap;
    /**
     * The d_UpcomingGamePhase is used to get next game phase.
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.ExitGame;

    /**
     * This is the default constructor for this constructor to get
     */
    public ExecuteOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This method starts the current game phase
     *
     * @param p_CurrentGamePhase the current game phase
     * @return the next game phase
     */
    @Override
    public GamePhase start(GamePhase p_CurrentGamePhase) {
        return run(p_CurrentGamePhase);
    }

    /**
     * run is entry method of Execute Order and it will run Execute order
     *
     * @param p_CurrentGamePhase : Current phase of game.
     * @return : It will return game phase to go next
     */
    private GamePhase run(GamePhase p_CurrentGamePhase) {
        /**
         * The d_CurrentGamePhase is used to know current game phase.
         */

        //Execute all orders and if it fails
        if (executeOrders()) {
            System.out.println(Constants.EXECUTE_ORDER_SUCCESS);
        } else {
            System.out.println(Constants.EXECUTE_ORDER_FAIL);
        }
        return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
    }

    /**
     * This method executes each order in the order list
     *
     * @return true if execution is successful
     */
    private boolean executeOrders() {
        while (!d_PlayerOrderList.isEmpty()) {
            Order l_PlayerOrder = Player.nextOrder();
            if (!l_PlayerOrder.execute()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method Clears the neutral players
     */
    private void clearAllNeutralPlayers() {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.removeNeutralPlayer();
        }
    }

    /**
     * Check if the player won the game after every execution phase
     *
     * @param p_GamePhase the next phase based on the status of player
     * @return the gamephase it has to change to based on the win
     */
    public GamePhase checkIfPlayerWon(GamePhase p_GamePhase) {
        HashMap<String, Country> l_ListOfAllCountries = d_GameMap.getCountries();
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            if (l_Player.getCapturedCountries().size() == d_GameMap.getCountries().size()) {
                System.out.println("The Player " + l_Player.getName() + " won the game.");
                System.out.println("Exiting the game...");
                return p_GamePhase.nextState(d_UpcomingGamePhase);
            }
        }
        return p_GamePhase.nextState(d_UpcomingGamePhase);
    }
}
