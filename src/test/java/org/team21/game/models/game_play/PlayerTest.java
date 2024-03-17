package org.team21.game.models.game_play;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.Country;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Player test.
 *
 * @author Nishith
 */
public class PlayerTest {

    private List<Player> d_Players;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_Players = new ArrayList<>();
        for (int l_I = 1; l_I <= 4; l_I++) {
            Player l_Player = new Player();
            l_Player.setId(l_I);
            l_Player.setName("TestPlayer" + l_I);
            l_Player.setReinforcementArmies(10);
            // Initialize captured countries
            List<Country> l_CapturedCountries = new ArrayList<>();
            Country l_Country1 = new Country();
            l_Country1.setCountryId("Country1");
            l_CapturedCountries.add(l_Country1);
            l_Player.setCapturedCountries(l_CapturedCountries);
            d_Players.add(l_Player);
        }
    }

    /**
     * Test player attributes.
     */
    @Test
    public void testPlayerAttributes() {
        assertEquals(4, d_Players.size());
        for (int l_I = 1; l_I <= 4; l_I++) {
            Player l_Player = d_Players.get(l_I - 1);
            assertEquals(l_I, l_Player.getId());
            assertEquals("TestPlayer" + l_I, l_Player.getName());
            assertEquals(10, l_Player.getReinforcementArmies());
            assertEquals(1, l_Player.getCapturedCountries().size());
            assertEquals("Country1", l_Player.getCapturedCountries().get(0).getCountryId());
        }
    }


    /**
     * Test check if country exists.
     */
    @Test
    public void testCheckIfCountryExists() {
        // Test with empty captured countries
        Player l_EmptyPlayer = new Player();
        assertFalse(l_EmptyPlayer.checkIfCountryExists("Country1", l_EmptyPlayer));

        // Test with non-existent country
        assertFalse(d_Players.getFirst().checkIfCountryExists("NonExistentCountry", d_Players.getFirst()));
    }

    /**
     * Test deploy reinforcement armies from player.
     */
    @Test
    public void testDeployReinforcementArmiesFromPlayer() {
        // Test with negative army count
        d_Players.get(0).deployReinforcementArmiesFromPlayer(-5);
        assertEquals(10, d_Players.get(0).getReinforcementArmies());

        // Test with insufficient reinforcement armies
        assertFalse(d_Players.get(1).deployReinforcementArmiesFromPlayer(15));
        assertEquals(10, d_Players.get(1).getReinforcementArmies());

        // Test with valid army count
        assertTrue(d_Players.get(2).deployReinforcementArmiesFromPlayer(5));
        assertEquals(5, d_Players.get(2).getReinforcementArmies());
    }

    /**
     * Test create a capture list.
     */
    @Test
    public void testCreateACaptureList() {
        // Test with empty captured countries
        Player l_EmptyPlayer = new Player();
        List<Country> l_EmptyCapturedCountries = new ArrayList<>();
        assertEquals("", l_EmptyPlayer.createACaptureList(l_EmptyCapturedCountries));

        // Test with non-empty captured countries
        assertEquals("Country1", d_Players.get(0).createACaptureList(d_Players.get(0).getCapturedCountries()));
    }

}