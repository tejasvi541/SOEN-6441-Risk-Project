package org.team21.game.interfaces.game;

import org.team21.game.game_engine.GamePhase;

/**
 * The Engine interface defines the contract for classes responsible for controlling the game engine.
 * It provides methods to start the game and set the current game phase.
 * This interface is implemented by classes that manage the flow and execution of game actions.
 *
 * @author Kapil Soni
 * @version 1.0.1
 */
public interface Engine {
    /**
     * Starts the game engine.
     *
     * @throws Exception if an error occurs during game initialization or execution.
     */
    void startEngine() throws Exception;

    /**
     * Sets the current game phase.
     *
     * @param p_GamePhase the game phase to be set.
     */
    void setGamePhase(GamePhase p_GamePhase);
}