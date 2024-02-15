package org.team25.game.game_engine;

import org.team25.game.interfaces.main_engine.MainEngine;
import org.team25.game.models.game_play.GamePhase;

public class GameEngine implements MainEngine {

    private static GameSetting d_GameSettings;

    /** Game phase instance representing the current state */
    private GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     *
     */
    @Override
    public void configureEngine() {
        GameEngine.super.configureEngine();
    }

    /**
     * default method declaration for starting the game
     *
     * @throws Exception if it occurs
     */
    @Override
    public void start() throws Exception {
        MainEngine.super.start();
    }

    /**
     * method declaration to set game phase
     *
     * @param p_gamePhase the game phase
     */
    @Override
    public void setGamePhase(GamePhase p_gamePhase) {

    }
}
