package org.team21.game.models.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;

import java.util.List;

import static org.junit.Assert.*;

/**
 * A class containing test cases for the AdvanceOrder class.
 * These tests cover various scenarios related to executing advance orders,
 * checking execution success/failure, ownership changes, skipping orders, etc.
 *
 * @author Test
 */
public class AdvanceOrderTest {
    /**
     * The instance of the game map.
     */
    GameMap gameMap;

    /**
     * Sets up the environment before each test case runs.
     *
     * @throws Exception if an exception occurs during setup.
     */
    @Before
    public void setUp() throws Exception {
        /*
          Singleton game map instance
         */
        gameMap = GameMap.getInstance();

        /*
          Singleton game settings instance
         */
        GameSettings gameSettings = GameSettings.getInstance();
        gameSettings.setStrategy("dice");

        // Add Continent
        gameMap.addContinent("Asia", "4");
        // Add Countries
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("China", "Asia");
        gameMap.addCountry("Vietnam", "Asia");
        // Add Neighbors
        Country country1 = gameMap.getCountry("India");
        Country country2 = gameMap.getCountry("China");
        Country country3 = gameMap.getCountry("Vietnam");
        country1.setNeighbors(country2);
        country2.setNeighbors(country3);
        country3.setNeighbors(country1);
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        // Add Countries to players
        gameMap.getPlayer("Player1").getCapturedCountries().add(gameMap.getCountry("India"));
        gameMap.getPlayer("Player1").getCapturedCountries().add(gameMap.getCountry("Vietnam"));
        gameMap.getPlayer("Player2").getCapturedCountries().add(gameMap.getCountry("China"));
        // Add Reinforcements to players
        gameMap.getPlayer("Player1").setReinforcementArmies(10);
        gameMap.getPlayer("Player2").setReinforcementArmies(10);
    }

    /**
     * Cleans up the environment after each test case runs.
     */
    @After
    public void tearDown() {
        gameMap.flushGameMap();
    }

    /**
     * Tests if the execution fails when no troops are deployed for the advance order.
     */
    @Test
    public void testExecutionFailOnNoTroopsDeployed() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> countriesPlayer2 = gameMap.getPlayer("Player2").getCapturedCountries();

        Player player1 = gameMap.getPlayer("Player1");
        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer2.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertFalse(player1.nextOrder().execute());
    }

    /**
     * Tests if the execution fails when the destination is not a neighbor.
     */
    @Test
    public void testExecutionFailOnNotANeighbor() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        countriesPlayer1.get(0).setArmies(6);
        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer1.get(1).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertFalse(player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor and troops are deployed.
     */
    @Test
    public void testExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        countriesPlayer1.get(1).setArmies(6);
        IssueOrderController.Commands = "advance " + countriesPlayer1.get(1).getName() + " " + countriesPlayer1.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertTrue(player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor and troops are deployed,
     * and no king exists.
     */
    @Test
    public void testAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> countriesPlayer2 = gameMap.getPlayer("Player2").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        // Set Armies to each country
        countriesPlayer1.get(0).setArmies(6);

        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer2.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertTrue(player1.nextOrder().execute());
        assertEquals("Armies Depleted from source and deployed to target", 5, gameMap.getCountry("China").getArmies());
        assertTrue(countriesPlayer1.get(0).getArmies() > 0);
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor, troops are deployed, and a king exists.
     */
    @Test
    public void testAttackSuccessOnNeighborWithKing() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> countriesPlayer2 = gameMap.getPlayer("Player2").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        Player player2 = gameMap.getPlayer("Player2");
        // Set Players to countries
        countriesPlayer1.get(0).setPlayer(player1);
        countriesPlayer2.get(0).setPlayer(player2);
        // Set Armies to each country
        countriesPlayer1.get(0).setArmies(6);
        countriesPlayer2.get(0).setArmies(6);

        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer2.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertTrue(player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds and ownership changes when the destination is a neighbor,
     * troops are deployed, and a king exists.
     */
    @Test
    public void testOwnershipChangeOnAdvanceSuccess() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> countriesPlayer2 = gameMap.getPlayer("Player2").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        Player player2 = gameMap.getPlayer("Player2");

        // Set Players to Countries
        countriesPlayer1.get(0).setPlayer(player1);
        countriesPlayer2.get(0).setPlayer(player2);
        // Set Armies to each country
        countriesPlayer1.get(0).setArmies(6);

        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer2.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertTrue(player1.nextOrder().execute());
        assertEquals("Ownership changed", player1, gameMap.getCountry("China").getPlayer());
    }

    /**
     * Tests if the advance command gets skipped if the destination is a neutral player.
     */
    @Test
    public void testExecutionSkipsOnNeutralPlayer() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> countriesPlayer2 = gameMap.getPlayer("Player2").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        Player player2 = gameMap.getPlayer("Player2");
        // Set Players to countries
        countriesPlayer1.get(0).setPlayer(player1);
        countriesPlayer2.get(0).setPlayer(player2);
        // Set Armies to each country
        countriesPlayer1.get(0).setArmies(6);
        countriesPlayer2.get(0).setArmies(6);
        // Set Neutral Players
        player1.getNeutralPlayers().add(player2);
        player2.getNeutralPlayers().add(player1);

        IssueOrderController.Commands = "advance " + countriesPlayer1.get(0).getName() + " " + countriesPlayer2.get(0).getName() + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertTrue(player1.nextOrder().execute());
    }

    /**
     * Tests if the advance execution fails and gets skipped if the command is invalid.
     */
    @Test
    public void testExecutionFailOnInvalidCommand() {
        List<Country> countriesPlayer1 = gameMap.getPlayer("Player1").getCapturedCountries();
        Player player1 = gameMap.getPlayer("Player1");
        countriesPlayer1.get(1).setArmies(6);
        IssueOrderController.Commands = "advance " + countriesPlayer1.get(1).getName() + " " + "Thailand" + " " + (player1.getReinforcementArmies() - 5);
        Order order1 = OrderOwner.CreateOrder(IssueOrderController.Commands.split(" "), player1);
        player1.addOrder(order1);
        assertFalse(player1.nextOrder().execute());
    }

}
