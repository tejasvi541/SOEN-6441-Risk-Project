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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the Blockade Order.
 * It includes tests for executing the order and validating the command for different scenarios.
 * The scenarios include cases where the target country belongs to the same player and where it does not belong to the player.
 *
 * @author Tejasvi
 */
public class BlockadeOrderTest {

    GameMap gameMap;
    List<Country> countryList1 = new ArrayList<Country>();
    List<Country> countryList2 = new ArrayList<Country>();

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        gameMap.addContinent("Asia", "5");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("Pakistan", "Asia");
        gameMap.addCountry("SriLanka", "Asia");
        gameMap.addCountry("Afganisthan", "Asia");
        gameMap.addCountry("Bangladesh", "Asia");
        gameMap.addCountry("Myanmar", "Asia");
        gameMap.addCountry("China", "Asia");
        gameMap.assignCountries();
        countryList1 = gameMap.getPlayer("Player1").getCapturedCountries();
        countryList2 = gameMap.getPlayer("Player2").getCapturedCountries();
    }

    /**
     * Clears the instance after each test case.
     *
     * @throws Exception if an exception occurs during teardown
     */
    @After
    public void tearDown() throws Exception {
        gameMap.flushGameMap();
    }

    /**
     * Tests the execution of the blockade order.
     * Verifies that the command executes correctly.
     *
     */
    @Test
    public void testExecution() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + countryList1.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().execute());
    }

    /**
     * Tests the validation of the blockade command for when the target country belongs to the same player.
     * Verifies that the command is valid in this scenario.
     */
    @Test
    public void testCommandValidationForSamePlayer() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + countryList1.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Tests the validation of the blockade command when the target country does not belong to the player.
     * Verifies that the command is invalid in this scenario.
     *
     */
    @Test
    public void testCommandValidationForDifferentPlayer() {
        Player player1 = gameMap.getPlayer("Player1");
        player1.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + countryList2.get(0).getName();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order);
        assertFalse(player1.nextOrder().validateCommand());
    }
}
