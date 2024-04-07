package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;

/**
 * The class is a extended from Order, and overrides the methods from Order
 * @author Nishith Soni
 */
public class NegotiateOrder extends Order implements Serializable {
    /**
     * A gamemap object
     */
    private final GameMap d_GameMap;
    /**
     * Logger variable
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructor for class Negotiate Order
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Negotiate Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        d_Logger.log("---------------------------------------------------------------------------------------------");
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            l_Player.addNeutralPlayers(l_NeutralPlayer);
            l_NeutralPlayer.addNeutralPlayers(l_Player);
            l_Player.removeCard(CardType.DIPLOMACY);
            return true;
        }
        return false;
    }

    /**
     * Validate the command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        //check if the player has the card
        if (!l_Player.checkIfCardAvailable(CardType.DIPLOMACY)) {
            System.err.println(Constants.NO_NEGOTIATE_CARD);
            d_Logger.log(Constants.NO_NEGOTIATE_CARD);
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null) {
            System.err.println(Constants.INVALID_PLAYER);
            d_Logger.log(Constants.INVALID_PLAYER);
            return false;
        }
        // check if the player exists
        d_Logger.log("player exists:" + d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName()));
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            System.err.println(Constants.NONEXISTENT_PLAYER);
            d_Logger.log(Constants.NONEXISTENT_PLAYER);
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Negotiated with " + getOrderInfo().getNeutralPlayer().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
