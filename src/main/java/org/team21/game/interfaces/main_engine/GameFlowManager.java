package org.team21.game.interfaces.main_engine;

import org.team21.game.models.game_play.GamePhase;

/**
 * GameFlowManager will handle Phases of Games
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public interface GameFlowManager {
    /**
     * Starts the game controller.
     *
     * @param p_CurrentPhase The current phase of the game.
     * @return The next phase of the game.
     * @throws Exception If an issue occurs during execution.
     */
    GamePhase start(GamePhase p_CurrentPhase) throws Exception;
}