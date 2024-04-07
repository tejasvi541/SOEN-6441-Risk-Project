package org.team21.game.utils.logger;

import org.team21.game.interfaces.observers.Observer;

import java.io.Serializable;


/**
 * The GameConsoleWriter class enables writing messages to the console using the observer pattern.
 * It implements the Observer interface to receive updates and provides functionality to clear logs.
 * This class acts as an observer, listening for notifications and printing messages to the console accordingly.
 * It can also clear the console logs for better readability.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class GameConsoleWriter implements Observer, Serializable {

    /**
     * Updates the console with the provided message.
     *
     * @param p_s The message to be displayed on the console.
     */
    @Override
    public void update(String p_s) {
        System.out.println(p_s);
    }

    /**
     * Clears the console logs by resetting the console screen.
     */
    @Override
    public void clearGameLogs() {
        System.out.print("\033[H\033[2J"); // ANSI escape sequence to clear console screen
    }
}