package org.team25.game.models.game_play;

/**
 * Enum representing the commands used in the game mainly in
 * {org.team25.game.controllers.StartGameController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public enum GameCommands {
    /**
     * Show map game commands.
     */
    SHOW_MAP("showmap"),
    /**
     * Load map game commands.
     */
    LOAD_MAP("loadmap"),
    /**
     * Game player game commands.
     */
    GAME_PLAYER("gameplayer"),
    /**
     * Assign countries game commands.
     */
    ASSIGN_COUNTRIES("assigncountries"),
    /**
     * Exit game commands.
     */
    EXIT("exit");

    /**
     * Command value
     */
    private final String d_Command;

    /**
     * GameCommand constructor
     *
     * @param p_Command : Command to be passed
     */
    GameCommands(String p_Command) {
        this.d_Command = p_Command;
    }

    /**
     * Parses the d_Command string into a Command enum value.
     *
     * @param p_Command The d_Command string.
     * @return The corresponding Command enum value.
     */
    public static GameCommands fromString(String p_Command) {
        for (GameCommands l_Command : GameCommands.values()) {
            if (l_Command.d_Command.equals(p_Command)) {
                return l_Command;
            }
        }
        return null;
    }
}
