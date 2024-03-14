package org.team21.game.models.cards;
import java.util.Objects;

/**
 * This is the Card class use to set Cards and get random cards
 * @author Tejasvi
 */
public class Card {
    /**
     * Private data member holding the card type
     */
    private CardType d_CardType;

    /**
     * Card class constructor, initialise the d_CardType.
     */
    public Card(){
        d_CardType = CardType.getRandomCard();
    }

    /**
     * Parameterised Constructor to populate d_CardType member with desired card
     *
     * @param p_cardType The card type
     */
    public Card(CardType p_cardType) {
        d_CardType = p_cardType;
    }

    /**
     * Getter function for CarD
     * @return current Card
     */
    public CardType getCard(){
        return this.d_CardType;
    }

    /**
     * Setter function of d_CardType member
     * @param p_CardType Object
     */
    public void setCard(CardType p_CardType){
        this.d_CardType = p_CardType;
    }

    /**
     * This method returns the hashcode of the object
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_CardType);
    }

    /**
     * This method checks if 2 cards are equal or not
     * @param p_cardObj The object with which card is going to be compared
     * @return true/false depending on equality
     */
    @Override
    public boolean equals(Object p_cardObj){
        if(this==p_cardObj)return true;
        if(!(p_cardObj instanceof Card l_Card)) return false;
        return d_CardType == l_Card.d_CardType;
    }

}
