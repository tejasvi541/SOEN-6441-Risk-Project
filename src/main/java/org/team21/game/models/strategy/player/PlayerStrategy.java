package org.team21.game.models.strategy.player;

import org.team21.game.models.map.Player;

/**
 * Abstract class representing different types of player strategies.
 * This class serves as a base for various player strategy implementations.
 * It provides a method to create commands based on the strategy.
 * It also contains a static factory method to instantiate the appropriate strategy class.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public abstract class PlayerStrategy {

    /**
     * The current player.
     */
    static Player d_Player;

    /**
     * Default constructor for PlayerStrategy class.
     */
    PlayerStrategy() {

    }

    /**
     * Declares an abstract method to create a command based on the player's strategy.
     *
     * @return The command created based on the strategy.
     */
    public abstract String createCommand();

    /**
     * Static factory method to instantiate the appropriate strategy class based on the provided strategy type.
     *
     * @param p_Strategy The type of player strategy.
     * @return An instance of the respective strategy class.
     * @throws IllegalStateException if the provided strategy type is not valid.
     */
    public static PlayerStrategy getStrategy(String p_Strategy) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy();
            }
            case "random": {
                return new RandomStrategy();
            }
            case "benevolent": {
                return new BenevolentStrategy();
            }
            case "aggressive": {
                return new AggressiveStrategy();
            }
            case "cheater": {
                return new CheaterStrategy();
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
