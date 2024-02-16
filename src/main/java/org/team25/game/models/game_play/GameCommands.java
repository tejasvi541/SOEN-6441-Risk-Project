package org.team25.game.models.game_play;

/**
 * Enum representing the commands used in the game.
 */
public enum GameCommands {
    SHOW_MAP("showmap"),
    LOAD_MAP("loadmap"),
    GAME_PLAYER("gameplayer"),
    ASSIGN_COUNTRIES("assigncountries"),
    EXIT("exit");

    private final String command;

    GameCommands(String command) {
        this.command = command;
    }

    /**
     * Parses the command string into a Command enum value.
     *
     * @param str The command string.
     * @return The corresponding Command enum value.
     */
    public static GameCommands fromString(String str) {
        for (GameCommands cmd : GameCommands.values()) {
            if (cmd.command.equals(str)) {
                return cmd;
            }
        }
        return null;
    }
}
