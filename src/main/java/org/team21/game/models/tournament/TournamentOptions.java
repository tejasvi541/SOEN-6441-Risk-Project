package org.team21.game.models.tournament;

import org.team21.game.models.strategy.player.PlayerStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to implement the tournament options
 *
 * @author Nishith Soni
 */
public class TournamentOptions {

    /**
     * list object for map
     */
    private List<String> d_Map = new ArrayList<>();
    /**
     * hash set object to hold player strategy
     */
    private Set<PlayerStrategy> d_PlayerStrategies = new HashSet<>();
    /**
     * number of games
     */
    private int d_Games;
    /**
     * maximum number of tries
     */
    private int d_MaxTries;

    /**
     * get the map
     *
     * @return the map
     */
    public List<String> getMap() {
        return d_Map;
    }

    /**
     * get the player strategies
     *
     * @return player strategies
     */
    public Set<PlayerStrategy> getPlayerStrategies() {
        return d_PlayerStrategies;
    }

    /**
     * To get the games
     *
     * @return number of games
     */
    public int getGames() {
        return d_Games;
    }

    /**
     * Set the games
     *
     * @param p_Games hold the number of games
     */
    public void setGames(int p_Games) {
        d_Games = p_Games;
    }

    /**
     * To get the maximum tries in a tournament
     *
     * @return the maximum tries
     */
    public int getMaxTries() {
        return d_MaxTries;
    }

    /**
     * To set the maximum tries in a tournament
     *
     * @param p_MaxTries hold the maximum tries
     */
    public void setMaxTries(int p_MaxTries) {
        d_MaxTries = p_MaxTries;
    }
}
