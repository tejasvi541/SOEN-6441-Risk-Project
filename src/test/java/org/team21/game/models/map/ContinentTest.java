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
        HashMap<String, Country> l_Countries = d_Continent.getCountries();
        assertNotNull(l_Countries);
        assertTrue(l_Countries.isEmpty());
    }

    /**
     * Test set countries.
     */
    @Test
    public void testSetCountries() {
        Country l_Country1 = new Country("UK", "Europe");
        Country l_Country2 = new Country("France", "Europe");
        HashMap<String, Country> l_Countries = new HashMap<>();
        l_Countries.put("UK", l_Country1);
        l_Countries.put("France", l_Country2);
        System.out.println(l_Countries);
        d_Continent.setCountries(l_Countries);
        assertEquals(l_Countries, d_Continent.getCountries());
    }

    /**
     * Test false set countries.
     */
    @Test
    public void testFalseSetCountries() {
        Country l_Country1 = new Country("UK", "Europe");
        Country l_Country2 = new Country("France", "Europe");
        HashMap<String, Country> l_Countries = new HashMap<>();
        l_Countries.put("UK", l_Country1);
        l_Countries.put("France", l_Country2);
        System.out.println(l_Countries);
        d_Continent.setCountries(l_Countries);
        assertNotEquals(l_Countries.put("France", l_Country2), d_Continent.getCountries());
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