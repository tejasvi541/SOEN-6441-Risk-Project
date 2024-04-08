package org.team21.game.models.order;

import org.team21.game.models.map.Country;
import org.team21.game.game_engine.GameSettings;
import org.team21.game.models.map.Player;
import org.team21.game.interfaces.game.GameStrategy;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing an Advance order, which allows a player to move armies from one country to another.
 * This order can be used to advance to a neighboring country, attack an enemy country, or negotiate with a neutral player.
 *
 * @author Yesha Shah
 * @version 1.0.0
 */
public class AdvanceOrder extends Order implements Serializable {

    /**
     * Instance of GameSettings to access game configuration.
     */
    GameSettings d_Settings = GameSettings.getInstance();

    /**
     * Instance of GameStrategy to handle game-specific strategies.
     */
    GameStrategy d_GameStrategy;

    /**
     * Instance of GameEventLogger to log game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs an AdvanceOrder object and sets its type to "advance".
     */
    public AdvanceOrder() {
        super();
        setType("advance");
        d_GameStrategy = d_Settings.getStrategy();
    }

    /**
     * Executes the AdvanceOrder command.
     * Moves armies from one country to another, advances if destination is unoccupied or conquered,
     * attacks if destination belongs to an enemy player, or negotiates with a neutral player.
     *
     * @return true if the command is successfully executed or skipped, false otherwise.
     */
    @Override
    public boolean execute() {
        d_Logger.log("---------------------------------------------------------------------------------------------");
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            Country l_From = getOrderInfo().getDeparture();
            Country l_To = getOrderInfo().getDestination();
            int l_Armies = getOrderInfo().getNumberOfArmy();
            if (l_Player.getNeutralPlayers().contains(l_To.getPlayer())) {
                d_Logger.log(String.format("Truce between %s and %s\n", l_Player.getName(), l_To.getPlayer().getName()));
                l_Player.getNeutralPlayers().remove(l_To.getPlayer());
                l_To.getPlayer().getNeutralPlayers().remove(l_Player);
                return true;
            }
            if (l_Player.isCaptured(l_To) || Objects.isNull(l_To.getPlayer())) {
                l_From.depleteArmies(l_Armies);
                l_To.deployArmies(l_Armies);
                if (!l_Player.getCapturedCountries().contains(l_To)) {
                    l_Player.getCapturedCountries().add(l_To);
                }
                l_To.setPlayer(l_Player);
                d_Logger.log("Advanced/Moved " + l_Armies + " from " + l_From.getName() + " to " + l_To.getName());
                return true;
            } else if (d_GameStrategy.attack(l_Player, l_From, l_To, l_Armies)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Validates the AdvanceOrder command.
     * Checks if the command is valid based on player, departure country, destination country, and number of armies.
     *
     * @return true if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_From = getOrderInfo().getDeparture();
        Country l_To = getOrderInfo().getDestination();
        int l_Armies = getOrderInfo().getNumberOfArmy();
        boolean l_success = true;
        String l_log = "Failed due to some errors\n";
        if (l_Player == null || l_To == null || l_From == null) {
            System.out.println("Invalid order information.");
            return false;
        }
        if (!l_Player.isCaptured(l_From)) {
            System.out.format("Failed due to this error-\n");
            System.out.format("\t-> Country %s does not belong to player %s\n", l_From.getName(), l_Player.getName());
            l_success = false;
        }
        if (!l_From.isNeighbor(l_To)) {
            System.out.println("Failed due to this error-\n");
            System.out.format("\t-> Destination country %s is not a neighbor of %s\n", l_To.getName(), l_From.getName());
            l_success = false;
        }
        if (l_Armies > l_From.getArmies()) {
            System.out.println("Failed due to this error-\n");
            System.out.format("\t-> Attacking troop count %d is greater than available troops %d", l_Armies, l_From.getArmies());

            l_success = false;
        }
        if (!l_success) {
            d_Logger.log(l_log);
        }
        return l_success;
    }

    /**
     * Prints the AdvanceOrder command.
     * Logs the order information including the number of armies, departure country, and destination country.
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Order Info: Advance " + getOrderInfo().getNumberOfArmy() + " armies " + " from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
