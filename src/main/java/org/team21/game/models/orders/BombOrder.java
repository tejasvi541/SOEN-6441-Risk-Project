package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.HashMap;

/**
 * This Class implements the Bomb Card and its various methods to execute and use it
 * @author Tejasvi
 */
public class BombOrder extends Order {

    /**
     * Game Map object variable
     */
    private GameMap d_GameMap;
    /**
     * GameEvent Logger object.
     */
    GameEventLogger d_GameEventLogger = new GameEventLogger();

    /**
     * Class constructor calls super and set GameMap object
     */
    public BombOrder(){
        super();
        d_GameMap = GameMap.getInstance();
        setType(Constants.BOMB_COMMAND);
    }

    /**
     * Method to Validate the bomb command
     * @return true/false based on tests
     */
    @Override
    public boolean validateCommand(){
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();

        // Check for valid player
        if(l_Player==null){
            Constants.printValidationOfValidateCommand("Invalid Player");
            d_GameEventLogger.logEvent("The Player is invalid");
            return false;
        }
        // Check for if the player has the card
        if(!l_Player.checkIfCardAvailable(CardType.BOMB)){
            Constants.printValidationOfValidateCommand("Invalid BOMB Card");
            d_GameEventLogger.logEvent("The BOMB card is invalid");
            return false;
        }
        // check if the target country belongs to the player
        if(l_Player.getCapturedCountries().contains(l_TargetCountry)){
            Constants.printValidationOfValidateCommand("The Player cannot bomb its own country");
            d_GameEventLogger.logEvent("The Player cannot bomb its own country");
            return false;
        }

        // Check if diplomacy is there or not
        if(l_Player.getNeutralPlayers().contains(l_TargetCountry.getPlayer())){
            System.out.printf("There is diplomacy between %s and %s\n", l_Player.getName(), l_TargetCountry.getPlayer().getName());
            d_GameEventLogger.logEvent("There is diplomacy between the countries");
            l_Player.getNeutralPlayers().remove(l_TargetCountry.getPlayer());
            l_TargetCountry.getPlayer().getNeutralPlayers().remove(l_Player);
            return false;
        }

        // Validate if the target country is a neighbor of the player owned country
        boolean l_adjacentCountry = false;

        for(Country l_OwnCountry:l_Player.getCapturedCountries()){
            HashMap<String, Country> Neighbors = l_OwnCountry.getNeighbours();
            if (Neighbors.containsKey(l_TargetCountry.getCountryId().toLowerCase())) {
                l_adjacentCountry = true;
                break;
            }
        }
        if (!l_adjacentCountry){
            Constants.printValidationOfValidateCommand("The target country is not a neighbor of player owned country");
            d_GameEventLogger.logEvent("The target country is not a neighbor of player owned country");
            return false;
        }
        return true;
    }

    /**
     * This function executes the bomb order by the player
     * @return true if successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            System.out.println("The order is: " + getType() + " " + l_TargetCountry.getCountryId());
            d_GameEventLogger.logEvent("The order is: " + getType() + " " + l_TargetCountry.getCountryId());
            int l_Armies = l_TargetCountry.getArmies();
            int l_NewArmies  = Math.max(0, l_Armies / 2);
            l_TargetCountry.setArmies(l_NewArmies);
            l_Player.removeCard(CardType.BOMB);
            return true;
        }
        return false;
    }

    /**
     * This is for printing information
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Bomb order issued by player: " + getOrderInfo().getPlayer().getName()
                + " targeting country: " + getOrderInfo().getTargetCountry().getCountryId());
        d_GameEventLogger.logEvent("Bomb order issued by player: " + getOrderInfo().getPlayer().getName()
                + " targeting country: " + getOrderInfo().getTargetCountry().getCountryId());
        System.out.println(Constants.SEPERATER);
    }


}
