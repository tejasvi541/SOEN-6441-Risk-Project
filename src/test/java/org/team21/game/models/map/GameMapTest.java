package org.team21.game.models.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.Player;

import static org.junit.Assert.*;

public class GameMapTest {

    private GameMap gameMap, gameMap1;

    private Player p1;

    @Before
    public void setUp() {
        gameMap = new GameMap();
        gameMap1 = new GameMap("Canada");
        p1 = new Player();
    }

    @Test
    public void isValidName(){
        assertEquals("", gameMap.get_mapName());
    }

    @Test
    public void isValidNameMap(){
        assertEquals("Canada", gameMap1.get_mapName());
    }

    @Test
    public void validPlayer(){
        gameMap.addPlayer("Player1");
        assertEquals("Player1", gameMap.getPlayers().keySet().toArray()[0]);
    }

    @After
    public void tearDown() {
        gameMap = null;
        gameMap1 = null;
    }
}