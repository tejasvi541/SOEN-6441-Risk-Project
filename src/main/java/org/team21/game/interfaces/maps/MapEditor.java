package org.team21.game.interfaces.maps;

import org.team21.game.models.game_play.GamePhase;

import java.util.List;

/**
 * This is the Interface for the Mapeditor
 * @author Bharti
 */
public interface MapEditor {

    /**
     * To take action on input map command
     * @param p_List to hold user input command
     * @param p_CurrentGamePhase holds current phase of the game
     * @return boolean: true if edit is successful
     */
    GamePhase action(List<String> p_List,GamePhase p_CurrentGamePhase);

    /**
     * To validate the user input command
     * @param p_InputList to hold user input command
     * @return boolean: true if command is valid CLI command
     */
    boolean validateUserInput(List<String> p_InputList);
}
