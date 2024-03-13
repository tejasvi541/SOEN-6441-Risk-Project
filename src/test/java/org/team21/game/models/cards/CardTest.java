package org.team21.game.models.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    private Card d_CardType;

    @Test
    public void getCard() {
        d_CardType = new Card(CardType.BLOCKADE);
        assertEquals(d_CardType.getCard(), CardType.BLOCKADE);
    }

    @Test
    public void setCard() {
        d_CardType = new Card();
        d_CardType.setCard(CardType.BOMB);
        assertEquals(d_CardType.getCard(), CardType.BOMB);
    }

    @Test
    public void testEquals() {
        Card d_CardType1 = new Card(CardType.AIRLIFT);
        Card d_CardType2 = new Card(CardType.AIRLIFT);
        Card d_CardType3 = new Card(CardType.DIPLOMACY);

        assertEquals(d_CardType1, d_CardType2);
        assertNotEquals(d_CardType1, d_CardType3);
    }
}