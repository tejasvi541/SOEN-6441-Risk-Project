package org.team21.game.controllers;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.utils.validation.ValidationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * JUnit test class for StartGameController.
 */
public class StartGameControllerTest {

    GamePhase gamePhase;
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    /**
     * Sets up the test environment.
     * Redirects System.out to a different PrintStream for testing.
     */
    @Before
    public void setUp() {
        // Redirect System.out to a different PrintStream
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    /**
     * Restores the original System.out and System.in after the test.
     */
    @After
    public void tearDown() {
        // Restore System.out and System.in after the test
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }

    /**
     * Tests the run method of StartGameController.
     *
     * @throws ValidationException if validation fails
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
     * Tests the run method of StartGameController with a false assertion.
     *
     * @throws ValidationException if validation fails
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