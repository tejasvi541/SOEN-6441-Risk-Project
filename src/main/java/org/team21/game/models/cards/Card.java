package org.team21.game.models.cards;

import java.util.Objects;

/**
 * This is the Card class use to set Cards and get random cards
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class Card {
    /**
     * Private data member to hold the card type.
     */
    private CardType d_CardType;

    /**
     * Constructor of card class, initialise the d_CardType.
     */
    public Card() {
        d_CardType = CardType.getRandomCard();
    }

    /**
     * Parameterized Card constructor to populate d_CardType with desired card type.
     *
     * @param p_CardType The card type
     */
    public Card(CardType p_CardType) {
        d_CardType = p_CardType;
    }


    /**
     * Getter method for card type.
     *
     * @return the Card Type
     */
    public CardType getCardType() {
        return d_CardType;
    }

    /**
     * Setter method for Card type
     *
     * @param p_cardType The card type
     */
    public void setCardType(CardType p_cardType) {
        d_CardType = p_cardType;
    }

    /**
     * Method to check 2 cards are equal or not.
     *
     * @param p_Obj The object which will get compared with  the card
     * @return true if cards are equal else false
     */
    @Override
    public boolean equals(Object p_Obj) {
        if (this == p_Obj) return true;
        if (!(p_Obj instanceof Card)) return false;
        Card l_Card = (Card) p_Obj;
        return d_CardType == l_Card.d_CardType;
    }

    /**
     * Method to generate hash code for card type and return..
     *
     * @return hash code object
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_CardType);
    }
}

