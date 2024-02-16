package org.team25.game.interfaces.maps;

import java.util.List;

public interface MapEditor {

    public abstract boolean action(List<String> p_List);
    public abstract boolean validateUserInput(List<String> p_InputList);
}
