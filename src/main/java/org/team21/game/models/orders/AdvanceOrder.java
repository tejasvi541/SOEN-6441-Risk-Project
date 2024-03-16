package org.team21.game.models.orders;
import org.team21.game.models.map.Country;
import org.team21.game.models.game_play.GameSettings;
import org.team21.game.models.game_play.Player;
import org.team21.game.interfaces.main_engine.GameStrategy;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.util.Objects;

/**
 * Class holding the properties of advance order
 *
 * @author Yesha Shah
 */
public class AdvanceOrder extends Order {

    /**
     * Event buffer object
     */
     GameEventLogger d_Leb = new GameEventLogger();

    /**
     * Game Settings object
     */
    GameSettings d_Settings = GameSettings.getInstance();

    /**
     * Game Strategy object
     */
    GameStrategy d_GameStrategy;

    /**
     * Constructor for class AdvanceOrder
     */
    public AdvanceOrder() {
        super();
        setType(Constants.ADVANCE_COMMAND);
        d_GameStrategy = d_Settings.getStrategy();
    }

    /**
     * Advance the number of armies from a source country to destination country
     * Advance if self conquered
     * Advance if destination does not belong to any player.
     * Attack if a country belongs to enemy player.
     * Skips the attack command if player exists in NeutralPlayers list.
     * Remove the neutral player from the list once the attack is negotiated.
     * The attack happens in the next turn of the same phase on same player if the
     * negotiation happened already once in the phase.
     *
     * @return true if command is successfully executed or skipped else false
     */
    @Override
    public boolean execute() {
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            Country l_From = getOrderInfo().getDeparture();
            Country l_To = getOrderInfo().getDestination();
            int l_Armies = getOrderInfo().getNumberOfArmy();
            if (l_Player.getNeutralPlayers().contains(l_To.getPlayer())) {
                System.out.printf("Truce between %s and %s\n", l_Player.getName(), l_To.getPlayer().getName());
                l_Player.getNeutralPlayers().remove(l_To.getPlayer());
                l_To.getPlayer().getNeutralPlayers().remove(l_Player);
            } else if (l_Player.isCaptured(l_To) || Objects.isNull(l_To.getPlayer())) {
                l_From.deployArmies(l_Armies);
                l_To.deployArmies(l_Armies);
                if (!l_Player.getCapturedCountries().contains(l_To)) {
                    l_Player.getCapturedCountries().add(l_To);
                }
                l_To.setPlayer(l_Player);
                System.out.println("Advanced/Moved " + l_Armies + " from " + l_From.getCountryId() + " to " + l_To.getCountryId());
                d_Leb.logEvent("Advanced/Moved " + l_Armies + " from " + l_From.getCountryId() + " to " + l_To.getCountryId());

            } else if (d_GameStrategy.attack(l_Player, l_From, l_To, l_Armies)) {
            }
            return true;
        }
        System.out.println(Constants.SEPERATER);
        return false;
    }


    /**
     * Validate command
     *
     * @return Returns true if valid command else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_From = getOrderInfo().getDeparture();
        Country l_To = getOrderInfo().getDestination();
        int l_Armies = getOrderInfo().getNumberOfArmy();
        boolean success = true;
        String log = "Failed due to some errors\n";
        boolean l_IsNeighbour = l_From.isNeighbor(l_To);
        if (l_Player == null || l_To == null || l_From == null) {
            System.err.println("Invalid order information.");
            return false;
        }
        if (!l_Player.isCaptured(l_From)) {
            log += String.format("\t-> Country %s does not belong to player %s\n", l_From.getCountryId(), l_Player.getName());
            success = false;
        }
        if (!l_IsNeighbour) {
            log += String.format("\t-> Destination country %s is not a neighbor of %s\n", l_To.getCountryId(), l_From.getCountryId());
            success = false;
        }
        if (l_Armies > l_From.getArmies()) {
            log += String.format("\t-> Attacking troop count %d is greater than available troops %d", l_Armies, l_From.getArmies());
            success = false;
        }
        if (!success) {
            System.err.println(log);
            d_Leb.logEvent(log);
        }
        return success;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Advanced " + getOrderInfo().getNumberOfArmy() + " armies " + " from " + getOrderInfo().getDeparture().getCountryId() + " to " + getOrderInfo().getDestination().getCountryId() + ".");
        System.out.println(Constants.SEPERATER);
        d_Leb.logEvent("Advanced " + getOrderInfo().getNumberOfArmy() + " armies " + " from " + getOrderInfo().getDeparture().getCountryId() + " to " + getOrderInfo().getDestination().getCountryId() + ".");
    }
}