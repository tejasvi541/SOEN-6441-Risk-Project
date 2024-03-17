package org.team21.game.models.orders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.GameMap;

import static org.junit.Assert.*;

/**
 * Test class for Negotiate Order
 */
public class NegotiateOrderTest {
    /**
     * Created gamemap instance.
     */
    GameMap d_GameMap;

    /***
     * Gamemap set up for the test cases.
     *
     * @throws Exception in case of any exception.
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
    }

    /**
     * To clear the gmaemap instance.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.clearMap();
    }

    /**
     * Test to check executes works perfectly.
     */
    @Test
    public void execute() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_IssueOrderCommand = "negotiate Player2";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test to validate the CardType
     */
    @Test
    public void validateCardTrue() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        assertTrue(l_Player.checkIfCardAvailable(CardType.DIPLOMACY));
    }

    /**
     * Test to validate the CardType
     */
    @Test
    public void validateCardFalse() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BOMB));
        assertFalse(l_Player.checkIfCardAvailable(CardType.DIPLOMACY));
    }

    /**
     * Test to validate the negotiation command.
     */
    @Test
    public void validateCommandTrue() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_IssueOrderCommand = "negotiate Player2";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test to validate the negotiation command.
     */
    @Test
    public void validateCommandFalse() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_IssueOrderCommand = "negotiate Player4";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}