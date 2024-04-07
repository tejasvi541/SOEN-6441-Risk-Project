package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for verifying the behavior of the ExecuteOrder class.
 * This class tests various functionalities related to executing orders in the game.
 * @author Tejasvi
 */
public class ExecuteOrderTest {
    /**
     * The instance of the GameMap.
     */
    private GameMap d_GameMap;
    /**
     * The instance of the ExecuteOrder class to be tested.
     */
    private ExecuteOrderController d_ExecuteOrder;
    /**
     * The list of countries owned by Player 1.
     */
    private List<Country> d_CountriesPlayer1;
    /**
     * The reference to the player object.
     */
    private Player d_Player;

    /**
     * Sets up the test environment before each test case execution.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.assignCountries();
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.calculateReinforcementArmies(d_GameMap);
        }
        d_ExecuteOrder = new ExecuteOrderController();
        d_ExecuteOrder.d_GamePhase = GamePhase.ExecuteOrder;
    }

    /**
     * Cleans up the test environment after each test case execution.
     *
     * @throws Exception if an error occurs during teardown
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    /**
     * Tests if the player has won by capturing all countries.
     */
    @Test
    public void testCheckIfPlayerWon() {
        HashMap<String, Country> l_ListOfAllCountries = d_GameMap.getCountries();
        d_Player = d_GameMap.getPlayer("Player1");
        for (Country l_Country : l_ListOfAllCountries.values()) {
            if (!d_Player.isCaptured(l_Country)) {
                d_Player.getCapturedCountries().add(l_Country);
                l_Country.setPlayer(d_Player);
            }
        }
        GamePhase l_Output = d_ExecuteOrder.checkIfPlayerWonOrTriesExhausted(GamePhase.ExecuteOrder);
        assertEquals(GamePhase.ExitGame, l_Output);
    }
}
