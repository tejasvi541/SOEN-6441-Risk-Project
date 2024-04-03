package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.utils.validation.InvalidExecutionException;
import org.team21.game.utils.validation.ValidationException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionalities for Reinforcement phase.
 * It includes tests for starting the next game phase and setting valid reinforcements.
 *
 * @author Tejasvi
 */
public class ReinforcementTest extends Player {
    /**
     * The next game phase.
     */
    GamePhase nextGamePhase = GamePhase.IssueOrder;
    /**
     * The game map instance.
     */
    GameMap gameMap;
    /**
     * Reinforcement object.
     */
    ReinforcementController reinforcement;

    /**
     * Constructor for Reinforcement Test class.
     */
    public ReinforcementTest() {
        super(PlayerStrategy.getStrategy("human"));
    }

    /**
     * This method initializes the values for continents, countries, and players
     * before execution of every test case.
     *
     * @throws Exception if initialization fails
     */
    @Before
    public void setUp() throws Exception {
        gameMap = GameMap.getInstance();
        gameMap.addContinent("Asia", "4");
        gameMap.addContinent("Europe", "3");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("China", "Asia");
        gameMap.addCountry("France", "Europe");
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        gameMap.assignCountries();
        reinforcement = new ReinforcementController();
        reinforcement.d_GamePhase = GamePhase.Reinforcement;
    }

    /**
     * This method will be executed at the end of the test.
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        gameMap.flushGameMap();
    }

    /**
     * Test the validation for next Game phase.
     *
     * @throws ValidationException        if validation fails
     * @throws InvalidExecutionException if execution is invalid
     */
    @Test
    public void testStartShouldReturnNextPhase() throws ValidationException, InvalidExecutionException {
        GamePhase nextPhase = reinforcement.start(GamePhase.Reinforcement);
        assertEquals(nextGamePhase, nextPhase);
    }

    /**
     * Test if valid reinforcements are set.
     *
     * @throws ValidationException        if validation fails
     * @throws InvalidExecutionException if execution is invalid
     */
    @Test
    public void testCheckReinforcementsSetOrNot() throws ValidationException, InvalidExecutionException {
        reinforcement.d_CurrentPlayer = gameMap.getPlayer("Player2");
        reinforcement.setReinforcementTroops();
        assertTrue(reinforcement.d_CurrentPlayer.getReinforcementArmies() >= 3);
    }
}
