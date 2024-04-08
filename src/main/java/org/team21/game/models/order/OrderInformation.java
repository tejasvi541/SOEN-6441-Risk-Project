package org.team21.game.models.order;

import org.team21.game.models.map.Country;
import org.team21.game.models.map.Player;

import java.io.Serializable;

/**
 * A class representing the information of an order.
 * This class holds details such as the command entered by the player,
 * player object, neutral player object, departure country, destination country,
 * target country, and the number of armies involved in the order.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class OrderInformation implements Serializable {

    /**
     * The command entered by the player.
     */
    private String d_Command;
    /**
     * The player involved in the order.
     */
    private Player d_Player;
    /**
     * The neutral player involved in the order.
     */
    private Player d_NeutralPlayer;
    /**
     * The departure country from where the armies are being deployed.
     */
    private Country d_Departure;
    /**
     * The destination country where the armies are being deployed.
     */
    private Country d_Destination;
    /**
     * The target country for the order.
     */
    private Country d_TargetCountry;
    /**
     * The number of armies involved in the order.
     */
    private int d_NumberOfArmy;

    /**
     * Retrieves the neutral player involved in the order.
     *
     * @return the neutral player.
     */
    public Player getNeutralPlayer() {
        return d_NeutralPlayer;
    }

    /**
     * Sets the neutral player involved in the order.
     *
     * @param p_NeutralPlayer the neutral player.
     */
    public void setNeutralPlayer(Player p_NeutralPlayer) {
        this.d_NeutralPlayer = p_NeutralPlayer;
    }

    /**
     * Retrieves the player involved in the order.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Sets the player involved in the order.
     *
     * @param p_Player the player.
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * Retrieves the departure country from the order.
     *
     * @return the departure country.
     */
    public Country getDeparture() {
        return d_Departure;
    }

    /**
     * Sets the departure country for the order.
     *
     * @param p_Departure the departure country.
     */
    public void setDeparture(Country p_Departure) {
        this.d_Departure = p_Departure;
    }

    /**
     * Retrieves the destination country for the order.
     *
     * @return the destination country.
     */
    public Country getDestination() {
        return d_Destination;
    }

    /**
     * Sets the destination country for the order.
     *
     * @param p_Destination the destination country.
     */
    public void setDestination(Country p_Destination) {
        this.d_Destination = p_Destination;
    }


    /**
     * Retrieves the number of armies involved in the order.
     *
     * @return the number of armies.
     */
    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    /**
     * Sets the number of armies involved in the order.
     *
     *  @param p_NumberOfArmy the number of armies.
     */
    public void setNumberOfArmy(int p_NumberOfArmy) {
        this.d_NumberOfArmy = p_NumberOfArmy;
    }

    /**
     * Retrieves the target country for the order.
     *
     * @return the target country.
     */
    public Country getTargetCountry() {
        return this.d_TargetCountry;
    }

    /**
     * Sets the target country for the order.
     *
     * @param p_TargetCountry the target country.
     */
    public void setTargetCountry(Country p_TargetCountry) {
        this.d_TargetCountry = p_TargetCountry;
    }

    /**
     * Retrieves the command entered by the player.
     *
     * @return the command.
     */
    public String getCommand(){
        return d_Command;
    }

    /**
     * Sets the command entered by the player.
     *
     * @param p_Command the command.
     */
    public void setCommand(String p_Command){
        d_Command = p_Command;
    }
}
