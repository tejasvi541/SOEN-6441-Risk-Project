package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.validation.InvalidExecutionException;
import org.team21.game.utils.validation.ValidationException;

/**
 * This class represents the controller responsible for managing the Reinforcement phase of the game.
 * It implements the {@code GameFlowManager} interface.
 * This controller handles the calculation and distribution of reinforcement armies to players based on the number of countries they control.
 *
 * @author : Yesha Shah
 * @version : 1.0.0
 */
public class ReinforcementController implements GameFlowManager {

    /**
     * Data member holding the next phase of the game.
     */
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;

    /**
     * Data member holding the current phase of the game.
     */
    GamePhase d_GamePhase;

    /**
     * Data member representing the game map.
     */
    GameMap d_GameMap;

    /**
     * Data member representing the current player.
     */
    Player d_CurrentPlayer;

    /**
     * Default constructor that initializes the game map data member with the {@code GameMap} singleton object.
     */
    public ReinforcementController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Begins the Reinforcement phase of the game.
     *
     * @param p_GamePhase The current game phase.
     * @return The next game phase upon successful execution.
     * @throws ValidationException       if there is invalid input or output.
     * @throws InvalidExecutionException if there is an invalid game phase command.
     */
    @Override
    public GamePhase startPhase(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        if (GameSettings.getInstance().MAX_TRIES != 0) {
            d_GameMap.nextTry();
        }
        d_GamePhase = p_GamePhase;
        calculateReinforcements();
        d_GameMap.setGamePhase(d_NextGamePhase);
        return d_NextGamePhase;
    }

    /**
     * Calculates and sets reinforcement armies for each player.
     *
     * @throws InvalidExecutionException if there is an invalid game phase command.
     */
    public void calculateReinforcements() throws InvalidExecutionException {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_Player;
            setReinforcementTroops();
        }
    }

    /**
     * Calculates the reinforcement armies for the current player based on the number of countries they control.
     *
     * @throws InvalidExecutionException if there is an invalid game phase command.
     */
    public void setReinforcementTroops() throws InvalidExecutionException {
        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
            d_CurrentPlayer.calculateReinforcementArmies(d_GameMap);
        } else {
            throw new InvalidExecutionException();
        }
    }
}
