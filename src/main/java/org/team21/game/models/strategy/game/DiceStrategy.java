package org.team21.game.models.strategy.game;

import org.team21.game.interfaces.game.GameStrategy;
import org.team21.game.models.cards.Card;
import org.team21.game.models.map.Country;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.models.map.Player;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.stream.IntStream;

/**
 * Implementation of Dice strategy for advance logic
 *
 * This class implements the GameStrategy interface to define the behavior of attack using dice rolls.
 * It calculates the outcome of an attack based on probabilities defined in the GameSettings class.
 *
 * @author Yesha Shah
 */
public class DiceStrategy implements GameStrategy {
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
            p_From.depleteArmies(p_Armies);
            int l_AttackerKills = (int) IntStream.range(0, p_Armies).boxed().filter((p_integer) -> Math.random() <= SETTINGS.ATTACKER_PROBABILITY).count();
            int l_DefenderKills = (int) IntStream.range(0, p_To.getArmies()).boxed().filter(p_integer -> Math.random() <= SETTINGS.DEFENDER_PROBABILITY).count();

            int l_ArmiesLeftAttacker = p_Armies - l_DefenderKills;
            int l_ArmiesLeftDefender = p_To.getArmies() - l_AttackerKills;
            if (l_ArmiesLeftAttacker > 0 && l_ArmiesLeftDefender <= 0) {
                p_To.setArmies(l_ArmiesLeftAttacker);
                makeMeKing(p_Player, p_To);
                d_Logger.log(String.format("Attacker : %s (%s) won",p_Player.getName(),p_From.getName()));
                // Assign a card
                Card l_AssignedCard = new Card();
                p_Player.addPlayerCard(l_AssignedCard);
                d_Logger.log("Attacker: " + p_Player.getName() + " received a card: " + l_AssignedCard.getCardType().toString());
                d_Logger.log(String.format("Since won, left out %s (Attacker) armies %s moved to %s.",p_From.getName(),l_ArmiesLeftAttacker,p_To.getName()));

            } else {
                p_From.deployArmies(l_ArmiesLeftAttacker);
                p_To.setArmies(l_ArmiesLeftDefender);
                d_Logger.log("Attacker : " + p_Player.getName() + " lost.");
                d_Logger.log("Remaining attacker's armies: " + p_From.getArmies());
                d_Logger.log("Remaining defender's armies: " + p_To.getArmies());
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }

    }

}
