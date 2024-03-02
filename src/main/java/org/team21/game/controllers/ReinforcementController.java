package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.validation.InvalidExecutionException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Reinforcement order controller will give armies/reinforcements linked with
 * {IssueOrderController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class ReinforcementController implements GameFlowManager {
    /**
     * The next phase of the game.
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.IssueOrder;
    /**
     * The game map.
     */
    private final GameMap d_GameMap;
    /**
     * The current phase of the game.
     */
    private GamePhase d_CurrentGamePhase;
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
     * @param p_CurrentGamePhase The current game phase.
     * @return The next game phase upon successful execution.
     */
    @Override
    public GamePhase start(GamePhase p_CurrentGamePhase) {
        return run(p_CurrentGamePhase);
    }

    /**
     * run is entry method of Reinforcement Controller and it will run Issue order
     *
     * @param p_CurrentGamePhase : Current phase of game.
     * @return : It will return game phase to go next
     */
    private GamePhase run(GamePhase p_CurrentGamePhase)  {
        d_CurrentGamePhase = p_CurrentGamePhase;
        try {
            computerReinforcements();
        } catch (InvalidExecutionException e) {
            throw new RuntimeException(e);
        }
        return d_UpcomingGamePhase;
    }

    /**
     * Calculates reinforcements for each player.
     *
     * @throws InvalidExecutionException If the game phase command is invalid.
     */
    public void computerReinforcements() throws InvalidExecutionException {
        for (Player l_GamePlayer : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_GamePlayer;
            assignReinforcementTroops();
        }
    }

    /**
     * Sets reinforcement armies for the current player based on captured countries.
     *
     * @throws InvalidExecutionException If the game phase command is invalid.
     */
    public void assignReinforcementTroops() throws InvalidExecutionException {
        if (d_CurrentGamePhase.equals(GamePhase.Reinforcement)) {
            if (!d_CurrentPlayer.getCapturedCountries().isEmpty()) {
                int l_Reinforcements = (int) Math.floor(d_CurrentPlayer.getCapturedCountries().size() / 3f);
                Map<String, List<Country>> l_CountryMap = d_CurrentPlayer.getCapturedCountries()
                        .stream()
                        .collect(Collectors.groupingBy(Country::get_parentContinent));
                for (String l_Continent : l_CountryMap.keySet()) {

                    if (d_GameMap.getContinent(l_Continent) != null) {
                        if (d_GameMap.getContinent(l_Continent).getCountries().size() == l_CountryMap.get(l_Continent).size()) {
                            l_Reinforcements += d_GameMap.getContinent(l_Continent).getAwardArmies();
                        }
                    }
                }
                d_CurrentPlayer.setReinforcementArmies(Math.max(l_Reinforcements, 5));
                System.out.println("Player " + d_CurrentPlayer.getName() + " receives " + d_CurrentPlayer.getReinforcementArmies() + " armies for reinforcements.");
            } else {
                d_CurrentPlayer.setReinforcementArmies(5);
                System.out.println("Player " + d_CurrentPlayer.getName() + " receives " + d_CurrentPlayer.getReinforcementArmies() + " armies for reinforcements.");
            }
        } else {
            throw new InvalidExecutionException(Constants.INVALID_GAME_PHASE);
        }
    }
}
