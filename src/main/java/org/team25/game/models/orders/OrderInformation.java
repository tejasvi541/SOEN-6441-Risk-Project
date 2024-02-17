package org.team25.game.models.orders;


import org.team25.game.models.game_play.Player;
import org.team25.game.models.map.Country;


/**
 * OrderInformation model will be used by {@linkplain OrderOwner,Order}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class OrderInformation {

    /**
     * The player initiating the movement.
     */
    private Player d_Player;

    /**
     * The country from which the movement begins.
     */
    private Country d_Departure;

    /**
     * The destination country for the movement.
     */
    private String d_Destination;

    /**
     * The number of armies being moved.
     */
    private int d_NumberOfArmy;

    /**
     * A function to get the player information
     *
     * @return the object of player
     */
    public Player getPlayer() {

        return d_Player;
    }

    /**
     * A function to set the player information
     *
     * @param d_Player the object of player
     */
    public void setPlayer(Player d_Player) {

        this.d_Player = d_Player;
    }

    /**
     * A function to get the departure of the armies from the order
     *
     * @return the departure country object
     */
    public Country getDeparture() {

        return d_Departure;
    }

    /**
     * A function to set the departure of the armies from the order
     *
     * @param d_Departure departure country object
     */
    public void setDeparture(Country d_Departure) {

        this.d_Departure = d_Departure;
    }

    /**
     * A function to get where the army is going to.
     *
     * @return the destination of armies
     */
    public String getDestination() {

        return d_Destination;
    }

    /**
     * A function to set the destination of the armies
     *
     * @param d_Destination the destination of armies
     */
    public void setDestination(String d_Destination) {

        this.d_Destination = d_Destination;
    }


    /**
     * A function to get the number of armies in the order
     *
     * @return the number of armies
     */
    public int getNumberOfArmy() {

        return d_NumberOfArmy;
    }

    /**
     * A function to set the number of armies in the order
     *
     * @param d_NumberOfArmy the number of armies
     */
    public void setNumberOfArmy(int d_NumberOfArmy) {

        this.d_NumberOfArmy = d_NumberOfArmy;
    }

}
