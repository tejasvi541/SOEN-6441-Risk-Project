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
 * This class tests the Airlift Order
 * It includes tests for executing the order, validating the command, and checking the validation for different scenarios.
 *
 * The tests cover scenarios where the target country belongs to the same player, and where it does not belong to the player.
 *
 * @author Tejasvi
 */
public class AirliftOrderTest {
    GameMap gameMap;
    List<Country> countryList1 = new ArrayList<Country>();
    List<Country> countryList2 = new ArrayList<Country>();

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
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
     * Clear the instance
     *
     * @throws Exception in case of any exception
     */
    @After
    public void tearDown() throws Exception {
        gameMap.flushGameMap();
    }

    /**
     * Tests the execution of the airlift order.
     * Verifies that the command executes correctly.
     *
     */
    @Test
    public void testExecution() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.AIRLIFT));
        countryList1.get(0).setArmies(100);
        IssueOrderController.Commands = "airlift " + countryList1.get(0).getName() + " " + countryList1.get(1).getName()+ " "+ 10;
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order1);
        assertTrue(player.nextOrder().execute());
    }

    /**
     * Tests the validation of the airlift command for when the target country belongs to the same player.
     * Verifies that the command is valid in this scenario.
     */
    @Test
    public void testCommandValidationForSamePlayer() {
        Player player = gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.AIRLIFT));
        countryList1.get(0).setArmies(100);
        System.out.println("Source: "+ countryList1.get(0).getArmies());
        IssueOrderController.Commands = "airlift " + countryList1.get(0).getName() + " " + countryList1.get(1).getName()+ " "+ 10;
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order1);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Tests the validation of the airlift command when the target country does not belong to the player.
     * Verifies that the command is invalid in this scenario.
     *
     */
    @Test
    public void testCommandValidationForDifferentPlayer() {
        Player player1 = gameMap.getPlayer("Player1");
        player1.addPlayerCard(new Card(CardType.AIRLIFT));
        IssueOrderController.Commands = "airlift " + countryList1.get(0).getName() + " " + countryList2.get(1).getName()+" "+2;
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertFalse(player1.nextOrder().validateCommand());
    }

}
