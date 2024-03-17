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
        Country l_Country = new Country("Country1", "Continent1");
        assertNotNull(l_Country);
        assertEquals("Country1", l_Country.getCountryId());
        assertEquals("Continent1", l_Country.getParentContinent());
    }

    /**
     * Add neighbor test.
     */
    @Test
    public void addNeighborTest() {
        Country l_Country = new Country("Country1", "Continent1");
        Country l_Neighbor = new Country("Country2", "Continent1");
        l_Country.getNeighbours().put("Country2", l_Neighbor);
        assertTrue(l_Country.getNeighbours().containsKey("Country2"));
        assertEquals(l_Neighbor, l_Country.getNeighbours().get("Country2"));
    }

    /**
     * Remove neighbor test.
     */
    @Test
    public void removeNeighborTest() {
        Country l_Country = new Country("Country1", "Continent1");
        Country l_Neighbor = new Country("Country2", "Continent1");
        l_Country.getNeighbours().put("Country2", l_Neighbor);
        l_Country.getNeighbours().remove("Country2");
        assertFalse(l_Country.getNeighbours().containsKey("Country2"));
    }

    /**
     * Deploy armies test.
     */
    @Test
    public void deployArmiesTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setArmies(5);
        l_Country.deployArmies(3);
        assertEquals(8, l_Country.getArmies());
    }

    /**
     * Reduce armies test.
     */
    @Test
    public void reduceArmiesTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setArmies(10);
        assertEquals(10, l_Country.getArmies());
    }

    /**
     * Reduce armies insufficient test.
     */
    @Test
    public void reduceArmiesInsufficientTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setArmies(2);
        assertEquals(2, l_Country.getArmies());
    }

    /**
     * Modify country id test.
     */
    @Test
    public void modifyCountryIdTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setCountryId("NewCountry1");
        assertEquals("NewCountry1", l_Country.getCountryId());
    }

    /**
     * Modify continent test.
     */
    @Test
    public void modifyContinentTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setParentContinent("NewContinent1");
        assertEquals("NewContinent1", l_Country.getParentContinent());
    }

    /**
     * Modify number of armies test.
     */
    @Test
    public void modifyNumberOfArmiesTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setNumberOfArmies(15);
        assertEquals(15, l_Country.getNumberOfArmies());
    }

    /**
     * Sets and get player test.
     */
    @Test
    public void setAndGetPlayerTest() {
        Country l_Country = new Country("Country1", "Continent1");
        Player l_Player = new Player();
        l_Country.setPlayer(l_Player);
        assertEquals(l_Player, l_Country.getPlayer());
    }

    /**
     * Sets and get armies test.
     */
    @Test
    public void setAndGetArmiesTest() {
        Country l_Country = new Country("Country1", "Continent1");
        l_Country.setArmies(20);
        assertEquals(20, l_Country.getArmies());
    }

    /**
     * Sets and get country file index test.
     */
    @Test
    public void setAndGetCountryFileIndexTest() {
        Country l_Country = new Country();
        l_Country.setCountryFileIndex(5);
        assertEquals(5, l_Country.getCountryFileIndex());
    }

    /**
     * Sets and get coordinates test.
     */
    @Test
    public void setAndGetCoordinatesTest() {
        Country l_Country = new Country();
        l_Country.setXCoordinate(10);
        l_Country.setYCoordinate(20);
    }
}