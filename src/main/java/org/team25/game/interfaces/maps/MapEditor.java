package org.team25.game.interfaces.maps;

import org.team25.game.models.game_play.GamePhase;
import org.team25.game.models.map.GameMap;

import java.util.List;

public interface MapEditor {

    /**
     * To take action on input map command
     * @param p_List to hold user input command
     * @return boolean: true if edit is successful
     */
    public abstract GamePhase action(List<String> p_List,GamePhase p_CurrentGamePhase);

    /**
     * To validate the user input command
     * @param p_InputList to hold user input command
     * @return boolean: true if command is valid CLI command
     */
    public abstract boolean validateUserInput(List<String> p_InputList);
}
