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
    GamePhase d_GamePhase;
    /**
     * The Game map.
     */
    GameMap d_GameMap = new GameMap();
    /**
     * The Load.
     */
    MapLoaderController d_MapLoaderController = new MapLoaderController();

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_GameMap = d_MapLoaderController.readMap("canada");
    }

    /**
     * Validate input.
     */
    @Test
    public void validateInput() {
        MapEditorController l_MapEditorController = new MapEditorController(d_GameMap);
        List<String> l_Input = Arrays.asList("showmap");
        List<String> l_Input_false = Arrays.asList("Watchmap");
        assertTrue(l_MapEditorController.validateUserInput(l_Input));
        assertFalse(l_MapEditorController.validateUserInput(l_Input_false));
    }

    /**
     * Apply action map.
     */
    @Test
    public void applyActionMap() {
        MapEditorController l_MapEditorController = new MapEditorController(d_GameMap);
        List<String> l_Input = Arrays.asList("showmap");
        assertNotNull(l_MapEditorController.action(l_Input, d_GamePhase.MapEditor));
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        d_GameMap = null;
    }
}