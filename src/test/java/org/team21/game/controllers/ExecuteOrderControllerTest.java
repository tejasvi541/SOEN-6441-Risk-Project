package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;

import java.util.List;
import static org.junit.Assert.*;

public class ExecuteOrderControllerTest {

    GamePhase gamePhase;

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