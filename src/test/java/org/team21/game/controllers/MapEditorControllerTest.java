package org.team21.game.controllers;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;

import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.map.GameMap;

/**
 * JUnit test class for {@link MapEditorController}.
 */

public class MapEditorControllerTest {

    /** The game phase. */
    GamePhase gamePhase;

    /** The game map. */
    GameMap gameMap = new GameMap();

    /** The map loader controller. */
    MapLoaderController load = new MapLoaderController();

    /**
     * Sets up the test fixture.
     */
    @Before
    public void setUp() {
        gameMap = load.readMap("canada");
    }

    /**
     * Tests the {@link MapEditorController#validateUserInput(List)} method.
     */
    @Test
    public void validateInput() {
        MapEditorController editor = new MapEditorController(gameMap);
        List<String> input = Arrays.asList("showmap");
        List<String> input_false = Arrays.asList("Watchmap");
        assertTrue(editor.validateUserInput(input));
        assertFalse(editor.validateUserInput(input_false));
    }

    /**
     * Tests the {@link MapEditorController#action(List, GamePhase)} method.
     */
    @Test
    public void applyActionMap(){
        MapEditorController editor = new MapEditorController(gameMap);
        List<String> input = Arrays.asList("showmap");
        assertNotNull(editor.action(input, gamePhase.MapEditor));
    }

    /**
     * Tears down the test fixture.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        gameMap = null;
    }
}