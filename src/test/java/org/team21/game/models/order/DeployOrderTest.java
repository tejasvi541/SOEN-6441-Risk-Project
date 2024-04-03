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
    GameMap gameMap;
    List<Country> player1Countries;
    List<Country> player2Countries;
    Player player;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws Exception if an exception occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();
        gameMap.addContinent("Asia", "4");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("China", "Asia");
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        gameMap.assignCountries();
        for (Player player : gameMap.getPlayers().values()) {
            player.calculateReinforcementArmies(gameMap);
        }
        player1Countries = gameMap.getPlayer("Player1").getCapturedCountries();
        player = gameMap.getPlayer("Player1");
    }

    /**
     * Clears the instance after each test case.
     */
    @After
    public void tearDown() {
        gameMap.flushGameMap();
    }

    /**
     * Test case to execute the deploy command.
     * Verifies that the command executes successfully.
     */
    @Test
    public void testExecution() {
        IssueOrderController.Commands = "deploy " + player1Countries.get(0).getName() + " " + player.getReinforcementArmies();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().execute());
    }

    /**
     * Test the validation of the deploy command.
     * Verifies that the command is valid when the specified country belongs to the player.
     */
    @Test
    public void testCommandValidationForValidCountry() {
        IssueOrderController.Commands = "deploy " + player1Countries.get(0).getName() + " " + player.getReinforcementArmies();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertTrue(player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the specified country in the command belongs to the player.
     * Verifies that the command is invalid if the specified country does not belong to the player.
     */
    @Test
    public void testInvalidCountry() {
        player2Countries = gameMap.getPlayer("Player2").getCapturedCountries();
        IssueOrderController.Commands = "deploy " + player2Countries.get(0).getName() + " " + player.getReinforcementArmies();
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertFalse(player.nextOrder().validateCommand());
    }

    /**
     * Test to check if the specified number of armies in the command is valid.
     * Verifies that the command is invalid if the specified number of armies is greater than the player's reinforcement armies.
     */
    @Test
    public void testInvalidArmies() {
        IssueOrderController.Commands = "deploy " + player1Countries.get(0).getName() + " 10";
        Order order = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player);
        player.addOrder(order);
        assertFalse(player.nextOrder().validateCommand());
    }
}
