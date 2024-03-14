package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

/**
 * The Blockade order controller will Block the attack.
 * @author Nishith
 * @version 1.0.0
 */
public class Blockade extends Order{
    /**
     * The d_GameMap is game map.
     */
    private final GameMap d_GameMap;

    /**
     * Constructor to set Blockade type and also to get the GameMap instance.
     */
    public Blockade(){
        super();
        setType("blockade");
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
            System.err.println(Constants.INVALID_PLAYER);
            return false;
        }
        if(l_Country.getPlayer() != l_Player){
            System.err.println(Constants.TARGET_COUNTRY_DOES_NOT_BELONG);
            return false;
        }
        if(!l_Player.checkIfCardAvailable(CardType.BLOCKADE)){
            System.err.println(Constants.NO_BLOCKADE_CARD);
            return false;
        }
        return true;
    }

    @Override
    public void printOrderCommand() {
        System.out.println("Blockade on " + getOrderInfo().getTargetCountry().getCountryId() + " by " + getOrderInfo().getPlayer().getName());
    }
}
