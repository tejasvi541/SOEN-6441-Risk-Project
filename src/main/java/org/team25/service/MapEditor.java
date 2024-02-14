package org.team25.service;

import java.util.List;

public interface MapEditor {

    public abstract GamePhase action(List<String> p_List);
    public abstract boolean validateUserInput(List<String> p_InputList);
}
