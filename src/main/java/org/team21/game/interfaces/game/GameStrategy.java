package org.team21.game.interfaces.game;

import org.team21.game.models.map.Country;
import org.team21.game.models.map.Player;

/**
 * The GameStrategy interface defines the contract for implementing game strategies.
 * It provides methods for executing attack logic and managing ownership of conquered territories.
 * This interface allows different strategies to be defined and implemented for handling game actions and outcomes.
 *
 * @author Yesha Shah
 */
public interface GameStrategy {

    /**
     * Executes the default attack logic between two countries.
     *
     * @param p_Player The player initiating the attack.
     * @param p_From   The country from which the attack is initiated.
     * @param p_To     The country being attacked.
     * @param p_Armies The number of armies involved in the attack.
     * @return {@code true} if the attack is successful, {@code false} otherwise.
     */
    boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies);

    /**
     * Transfers ownership of conquered territories to the specified player.
     *
     * @param p_Player  The player to whom ownership of the territory is transferred.
     * @param p_Country The country that has been conquered.
     */
    default void makeMeKing(Player p_Player, Country p_Country) {
        p_Country.getPlayer().getCapturedCountries().remove(p_Country);
        p_Country.setPlayer(p_Player);
        p_Player.getCapturedCountries().add(p_Country);
    }
}
