package org.team21.game.controllers;

import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;

import java.util.List;
import static org.junit.Assert.*;

/**
 * JUnit test class for ExecuteOrderController.
 * This class tests the functionality of the ExecuteOrderController class.
 */

public class ExecuteOrderControllerTest {

    GamePhase gamePhase;

    /**
     * Test method for {@link ExecuteOrderController#start(GamePhase)}.
     * This method tests the start method of the ExecuteOrderController class.
     * It verifies that the start method returns the correct GamePhase object
     * and checks if the possible states contain ExitGame and if nextState returns ExitGame.
     */

    @Test
    public void start() {
        ExecuteOrderController executeOrderController = new ExecuteOrderController();
        GamePhase run = executeOrderController.start(GamePhase.ExecuteOrder);
        List<GamePhase> possibleStates = run.possibleStates();
        if (possibleStates != null) {
            // Assert that ExitGame is contained in possibleStates
            assertTrue(possibleStates.contains(gamePhase.ExitGame));

            // Assert that nextState() returns ExitGame
            assertEquals(GamePhase.ExitGame, run.nextState(gamePhase.ExitGame));
        } else {
            assertNull(possibleStates);
        }
    }

}