package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.map.GameMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Map editor controller test.
 *
 * @author Meet
 */
public class MapEditorControllerTest {

    /**
     * The Game phase.
     */
    GamePhase gamePhase;
    /**
     * The Game map.
     */
    GameMap gameMap = new GameMap();
    /**
     * The Load.
     */
    MapLoaderController load = new MapLoaderController();

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        gameMap = load.readMap("canada");
    }

    /**
     * Validate input.
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
     * Apply action map.
     */
    @Test
    public void applyActionMap() {
        MapEditorController editor = new MapEditorController(gameMap);
        List<String> input = Arrays.asList("showmap");
        assertNotNull(editor.action(input, gamePhase.MapEditor));
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        gameMap = null;
    }
}