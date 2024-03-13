package org.team21.game.models.orders;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;

public class Blockade extends Order{
    private final GameMap d_GameMap;

    public Blockade(){
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getDestination();
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
        return false;
    }

    @Override
    public void printOrderCommand() {

    }
}
