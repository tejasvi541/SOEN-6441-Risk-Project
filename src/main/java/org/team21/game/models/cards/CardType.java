package org.team21.game.models.cards;

import java.util.Random;

/**
 * This is the enum class for card type
 * @author Kapil Soni
 */
public enum CardType {
    /**
     * Bomb Card Type
     */
    BOMB,
    /**
     * Blockade Card Type
     */
    BLOCKADE,
    /**
     * Airlift Card Type
     */
    AIRLIFT,
    /**
     * Diplomacy Card Type
     */
    DIPLOMACY;

    /**
     * This method assigns a Random Card from the Enum of Card types
     *
     * @return The random Card Name
     */
    public static CardType getRandomCard() {
        Random l_Random = new Random();
        CardType l_Type = values()[l_Random.nextInt(values().length)];
        return l_Type;
    }
}