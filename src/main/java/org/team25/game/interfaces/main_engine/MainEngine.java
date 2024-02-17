package org.team25.game.interfaces.main_engine;

import org.team25.game.models.game_play.GamePhase;

/**
 * The interface Main engine.
 */
public interface MainEngine {

    /**
     * Configure engine.
     */
    default void configureEngine(){
        System.out.println("Engine configured.");
    }

    /**
     * default method declaration for starting the game
     *
     * @throws Exception if it occurs
     */
    default void start() throws Exception {
        // Default implementation for starting the game
        System.out.println("Game started.");
    }

}
