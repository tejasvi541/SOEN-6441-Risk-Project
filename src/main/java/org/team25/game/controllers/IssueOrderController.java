package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GamePhase;

public class IssueOrderController implements GameFlowManager {
    /**
     * Starts the game controller.
     *
     * @param p_currentPhase The current phase of the game.
     * @return The next phase of the game.
     * @throws Exception If an issue occurs during execution.
     */
    @Override
    public GamePhase start(GamePhase p_currentPhase) throws Exception {
        return null;
    }
}
