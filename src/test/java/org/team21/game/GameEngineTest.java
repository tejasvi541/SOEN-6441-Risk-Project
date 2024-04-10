package org.team21.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.game_engine.GameEngine;
import org.team21.game.game_engine.GamePhase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test case class to check the correct starting phase of game
 *
 * @author yesha shah
 */
public class GameEngineTest {
    /**
     * Game Engine object
     */
    GameEngine d_GameEngine;

    /**
     * Method to set up the basics for each test case
     *
     * @throws Exception if any exception occurs
     */
    @Before
    public void setUp() throws Exception {
        d_GameEngine = new GameEngine();
    }

    /**
     * Method to tear down the basics after each test case ran
     *
     * @throws Exception if any exception occurs
     */
    @After
    public void tearDown() throws Exception {
        d_GameEngine = null;
    }

    /**
     * Test case to check if the starting phase of game is Map Editor
     */
    @Test
    public void checkStartingPhaseOfGame() {
        assertEquals(GamePhase.MapEditor,d_GameEngine.d_GamePhase);
    }

    /**
     * Test case to check if starting phase of game can be some middle phases of game.
     */
    @Test
    public void checkInvalidStartingPhaseOfGame() {
        assertFalse(GamePhase.ExecuteOrder == d_GameEngine.d_GamePhase);
    }
}
