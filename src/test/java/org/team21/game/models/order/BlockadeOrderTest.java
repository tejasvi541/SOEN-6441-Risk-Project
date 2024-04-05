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

    GameMap d_gameMap;
    List<Country> d_countryList1 = new ArrayList<Country>();
    List<Country> d_countryList2 = new ArrayList<Country>();

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = GameMap.getInstance();
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.addContinent("Asia", "5");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("Pakistan", "Asia");
        d_gameMap.addCountry("SriLanka", "Asia");
        d_gameMap.addCountry("Afganisthan", "Asia");
        d_gameMap.addCountry("Bangladesh", "Asia");
        d_gameMap.addCountry("Myanmar", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.assignCountries();
        d_countryList1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        d_countryList2 = d_gameMap.getPlayer("Player2").getCapturedCountries();
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Tests the execution of the blockade order.
     * Verifies that the command executes correctly.
     *
     */
    @Test
    public void testExecution() {
        Player player = d_gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + d_countryList1.get(0).getName();
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
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + d_countryList1.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), l_player);
        l_player.addOrder(l_order);
        assertTrue(l_player.nextOrder().validateCommand());
    }

    /**
     * Tests the validation of the blockade command when the target country does not belong to the player.
     * Verifies that the command is invalid in this scenario.
     *
     */
    @Test
    public void testCommandValidationForDifferentPlayer() {
        Player l_player1 = d_gameMap.getPlayer("Player1");
        l_player1.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.Commands = "blockade " + d_countryList2.get(0).getName();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), l_player1);
        l_player1.addOrder(l_order);
        assertFalse(l_player1.nextOrder().validateCommand());
    }
}
