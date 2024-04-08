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
    GameMap d_gameMap;
    List<Country> d_countryList1 = new ArrayList<Country>();
    List<Country> d_countryList2 = new ArrayList<Country>();

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = GameMap.getInstance();
        d_gameMap.flushGameMap();

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
     * Clear the instance
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Tests the execution of the airlift order.
     * Verifies that the command executes correctly.
     *
     */
    @Test
    public void testExecution() {
        Player l_player = d_gameMap.getPlayer("Player1");
        l_player.addPlayerCard(new Card(CardType.AIRLIFT));
        d_countryList1.get(0).setArmies(100);
        IssueOrderController.d_Commands = "airlift " + d_countryList1.get(0).getName() + " " + d_countryList1.get(1).getName()+ " "+ 10;
        Order l_order1 = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), l_player);
        l_player.addOrder(l_order1);
        assertTrue(l_player.nextOrder().execute());
    }

    /**
     * Tests the validation of the airlift command for when the target country belongs to the same player.
     * Verifies that the command is valid in this scenario.
     */
    @Test
    public void testCommandValidationForSamePlayer() {
        Player player = d_gameMap.getPlayer("Player1");
        player.addPlayerCard(new Card(CardType.AIRLIFT));
        d_countryList1.get(0).setArmies(100);
        System.out.println("Source: "+ d_countryList1.get(0).getArmies());
        IssueOrderController.d_Commands = "airlift " + d_countryList1.get(0).getName() + " " + d_countryList1.get(1).getName()+ " "+ 10;
        Order l_order1 = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), player);
        player.addOrder(l_order1);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Tests the validation of the airlift command when the target country does not belong to the player.
     * Verifies that the command is invalid in this scenario.
     *
     */
    @Test
    public void testCommandValidationForDifferentPlayer() {
        Player player1 = d_gameMap.getPlayer("Player1");
        player1.addPlayerCard(new Card(CardType.AIRLIFT));
        IssueOrderController.d_Commands = "airlift " + d_countryList1.get(0).getName() + " " + d_countryList2.get(1).getName()+" "+2;
        Order l_order1 = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), player1);
        player1.addOrder(l_order1);
        assertFalse(player1.nextOrder().validateCommand());
    }

}
