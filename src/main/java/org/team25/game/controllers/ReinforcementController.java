package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.models.game_play.Player;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;
import org.team25.game.utils.validation.InvalidExecutionException;
import org.team25.game.utils.validation.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReinforcementController implements GameFlowManager {
    /**
     * The next phase of the game.
     */
    private GamePhase d_NextGamePhase = GamePhase.IssueOrder;

    /**
     * The current phase of the game.
     */
    private GamePhase d_GamePhase;

    /**
     * The game map.
     */
    private GameMap d_GameMap;

    /**
     * The current player.
     */
    private Player d_CurrentPlayer;

    /**
     * Default constructor initializing the game map data member with
     * {@code GameMap} singleton object.
     */
    public ReinforcementController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Begins the Reinforcement phase of the game.
     *
     * @param p_GamePhase The current game phase.
     * @return The next game phase upon successful execution.
     * @throws ValidationException       If there is invalid input or output.
     * @throws InvalidExecutionException If the game phase command is invalid.
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws InvalidExecutionException {
        d_GamePhase = p_GamePhase;
        calculateReinforcements();
        return d_NextGamePhase;
    }

    /**
     * Calculates reinforcements for each player.
     *
     * @throws InvalidExecutionException If the game phase command is invalid.
     */
    public void calculateReinforcements() throws InvalidExecutionException {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_Player;
            setReinforcementTroops();
        }
    }

    /**
     * Sets reinforcement armies for the current player based on captured countries.
     *
     * @throws InvalidExecutionException If the game phase command is invalid.
     */
    public void setReinforcementTroops() throws InvalidExecutionException {
        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
            if (!d_CurrentPlayer.getCapturedCountries().isEmpty()) {
                int reinforcements = (int) Math.floor(d_CurrentPlayer.getCapturedCountries().size() / 3f);
                Map<String, List<Country>> l_CountryMap = d_CurrentPlayer.getCapturedCountries()
                        .stream()
                        .collect(Collectors.groupingBy(Country::get_parentContinent));
                for (String continent : l_CountryMap.keySet()) {

                     if(d_GameMap.getContinent(continent)!=null){
                         if (d_GameMap.getContinent(continent).get_countries().size() == l_CountryMap.get(continent).size()) {
                             reinforcements += d_GameMap.getContinent(continent).getAwardArmies();
                         }
                     }
                }
                d_CurrentPlayer.setReinforcementArmies(Math.max(reinforcements, 3));
                System.out.println("Player " + d_CurrentPlayer.getName() + " receives " + d_CurrentPlayer.getReinforcementArmies() + " armies for reinforcements.");
            } else {
                d_CurrentPlayer.setReinforcementArmies(3);
                System.out.println("Player " + d_CurrentPlayer.getName() + " receives " + d_CurrentPlayer.getReinforcementArmies() + " armies for reinforcements.");
            }
        } else {
            throw new InvalidExecutionException("Invalid game phase for reinforcement.");
        }
    }
}
