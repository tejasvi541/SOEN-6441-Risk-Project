package org.team21.game.models.strategy.player;

import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implements the Cheater player strategy, which aggressively conquers neighboring countries and doubles the army count of its own countries.
 * This strategy focuses on expanding its territory rapidly and overpowering nearby enemies.
 *
 * @author Bharti Chhabra
 */
public class CheaterStrategy extends PlayerStrategy implements Serializable {

    /**
     * Logger for game events
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Creates commands for the Cheater player strategy.
     *
     * @return "null" to indicate no further action.
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Cheater Player - " + d_Player.getName());
        Player l_NeighborOwner = null;
        List<Country> l_Enemies = new ArrayList<>();
        //find and conquer neighbor countries
        for (Country l_Country : d_Player.getCapturedCountries()) {
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_NeighborOwner = l_Neighbor.getPlayer();
                    l_NeighborOwner.getCapturedCountries().remove(l_Neighbor);
                    l_Enemies.add(l_Neighbor);
                    l_Neighbor.setPlayer(d_Player);
                    d_Logger.log("Conquered the neighbor country of enemy - " + l_Neighbor.getName());
                }
            }
        }
        d_Player.getCapturedCountries().addAll(l_Enemies);

        //double the army of a country if it has an enemy
        for (Country l_Country : d_Player.getCapturedCountries()) {
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_Country.setArmies(l_Country.getArmies() * 2);
                    d_Logger.log("Armies doubled in Cheater Player's country" + l_Country.getName());
                }
            }
        }
        return Constants.PASS_COMMAND;
    }
}
