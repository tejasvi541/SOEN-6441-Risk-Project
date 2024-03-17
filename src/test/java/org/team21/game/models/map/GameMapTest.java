package org.team21.game.models.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.Player;

import static org.junit.Assert.assertEquals;

/**
 * The type Game map test.
 *
 * @author Nishith
 */
public class GameMapTest {

    /**
     * Game map objects
     */
    private GameMap d_GameMap, d_GameMap1;
    /**
     * Player for map objects
     */
    private Player d_P;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_GameMap = new GameMap();
        d_GameMap1 = new GameMap("Canada");
        d_P = new Player();
    }

    /**
     * Is valid name.
     */
    @Test
    public void isValidName() {
        assertEquals("", d_GameMap.getMapName());
    }

    /**
     * Is valid name map.
     */
    @Test
    public void isValidNameMap() {
        assertEquals("Canada", d_GameMap1.getMapName());
    }

    /**
     * Valid player.
     */
    @Test
    public void validPlayer() {
        d_GameMap.addPlayer("Player1");
        assertEquals("Player1", d_GameMap.getPlayers().keySet().toArray()[0]);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        d_GameMap = null;
        d_GameMap1 = null;
    }
}