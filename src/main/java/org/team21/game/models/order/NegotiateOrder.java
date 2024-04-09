package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;

/**
 * Represents a Negotiate order, which allows a player to negotiate with another player.
 * This order is executed using a Negotiate card during the player's turn.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class NegotiateOrder extends Order implements Serializable {

    /**
     * Instance of GameMap to access game information.
     */
    private final GameMap d_GameMap;
    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs a NegotiateOrder object and sets its type to "negotiate".
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Executes the NegotiateOrder command.
     * Initiates a negotiation between the player and a neutral player.
     *
     * @return true if the execution was successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        d_Logger.log(Constants.EQUAL_SEPARATOR);
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
     * Validates the NegotiateOrder command.
     * Checks if the command is valid based on the player's possession of the Diplomacy card and the existence of the neutral player.
     *
     * @return true if the command can be executed, false otherwise.
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
     * Prints the NegotiateOrder command.
     * Logs the order information including the negotiated neutral player.
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Negotiated with " + getOrderInfo().getNeutralPlayer().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
