package org.team21.game.models.map;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.strategy.player.PlayerStrategy;

import static org.junit.Assert.*;

/**
 * Test Cases for various functions in Country class
 * @author Tejasvi
 */
public class CountryTest {

    private Country d_Country;
    Player l_player = new Player(PlayerStrategy.getStrategy("human"));

    /**
     * This function setups every test case
     * @throws Exception if an error occurs during the setup process.
     */
    @Before
    public void setUp() throws Exception {
        l_player.setName("Harsh");
        d_Country = new Country();
        d_Country.setContinent("Asia");
        d_Country.setId("India");
        d_Country.setArmies(10);
        d_Country.setPlayer(l_player);
        d_Country.setName("India");
    }

    /**
     * Test getId
     */
    @Test
    public void getId() {
        assertEquals("India", d_Country.getId());
    }

    /**
     * Test getName
     */
    @Test
    public void getName() {
        assertEquals("India", d_Country.getName());

    }

    /**
     * Test the name of continent of country
     */
    @Test
    public void getContinent() {
        assertEquals("Asia", d_Country.getContinent());
    }

    /**
     * Test getting player who owns the country
     */
    @Test
    public void getPlayer() {
        assertEquals(d_Country.getPlayer(), l_player);
    }

    /**
     * Test to check no. of armies
     */
    @Test
    public void getArmies() {
        assertEquals(d_Country.getArmies(), 10);
    }

    /**
     * Test to add more armies
     */
    @Test
    public void deployArmies() {
        d_Country.deployArmies(5);
        assertEquals(d_Country.getArmies(), 15);
    }

    /**
     * Test to depleat armies
     */
    @Test
    public void depleteArmies() {
        d_Country.depleteArmies(5);
        assertEquals(d_Country.getArmies(), 5);
    }

    /**
     * Test to check the neighbor
     */
    @Test
    public void isNeighbor() {
        assertEquals(d_Country.getNeighborsName().contains("Pakistant"), false);
    }

}