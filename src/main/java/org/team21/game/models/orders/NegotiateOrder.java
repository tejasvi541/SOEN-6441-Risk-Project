package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.game_play.Player;
import org.team21.game.utils.Constants;
//import utils.logger.LogEntryBuffer;

/**
 * This java class will help to prevent attacks between current player and another player
 * till the of the turn.
 * @author Meet Boghani
 * @version 1.0.0
 */
public class NegotiateOrder extends Order{
    //Todo Kapil
//    LogEntryBuffer d_Leb = new LogEntryBuffer();
    /**
     * A gamemap object
     */
    private final GameMap d_GameMap;

    /**
     * Constructor for the class Negotiate Order
     */
    public NegotiateOrder() {
        super();
        setType(Constants.NEGOTIATE_COMMAND);
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Negotiate Order
     *
     * @return true if the execution was successful else false
     */
    @Override
    public boolean execute() {
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        if (validateCommand()) {
            System.out.println("The order: " + getType() + " " + l_NeutralPlayer.getName());
            Player l_Player = getOrderInfo().getPlayer();
            l_Player.addNeutralPlayers(l_NeutralPlayer);
            l_NeutralPlayer.addNeutralPlayers(l_Player);
            l_Player.removeCard(CardType.DIPLOMACY);
            return true;
        }
        return false;
    }

    /**
     * This method validates the command passed by the player.
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        //check if the player has the card
        if (!l_Player.checkIfCardAvailable(CardType.DIPLOMACY)) {
            Constants.printValidationOfValidateCommand("The player does not have the card available for use.");
//            d_Leb.logInfo("Player doesn't have the card to be used.");
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null) {
            Constants.printValidationOfValidateCommand("The Player is not valid.");
//            d_Leb.logInfo("The Player is not valid.");
            return false;
        }
        // check if the player exists
//        d_Leb.logInfo(d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName()));
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            Constants.printValidationOfValidateCommand("The Player name does not exist.");
//            d_Leb.logInfo("The Player name doesn't exist.");
            return false;
        }
        return true;
    }

    /**
     * This method will print the negotiation happened between current player with neutral player.
     */
    @Override
    public void printOrderCommand() {
        System.out.println(getOrderInfo().getPlayer().getName() + " player negotiated with " + getOrderInfo().getNeutralPlayer().getName() + " player.");
        System.out.println(Constants.SEPERATER);
//        d_Leb.logInfo("Negotiated with" + getOrderInfo().getNeutralPlayer().getName() + ".");
    }
}
