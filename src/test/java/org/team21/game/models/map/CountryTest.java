package org.team21.game.models.map;

import org.junit.Test;
import org.team21.game.models.game_play.Player;

import static org.junit.Assert.*;


/**
 * The type Country test.
 *
 * @author Nishith
 */
public class CountryTest {

    /**
     * Country initialization test.
     */
    @Test
    public void countryInitializationTest() {
        Country country = new Country("Country1", "Continent1");
        assertNotNull(country);
        assertEquals("Country1", country.getCountryId());
        assertEquals("Continent1", country.getParentContinent());
    }

    /**
     * Add neighbor test.
     */
    @Test
    public void addNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.getNeighbours().put("Country2", neighbor);
        assertTrue(country.getNeighbours().containsKey("Country2"));
        assertEquals(neighbor, country.getNeighbours().get("Country2"));
    }

    /**
     * Remove neighbor test.
     */
    @Test
    public void removeNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.getNeighbours().put("Country2", neighbor);
        country.getNeighbours().remove("Country2");
        assertFalse(country.getNeighbours().containsKey("Country2"));
    }

    /**
     * Deploy armies test.
     */
    @Test
    public void deployArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(5);
        country.deployArmies(3);
        assertEquals(8, country.getArmies());
    }

    /**
     * Reduce armies test.
     */
    @Test
    public void reduceArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(10);
        assertEquals(10, country.getArmies());
    }

    /**
     * Reduce armies insufficient test.
     */
    @Test
    public void reduceArmiesInsufficientTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(2);
        assertEquals(2, country.getArmies());
    }

    /**
     * Modify country id test.
     */
    @Test
    public void modifyCountryIdTest() {
        Country country = new Country("Country1", "Continent1");
        country.setCountryId("NewCountry1");
        assertEquals("NewCountry1", country.getCountryId());
    }

    /**
     * Modify continent test.
     */
    @Test
    public void modifyContinentTest() {
        Country country = new Country("Country1", "Continent1");
        country.setParentContinent("NewContinent1");
        assertEquals("NewContinent1", country.getParentContinent());
    }

    /**
     * Modify number of armies test.
     */
    @Test
    public void modifyNumberOfArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setNumberOfArmies(15);
        assertEquals(15, country.getNumberOfArmies());
    }

    /**
     * Sets and get player test.
     */
    @Test
    public void setAndGetPlayerTest() {
        Country country = new Country("Country1", "Continent1");
        Player player = new Player();
        country.setPlayer(player);
        assertEquals(player, country.getPlayer());
    }

    /**
     * Sets and get armies test.
     */
    @Test
    public void setAndGetArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(20);
        assertEquals(20, country.getArmies());
    }

    /**
     * Sets and get country file index test.
     */
    @Test
    public void setAndGetCountryFileIndexTest() {
        Country country = new Country();
        country.setCountryFileIndex(5);
        assertEquals(5, country.getCountryFileIndex());
    }

    /**
     * Sets and get coordinates test.
     */
    @Test
    public void setAndGetCoordinatesTest() {
        Country country = new Country();
        country.setXCoordinate(10);
        country.setYCoordinate(20);
    }
}