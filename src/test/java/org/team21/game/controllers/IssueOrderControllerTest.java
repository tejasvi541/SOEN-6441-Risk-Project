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
    private GameMap d_GameMap;

    /**
     * Sets up the test environment before each test case execution.
     */

    @Before
    public void setUp() {
        d_GameMap = GameMap.getInstance();

        // Set up continents
        Continent d_Continent1 = new Continent("Continent1", "5", 1);
        d_GameMap.getContinents().put("Continent1", d_Continent1);

        // Set up countries
        Country l_Country1 = new Country("Country1", "Continent1");
        Country l_Country2 = new Country("Country2", "Continent1");
        Country l_Country3 = new Country("Country2", "Continent1");
        Country l_Country4 = new Country("Country2", "Continent1");
        List<Country> l_Countries = new ArrayList<>();
        l_Countries.add(l_Country1);
        l_Countries.add(l_Country2);
        l_Countries.add(l_Country3);
        l_Countries.add(l_Country4);
        d_GameMap.getCountries().put("1", l_Country1);
        d_GameMap.getCountries().put("2", l_Country2);

        // Set up players
        HashMap<String, Player> l_Players = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            Player l_Player = new Player();
            l_Player.setName("Player" + i);
            l_Player.setReinforcementArmies(5); // Set reinforcement armies for each player
            l_Player.setCapturedCountries(l_Countries); // Assuming each player has captured the same countries
            l_Players.put("Player" + i, l_Player);
        }
        d_GameMap.setPlayers(l_Players);
        // Set up the input stream for simulating user input
    }

    /**
     * Tests the {@code run} method when all players have zero reinforcement armies.
     */

    @Test
    public void testRun_AllPlayersHaveZeroReinforcementArmies() {
        int l_ReinforcmentArmy = 5;
        // Set up player's reinforcement armies to zero
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.setReinforcementArmies(l_ReinforcmentArmy);

            String l_Test1 = "DEPLOY Country1 21";
            String[] l_CommandArr1 = l_Test1.split("\\s");
            assertEquals(3, l_CommandArr1.length);
            assertNotEquals("deploy", l_CommandArr1[0]);
            l_Player.issueOrder(l_Test1);
            assertEquals(5, l_Player.getReinforcementArmies());

            String l_Test2 = "deploy Country2 3";
            String[] l_CommandArr2 = l_Test2.split("\\s");
            assertEquals(3, l_CommandArr2.length);
            l_Player.issueOrder(l_Test2);
            assertEquals(5, l_Player.getReinforcementArmies());

            String l_Test3 = "deploy Country3 2";
            String[] l_CommandArr3 = l_Test2.split("\\s");
            assertEquals(3, l_CommandArr3.length);
            l_Player.issueOrder(l_Test3);
            assertEquals(5, l_Player.getReinforcementArmies());

            String l_Test4 = "deploy Country4 4";
            String[] l_CommandArr4 = l_Test2.split("\\s");
            assertEquals(3, l_CommandArr4.length);
            l_Player.issueOrder(l_Test4);
            assertEquals(5, l_Player.getReinforcementArmies());

            assertEquals(Constants.DEPLOY_COMMAND, l_CommandArr2[0]);
        }


    }

}