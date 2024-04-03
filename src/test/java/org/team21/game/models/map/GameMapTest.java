package org.team21.game.models.map;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.utils.validation.ValidationException;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the GameMap class.
 * @author Tejasvi
 */
public class GameMapTest {
    private GameMap d_GameMap;

    /**
     * Initializes a GameMap object before each test.
     */
    @Before
    public void setUp() {
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


}
