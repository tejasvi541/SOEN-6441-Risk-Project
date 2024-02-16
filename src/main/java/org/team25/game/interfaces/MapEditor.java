package org.team25.game.interfaces;

import java.util.List;

public interface MapEditor {

    /**
     * To take action on input map command
     * @param p_List to hold user input command
     * @return boolean: true if edit is successful
     */
    public abstract boolean action(List<String> p_List);

    /**
     * To validate the user input command
     * @param p_InputList to hold user input command
     * @return boolean: true if command is valid CLI command
     */
    public abstract boolean validateUserInput(List<String> p_InputList);
}
