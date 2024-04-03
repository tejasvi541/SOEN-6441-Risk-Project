package org.team21.game.interfaces.game;

import org.team21.game.game_engine.GamePhase;

/**
 * interface for Engine classes
 * @author Kapil Soni
 */
public interface Engine {
    /**
     * start method declaration
     *
     * @throws Exception if it occurs
     */
    void start() throws Exception;

    /**
     * method declaration to set game phase
     * @param p_GamePhase the game phase
     */
    void setGamePhase(GamePhase p_GamePhase);
}
