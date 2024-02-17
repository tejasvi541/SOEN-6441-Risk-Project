package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.models.orders.Order;
import org.team25.game.utils.Constants;

import static org.team25.game.models.game_play.Player.d_PlayerOrderList;

/**
 * TheExecute order controller will execute orders based on {@linkplain IssueOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class ExecuteOrderController implements GameFlowManager {


    /**
     * The d_UpcomingGamePhase is used to get next game phase.
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.Reinforcement;

    /**
     * This is the default constructor for this constructor to get
     */
    public ExecuteOrderController() {
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
        if (ExecuteOrders()) {
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
    private boolean ExecuteOrders() {
        for (Order l_Order : d_PlayerOrderList) {
            if (!l_Order.execute()) {
                return false;
            }
        }
        return true;
    }
}
