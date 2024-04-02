package org.team21.game.models.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * The ContinentTest class is responsible for testing the methods of the Continent class.
 */
public class ContinentTest {
    private Continent d_Continent;
    /**
     * Sets up the test environment by initializing a Continent object with predefined values.
     */
    @Before
    public void setUp(){
        d_Continent = new Continent();
        d_Continent.setId("Asia");
        d_Continent.setName("Asia");
        d_Continent.setCredited(true);
        d_Continent.setAwardArmies(10);
        d_Continent.setCountries(new HashSet<>());
    }
    /**
     * Test method for getId().
     * It checks if the ID of the continent matches the expected value.
     */
    @Test
    public void getId() {
        assertEquals(d_Continent.getId(), "Asia");
    }
    /**
     * Test method for getName().
     * It checks if the name of the continent matches the expected value.
     */
    @Test
    public void getName() {
        assertEquals(d_Continent.getName(), "Asia");
    }

    /**
     * Test method for getAwardArmies().
     * It checks if the number of awarded armies for the continent matches the expected value.
     */
    @Test
    public void getAwardArmies() {
        assertEquals(d_Continent.getAwardArmies(), 10);
    }
    /**
     * Test method for isCredited().
     * It checks if the continent is credited, i.e., if it has been assigned a credit or not.
     */
    @Test
    public void isCredited() {
        assertEquals(d_Continent.isCredited(), true);
    }
    /**
     * Test method for getCountries().
     * It checks if the set of countries belonging to the continent is empty.
     */
    @Test
    public void getCountries() {
        assertEquals(d_Continent.getCountries(), new HashSet<>());
    }
}