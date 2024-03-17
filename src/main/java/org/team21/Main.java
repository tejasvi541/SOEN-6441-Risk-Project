package org.team21;
import org.team21.game.game_engine.GameEngine;
import org.team21.game.utils.Constants;

/**
 * The Main will call the GameEngine, and it will start the MapEditor.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(Constants.WELCOME_MESSAGE);

        new GameEngine().start();
    }
}