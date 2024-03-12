package org.team21.game.models.orders;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;


/**
 * This class gives the order to execute Airlift from one country to another destination Country.
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class AirliftOrder extends Order {


    /**
     * A data member for storing the object of the GameMap.
     */
    private final GameMap d_GameMap;

    /**
     * A data member for storing number of army for airlift order
     */
    int d_armyToAirLift;

    /**
     * A data member for storing order information
     */
    OrderInformation d_orderInfo;

    /**
     * A data member for storing player information
     */
    Player d_Player;

    /**
     * Default constructor for Airlift Order
     * to set type of order as Airlift
     */
    public AirliftOrder() {
        d_GameMap = GameMap.getInstance();
        this.setType("airlift");
    }

    /**
     * method to initialise for airlift order information
     */
    public void init()
    {
        d_orderInfo=getOrderInfo();
        d_armyToAirLift = d_orderInfo.getNumberOfArmy();
        d_Player = d_orderInfo.getPlayer();
    }

    /**
     * execute the Airlift Order
     *
     * @return true: if airlift order execution is successful; else false
     */
    @Override
    public boolean execute() {
        Country l_fromCountry = d_orderInfo.getDeparture();
        //ToDo kapil to convert string return type to Country for getDestination method in Order class
        Country l_destinationCountry = d_orderInfo.getDestination();

       if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - d_armyToAirLift);
           l_destinationCountry.setArmies(l_destinationCountry.getArmies() + d_armyToAirLift);
            System.out.println("The order: " + getType() + " " + d_armyToAirLift + " armies from "+l_fromCountry.getCountryId()+" to "+l_destinationCountry.getCountryId());
           //ToDo kapil to remove card for given player after execution is completed
            d_Player.removeCard(CardType.AIRLIFT);
            return true;
        }
        return false;
    }

    /**
     * Method to validate command
     *
     * @return true: if command is validated successfully ; else false
     */
    @Override
    public boolean validateCommand() {
        Country l_departureCountry = d_orderInfo.getDeparture();
        //ToDo kapil to convert string return type to Country for getDestination method in Order class
        Country l_destinationCountry = d_orderInfo.getDestination();
        //check if the player is valid
        if (d_Player == null) {
            System.out.println("Found no valid Player");
            return false;
        }
        //check if army number is more than 0
        if (d_armyToAirLift <= 0) {
            System.out.println("Airlift army is less than 0. It should be greater than 0");
            return false;
        }

        //check if the player has an airlift card
        //ToDO kapil to add method checkIfCardAvailable for validation
        if (!d_Player.checkIfCardAvailable(CardType.AIRLIFT)) {
            System.out.println("Airlift Card not available for the player");
            return false;
        }
        //check if countries belong to the player
        if (!d_Player.getCapturedCountries().contains(l_departureCountry) || !d_Player.getCapturedCountries().contains(l_destinationCountry)) {
            System.out.println("Departure or Destination country do not belong to player.");
            return false;
        }
        //check if army number is more that they own
        if (l_departureCountry.getArmies() < d_armyToAirLift) {
            System.out.println("Less number of army for the player in country " + getOrderInfo().getDeparture().getCountryId());
            return false;
        }
        return true;
    }

    /**
     * Printing the command for Airlift Order
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getCountryId() + " to " + getOrderInfo().getDestination().getCountryId() + ".");
        System.out.println(Constants.SEPERATER);
        //ToDo Kapil to print the commands in logger file
        System.out.println("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getCountryId() + " to " + getOrderInfo().getDestination().getCountryId() + ".");
    }
}

