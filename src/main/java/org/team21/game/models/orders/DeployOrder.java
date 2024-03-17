package org.team21.game.models.orders;


import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;


/**
 * Deploy order model will be used by {OrderOwner}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class DeployOrder extends Order {
    /**
     * Created object d_GameEventLogger of GameEventLogger.
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();
    /**
     * Constructor for DeployOrder
     */
    public DeployOrder() {
        super();
        setType(Constants.DEPLOY_COMMAND);
    }

    /**
     * Overriding order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if(validateCommand()){
            if (getOrderInfo().getPlayer() == null || getOrderInfo().getDestination() == null) {
                System.out.println(Constants.DEPLOY_COMMAND+" "+Constants.INVALID_COMMAND);
                d_GameEventLogger.logEvent(Constants.DEPLOY_COMMAND+" "+Constants.INVALID_COMMAND);
                return false;
            }
            Player l_Player = getOrderInfo().getPlayer();
            Country l_Destination = getOrderInfo().getDestination();
            int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
            for (Country l_Country : l_Player.getCapturedCountries()) {
                if (l_Country.getCountryId().equals(l_Destination.getCountryId())) {
                    l_Country.deployArmies(l_ArmiesToDeploy);
                    System.out.println("The country " + l_Country.getCountryId() + " has been deployed with " + l_Country.getArmies() + " armies.");
                    d_GameEventLogger.logEvent("The country " + l_Country.getCountryId() + " has been deployed with " + l_Country.getArmies() + " armies.");
                }
            }
            System.out.println("\nExecution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination.getCountryId() + ".");
            d_GameEventLogger.logEvent("Execution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination.getCountryId() + ".");
            System.out.println(Constants.SEPERATER);
            return true;
        }else{
            System.out.println(Constants.DEPLOY_COMMAND+" "+Constants.INVALID_COMMAND);
            d_GameEventLogger.logEvent(Constants.DEPLOY_COMMAND+" "+Constants.INVALID_COMMAND);
            return false;
        }

    }

    /**
     * Validates the command associated with the order. This method is intended to be overridden by child classes.
     *
     * @return true if the command associated with the order is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Destination = getOrderInfo().getDestination();
        int l_Reinforcements = getOrderInfo().getNumberOfArmy();
        if (l_Player == null || l_Destination == null) {
            System.out.println(Constants.INVALID_COMMAND);
            d_GameEventLogger.logEvent(Constants.INVALID_COMMAND);
            return false;
        }
        if (!l_Player.isCaptured(l_Destination)) {
            System.out.println(Constants.COUNTRIES_DOES_NOT_BELONG);
            d_GameEventLogger.logEvent(Constants.COUNTRIES_DOES_NOT_BELONG);
            return false;
        }
        if (!l_Player.deployReinforcementArmiesFromPlayer(l_Reinforcements)) {
            System.out.println(Constants.NOT_ENOUGH_REINFORCEMENTS);
            d_GameEventLogger.logEvent(Constants.NOT_ENOUGH_REINFORCEMENTS);
            return false;
        }
        return true;
    }

    /**
     * Prints the command that is executed successfully. This method is intended to be overridden by child classes.
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination().getCountryId() + ".");
        d_GameEventLogger.logEvent("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination().getCountryId() + ".");
        System.out.println(Constants.SEPERATER);
    }
}
