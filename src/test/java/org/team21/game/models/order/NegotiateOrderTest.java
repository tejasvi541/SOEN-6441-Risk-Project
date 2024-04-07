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
    GameMap gameMap;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();
        gameMap.flushGameMap();
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        gameMap.flushGameMap();
    }

    /**
     * Test case to execute the negotiate command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.Commands = "negotiate Player2";
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().execute());
    }

    /**
     * Test the validation of the negotiate command.
     * Verifies that the command is valid when the specified player exists.
     */
    @Test
    public void testCommandValidationForValidPlayer() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.Commands = "negotiate Player2";
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Test the validation of the negotiate command.
     * Verifies that the command is invalid when the specified player does not exist.
     */
    @Test
    public void testInvalidPlayer() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrderController.Commands = "negotiate Player3";
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertFalse(player.nextOrder().validateCommand());
    }
}
