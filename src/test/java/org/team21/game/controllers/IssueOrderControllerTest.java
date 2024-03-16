package org.team21.game.controllers;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class contains unit tests for the IssueOrderController class.
 *
 * @author Nishith
 */

public class IssueOrderControllerTest {

    /**
     * Game map object
     */
    private GameMap gameMap;

    /**
     * Sets up the test environment before each test case execution.
     */

    @Before
    public void setUp() {
        gameMap = GameMap.getInstance();

        // Set up continents
        Continent continent1 = new Continent("Continent1", "5", 1);
        gameMap.getContinents().put("Continent1", continent1);

        // Set up countries
        Country country1 = new Country("Country1", "Continent1");
        Country country2 = new Country("Country2", "Continent1");
        Country country3 = new Country("Country2", "Continent1");
        Country country4 = new Country("Country2", "Continent1");
        List<Country> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);
        countries.add(country3);
        countries.add(country4);
        gameMap.getCountries().put("1", country1);
        gameMap.getCountries().put("2", country2);

        // Set up players
        HashMap<String, Player> players = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player();
            player.setName("Player" + i);
            player.setReinforcementArmies(5); // Set reinforcement armies for each player
            player.setCapturedCountries(countries); // Assuming each player has captured the same countries
            players.put("Player" + i, player);
        }
        gameMap.setPlayers(players);
        // Set up the input stream for simulating user input
    }

    /**
     * Tests the {@code run} method when all players have zero reinforcement armies.
     */

    @Test
    public void testRun_AllPlayersHaveZeroReinforcementArmies() {
        int d_ReinforcmentArmy = 5;
        // Set up player's reinforcement armies to zero
        for (Player player : gameMap.getPlayers().values()) {
            player.setReinforcementArmies(d_ReinforcmentArmy);

            String test1 = "DEPLOY Country1 21";
            String[] commandArr1 = test1.split("\\s");
            assertEquals(3, commandArr1.length);
            assertNotEquals("deploy", commandArr1[0]);
            player.issueOrder(test1);
            assertEquals(5, player.getReinforcementArmies());

            String test2 = "deploy Country2 3";
            String[] commandArr2 = test2.split("\\s");
            assertEquals(3, commandArr2.length);
            player.issueOrder(test2);
            assertEquals(5, player.getReinforcementArmies());

            String test3 = "deploy Country3 2";
            String[] commandArr3 = test2.split("\\s");
            assertEquals(3, commandArr3.length);
            player.issueOrder(test3);
            assertEquals(5, player.getReinforcementArmies());

            String test4 = "deploy Country4 4";
            String[] commandArr4 = test2.split("\\s");
            assertEquals(3, commandArr4.length);
            player.issueOrder(test4);
            assertEquals(5, player.getReinforcementArmies());

            assertEquals(Constants.DEPLOY_COMMAND, commandArr2[0]);
        }


    }

}