package org.team21.game.models.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.team21.game.models.strategy.player.PlayerStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests the functionalities of the Player class.
 * It extends Player to access its methods for testing.
 *
 * @author Tejasvi
 */
public class PlayerTest extends Player {

    private int d_playerId;
    private String d_playerName;
    private int d_reinforcementArmies;
    private int d_validArmyCount;
    private int d_invalidArmyCount;
    private Player d_playerUnderTest;
    private String d_validCountry;
    private String d_invalidCountry;
    private List<Country> d_capturedCountries = new ArrayList<>();
    private Country d_country1 = new Country();
    private Country d_country2 = new Country();
    private Country d_country3 = new Country();
    private GameMap d_gameMap = GameMap.getInstance();

    /**
     * Constructor for PlayerTest.
     * Initializes a Player object with a human strategy.
     */
    public PlayerTest() {
        super(PlayerStrategy.getStrategy("human"));
        d_playerUnderTest = this;
    }

    /**
     * Sets up the test environment before each test method execution.
     *
     * @throws Exception if initialization fails
     */
    @Before
    public void setUp() throws Exception {
        d_playerId = 4;
        d_playerUnderTest.setId(d_playerId);
        d_playerName = "Shiro";
        d_playerUnderTest.setName(d_playerName);

        d_reinforcementArmies = 10;
        d_validArmyCount = 5;
        d_invalidArmyCount = 13;
        d_playerUnderTest.setReinforcementArmies(d_reinforcementArmies);

        d_country1.setName("India");
        d_country2.setName("China");
        d_country3.setName("Japan");
        d_validCountry = "India";
        d_invalidCountry = "Canada";
        d_capturedCountries.add(d_country1);
        d_capturedCountries.add(d_country2);
        d_capturedCountries.add(d_country3);
        d_playerUnderTest.setCapturedCountries(d_capturedCountries);
    }

    /**
     * Tears down the test environment after each test method execution.
     */
    @After
    public void tearDown() {
        d_gameMap.getContinents().clear();
        d_gameMap.getCountries().clear();
        d_gameMap.getPlayers().clear();
    }

    /**
     * Tests the getId method of the Player class.
     */
    @Test
    public void testPlayerId() {
        int id = d_playerUnderTest.getId();
        assertEquals(d_playerId, id);
    }

    /**
     * Tests the getName method of the Player class.
     */
    @Test
    public void testPlayerName() {
        String name = d_playerUnderTest.getName();
        assertEquals(d_playerName, name);
    }

    /**
     * Tests the deployReinforcementArmiesFromPlayer method with valid army count.
     */
    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(d_playerUnderTest.deployReinforcementArmiesFromPlayer(d_validArmyCount));
    }

    /**
     * Tests the deployReinforcementArmiesFromPlayer method with invalid army count.
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(d_playerUnderTest.deployReinforcementArmiesFromPlayer(d_invalidArmyCount));
    }
}
