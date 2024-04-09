package org.team21.game.models.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;

import static org.junit.Assert.*;

/**
 * Test class for Negotiate Order.
 * It includes tests for executing the order and validating the command.
 * The tests cover scenarios where the command is valid and invalid based on the players involved.
 *
 * @author Tejasvi
 */
public class NegotiateOrderTest {
    GameMap d_gameMap;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = GameMap.getInstance();
        d_gameMap.flushGameMap();

        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Test case to execute the negotiate command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_Commands = "negotiate Player2";
        Order l_order = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertTrue(l_player.nextOrder().execute());
    }

    /**
     * Test the validation of the negotiate command.
     * Verifies that the command is valid when the specified player exists.
     */
    @Test
    public void testCommandValidationForValidPlayer() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_Commands = "negotiate Player2";
        Order l_order = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertTrue(l_player.nextOrder().validateCommand());
    }

    /**
     * Test the validation of the negotiate command.
     * Verifies that the command is invalid when the specified player does not exist.
     */
    @Test
    public void testInvalidPlayer() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.d_Commands = "negotiate Player3";
        Order l_order = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertFalse(l_player.nextOrder().validateCommand());
    }
}
