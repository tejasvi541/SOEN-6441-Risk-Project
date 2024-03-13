package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.utils.validation.ValidationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * The type Start game controller test.
 *
 * @author Meet
 */
public class StartGameControllerTest {

    /**
     * Original system in for cmd input
     */
    private final InputStream originalSystemIn = System.in;
    /**
     * Original system out for Print stream
     */
    private final PrintStream originalSystemOut = System.out;
    /**
     * The Game phase.
     */
    GamePhase gamePhase;
    /**
     * Test input
     */
    private ByteArrayInputStream testIn;
    /**
     * Test output
     */
    private ByteArrayOutputStream testOut;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        // Redirect System.out to a different PrintStream
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Restore System.out and System.in after the test
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }

    /**
     * Test run.
     *
     * @throws ValidationException the validation exception
     */
    @Test
    public void testRun() throws ValidationException {
        // Prepare test input
        String input = "exit"; // Simulate user input "exit"
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Call the method under test
        StartGameController startGame = new StartGameController();
        assertEquals(GamePhase.Reinforcement, startGame.start(gamePhase.StartUp));
    }

    /**
     * False test run.
     *
     * @throws ValidationException the validation exception
     */
    @Test
    public void falseTestRun() throws ValidationException {
        // Prepare test input
        String input = "exit"; // Simulate user input "exit"
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Call the method under test
        StartGameController startGame = new StartGameController();
        assertNotEquals(GamePhase.IssueOrder, startGame.start(gamePhase.StartUp));
    }

}