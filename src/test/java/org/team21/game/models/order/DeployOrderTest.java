package org.team21.game.models.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Deploy Order.
 * It includes tests for executing the order, validating the command, and checking if the countries and armies in the command are valid.
 * The tests cover scenarios where the command is valid and invalid based on the player's captured countries and reinforcement armies.
 *
 * @author Tejasvi
 */
public class DeployOrderTest {
    GameMap d_gameMap;
    List<Country> d_player1Countries;
    List<Country> d_player2Countries;
    Player d_player;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = GameMap.getInstance();
        d_gameMap.flushGameMap();

        d_gameMap.addContinent("Asia", "4");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.assignCountries();
        for (Player player : d_gameMap.getPlayers().values()) {
            player.calculateReinforcementArmies(d_gameMap);
        }
        d_player1Countries = d_gameMap.getPlayer("Player1").getCapturedCountries();
        d_player = d_gameMap.getPlayer("Player1");
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Test case to execute the deploy command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        IssueOrderController.d_Commands = "deploy " + d_player1Countries.get(0).getName() + " " + d_player.getReinforcementArmies();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), d_player);
        d_player.addOrder(l_order);
        assertTrue(d_player.nextOrder().execute());
    }

    /**
     * Test the validation of the deploy command.
     * Verifies that the command is valid when the specified country belongs to the player.
     */
    @Test
    public void testCommandValidationForValidCountry() {
        IssueOrderController.d_Commands = "deploy " + d_player1Countries.get(0).getName() + " " + d_player.getReinforcementArmies();
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), d_player);
        d_player.addOrder(l_order);
        assertTrue(d_player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the specified number of armies in the command is valid.
     * Verifies that the command is invalid if the specified number of armies is greater than the player's reinforcement armies.
     */
    @Test
    public void testInvalidArmies() {
        IssueOrderController.d_Commands = "deploy " + d_player1Countries.get(0).getName() + " 10";
        Order l_order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), d_player);
        d_player.addOrder(l_order);
        assertFalse(d_player.nextOrder().validateCommand());
    }
}
