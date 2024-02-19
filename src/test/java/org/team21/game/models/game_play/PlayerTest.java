package org.team21.game.models.game_play;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.Country;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    private List<Player> players;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("TestPlayer" + i);
            player.setReinforcementArmies(10);
            // Initialize captured countries
            List<Country> capturedCountries = new ArrayList<>();
            Country country1 = new Country();
            country1.set_countryId("Country1");
            capturedCountries.add(country1);
            player.setCapturedCountries(capturedCountries);
            players.add(player);
        }
    }

    @Test
    public void testPlayerAttributes() {
        assertEquals(4, players.size());
        for (int i = 1; i <= 4; i++) {
            Player player = players.get(i - 1);
            assertEquals(i, player.getId());
            assertEquals("TestPlayer" + i, player.getName());
            assertEquals(10, player.getReinforcementArmies());
            assertEquals(1, player.getCapturedCountries().size());
            assertEquals("Country1", player.getCapturedCountries().get(0).get_countryId());
        }
    }

    @Test
    public void testIssueOrder() {
        // Test with invalid command
        players.get(0).issue_order("invalid command");
        assertEquals(0, players.get(0).getOrders().size());

        // Test with insufficient reinforcement armies
        players.get(1).issue_order("deploy Country1 15");
        assertEquals(0, players.get(1).getOrders().size());

        // Test with valid command
        players.get(2).issue_order("deploy Country1 5");
        assertEquals(1, players.get(2).getOrders().size());
    }

    @Test
    public void testCheckIfCountryExists() {
        // Test with empty captured countries
        Player emptyPlayer = new Player();
        assertFalse(emptyPlayer.checkIfCountryExists("Country1", emptyPlayer));

        // Test with non-existent country
        assertFalse(players.getFirst().checkIfCountryExists("NonExistentCountry", players.getFirst()));
    }

    @Test
    public void testDeployReinforcementArmiesFromPlayer() {
        // Test with negative army count
        players.get(0).deployReinforcementArmiesFromPlayer(-5);
        assertEquals(10, players.get(0).getReinforcementArmies());

        // Test with insufficient reinforcement armies
        assertFalse(players.get(1).deployReinforcementArmiesFromPlayer(15));
        assertEquals(10, players.get(1).getReinforcementArmies());

        // Test with valid army count
        assertTrue(players.get(2).deployReinforcementArmiesFromPlayer(5));
        assertEquals(5, players.get(2).getReinforcementArmies());
    }

    @Test
    public void testCreateACaptureList() {
        // Test with empty captured countries
        Player emptyPlayer = new Player();
        List<Country> emptyCapturedCountries = new ArrayList<>();
        assertEquals("", emptyPlayer.createACaptureList(emptyCapturedCountries));

        // Test with non-empty captured countries
        assertEquals("Country1", players.get(0).createACaptureList(players.get(0).getCapturedCountries()));
    }

}