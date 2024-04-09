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
    GameMap d_gameMap;

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
        d_gameMap = GameMap.getInstance();
        d_gameMap.flushGameMap();

        /*
          Singleton game settings instance
         */
        GameSettings gameSettings = GameSettings.getInstance();
        gameSettings.setStrategy("dice");

        // Add Continent
        d_gameMap.addContinent("Asia", "4");
        // Add Countries
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addCountry("Vietnam", "Asia");
        // Add Neighbors
        Country l_country1 = d_gameMap.getCountry("India");
        Country l_country2 = d_gameMap.getCountry("China");
        Country l_country3 = d_gameMap.getCountry("Vietnam");
        l_country1.setNeighbors(l_country2);
        l_country2.setNeighbors(l_country3);
        l_country3.setNeighbors(l_country1);
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        // Add Countries to players
        d_gameMap.getPlayer("Player1").getCapturedCountries().add(d_gameMap.getCountry("India"));
        d_gameMap.getPlayer("Player1").getCapturedCountries().add(d_gameMap.getCountry("Vietnam"));
        d_gameMap.getPlayer("Player2").getCapturedCountries().add(d_gameMap.getCountry("China"));
        // Add Reinforcements to players
        d_gameMap.getPlayer("Player1").setReinforcementArmies(10);
        d_gameMap.getPlayer("Player2").setReinforcementArmies(10);
    }

    /**
     * Cleans up the environment after each test case runs.
     */
    @After
    public void tearDown() {
        d_gameMap.flushGameMap();
    }

    /**
     * Tests if the execution fails when no troops are deployed for the advance order.
     */
    @Test
    public void testExecutionFailOnNoTroopsDeployed() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_countriesPlayer2 = d_gameMap.getPlayer("Player2").getCapturedCountries();

        Player l_player1 = d_gameMap.getPlayer("Player1");
        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer2.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertFalse(l_player1.nextOrder().execute());
    }

    /**
     * Tests if the execution fails when the destination is not a neighbor.
     */
    @Test
    public void testExecutionFailOnNotANeighbor() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        l_countriesPlayer1.get(0).setArmies(6);
        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer1.get(1).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertFalse(l_player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor and troops are deployed.
     */
    @Test
    public void testExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        l_countriesPlayer1.get(1).setArmies(6);
        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(1).getName() + " " + l_countriesPlayer1.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertTrue(l_player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor and troops are deployed,
     * and no king exists.
     */
    @Test
    public void testAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_countriesPlayer2 = d_gameMap.getPlayer("Player2").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        // Set Armies to each country
        l_countriesPlayer1.get(0).setArmies(6);

        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer2.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertTrue(l_player1.nextOrder().execute());
        assertEquals("Armies Depleted from source and deployed to target", 5, d_gameMap.getCountry("China").getArmies());
        assertTrue(l_countriesPlayer1.get(0).getArmies() > 0);
    }

    /**
     * Tests if the execution succeeds when the destination is a neighbor, troops are deployed, and a king exists.
     */
    @Test
    public void testAttackSuccessOnNeighborWithKing() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_countriesPlayer2 = d_gameMap.getPlayer("Player2").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        Player l_player2 = d_gameMap.getPlayer("Player2");
        // Set Players to countries
        l_countriesPlayer1.get(0).setPlayer(l_player1);
        l_countriesPlayer2.get(0).setPlayer(l_player2);
        // Set Armies to each country
        l_countriesPlayer1.get(0).setArmies(6);
        l_countriesPlayer2.get(0).setArmies(6);

        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer2.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertTrue(l_player1.nextOrder().execute());
    }

    /**
     * Tests if the execution succeeds and ownership changes when the destination is a neighbor,
     * troops are deployed, and a king exists.
     */
    @Test
    public void testOwnershipChangeOnAdvanceSuccess() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_countriesPlayer2 = d_gameMap.getPlayer("Player2").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        Player l_player2 = d_gameMap.getPlayer("Player2");

        // Set Players to Countries
        l_countriesPlayer1.get(0).setPlayer(l_player1);
        l_countriesPlayer2.get(0).setPlayer(l_player2);
        // Set Armies to each country
        l_countriesPlayer1.get(0).setArmies(6);

        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer2.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertTrue(l_player1.nextOrder().execute());
        assertEquals("Ownership changed", l_player1, d_gameMap.getCountry("China").getPlayer());
    }

    /**
     * Tests if the advance command gets skipped if the destination is a neutral player.
     */
    @Test
    public void testExecutionSkipsOnNeutralPlayer() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_countriesPlayer2 = d_gameMap.getPlayer("Player2").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        Player l_player2 = d_gameMap.getPlayer("Player2");
        // Set Players to countries
        l_countriesPlayer1.get(0).setPlayer(l_player1);
        l_countriesPlayer2.get(0).setPlayer(l_player2);
        // Set Armies to each country
        l_countriesPlayer1.get(0).setArmies(6);
        l_countriesPlayer2.get(0).setArmies(6);
        // Set Neutral Players
        l_player1.getNeutralPlayers().add(l_player2);
        l_player2.getNeutralPlayers().add(l_player1);

        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(0).getName() + " " + l_countriesPlayer2.get(0).getName() + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertTrue(l_player1.nextOrder().execute());
    }

    /**
     * Tests if the advance execution fails and gets skipped if the command is invalid.
     */
    @Test
    public void testExecutionFailOnInvalidCommand() {
        List<Country> l_countriesPlayer1 = d_gameMap.getPlayer("Player1").getCapturedCountries();
        Player l_player1 = d_gameMap.getPlayer("Player1");
        l_countriesPlayer1.get(1).setArmies(6);
        IssueOrderController.d_Commands = "advance " + l_countriesPlayer1.get(1).getName() + " " + "Thailand" + " " + (l_player1.getReinforcementArmies() - 5);
        Order l_order1 = OrderOwner.createOrder(IssueOrderController.d_Commands.split(" "), l_player1);
        l_player1.addOrder(l_order1);
        assertFalse(l_player1.nextOrder().execute());
    }

}
