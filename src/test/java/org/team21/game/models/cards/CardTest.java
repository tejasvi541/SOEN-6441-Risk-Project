package org.team21.game.models.cards;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This Class Provides test for the Card Class
 */
public class CardTest {

    private Card d_CardType;

    /**
     * Get Card Function Test
     */
    @Test
    public void getCard() {
        d_CardType = new Card(CardType.BLOCKADE);
        assertEquals(d_CardType.getCard(), CardType.BLOCKADE);
    }

    /**
     * Set Card Function Test
     */
    @Test
    public void setCard() {
        d_CardType = new Card();
        d_CardType.setCard(CardType.BOMB);
        assertEquals(d_CardType.getCard(), CardType.BOMB);
    }

    /**
     * Test if cards are equal or not
     */
    @Test
    public void testEquals() {
        Card d_CardType1 = new Card(CardType.AIRLIFT);
        Card d_CardType2 = new Card(CardType.AIRLIFT);
        Card d_CardType3 = new Card(CardType.DIPLOMACY);

        assertEquals(d_CardType1, d_CardType2);
        assertNotEquals(d_CardType1, d_CardType3);
    }
}