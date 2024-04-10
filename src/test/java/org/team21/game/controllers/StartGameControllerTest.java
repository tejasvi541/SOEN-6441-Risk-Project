
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
/**
 * This class provides unit tests for the {@link StartGameController} class.
 * @author Tejasvi
 */
public class StartGameControllerTest {
    GameMap d_gameMap;
    StartGameController l_sgc;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a new {@link StartGameController} instance and obtains the singleton instance of {@link GameMap}.
     *
     * @throws Exception if there is an error during setup.
     */
    @Before
    public void setUp() throws Exception {
        l_sgc = new StartGameController();
        d_gameMap = GameMap.getInstance();
    }

    /**
     * Tests the {@link StartGameController#loadMap(String)} method for loading a map in Domination format.
     * Verifies that the map is loaded successfully.
     *
     * @throws ValidationException if there is an error in map validation.
     */
    @Test
    public void loadMapDominationOkay() throws ValidationException {
        l_sgc.loadMap("Australia.map");
        assertNotNull(d_gameMap);
    }

    /**
     * Tests the {@link StartGameController#loadMap(String)} method for loading a map in Conquest format.
     * Verifies that the map is loaded successfully.
     *
     * @throws ValidationException if there is an error in map validation.
     */
    @Test
    public void loadMapConquest() throws ValidationException {
        l_sgc.loadMap("Australia.map");
        assertNotNull(d_gameMap);
    }

    /**
     * Tests the {@link StartGameController#inputValidator(List)} method with valid input.
     * Verifies that the input is validated correctly and returns true.
     */
    @Test
    public void validInputValidator() {
        List<String> valid = new ArrayList<>();
        valid.add("loadmap");
        valid.add("Australia");
        assertTrue(l_sgc.inputValidator(valid));
    }

    /**
     * Tests the {@link StartGameController#inputValidator(List)} method with invalid input.
     * Verifies that the input is validated correctly and returns false.
     */
    @Test
    public void invalidInputValidator() {
        List<String> invalid = new ArrayList<>();
        invalid.add("lodmap");
        invalid.add("Australia");
        assertFalse(l_sgc.inputValidator(invalid));
    }
}
