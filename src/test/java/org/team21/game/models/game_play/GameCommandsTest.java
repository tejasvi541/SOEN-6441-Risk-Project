package org.team21.game.models.game_play;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameCommandsTest {

    /**
     * This class contains unit tests for the GameCommands class.
     *
     * @author Yesha
     */
    @Test
    public void testFromString() {
        // Test valid command
        assertEquals(GameCommands.SHOW_MAP, GameCommands.fromString("showmap"));

        // Test valid command with different case
        assertEquals(GameCommands.LOAD_MAP, GameCommands.fromString("loadmap"));

        // Test Ensures case sensitivity
        assertNotEquals(GameCommands.GAME_PLAYER,"gamePlayer");

        // Test invalid command
        assertNull(GameCommands.fromString("LoadMap"));

        // Test invalid command
        assertNull(GameCommands.fromString("invalid"));

        // Test invalid command
        assertNull(GameCommands.fromString("Hello"));

        // Test null command
        assertNull(GameCommands.fromString(null));
    }
}