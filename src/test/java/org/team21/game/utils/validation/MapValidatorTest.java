package org.team21.game.utils.validation;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.MapLoaderController;
import org.team21.game.models.map.GameMap;

import static org.junit.Assert.*;

public class MapValidatorTest {
    GameMap d_gameMap; // Game Map Object
    MapLoaderController mapLoaderController; // MapLoader Object to Load Map
    MapValidator validator; // Map Validator Object
    @Before
    public void setUp() {
        d_gameMap = new GameMap();
        mapLoaderController = new MapLoaderController();
        validator = new MapValidator();
    }

    @Test
    public void validateMapObject() {
        d_gameMap = mapLoaderController.readMap("canada");
        assertTrue(validator.ValidateMapObject(d_gameMap));
    }
}