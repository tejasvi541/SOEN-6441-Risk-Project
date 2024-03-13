package org.team21.game.controllers;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.GameMap;


import static org.junit.Assert.*;

/**
 * This test class checks if the number and name of the loaded canada map is right or not
 *
 * @author Tejasvi
 */
public class MapLoaderControllerTest {
    /**
     * The D game map.
     */
    GameMap d_gameMap;
    /**
     * The Map loader controller.
     */
    MapLoaderController mapLoaderController;
    /**
     * The D map name.
     */
    String d_mapName;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_gameMap = new GameMap();
        mapLoaderController = new MapLoaderController();
        d_mapName = "";
    }

    /**
     * Read map.
     */
    @Test
    public void readMap() {
        d_mapName = "canada";
        d_gameMap = mapLoaderController.readMap(d_mapName);
        String[] continents = {"Atlantic_Provinces", "Ontario_and_Quebec", "Western_Provinces-South", "Western_Provinces-North", "Nunavut", "Northwestern_Territories"};
        int i=0;
        while(i<6){
            assertTrue(d_gameMap.getContinents().containsKey(continents[i].toLowerCase()));
            i++;
        }
    }
}