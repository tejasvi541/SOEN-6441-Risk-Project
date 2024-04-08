package org.team21.game.models.strategy.player;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Implements the Human player strategy, allowing command inputs from a human player.
 * This strategy simply reads the user's input from the console.
 *
 * @author Yesha Shah
 * @version 1.0.0
 */
public class HumanStrategy extends PlayerStrategy implements Serializable {

    /**
     * Scanner object to read input from the user
     */
    private final static Scanner SCANNER = new Scanner(System.in);

    /**
     * Default constructor for HumanStrategy class.
     */
    public HumanStrategy() {

    }

    /**
     * Reads and returns the next input provided by the human player.
     *
     * @return The next input provided by the human player.
     */
    @Override
    public String createCommand() {
        return SCANNER.nextLine();
    }

}
