package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

/**
 * The BlockadeOrder order controller will triple the number of armies of
 * the current player’s territories and make it a neutral territory.
 * @author Nishith
 * @version 1.0.0
 */
public class BlockadeOrder extends Order{
    /**
     * The d_GameMap is game map.
     */
    private final GameMap d_GameMap;

    /**
     * Constructor to set BlockadeOrder type and also to get the GameMap instance.
     */
    public BlockadeOrder(){
        super();
        setType(Constants.BLOCKADE_COMMAND);
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        if(validateCommand()){
            l_Country.setArmies(l_Country.getArmies() * 3);
            l_Player.getCapturedCountries().remove(l_Country);
            l_Country.setPlayer(null);
            System.out.println("The order: " + getType() + " " + l_Country.getCountryId());
            l_Player.removeCard(CardType.BLOCKADE);
            return true;
        }
        return false;
    }

    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        if(l_Player == null){
            Constants.printValidationOfValidateCommand(Constants.INVALID_PLAYER);
            return false;
        }
        if(l_Country.getPlayer() != l_Player){
            Constants.printValidationOfValidateCommand(Constants.TARGET_COUNTRY_DOES_NOT_BELONG);
            return false;
        }
        if(!l_Player.checkIfCardAvailable(CardType.BLOCKADE)){
            Constants.printValidationOfValidateCommand(Constants.NO_BLOCKADE_CARD);
            return false;
        }
        return true;
    }

    @Override
    public void printOrderCommand() {
        System.out.println("BlockadeOrder on " + getOrderInfo().getTargetCountry().getCountryId() + " by " + getOrderInfo().getPlayer().getName());
    }
}
