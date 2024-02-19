package org.team21.game.game_engine;

import org.team21.game.controllers.ShowMapController;
import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.interfaces.main_engine.MainEngine;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

import java.util.Objects;

/**
 * Game Engine is Entry point of the game.
 * It will connect ::
 * {ExecuteOrderController,IssueOrderController,ReinforcementController,StartGameController,MapEditorController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class GameEngine implements MainEngine {

    /**
     * Game phase instance representing the current state
     */
    private GamePhase d_GamePhase;

    /**
     * GameEngine instance initializer
     */
    public GameEngine() {
        configureEngine();
    }

    /**
     * Config engine
     */
    @Override
    public void configureEngine() {
        d_GamePhase = GamePhase.MapEditor;

    }

    /**
     * default method declaration for starting the game
     *
     */
    @Override
    public void start() {

        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameFlowManager l_GameFlowManager = d_GamePhase.getController();
                if (Objects.isNull(l_GameFlowManager)) {
                    throw new Exception(Constants.CONTROLLERS_NOT_FOUND);
                }
                d_GamePhase = l_GameFlowManager.start(d_GamePhase);
                System.out.println(Constants.SEPERATER);
                System.out.println("************************"+"The Current phase " + d_GamePhase + " Phase"+"***********************");
                System.out.println(Constants.SEPERATER);
                if(d_GamePhase.equals(GamePhase.ExitGame)){
                    new ShowMapController(GameMap.getInstance()).show();
                }
                start();
            }

        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }

    }
}
