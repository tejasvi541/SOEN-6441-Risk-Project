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

    private int playerId;
    private String playerName;
    private int reinforcementArmies;
    private int validArmyCount;
    private int invalidArmyCount;
    private Player playerUnderTest;
    private String validCountry;
    private String invalidCountry;
    private List<Country> capturedCountries = new ArrayList<>();
    private Country country1 = new Country();
    private Country country2 = new Country();
    private Country country3 = new Country();
    private GameMap gameMap = GameMap.getInstance();

    /**
     * Constructor for PlayerTest.
     * Initializes a Player object with a human strategy.
     */
    public PlayerTest() {
        super(PlayerStrategy.getStrategy("human"));
        playerUnderTest = this;
    }

    /**
     * Sets up the test environment before each test method execution.
     *
     * @throws Exception if initialization fails
     */
    @Before
    public void setUp() throws Exception {
        playerId = 4;
        playerUnderTest.setId(playerId);
        playerName = "Shiro";
        playerUnderTest.setName(playerName);

        reinforcementArmies = 10;
        validArmyCount = 5;
        invalidArmyCount = 13;
        playerUnderTest.setReinforcementArmies(reinforcementArmies);

        country1.setName("India");
        country2.setName("China");
        country3.setName("Japan");
        validCountry = "India";
        invalidCountry = "Canada";
        capturedCountries.add(country1);
        capturedCountries.add(country2);
        capturedCountries.add(country3);
        playerUnderTest.setCapturedCountries(capturedCountries);
    }

    /**
     * Tears down the test environment after each test method execution.
     */
    @After
    public void tearDown() {
        gameMap.getContinents().clear();
        gameMap.getCountries().clear();
        gameMap.getPlayers().clear();
    }

    /**
     * Tests the getId method of the Player class.
     */
    @Test
    public void testPlayerId() {
        int id = playerUnderTest.getId();
        assertEquals(playerId, id);
    }

    /**
     * Tests the getName method of the Player class.
     */
    @Test
    public void testPlayerName() {
        String name = playerUnderTest.getName();
        assertEquals(playerName, name);
    }

    /**
     * Tests the deployReinforcementArmiesFromPlayer method with valid army count.
     */
    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(playerUnderTest.deployReinforcementArmiesFromPlayer(validArmyCount));
    }

    /**
     * Tests the deployReinforcementArmiesFromPlayer method with invalid army count.
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(playerUnderTest.deployReinforcementArmiesFromPlayer(invalidArmyCount));
    }
}
