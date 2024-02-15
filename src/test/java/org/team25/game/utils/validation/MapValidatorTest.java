package org.team25.game.utils.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.team25.game.controllers.MapLoader;
import org.team25.game.models.GameMap;

import static org.junit.Assert.*;

public class MapValidatorTest {
    GameMap d_gameMap; // Game Map Object
    MapLoader mapLoader; // MapLoader Object to Load Map
    MapValidator validator; // Map Validator Object
    @Before
    public void setUp() {
        d_gameMap = new GameMap();
        mapLoader = new MapLoader();
        validator = new MapValidator();
    }

    @Test
    public void validateMapObject() {
        d_gameMap = mapLoader.readMap("canada");
        assertTrue(validator.ValidateMapObject(d_gameMap));
    }
}