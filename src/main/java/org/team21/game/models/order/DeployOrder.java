package org.team21.game.models.order;

import org.team21.game.models.map.Country;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;

/**
 * Represents a Deploy order, used to deploy armies from a player's reinforcement pool to a country.
 * This order is executed during the reinforcement phase of a player's turn.
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public class DeployOrder extends Order implements Serializable {

    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs a DeployOrder object and sets its type to "deploy".
     */
    public DeployOrder() {
        super();
        setType("deploy");
    }

    /**
     * Executes the DeployOrder command.
     * Deploys the specified number of armies from the player's reinforcement pool to the destination country.
     *
     * @return true if the execution was successful, false otherwise.
     */
    public boolean execute() {
        Country l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        d_Logger.log(Constants.EQUAL_SEPARATOR);
        if (validateCommand()) {
            l_Destination.deployArmies(l_ArmiesToDeploy);
            return true;
        }
        return false;
    }

    /**
     * Validates the DeployOrder command.
     * Checks if the command is valid based on player, destination country, and available reinforcement armies.
     *
     * @return true if the command can be executed, false otherwise.
     */
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Destination = getOrderInfo().getDestination();
        int l_Reinforcements = getOrderInfo().getNumberOfArmy();
        if (l_Player == null || l_Destination == null) {
            d_Logger.log("Invalid order information.The entered values are invalid.");
            return false;
        }
        if (!l_Player.isCaptured(l_Destination)) {
            d_Logger.log("The country does not belong to you");
            return false;
        }
        if (!l_Player.deployReinforcementArmiesFromPlayer(l_Reinforcements)) {
            d_Logger.log("You do not have enough Reinforcement Armies to deploy.");
            return false;
        }
        return true;
    }

    /**
     * Prints the DeployOrder command.
     * Logs the order information including the number of armies deployed and the destination country.
     */
    public void printOrderCommand() {
        d_Logger.log("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination().getName() + ".");
        d_Logger.log(Constants.EQUAL_SEPARATOR);
    }

}
