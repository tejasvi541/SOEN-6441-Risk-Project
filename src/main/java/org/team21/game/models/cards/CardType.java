package org.team21.game.models.cards;

import java.util.Random;

/**
 * Enum with different card types which will be held by the player.
 *
 * @author Kapil Soni
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
     * Diplomacy Card
     */
    DIPLOMACY;

    /**
     * Method to generate and return a random card name.
     *
     * @return The random Card Name
     */
    public static CardType getRandomCard() {
        Random l_Random = new Random();
        CardType l_Type = values()[l_Random.nextInt(values().length)];
        return l_Type;
    }
}