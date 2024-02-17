package org.team25.game.models.orders;


import org.team25.game.models.game_play.Player;
import org.team25.game.models.map.Country;
//Todo refactor
public class DeployOrder extends Order {
    /**
     * Constructor for class DeployOrder
     */
    public DeployOrder() {
        super();
        setType("deploy");
    }
    /**
     * Overriding the execute function for the order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if (getOrderInfo().getPlayer() == null || getOrderInfo().getDestination() == null) {
            System.out.println("Fail to execute Deploy order: Invalid order information.");
            return false;
        }
        Player l_Player = getOrderInfo().getPlayer();
        String l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        for(Country l_Country : l_Player.getCapturedCountries()){
            if(l_Country.get_countryId().equals(l_Destination)){

                //Todo deploy armies
                // l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("The country " + l_Country.get_countryId() + " has been deployed with " + l_Country.get_numberOfArmies() + " armies.");
            }
        }
        System.out.println("\nExecution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination + ".");
        System.out.println("=========================================================================================");
        return true;
    }

}
