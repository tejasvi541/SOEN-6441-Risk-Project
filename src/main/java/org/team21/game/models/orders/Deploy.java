package org.team21.game.models.orders;


import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.utils.Constants;

/**
 * Deploy order model will be used by {OrderOwner}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class Deploy extends Order {
    /**
     * Constructor for DeployOrder
     */
    public Deploy() {
        super();
        setType(Constants.DEPLOY_COMMAND);
    }

    /**
     * Overriding order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if (getOrderInfo().getPlayer() == null || getOrderInfo().getDestination() == null) {
            System.out.println(Constants.INVALID_COMMAND);
            return false;
        }
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        for (Country l_Country : l_Player.getCapturedCountries()) {
            if (l_Country.getCountryId().equals(l_Destination.getCountryId())) {
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("The country " + l_Country.getCountryId() + " has been deployed with " + l_Country.getArmies() + " armies.");
            }
        }
        System.out.println("\nExecution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination + ".");
        System.out.println(Constants.SEPERATER);
        return true;
    }

    /**
     * Validates the command associated with the order. This method is intended to be overridden by child classes.
     *
     * @return true if the command associated with the order is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        //Todo implement ValidateCommand
        return false;
    }

    /**
     * Prints the command that is executed successfully. This method is intended to be overridden by child classes.
     */
    @Override
    public void printOrderCommand() {
        //Todo implement printOrderCommand

    }

}
