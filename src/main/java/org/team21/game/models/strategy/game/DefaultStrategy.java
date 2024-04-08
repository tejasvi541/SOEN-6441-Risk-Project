package org.team21.game.models.strategy.game;

import org.team21.game.interfaces.game.GameStrategy;
import org.team21.game.models.cards.Card;
import org.team21.game.models.map.Country;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.models.map.Player;
import org.team21.game.utils.logger.GameEventLogger;

/**
 * Class representing the default strategy for the game's advance order.
 * This strategy executes the default attack logic using dice rolls.
 *
 * @author Yesha Shah
 */
public class DefaultStrategy implements GameStrategy {

    /**
     * The singleton instance of GameSettings.
     */
    GameSettings SETTINGS = GameSettings.getInstance();

    /**
     * The logger for recording game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Executes the default attack logic using dice rolls.
     *
     * @param p_Player The player initiating the attack.
     * @param p_From   The country from which the attack is initiated.
     * @param p_To     The target country of the attack.
     * @param p_Armies The number of armies involved in the attack.
     * @return true if the attack is successful, false otherwise.
     */
    @Override
    public boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies) {
        try {
            if(p_Armies <= 0) {
                d_Logger.log("You cannot move/attack with 0 armies");
                return false;
            }
            p_From.depleteArmies(p_Armies);
            if(p_To.getArmies() == 0 ) {
                p_To.setArmies(p_Armies);
                makeMeKing(p_Player, p_To);
                d_Logger.log(String.format("Attacker : %s (%s) won", p_Player.getName(), p_From.getName()));
                //Assign power card to king
                Card l_AssignedCard = new Card();
                p_Player.addPlayerCard(l_AssignedCard);
                d_Logger.log("Attacker: " + p_Player.getName() + " received a card: " + l_AssignedCard.getCardType().toString());
                d_Logger.log(String.format("Since won, left out %s (Attacker) armies %s moved to %s.", p_From.getName(), p_Armies, p_To.getName()));
                return true;
            }
            int l_attackerKills = (int) Math.round(p_Armies * SETTINGS.ATTACKER_PROBABILITY);
            int l_defenderKills = (int) Math.round(p_To.getArmies() * SETTINGS.DEFENDER_PROBABILITY);

            int l_armiesLeftAttacker = p_Armies > l_defenderKills ? (p_Armies - l_defenderKills) : 0;
            int l_armiesLeftDefender = p_To.getArmies() > l_attackerKills ? p_To.getArmies() - l_attackerKills : 0;
            if (l_armiesLeftAttacker > 0 && l_armiesLeftDefender <= 0) {
                p_To.setArmies(l_armiesLeftAttacker);
                makeMeKing(p_Player, p_To);
                d_Logger.log(String.format("Attacker : %s (%s) won", p_Player.getName(), p_From.getName()));
                //Assign power card to king
                Card l_AssignedCard = new Card();
                p_Player.addPlayerCard(l_AssignedCard);
                d_Logger.log("Attacker: " + p_Player.getName() + " received a card: " + l_AssignedCard.getCardType().toString());
                d_Logger.log(String.format("Since won, left out %s (Attacker) armies %s moved to %s.", p_From.getName(), l_armiesLeftAttacker, p_To.getName()));
            } else {
                p_From.deployArmies(l_armiesLeftAttacker);
                p_To.setArmies(l_armiesLeftDefender);
                d_Logger.log(String.format("Attacker : %s (%s) lost", p_Player.getName(), p_From.getName()));
                d_Logger.log(String.format("Remaining armies of %s (Attacker) in attack: %s ", p_From.getName(), l_armiesLeftAttacker));
                d_Logger.log(String.format("Remaining armies of %s (Defender) in attack: %s ", p_To.getName(), l_armiesLeftDefender));
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }
    }
}
