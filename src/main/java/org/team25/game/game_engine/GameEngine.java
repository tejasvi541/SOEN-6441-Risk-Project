package org.team25.game.game_engine;

import org.team25.game.interfaces.main_engine.MainEngine;
import org.team25.game.models.game_play.GamePhase;

public class GameEngine implements MainEngine {

    /**
     * Game phase instance representing the current state
     */
    private GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     *
     */
    @Override
    public void configureEngine() {
        //Todo config engine

    }

    /**
     * default method declaration for starting the game
     *
     * @throws Exception if it occurs
     */
    @Override
    public void start() throws Exception {

        //Todo start map editing

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
