package org.team21.game.utils.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.GameMap;

import static org.junit.Assert.*;

/**
 * A class to test the functionalities of map validation.
 * It verifies whether the map validation operations are performed correctly.
 * It includes tests for checking continent emptiness, duplicate neighbors, continent connectivity, and overall map connectivity.
 * @author Tejasvi
 */
public class MapValidationTest {
    /**
     * Instance of the game map
     */
    GameMap gameMap;

    /**
     * Sets up the initial conditions before each test case execution.
     * @throws Exception if setup fails
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();
        gameMap.addContinent("Asia", "5");
        gameMap.addContinent("US", "5");
        gameMap.addContinent("Africa", "5");
        gameMap.addContinent("Antarctica", "5");
        gameMap.addContinent("Aus", "5");

        gameMap.addCountry("Pakistan", "Africa");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("NewYork", "US");
        gameMap.addCountry("Penguin", "Antarctica");
        gameMap.addCountry("Melbourne", "Aus");

        gameMap.addNeighbor("Pakistan", "India");
        gameMap.addNeighbor("India", "Pakistan");
        gameMap.addNeighbor("Pakistan", "NewYork");
        gameMap.addNeighbor("NewYork", "Pakistan");
        gameMap.addNeighbor("Penguin", "India");
        gameMap.addNeighbor("India", "Penguin");
        gameMap.addNeighbor("Penguin", "Melbourne");
        gameMap.addNeighbor("Melbourne", "Penguin");
    }

    /**
     * Cleans up the test environment after each test method.
     * @throws Exception if cleanup fails
     */
    @After
    public void tearDown() throws Exception {
        gameMap.getContinents().clear();
        gameMap.getCountries().clear();
        gameMap.getPlayers().clear();
    }

    /**
     * Tests if a continent is empty.
     * @throws ValidationException if validation fails
     */
    @Test
    public void testCheckIfContinentIsEmpty() throws ValidationException {
        gameMap.addContinent("Europe", "5");
        assertTrue(MapValidation.checkIfContinentIsEmpty(gameMap));
    }

    /**
     * Tests if duplicate neighbors exist.
     * @throws ValidationException if validation fails
     */
    @Test
    public void testCheckDuplicateNeighbors() throws ValidationException {
        gameMap.addNeighbor("Pakistan", "Pakistan");
        assertTrue(MapValidation.checkDuplicateNeighbours(gameMap));
    }

    /**
     * Tests if the continent subgraph is connected.
     */
    @Test
    public void testCheckIfContinentIsConnected() {
        assertTrue(MapValidation.checkIfContinentIsConnected(gameMap));
    }

    /**
     * Tests if the whole graph is connected.
     */
    @Test
    public void testCheckIfMapIsConnected() {
        assertTrue(MapValidation.checkIfMapIsConnected(gameMap.getCountries()));
    }

    /**
     * Checks if the map is invalid.
     * @throws ValidationException if an exception occurs
     */
    @Test
    public void testCheckIfMapIsInvalid() throws ValidationException {
        gameMap.removeNeighbor("NewYork", "Pakistan");
        assertFalse(MapValidation.checkIfMapIsConnected(gameMap.getCountries()));
    }
}
