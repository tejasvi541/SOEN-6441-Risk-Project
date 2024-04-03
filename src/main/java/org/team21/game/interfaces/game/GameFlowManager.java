package org.team21.game.interfaces.game;

import org.team21.game.game_engine.GamePhase;

/**
 * Interface containing {@code start} method for the
 * controllers of the game phases to implement
 *
 * @version 1.0
 */

public interface GameFlowManager {
    /**
     * The start method of Game Controller
     *
     * @param p_GamePhase holding the current game phase
     * @return each phase return the next game phase after it
     * @throws Exception If an issue occurred
     */
    GamePhase start(GamePhase p_GamePhase) throws Exception;
}
