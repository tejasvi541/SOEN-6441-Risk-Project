package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;

/**
 * Represents an Airlift order, which allows a player to move armies from one country to another.
 * This order requires an Airlift card.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class AirliftOrder extends Order implements Serializable {

    /**
     * Instance of GameMap to access game map information.
     */
    private final GameMap d_GameMap;

    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs an AirliftOrder object and sets its type to "airlift".
     */
    public AirliftOrder() {
        super();
        setType("airlift");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Executes the AirliftOrder command.
     * Moves armies from one country to another and consumes an Airlift card.
     * @return true if the execution was successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_toCountry = getOrderInfo().getDestination();
        int l_armyNumberToAirLift = getOrderInfo().getNumberOfArmy();
        d_Logger.log(Constants.EQUAL_SEPARATOR);
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - l_armyNumberToAirLift);
            l_toCountry.setArmies(l_toCountry.getArmies() + l_armyNumberToAirLift);
            l_Player.removeCard(CardType.AIRLIFT);
            return true;
        }
        return false;
    }

    /**
     * Validates the AirliftOrder command.
     * Checks if the command is valid based on player, departure country, destination country, and number of armies.
     *
     * @return true if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_toCountry = getOrderInfo().getDestination();
        int l_armyNumberToAirLift = getOrderInfo().getNumberOfArmy();

        //check if the player is valid
        if (l_Player == null) {
            d_Logger.log(Constants.INVALID_PLAYER);
            return false;
        }
        //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardType.AIRLIFT)) {
            d_Logger.log(Constants.NO_AIRLIFT_CARD);
            return false;
        }
        //check if countries belong to the player
        if (!l_Player.getCapturedCountries().contains(l_fromCountry) || !l_Player.getCapturedCountries().contains(l_toCountry)) {
            d_Logger.log("Source or target country do not belong to the player.");
            return false;

        }
        //check if army number is more than 0
        if (l_armyNumberToAirLift <= 0) {
            d_Logger.log("The number of airlift army should be greater than 0");
            return false;
        }
        //check if army number is more that they own
        if (l_fromCountry.getArmies() < l_armyNumberToAirLift) {
            d_Logger.log("Player has less no. of army in country " + getOrderInfo().getDeparture().getName());
            return false;
        }
        return true;
    }

    /**
     * Prints the AirliftOrder command.
     * Logs the order information including the number of armies, departure country, and destination country.
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
