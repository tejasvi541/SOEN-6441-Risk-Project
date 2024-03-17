package org.team21.game.utils.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.MapLoaderController;
import org.team21.game.models.map.GameMap;

import static org.junit.Assert.assertTrue;

/**
 * The type Map validator test.
 *
 * @author Tejasvi
 */
public class MapValidatorTest {
    /**
     * The D game map.
     */
    GameMap d_GameMap; // Game Map Object
    /**
     * The Map loader controller.
     */
    MapLoaderController d_MapLoaderController; // MapLoader Object to Load Map
    /**
     * The Validator.
     */
    MapValidator d_Validator; // Map Validator Object

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_GameMap = new GameMap();
        d_MapLoaderController = new MapLoaderController();
        d_Validator = new MapValidator();
    }

    /**
     * Validate map object.
     */
    @Test
    public void validateMapObject() {
        d_GameMap = d_MapLoaderController.readMap("canada");
        assertTrue(d_Validator.validateMapObject(d_GameMap));
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