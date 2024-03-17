package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.game_play.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

/**
 * This java class will help to prevent attacks between current player and another player
 * till the of the turn.
 * @author Meet Boghani
 * @version 1.0.0
 */
public class NegotiateOrder extends Order{
    /**
     * A gamemap object
     */
    private final GameMap d_GameMap;
    /**
     * Created object d_GameEventLogger of GameEventLogger.
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();
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
            d_GameEventLogger.logEvent("The order: " + getType() + " " + l_NeutralPlayer.getName());
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
            Constants.printValidationOfValidateCommand(Constants.NO_NEGOTIATE_CARD);
            d_GameEventLogger.logEvent(Constants.NO_NEGOTIATE_CARD);
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null) {
            Constants.printValidationOfValidateCommand(Constants.INVALID_PLAYER);
            d_GameEventLogger.logEvent(Constants.INVALID_PLAYER);
            return false;
        }
        // check if the player exists
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            Constants.printValidationOfValidateCommand(Constants.NONEXISTENT_PLAYER);
            d_GameEventLogger.logEvent(Constants.NONEXISTENT_PLAYER);
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
        d_GameEventLogger.logEvent(getOrderInfo().getPlayer().getName() + " player negotiated with " + getOrderInfo().getNeutralPlayer().getName() + " player.");
    }
}
