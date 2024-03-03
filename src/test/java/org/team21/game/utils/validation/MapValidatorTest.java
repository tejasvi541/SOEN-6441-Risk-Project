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
    GameMap d_gameMap; // Game Map Object
    /**
     * The Map loader controller.
     */
    MapLoaderController mapLoaderController; // MapLoader Object to Load Map
    /**
     * The Validator.
     */
    MapValidator validator; // Map Validator Object

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_gameMap = new GameMap();
        mapLoaderController = new MapLoaderController();
        validator = new MapValidator();
    }

    /**
     * Validate map object.
     */
    @Test
    public void validateMapObject() {
        d_gameMap = mapLoaderController.readMap("canada");
        assertTrue(validator.validateMapObject(d_gameMap));
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        d_gameMap = null;
    }
}