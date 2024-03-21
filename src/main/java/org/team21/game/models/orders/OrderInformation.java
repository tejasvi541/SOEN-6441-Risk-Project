package org.team21.game.models.orders;


import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;


/**
 * OrderInformation model will be used by {OrderOwner,Order}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
//Todo:Kapil soni refactor
public class OrderInformation {

    /**
     * Default constructor
     */
    public OrderInformation(){

    }

    /**
     * The player who issued this order.
     */
    private Player d_Player;

    /**
     * A special player object representing neutral territories or actions
     * not associated with a specific player. For example, claiming an unowned
     * country at the start of the game would likely use the neutral player.
     */
    private Player d_NeutralPlayer;

    /**
     * The country from which armies will depart. This is used for movement
     * orders (moving armies between owned territories) and attack orders.
     */
    private Country d_Departure;

    /**
     * The country where armies are moving to. Used for both movement and
     * attack orders.
     */
    private Country d_Destination;

    /**
     * The country targeted for attack (if applicable). This will likely be
     * null for orders that are not attacks.
     */
    private Country d_TargetCountry;

    /**
     * The number of armies involved in this order. Note that there might be
     * game rules limiting how many armies can be moved or used in an attack.
     */
    private int d_NumberOfArmy;

    // ... (Constructor goes here) ...

    /**
     * Returns the neutral player object. This is used for actions not
     * directly associated with a specific player (e.g., claiming unowned
     * territories).
     *
     * @return the neutral player object
     */
    public Player getNeutralPlayer() {
        return d_NeutralPlayer;
    }

    /**
     * Sets the neutral player object.
     *
     * @param d_NeutralPlayer the neutral player object
     */
    public void setNeutralPlayer(Player d_NeutralPlayer) {
        this.d_NeutralPlayer = d_NeutralPlayer;
    }

    /**
     * Returns the player who issued this order.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Sets the player who issued this order.
     *
     * @param p_Player the player object
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * Returns the country from which armies depart.
     *
     * @return the departure country object
     */
    public Country getDeparture() {
        return d_Departure;
    }

    /**
     * Sets the country from which armies depart.
     *
     * @param p_Departure departure country object
     */
    public void setDeparture(Country p_Departure) {
        this.d_Departure = p_Departure;
    }

    /**
     * Returns the country where armies are moving to.
     *
     * @return the destination of armies
     */
    public Country getDestination() {
        return d_Destination;
    }

    /**
     * Sets the destination country of the armies.
     *
     * @param p_Destination the destination of armies
     */
    public void setDestination(Country p_Destination) {
        this.d_Destination = p_Destination;
    }

    /**
     * Returns the number of armies in the order
     *
     * @return the number of armies
     */
    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    /**
     * Sets the number of armies in the order.
     *
     * @param p_NumberOfArmy the number of armies
     */
    public void setNumberOfArmy(int p_NumberOfArmy) {
        this.d_NumberOfArmy = p_NumberOfArmy;
    }

    /**
     * Returns the target country of the order (relevant for attack orders).
     *
     * @return the target country
     */
    public Country getTargetCountry() {
        return this.d_TargetCountry;
    }

    /**
     * Sets the target country of the order (relevant for attack orders).
     *
     * @param p_TargetCountry the target country
     */
    public void setTargetCountry(Country p_TargetCountry) {
        this.d_TargetCountry = p_TargetCountry;
    }
}
