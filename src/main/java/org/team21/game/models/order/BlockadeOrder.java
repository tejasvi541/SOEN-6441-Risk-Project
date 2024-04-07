package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;


/**
 * This class helps in executing the Blockade Card
 * @author Bharti Chhabra
 */
public class BlockadeOrder extends Order implements Serializable {
    /**
     * A Gamemap object
     */
    private final GameMap d_GameMap;
    /**
     * Logger Observable
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructor for class Blockade Order
     */
    public BlockadeOrder() {
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Execute the Blockade Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        d_Logger.log("---------------------------------------------------------------------------------------------");
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
     * Validate the command
     *
     * @return true if successful or else false
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
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Blockade on " + getOrderInfo().getTargetCountry().getName() + " by " + getOrderInfo().getPlayer().getName());
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}