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
    GameMap d_GameMap;
    /**
     * The Map loader controller.
     */
    MapLoaderController d_MapLoaderController;
    /**
     * The D map name.
     */
    String d_MapName;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_GameMap = new GameMap();
        d_MapLoaderController = new MapLoaderController();
        d_MapName = "";
    }

    /**
     * Read map.
     */
    @Test
    public void readMap() {
        d_MapName = "canada";
        d_GameMap = d_MapLoaderController.readMap(d_MapName);
        String[] l_Continents = {"Atlantic_Provinces", "Ontario_and_Quebec", "Western_Provinces-South", "Western_Provinces-North", "Nunavut", "Northwestern_Territories"};
        int l_I=0;
        while(l_I<6){
            assertTrue(d_GameMap.getContinents().containsKey(l_Continents[l_I].toLowerCase()));
            l_I++;
        }
    }
}