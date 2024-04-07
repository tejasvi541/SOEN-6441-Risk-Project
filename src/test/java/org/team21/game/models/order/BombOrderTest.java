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
    GameMap d_gameMap;
    List<Country> d_player1Countries;
    List<Country> d_player2Countries;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = GameMap.getInstance();
        d_gameMap.flushGameMap();

        d_gameMap.addContinent("Asia", "10");
        d_gameMap.addContinent("Africa", "20");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("Zambia", "Africa");
        d_gameMap.addNeighbor("India", "Zambia");
        d_gameMap.addNeighbor("Zambia", "India");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.assignCountries();
        for (Player player : d_gameMap.getPlayers().values()) {
            player.calculateReinforcementArmies(d_gameMap);
        }
        d_player1Countries = d_gameMap.getPlayer("Player1").getCapturedCountries();
        d_player2Countries = d_gameMap.getPlayer("Player2").getCapturedCountries();
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Test case to execute the bomb command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        Player l_player = d_gameMap.getPlayer("Player2");
        l_player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.d_Commands = "bomb " + d_player1Countries.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertTrue(l_player.nextOrder().execute());
    }

    /**
     * Test the validation of the bomb command.
     * Verifies that the command is valid when the target country belongs to the other player.
     */
    @Test
    public void testCommandValidationForValidTargetCountry() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.d_Commands = "bomb " + d_player2Countries.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertTrue(l_player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the player has the bomb card.
     * Verifies that the command is invalid if the player does not have the bomb card.
     */
    @Test
    public void testNoBombCard() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.d_Commands = "bomb " + d_player2Countries.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertFalse(l_player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the target country belongs to the player.
     * Verifies that the command is invalid if the target country belongs to the player.
     */
    @Test
    public void testInvalidTargetCountry() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrderController.d_Commands = "bomb " + d_player1Countries.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertFalse(l_player.nextOrder().validateCommand());
    }
}
