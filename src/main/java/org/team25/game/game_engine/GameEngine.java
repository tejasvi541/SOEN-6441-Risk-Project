package org.team25.game.game_engine;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.interfaces.main_engine.MainEngine;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.utils.validation.ValidationException;

import java.util.Objects;

public class GameEngine implements MainEngine {

    /**
     * Game phase instance representing the current state
     */
    private GamePhase d_GamePhase;

    /**
     * GameEngine instance initializer
     */
    public GameEngine(){
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
     * @throws Exception if it occurs
     */
    @Override
    public void start() {

        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameFlowManager l_GameFlowManager = d_GamePhase.getController();
                if(Objects.isNull(l_GameFlowManager)) {
                    throw new Exception("No controller associated to this phase/feature found.");
                }
                d_GamePhase = l_GameFlowManager.start(d_GamePhase);
                System.out.println("The current phase " + d_GamePhase + " Phase.");
                start();
            }
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }

    }

    /**
     * method declaration to set game phase
     *
     * @param p_gamePhase the game phase
     */
    @Override
    public void setGamePhase(GamePhase p_gamePhase) {
        //Todo set game phase
    }
}
