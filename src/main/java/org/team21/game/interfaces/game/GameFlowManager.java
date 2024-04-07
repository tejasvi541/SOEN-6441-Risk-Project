package org.team21.game.interfaces.game;

import org.team21.game.game_engine.GamePhase;

/**
 * The GameFlowManager interface defines the contract for controllers managing the flow of game phases.
 * Controllers implementing this interface must provide a {@code start} method to initiate and control the game flow.
 * This interface allows controllers to handle the progression from one game phase to the next.
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public interface GameFlowManager {
    /**
     * Initiates and controls the flow of the game based on the provided game phase.
     *
     * @param p_GamePhase The current game phase.
     * @return The next game phase after executing actions related to the current phase.
     * @throws Exception If an issue occurs during the execution of game actions.
     */
    GamePhase startPhase(GamePhase p_GamePhase) throws Exception;
}
