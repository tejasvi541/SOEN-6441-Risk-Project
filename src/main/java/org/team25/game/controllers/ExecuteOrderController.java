package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.models.map.GameMap;
import org.team25.game.models.orders.Order;

import static org.team25.game.models.game_play.Player.OrderList;

//Todo refactor
public class ExecuteOrderController implements GameFlowManager {


    GamePhase d_NextGamePhase = GamePhase.Reinforcement;
    GamePhase d_GamePhase = GamePhase.ExecuteOrder;
    GameMap d_GameMap;

    /**
     * This is the default constructor
     *
     */
    public ExecuteOrderController(){
        //Todo get game map instance
        // d_GameMap = GameMap.getInstance();
    }
    /**
     * This method starts the current game phase
     *
     * @param p_GamePhase the current game phase
     * @return the next game phase
     * @throws Exception when execution fails
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        if(ExecuteOrders()){
            System.out.println("All the orders have been executed successfully");
        }
        else{
            System.out.println("Could not execute the orders.");
        }
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    /**
     * This method executes each order in the order list
     *
     * @return true if execution is successful
     */
    private boolean ExecuteOrders()
    {
        for (Order l_Order : OrderList){
            if(!l_Order.execute()){
                return false;
            }
        }
        return true;
    }
}
