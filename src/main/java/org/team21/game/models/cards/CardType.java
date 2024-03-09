package org.team21.game.models.cards;
import java.util.Random;

/**
 * This is an enum for the cards to be held by the player
 * @author Tejasvi
 */
public enum CardType {
    /**
     * Bomb Card
     */
    BOMB,

    /**
     * Blockade Card
     */
    BLOCKADE,

    /**
     * Airlift Card
     */
    AIRLIFT,

    /**
     * Negotiate Card
      */
    DIPLOMACY;

    /**
     * This function will generate and return a random card name
     *
     * @return The random Card Name
     */
    public static CardType getRandomCard() {
        Random d_Random = new Random();
        return values()[d_Random.nextInt(values().length)];
    }
}
