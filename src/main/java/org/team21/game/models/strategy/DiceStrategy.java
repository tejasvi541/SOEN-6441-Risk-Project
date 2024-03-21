package org.team21.game.models.strategy;

import org.team21.game.models.cards.Card;
import org.team21.game.models.map.Country;
import org.team21.game.models.game_play.GameSettings;
import org.team21.game.models.game_play.Player;
import org.team21.game.interfaces.main_engine.GameStrategy;
import org.team21.game.models.cards.Card;
import org.team21.game.models.map.Country;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.stream.IntStream;

/**
 * Class holding the Dice strategy for advance logic
 *
 * @author Yesha Shah
 */
public class DiceStrategy implements GameStrategy {

    /**
     * Default constructor
     */
    public DiceStrategy(){

    }
    /**
     * Game settings object
     */
    GameSettings SETTINGS = GameSettings.getInstance();

    /**
     * Logger for game a actions
     */
    GameEventLogger d_LogEntryBuffer = new GameEventLogger();

    /**
     * Method holding the default attack logic
     *
     * @param p_Player The player who initiated attack
     * @param p_From   The country from which the attack is initiated
     * @param p_To     The country on which the attack is going to happen
     * @param p_Armies The number of armies to be moved
     * @return true on successful execution else false
     */
    @Override
    public boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies) {
        try{
            p_From.depleteArmies(p_Armies);
            int l_AttackerKills = (int) IntStream.range(0, p_Armies).boxed().filter((p_integer) -> Math.random() <= SETTINGS.ATTACKER_PROBABILITY).count();
            int l_DefenderKills = (int) IntStream.range(0, p_To.getArmies()).boxed().filter(p_integer -> Math.random() <= SETTINGS.DEFENDER_PROBABILITY).count();

            int l_ArmiesLeftAttacker = p_Armies - l_DefenderKills;
            int l_ArmiesLeftDefender = p_To.getArmies() - l_AttackerKills;
            if(l_ArmiesLeftDefender <= 0){
                l_ArmiesLeftDefender = 0;
            } else if (l_ArmiesLeftAttacker <= 0){
                l_ArmiesLeftAttacker = 0;
            }
            if (l_ArmiesLeftAttacker > 0 && l_ArmiesLeftDefender == 0) {
                p_To.setArmies(l_ArmiesLeftAttacker);
                makeMeKing(p_Player, p_To);
                Card l_AssignedCard = new Card();
                p_Player.addPlayerCard(l_AssignedCard);
                System.out.println("Attacker: " + p_Player.getName() + " received a card: " + l_AssignedCard.getCard());
                d_LogEntryBuffer.logEvent("Attacker: " + p_Player.getName() + " received a card: "+ l_AssignedCard.getCard());
                System.out.println("Attacker : " + p_Player.getName() + " won.");
                System.out.println("Remaining attacker's armies " + p_To.getArmies() + " moved from " + p_From.getCountryId() + " to " + p_To.getCountryId() + ".");

            } else {
                p_From.deployArmies(l_ArmiesLeftAttacker);
                p_To.setArmies(l_ArmiesLeftDefender);
                System.out.println("Attacker : " + p_Player.getName() + " lost.");
                System.out.println("Remaining attacker's armies: " + p_From.getArmies());
                System.out.println("Remaining defender's armies: " + p_To.getArmies());
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }

    }

}
