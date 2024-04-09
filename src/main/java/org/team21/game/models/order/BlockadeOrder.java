package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;


/**
 * Represents a Blockade order, which allows a player to execute a blockade on a country.
 * This order requires a Blockade card.
 *
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class BlockadeOrder extends Order implements Serializable {

    /**
     * Instance of GameMap to access game map information.
     */
    private final GameMap d_GameMap;
    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs a BlockadeOrder object and sets its type to "blockade".
     */
    public BlockadeOrder() {
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Executes the BlockadeOrder command.
     * Triples the armies in the target country, adds it to the neutral countries list, removes it from the player's captured countries, and consumes a Blockade card.
     *
     * @return true if the execution was successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        d_Logger.log(Constants.EQUAL_SEPARATOR);
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            l_Country.setArmies(l_Country.getArmies() * 3);
            l_Country.addNeutralCountry(l_Country);
            l_Player.getCapturedCountries().remove(l_Country);
            l_Player.removeCard(CardType.BLOCKADE);
            return true;
        }
        return false;
    }

    /**
     * Validates the BlockadeOrder command.
     * Checks if the command is valid based on player and target country, and if the player has a Blockade card.
     *
     * @return true if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.err.println(Constants.INVALID_PLAYER);
            d_Logger.log(Constants.INVALID_PLAYER);
            return false;
        }

        if (l_Country.getPlayer() != l_Player) {
            System.err.println(Constants.TARGET_COUNTRY_DOES_NOT_BELONG);
            d_Logger.log(Constants.TARGET_COUNTRY_DOES_NOT_BELONG);
            return false;
        }
        if (!l_Player.checkIfCardAvailable(CardType.BLOCKADE)) {
            System.err.println(Constants.NO_BLOCKADE_CARD);
            d_Logger.log(Constants.NO_BLOCKADE_CARD);
            return false;
        }
        return true;
    }

    /**
     * Prints the BlockadeOrder command.
     * Logs the order information including the target country and player.
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Blockade on " + getOrderInfo().getTargetCountry().getName() + " by " + getOrderInfo().getPlayer().getName());
        d_Logger.log(Constants.EQUAL_SEPARATOR);
    }
}