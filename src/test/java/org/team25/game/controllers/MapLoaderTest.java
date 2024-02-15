package org.team25.game.controllers;

import org.junit.Before;
import org.junit.Test;
import org.team25.game.models.GameMap;


import static org.junit.Assert.*;

/**
 * This test class checks if the number and name of the loaded canada map is right or not
 * @author Tejasvi
 */
public class MapLoaderTest {
    GameMap d_gameMap;
    MapLoader mapLoader;
    String d_mapName;

    @Before
    public void setUp() {
        d_gameMap = new GameMap();
        mapLoader = new MapLoader();
        d_mapName = "";
    }

    @Test
    public void readMap() {
        d_mapName = "Canada";
        d_gameMap = mapLoader.readMap(d_mapName);
        String[] continents = {"Atlantic_Provinces", "Ontario_and_Quebec", "Western_Provinces-South", "Western_Provinces-North", "Nunavut", "Northwestern_Territories"};
        int i=0;
        while(i<6){
            assertTrue(d_gameMap.get_continents().containsKey(continents[i].toLowerCase()));
            i++;
        }
    }
}