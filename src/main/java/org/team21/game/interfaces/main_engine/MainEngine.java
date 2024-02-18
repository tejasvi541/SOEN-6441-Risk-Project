package org.team21.game.interfaces.main_engine;

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
