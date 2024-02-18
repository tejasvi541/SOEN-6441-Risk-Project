package org.team21.game.controllers;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;

import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.map.GameMap;

public class MapEditorControllerTest {

    GamePhase gamePhase;
    GameMap gameMap = new GameMap();
    MapLoaderController load = new MapLoaderController();

    @Before
    public void setUp() {
        gameMap = load.readMap("canada");
    }

    @Test
    public void validateInput() {
        MapEditorController editor = new MapEditorController(gameMap);
        List<String> input = Arrays.asList("showmap");
        assertTrue(editor.validateUserInput(input));
    }

    @Test
    public void applyActionMap(){
        MapEditorController editor = new MapEditorController(gameMap);
        List<String> input = Arrays.asList("showmap");
        assertNotNull(editor.action(input, gamePhase.MapEditor));
    }

    @After
    public void tearDown() {
        // Clean up resources or release objects
        gameMap = null;
    }
}