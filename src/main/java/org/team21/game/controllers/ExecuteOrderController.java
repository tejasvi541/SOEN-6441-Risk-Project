package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.models.order.Order;
import org.team21.game.utils.logger.GameEventLogger;

/**
 * The ExecuteOrderController class implements the GameFlowManager interface and is responsible for executing orders
 * and managing the flow of the game.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class ExecuteOrderController implements GameFlowManager {
    /**
     * Reinforcement Phase enum keyword
     */
    GamePhase d_ReinforcementGamePhase = GamePhase.Reinforcement;
    /**
     * Exit Phase enum keyword
     */
    GamePhase d_ExitGamePhase = GamePhase.ExitGame;
    /**
     * GamePhase
     */
    GamePhase d_GamePhase;
    /**
     * GameMap instance
     */
    GameMap d_GameMap;
    /**
     * Log entry Buffer Object
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * This is the default constructor
     */
    public ExecuteOrderController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This method starts the current game phase
     *
     * @param p_GamePhase the current game phase
     * @return the next game phase
     * @throws Exception when execution fails
     */
    @Override
    public GamePhase startPhase(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        executeOrders();
        clearAllNeutralPlayers();
        return checkIfPlayerWonOrTriesExhausted(p_GamePhase);
    }

    /**
     * This method  executes each order in the order list
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
     * Or if the number of tries are exhausted
     *
     * @param p_GamePhase the next phase based on the status of player
     * @return the gamephase it has to change to based on the win
     */
    public GamePhase checkIfPlayerWonOrTriesExhausted(GamePhase p_GamePhase) {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            if (l_Player.getCapturedCountries().size() == d_GameMap.getCountries().size()) {
                d_Logger.log("The Player " + l_Player.getName() + " won the game.");
                d_Logger.log("Exiting the game...");
                d_GameMap.setWinner(l_Player);
                d_GameMap.setGamePhase(d_ExitGamePhase);
                d_GameMap.setWinner(l_Player);
                return p_GamePhase.nextState(d_ExitGamePhase);
            }
        }

        if (GameSettings.getInstance().MAX_TRIES > 0 && d_GameMap.getTries() >= GameSettings.getInstance().MAX_TRIES) {
            d_GameMap.setGamePhase(d_ExitGamePhase);
            return p_GamePhase.nextState(d_ExitGamePhase);
        }
        d_GameMap.setGamePhase(d_ReinforcementGamePhase);
        return p_GamePhase.nextState(d_ReinforcementGamePhase);
    }

}