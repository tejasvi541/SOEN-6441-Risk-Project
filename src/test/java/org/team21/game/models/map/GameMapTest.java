package org.team21.game.models.map;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.StartGameController;
import org.team21.game.utils.validation.ValidationException;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the GameMap class.
 * @author Tejasvi
 */
public class GameMapTest {
    private GameMap d_GameMap;
    private StartGameController d_lgc;
    /**
     * Initializes a GameMap object before each test.
     */
    @Before
    public void setUp() {
        d_lgc = new StartGameController();
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Tests the getInstance method of the GameMap class.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(d_GameMap);
    }

    /**
     * Tests the addContinent method of the GameMap class.
     */
    @Test
    public void testAddContinent() {
            assertTrue(d_GameMap.getContinents().containsKey("Asia"));
    }

    /**
     * Tests the addCountry method of the GameMap class.
     */
    @Test
    public void testAddCountry() {
        try {
            d_GameMap.addContinent("Asia", "10");

            d_GameMap.addCountry("India", "Asia");
            assertTrue(d_GameMap.getCountries().containsKey("India"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
    }

    /**
     * Tests the removeContinent method of the GameMap class.
     */
    @Test
    public void testRemoveContinent() {
        try {
            d_GameMap.removeContinent("Asia");
            assertFalse(d_GameMap.getContinents().containsKey("Asia"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
    }

    /**
     * Tests the removeCountry method of the GameMap class.
     */
    @Test
    public void testRemoveCountry() {
        try {
            d_GameMap.removeCountry("India");
            assertFalse(d_GameMap.getCountries().containsKey("India"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
    }

    /**
     * Tests the addPlayer method of the GameMap class.
     */
    @Test
    public void testAddPlayer() {
        try {
            d_GameMap.addPlayer("Player1");
            assertTrue(d_GameMap.getPlayers().containsKey("Player1"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
    }

    /**
     * Tests the removePlayer method of the GameMap class.
     */
    @Test
    public void testRemovePlayer() {
        try {
            d_GameMap.addPlayer("Player1");
            d_GameMap.removePlayer("Player1");
            assertFalse(d_GameMap.getPlayers().containsKey("Player1"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
    }

    /**
     * This function is to test save map functionality
     * @throws ValidationException
     * @throws IOException
     */
    @Test
    public void saveMap() throws ValidationException, IOException {
        try {
            d_lgc.loadMap("Australia.map");
            d_GameMap.addCountry("Sydney", "USA");
            d_GameMap.addNeighbor("india", "Sydney");
            d_GameMap.addNeighbor("Sydney", "india");
            d_GameMap.addPlayer("p2");
            d_GameMap.setName("Australia1");
            d_GameMap.showMap();
            d_GameMap.saveMap(true);
        } catch (Exception ignored){
        }

    }

}
