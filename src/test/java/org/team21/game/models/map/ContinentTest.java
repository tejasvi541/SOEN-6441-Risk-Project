package org.team21.game.models.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * The type Continent test.
 *
 * @author Nishith
 */
public class ContinentTest {

    private Continent d_Continent;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        // Initialize the Continent object
        d_Continent = new Continent("Africa", "5", 1);
    }

    /**
     * Test get continent id.
     */
    @Test
    public void testGetContinentId() {
        assertEquals("Africa", d_Continent.getContinentId());
    }

    /**
     * Test set continent id.
     */
    @Test
    public void testSetContinentId() {
        d_Continent.setContinentId("Europe");
        assertEquals("Europe", d_Continent.getContinentId());
    }

    /**
     * Test get control value.
     */
    @Test
    public void testGetControlValue() {
        assertEquals(5, d_Continent.getControlValue());
    }

    /**
     * Test set control value.
     */
    @Test
    public void testSetControlValue() {
        d_Continent.setControlValue("10");
        assertEquals(10, d_Continent.getControlValue());
    }

    /**
     * Test get countries.
     */
    @Test
    public void testGetCountries() {
        HashMap<String, Country> countries = d_Continent.getCountries();
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test set countries.
     */
    @Test
    public void testSetCountries() {
        Country country1 = new Country("UK", "Europe");
        Country country2 = new Country("France", "Europe");
        HashMap<String, Country> countries = new HashMap<>();
        countries.put("UK", country1);
        countries.put("France", country2);
        System.out.println(countries);
        d_Continent.setCountries(countries);
        assertEquals(countries, d_Continent.getCountries());
    }

    /**
     * Test false set countries.
     */
    @Test
    public void testFalseSetCountries() {
        Country country1 = new Country("UK", "Europe");
        Country country2 = new Country("France", "Europe");
        HashMap<String, Country> countries = new HashMap<>();
        countries.put("UK", country1);
        countries.put("France", country2);
        System.out.println(countries);
        d_Continent.setCountries(countries);
        assertNotEquals(countries.put("France", country2), d_Continent.getCountries());
    }

    /**
     * Test get continent file index.
     */
    @Test
    public void testGetContinentFileIndex() {
        assertEquals(1, d_Continent.getContinentFileIndex());
    }

    /**
     * Test set continent file index.
     */
    @Test
    public void testSetContinentFileIndex() {
        d_Continent.setContinentFileIndex("2");
        assertEquals(2, d_Continent.getContinentFileIndex());
    }

    /**
     * Test get award armies.
     */
    @Test
    public void testGetAwardArmies() {
        assertEquals(0, d_Continent.getAwardArmies());
    }

    /**
     * Test set award armies.
     */
    @Test
    public void testSetAwardArmies() {
        d_Continent.setAwardArmies(3);
        assertEquals(3, d_Continent.getAwardArmies());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        d_Continent = null;
    }
}