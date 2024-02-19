package org.team21.game.models.map;

import org.junit.After;
import org.junit.Test;
import org.team21.game.models.game_play.Player;

import static org.junit.Assert.*;


public class CountryTest {

    @Test
    public void countryInitializationTest() {
        Country country = new Country("Country1", "Continent1");
        assertNotNull(country);
        assertEquals("Country1", country.get_countryId());
        assertEquals("Continent1", country.get_parentContinent());
    }

    @Test
    public void addNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.get_Neighbours().put("Country2", neighbor);
        assertTrue(country.get_Neighbours().containsKey("Country2"));
        assertEquals(neighbor, country.get_Neighbours().get("Country2"));
    }

    @Test
    public void removeNeighborTest() {
        Country country = new Country("Country1", "Continent1");
        Country neighbor = new Country("Country2", "Continent1");
        country.get_Neighbours().put("Country2", neighbor);
        country.get_Neighbours().remove("Country2");
        assertFalse(country.get_Neighbours().containsKey("Country2"));
    }

    @Test
    public void deployArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(5);
        country.deployArmies(3);
        assertEquals(8, country.getArmies());
    }

    @Test
    public void reduceArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(10);
        assertEquals(10, country.getArmies());
    }

    @Test
    public void reduceArmiesInsufficientTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(2);
        assertEquals(2, country.getArmies());
    }

    @Test
    public void modifyCountryIdTest() {
        Country country = new Country("Country1", "Continent1");
        country.set_countryId("NewCountry1");
        assertEquals("NewCountry1", country.get_countryId());
    }

    @Test
    public void modifyContinentTest() {
        Country country = new Country("Country1", "Continent1");
        country.set_parentContinent("NewContinent1");
        assertEquals("NewContinent1", country.get_parentContinent());
    }

    @Test
    public void modifyNumberOfArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.set_numberOfArmies(15);
        assertEquals(15, country.get_numberOfArmies());
    }

    @Test
    public void setAndGetPlayerTest() {
        Country country = new Country("Country1", "Continent1");
        Player player = new Player();
        country.setPlayer(player);
        assertEquals(player, country.getPlayer());
    }

    @Test
    public void setAndGetArmiesTest() {
        Country country = new Country("Country1", "Continent1");
        country.setArmies(20);
        assertEquals(20, country.getArmies());
    }

    @Test
    public void setAndGetCountryFileIndexTest() {
        Country country = new Country();
        country.set_countryFileIndex(5);
        assertEquals(5, country.get_countryFileIndex());
    }

    @Test
    public void setAndGetCoordinatesTest() {
        Country country = new Country();
        country.set_xCoordinate(10);
        country.set_yCoordinate(20);
    }
}