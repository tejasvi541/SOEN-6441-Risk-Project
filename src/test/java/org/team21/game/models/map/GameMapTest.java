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
    private GameMap gameMap, gameMap1;
    /**
     * Player for map objects
     */
    private Player p1;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        gameMap = new GameMap();
        gameMap1 = new GameMap("Canada");
        p1 = new Player();
    }

    /**
     * Is valid name.
     */
    @Test
    public void isValidName() {
        assertEquals("", gameMap.getMapName());
    }

    /**
     * Is valid name map.
     */
    @Test
    public void isValidNameMap() {
        assertEquals("Canada", gameMap1.getMapName());
    }

    /**
     * Valid player.
     */
    @Test
    public void validPlayer() {
        gameMap.addPlayer("Player1");
        assertEquals("Player1", gameMap.getPlayers().keySet().toArray()[0]);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        gameMap = null;
        gameMap1 = null;
    }
}