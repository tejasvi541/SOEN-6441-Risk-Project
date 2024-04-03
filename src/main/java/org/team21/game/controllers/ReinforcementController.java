package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.validation.InvalidExecutionException;
import org.team21.game.utils.validation.ValidationException;

/**
 * Controller for {@code Reinforcement} phase of game.
 *
 * @version 1.0.0
 */
public class ReinforcementController implements GameFlowManager {
    /**
     * Data Member holding next phase of game
     */
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    /**
     * Data Member holding current phase of game
     */
    GamePhase d_GamePhase;
    /**
     * Data Member for Game map
     */
    GameMap d_GameMap;

    /**
     * Data Member for Current Player
     */
    Player d_CurrentPlayer;

    /**
     * Default constructor initialising the Game map data member with
     * {@code GameMap} singleton object
     */
    public ReinforcementController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Beginner method of the Reinforcement phase
     *
     * @param p_GamePhase holding the current game phase
     * @return Next game phase upon successful execution
     * @throws ValidationException       upon invalid input or output
     * @throws InvalidExecutionException upon invalid game phase command
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        if (GameSettings.getInstance().MAX_TRIES != 0) {
            d_GameMap.nextTry();
        }
        d_GamePhase = p_GamePhase;
        calculateReinforcements();
        d_GameMap.setGamePhase(d_NextGamePhase);
        return d_NextGamePhase;
    }

    /**
     * Method to calculate and set reinforcement armies for each player.
     *
     * @throws InvalidExecutionException upon invalid game phase command
     */
    public void calculateReinforcements() throws InvalidExecutionException {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_Player;
            setReinforcementTroops();
        }
    }

    /**
     * Game Logic to calculate reinforcement armies for each player based on
     * number of countries captured.
     *
     * @throws InvalidExecutionException upon invalid game phase command
     */
    public void setReinforcementTroops() throws InvalidExecutionException {
        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
            d_CurrentPlayer.calculateReinforcementArmies(d_GameMap);
        } else throw new InvalidExecutionException();
    }
}
