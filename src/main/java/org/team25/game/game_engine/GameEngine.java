package org.team25.game.game_engine;

import org.team25.game.controllers.*;
import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.interfaces.main_engine.MainEngine;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.utils.Constants;

import java.util.Objects;

/**
 * Game Engine is Entry point of the game.
 * It will connect ::
 * {@linkplain ExecuteOrderController,IssueOrderController,ReinforcementController,StartGameController,MapEditorController}
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
        d_GamePhase = GamePhase.StartUp;

    }

    /**
     * default method declaration for starting the game
     *
     * @throws Exception if it occurs
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
                System.out.println("The current phase " + d_GamePhase + " Phase.");
                start();
            }
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }

    }
}
