package org.team21.game.game_engine;

import org.team21.game.interfaces.game.GameStrategy;
import org.team21.game.models.strategy.game.DefaultStrategy;
import org.team21.game.models.strategy.game.DiceStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class containing the different strategy settings for advance order calculation.
 *
 * @author Yesha Shah
 */
public class GameSettings {
    /**
     * Game settings object
     */
    private static GameSettings d_Settings;
    /**
     * The Attacker probability
     */
    public final double ATTACKER_PROBABILITY = 60 / 100d;
    /**
     * The Defender probability
     */
    public final double DEFENDER_PROBABILITY = 70 / 100d;
    /**
     * Max tries
     */
    public int MAX_TRIES;
    /**
     * Game strategy object
     */
    private GameStrategy d_Strategy;

    /**
     * Constructor for Game Settings
     */
    private GameSettings() {
    }

    /**
     * Method to get the instance of GameSettings
     *
     * @return game settings object
     */
    public static GameSettings getInstance() {
        if (Objects.isNull(d_Settings)) {
            d_Settings = new GameSettings();
        }
        return d_Settings;
    }

    /**
     * Getter for strategy
     *
     * @return the game strategy chosen
     */
    public GameStrategy getStrategy() {
        return d_Strategy;
    }

    /**
     * Setter for game strategy with settings
     *
     * @param p_Strategy the game strategy chosen
     */
    public void setStrategy(String p_Strategy) {
        List<String> l_Strategy = Arrays.asList("dice", "default");
        if (l_Strategy.contains(p_Strategy)) {
            switch (p_Strategy) {
                case "dice":
                    this.d_Strategy = new DiceStrategy();
                    break;
                case "default":
                    this.d_Strategy = new DefaultStrategy();

            }
        } else
            throw new IllegalStateException("Game strategy " + p_Strategy + " not available in " + l_Strategy);
    }

    /**
     * Setter for game strategy
     *
     * @param p_Strategy the game strategy chosen
     */
    public void setStrategy(GameStrategy p_Strategy) {
        this.d_Strategy = p_Strategy;
    }

}
