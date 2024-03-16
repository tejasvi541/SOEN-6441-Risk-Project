package org.team21.game.models.orders;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;


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
     * A logger for storing airlift order information
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();
    /**
     * Default constructor for Airlift Order
     * to set type of order as Airlift
     */
    public AirliftOrder() {
        d_GameMap = GameMap.getInstance();
        this.setType(Constants.AIRLIFT_COMMAND);
    }

    /**
     * execute the Airlift Order
     *
     * @return true: if airlift order execution is successful; else false
     */
    @Override
    public boolean execute() {
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_destinationCountry = getOrderInfo().getDestination();
        Player l_Player = getOrderInfo().getPlayer();
        int p_armyToAirLift = getOrderInfo().getNumberOfArmy();


        if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - p_armyToAirLift);
            l_destinationCountry.setArmies(l_destinationCountry.getArmies() + p_armyToAirLift);
            System.out.println("The order: " + getType() + " " + p_armyToAirLift + " armies from "+l_fromCountry.getCountryId()+" to "+l_destinationCountry.getCountryId());
            l_Player.removeCard(CardType.AIRLIFT);
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
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_destinationCountry = getOrderInfo().getDestination();
        Player l_Player = getOrderInfo().getPlayer();
        int p_armyToAirLift = getOrderInfo().getNumberOfArmy();

        //check if the player is valid
        if (l_Player == null) {
            Constants.printValidationOfValidateCommand("Found no valid Player");
            return false;
        }
        //check if army number is more than 0
        if (p_armyToAirLift <= 0) {
            Constants.printValidationOfValidateCommand("Airlift army is less than 0. It should be greater than 0");
            return false;
        }

        //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardType.AIRLIFT)) {
            Constants.printValidationOfValidateCommand("Airlift Card not available for the player");
            return false;
        }
        //check if countries belong to the player
        if (!l_Player.getCapturedCountries().contains(l_fromCountry) || !l_Player.getCapturedCountries().contains(l_destinationCountry)) {
            Constants.printValidationOfValidateCommand("Departure or Destination country do not belong to player.");
            return false;
        }
        //check if army number is more that they own
        if (l_fromCountry.getArmies() < p_armyToAirLift) {
            Constants.printValidationOfValidateCommand("Less number of army for the player in country " + getOrderInfo().getDeparture().getCountryId());
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
    }
}
