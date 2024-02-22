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
        assertEquals("Country1", country.get_countryId());
        assertEquals("Continent1", country.get_parentContinent());
    }

    /**
     * Add neighbor test.
     */
    @Test
    public void addNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.get_Neighbours().put("Country2", neighbor);
        assertTrue(country.get_Neighbours().containsKey("Country2"));
        assertEquals(neighbor, country.get_Neighbours().get("Country2"));
    }

    /**
     * Remove neighbor test.
     */
    @Test
    public void removeNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.get_Neighbours().put("Country2", neighbor);
        country.get_Neighbours().remove("Country2");
        assertFalse(country.get_Neighbours().containsKey("Country2"));
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
        country.set_countryId("NewCountry1");
        assertEquals("NewCountry1", country.get_countryId());
    }

    /**
     * Modify continent test.
     */
    @Test
    public void modifyContinentTest() {
        Country country = new Country("Country1", "Continent1");
        country.set_parentContinent("NewContinent1");
        assertEquals("NewContinent1", country.get_parentContinent());
    }

    /**
     * Modify number of armies test.
     */
    @Test
    public void modifyNumberOfArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.set_numberOfArmies(15);
        assertEquals(15, country.get_numberOfArmies());
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
        country.set_countryFileIndex(5);
        assertEquals(5, country.get_countryFileIndex());
    }

    /**
     * Sets and get coordinates test.
     */
    @Test
    public void setAndGetCoordinatesTest() {
        Country country = new Country();
        country.set_xCoordinate(10);
        country.set_yCoordinate(20);
    }
}