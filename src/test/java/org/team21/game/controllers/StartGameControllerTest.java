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
    private final InputStream d_OriginalSystemIn = System.in;
    /**
     * Original system out for Print stream
     */
    private final PrintStream d_OriginalSystemOut = System.out;
    /**
     * The Game phase.
     */
    GamePhase d_GamePhase;
    /**
     * Test input
     */
    private ByteArrayInputStream d_TestIn;
    /**
     * Test output
     */
    private ByteArrayOutputStream d_TestOut;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        // Redirect System.out to a different PrintStream
        d_TestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_TestOut));
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Restore System.out and System.in after the test
        System.setOut(d_OriginalSystemOut);
        System.setIn(d_OriginalSystemIn);
    }

    /**
     * Test run.
     *
     * @throws ValidationException the validation exception
     */
    @Test
    public void testRun() throws ValidationException {
        // Prepare test input
        String l_Input = "exit"; // Simulate user input "exit"
        d_TestIn = new ByteArrayInputStream(l_Input.getBytes());
        System.setIn(d_TestIn);

        // Call the method under test
        StartGameController l_StartGame = new StartGameController();
        assertEquals(GamePhase.Reinforcement, l_StartGame.start(d_GamePhase.StartUp));
    }

    /**
     * False test run.
     *
     * @throws ValidationException the validation exception
     */
    @Test
    public void falseTestRun() throws ValidationException {
        // Prepare test input
        String l_Input = "exit"; // Simulate user input "exit"
        d_TestIn = new ByteArrayInputStream(l_Input.getBytes());
        System.setIn(d_TestIn);

        // Call the method under test
        StartGameController L_StartGame = new StartGameController();
        assertNotEquals(GamePhase.IssueOrder, L_StartGame.start(d_GamePhase.StartUp));
    }

}