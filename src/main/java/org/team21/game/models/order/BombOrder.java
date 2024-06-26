package org.team21.game.models.order;

import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;

/**
 * Represents a Bomb order, which allows a player to execute a bomb attack on a country.
 * This order requires a Bomb card.
 *
 * @author Tejasvi
 * @version 1.0.0
 */
public class BombOrder extends Order implements Serializable {

    /**
     * Instance of GameMap to access game map information.
     */
    private GameMap d_GameMap;
    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs a BombOrder object and sets its type to "bomb".
     */
    public BombOrder() {
        super();
        setType("bomb");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Executes the BombOrder command.
     * Reduces the armies in the target country by half and consumes a Bomb card.
     *
     * @return true if the execution was successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();
        d_Logger.log(Constants.EQUAL_SEPARATOR);
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            int l_Armies = l_TargetCountry.getArmies();
            int l_NewArmies = l_Armies / 2;
            if (l_NewArmies < 0) {
                l_NewArmies = 0;
            }
            l_TargetCountry.setArmies(l_NewArmies);
            l_Player.removeCard(CardType.BOMB);
            return true;
        }
        return false;
    }

    /**
     * Validates the BombOrder command.
     * Checks if the command is valid based on player, target country, and if the player has a Bomb card.
     *
     * @return true if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.err.println(Constants.INVALID_PLAYER);
            d_Logger.log(Constants.INVALID_PLAYER);
            return false;
        }
        // validate that the player has the bomb card
        if (!l_Player.checkIfCardAvailable(CardType.BOMB)) {
            System.err.println(Constants.NO_BOMB_CARD);
            d_Logger.log(Constants.NO_BOMB_CARD);
            return false;
        }

        //check whether the target country belongs to the player
        if (l_Player.getCapturedCountries().contains(l_TargetCountry)) {
            System.err.println("The player cannot destroy armies in his own country.");
            d_Logger.log("The player cannot destroy armies in his own country.");
            return false;
        }

        // validate that the country is adjacent to one of the neighbors of the current player
        Boolean l_Adjacent = false;
        for (Country l_PlayerCountry : l_Player.getCapturedCountries()) {
            for (Country l_NeighbourCountry : l_PlayerCountry.getNeighbors()) {
                if (l_NeighbourCountry.getName().equals(l_TargetCountry.getName())) {
                    l_Adjacent = true;
                    break;
                }
            }
        }
        if (!l_Adjacent) {
            System.err.println("The target country is not adjacent to one of the countries that belong to the player.");
            d_Logger.log("The target country is not adjacent to one of the countries that belong to the player.");
            return false;
        }

        //Check diplomacy
        if (l_Player.getNeutralPlayers().contains(l_TargetCountry.getPlayer())) {
            System.err.printf("Truce between %s and %s\n", l_Player.getName(), l_TargetCountry.getPlayer().getName());
            d_Logger.log("Truce between" + l_Player.getName() + "and " + l_TargetCountry.getPlayer().getName());
            l_Player.getNeutralPlayers().remove(l_TargetCountry.getPlayer());
            l_TargetCountry.getPlayer().getNeutralPlayers().remove(l_Player);
            return false;
        }
        return true;
    }

    /**
     * Prints the BlockadeOrder command.
     * Logs the order information including the target country and player.
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Bomb Order issued by player: " + getOrderInfo().getPlayer().getName()
                + " on Country: " + getOrderInfo().getTargetCountry().getName());
        d_Logger.log(Constants.EQUAL_SEPARATOR);
    }
}

