package org.team21.game.controllers;

import org.junit.Test;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameProgress;

import static org.junit.Assert.*;

/**
 * This class provide tests for the LoadGame functionality
 * @author Tejasvi
 */
public class LoadGameControllerTest {
    private GamePhase phase;

    /**
     * This Tests the loading of a game to a phase
     */
    @Test
    public void startPhase() {
        LoadGameController lgc = new LoadGameController();

        phase = GameProgress.LoadGameProgress("g1");

        assertNotEquals(phase, GamePhase.StartUp);
    }
}