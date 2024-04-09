package org.team21.game.controllers;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.adapter.Adaptee;
import org.team21.game.utils.adapter.Adapter;
import org.team21.game.utils.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StartGameControllerTest {
    GameMap d_gameMap;
    StartGameController l_sgc;
    @Before
    public void setUp() throws Exception {
        l_sgc = new StartGameController();
        d_gameMap = GameMap.getInstance();
    }

    @Test
    public void loadMapDominationOkay() throws ValidationException {
        l_sgc.loadMap("Australia.map");
        assertNotNull(d_gameMap);
    }

    @Test
    public void loadMapConquest() throws ValidationException {
        l_sgc.loadMap("Australia.map");
        assertNotNull(d_gameMap);
    }

    @Test
    public void validInputValidator() {
        List<String> valid = new ArrayList<>();
        valid.add("loadmap");
        valid.add("Australia");
        assertTrue(l_sgc.inputValidator(valid));

    }
    @Test
    public void invalidInputValidator() {
        List<String> invalid = new ArrayList<>();
        invalid.add("lodmap");
        invalid.add("Australia");
        assertFalse(l_sgc.inputValidator(invalid));

    }
}