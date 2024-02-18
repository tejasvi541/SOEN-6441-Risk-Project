package org.team25.game.controllers;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.utils.validation.ValidationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;


public class StartGameControllerTest {

    GamePhase gamePhase;
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() {
        // Redirect System.out to a different PrintStream
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void tearDown() {
        // Restore System.out and System.in after the test
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }

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

}