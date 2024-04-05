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
    GamePhase d_nextGamePhase = GamePhase.IssueOrder;
    /**
     * The game map instance.
     */
    GameMap d_gameMap;
    /**
     * Reinforcement object.
     */
    ReinforcementController d_reinforcement;

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
        d_gameMap = GameMap.getInstance();
        d_gameMap.addContinent("Asia", "4");
        d_gameMap.addContinent("Europe", "3");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addCountry("France", "Europe");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.assignCountries();
        d_reinforcement = new ReinforcementController();
        d_reinforcement.d_GamePhase = GamePhase.Reinforcement;
    }

    /**
     * This method will be executed at the end of the test.
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        d_gameMap.flushGameMap();
    }

    /**
     * Test the validation for next Game phase.
     *
     * @throws ValidationException        if validation fails
     * @throws InvalidExecutionException if execution is invalid
     */
    @Test
    public void testStartShouldReturnNextPhase() throws ValidationException, InvalidExecutionException {
        GamePhase nextPhase = d_reinforcement.start(GamePhase.Reinforcement);
        assertEquals(d_nextGamePhase, nextPhase);
    }

    /**
     * Test if valid reinforcements are set.
     *
     * @throws ValidationException        if validation fails
     * @throws InvalidExecutionException if execution is invalid
     */
    @Test
    public void testCheckReinforcementsSetOrNot() throws ValidationException, InvalidExecutionException {
        d_reinforcement.d_CurrentPlayer = d_gameMap.getPlayer("Player2");
        d_reinforcement.setReinforcementTroops();
        assertTrue(d_reinforcement.d_CurrentPlayer.getReinforcementArmies() >= 3);
    }
}
