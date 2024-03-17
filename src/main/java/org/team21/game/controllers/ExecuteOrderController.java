package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.orders.Order;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.HashMap;

/**
 * TheExecute order controller will execute orders based on {IssueOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class ExecuteOrderController implements GameFlowManager {

    /**
     * The d_UpcomingGamePhase is used to get next game phase.
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.ExitGame;
    /**
     * Created object d_GameEventLogger of GameEventLogger.
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();
    /**
     * GameMap instance
     */
    GameMap d_GameMap;

    /**
     * This is the default constructor for this constructor to get
     */
    public ExecuteOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This method starts the current game phase
     *
     * @param p_CurrentPhase the current game phase
     * @return the next game phase
     */
    @Override
    public GamePhase start(GamePhase p_CurrentPhase) {
        d_GameEventLogger.logEvent(Constants.EXECUTE_ORDER_PHASE);
        return run(p_CurrentPhase);
    }

    /**
     * run is entry method of Execute Order and it will run Execute order
     *
     * @param p_CurrentGamePhase : Current phase of game.
     * @return : It will return game phase to go next
     */
    private GamePhase run(GamePhase p_CurrentGamePhase) {
        //Execute all orders and if it fails
        executeOrders();
        clearAllNeutralPlayers();
        return checkIfPlayerWon(p_CurrentGamePhase, d_UpcomingGamePhase);
    }

    /**
     * This method executes each order in the order list
     */
    private void executeOrders() {
        int l_Counter = 0;
        while (l_Counter < d_GameMap.getPlayers().size()) {
            l_Counter = 0;
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                Order l_Order = l_Player.nextOrder();
                if (l_Order == null) {
                    l_Counter++;
                } else {
                    if (l_Order.execute()) {
                        l_Order.printOrderCommand();
                    }
                }
            }
        }
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
     * @param p_CurrentGamePhase : the current phase of game command
     * @param p_GamePhase        : the next phase based on the status of player
     * @return : the gamePhase it has to change to based on the win
     */
    public GamePhase checkIfPlayerWon(GamePhase p_CurrentGamePhase, GamePhase p_GamePhase) {
        HashMap<String, Country> l_ListOfAllCountries = d_GameMap.getCountries();
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            if (l_Player.getCapturedCountries().size() == d_GameMap.getCountries().size()) {
                System.out.println("The Player " + l_Player.getName() + " won the game.");
                d_GameEventLogger.logEvent("The Player " + l_Player.getName() + " won the game.");
                System.out.println("Exiting the game...");
                d_GameEventLogger.logEvent("Exiting the game...");
                return p_CurrentGamePhase.nextState(d_UpcomingGamePhase);
            }
        }
        return p_CurrentGamePhase.nextState(GamePhase.Reinforcement);
    }
}
