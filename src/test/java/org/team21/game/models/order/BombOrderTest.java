package org.team21.game.models.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Bomb Order.
 * It includes tests for executing the order, validating the command, and checking if the player has the bomb card.
 * The tests cover scenarios where the target country belongs to the same player and when it does not.
 * Additionally, it verifies that the player has the bomb card before executing the command.
 *
 * @author Tejasvi
 */
public class BombOrderTest {
    GameMap gameMap;
    List<Country> player1Countries;
    List<Country> player2Countries;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();

        gameMap.addContinent("Asia", "10");
        gameMap.addContinent("Africa", "20");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("Zambia", "Africa");
        gameMap.addNeighbor("India", "Zambia");
        gameMap.addNeighbor("Zambia", "India");
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        gameMap.assignCountries();
        for (Player player : gameMap.getPlayers().values()) {
            player.calculateReinforcementArmies(gameMap);
        }
        player1Countries = gameMap.getPlayer("Player1").getCapturedCountries();
        player2Countries = gameMap.getPlayer("Player2").getCapturedCountries();
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        gameMap.flushGameMap();
    }

    /**
     * Test case to execute the bomb command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        Player player = gameMap.getPlayer("Player2");
        player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.Commands = "bomb " + player1Countries.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().execute());
    }

    /**
     * Test the validation of the bomb command.
     * Verifies that the command is valid when the target country belongs to the other player.
     */
    @Test
    public void testCommandValidationForValidTargetCountry() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.Commands = "bomb " + player2Countries.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the player has the bomb card.
     * Verifies that the command is invalid if the player does not have the bomb card.
     */
    @Test
    public void testNoBombCard() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "bomb " + player2Countries.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertFalse(player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the target country belongs to the player.
     * Verifies that the command is invalid if the target country belongs to the player.
     */
    @Test
    public void testInvalidTargetCountry() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.Commands = "bomb " + player1Countries.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertFalse(player.nextOrder().validateCommand());
    }
}
