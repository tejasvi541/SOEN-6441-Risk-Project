package org.team21.game.models.map;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Continent Class for the information storage of Continent IDs, their Names and Countries within that continent
 * A Set is used to store all the countries belonging to one Continent.
 *
 * @author Tejasvi
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class Continent implements Serializable {
    /**
     * ID of the continent
     */
    private String d_Id;
    /**
     * Name of the continent
     */
    private String d_Name;
    /**
     * Award armies to a player when captured.
     */
    private int d_AwardArmies;
    /**
     * A boolean when armies credited or not
     */
    private boolean d_Credited;
    /**
     * A set of countries in the continent
     */
    private Set<Country> d_Countries;

    /**
     * Getter to get the continent ID
     *
     * @return the continent ID
     */
    public String getId() {
        return d_Id;
    }

    /**
     * Setter to set the continent ID
     *
     * @param p_Id holds the continent ID
     */
    public void setId(String p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * Getter to get the continent name
     *
     * @return the continent name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Setter to set the continent name
     *
     * @param p_Name hold the continent name
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Get the Awarded armies
     *
     * @return d_AwardArmies  The Awarded armies assigned to the continent
     */
    public int getAwardArmies() {
        return d_AwardArmies;
    }

    /**
     * Set the Awarded armies for the continent
     *
     * @param p_AwardArmies Awarded armies
     */
    public void setAwardArmies(int p_AwardArmies) {
        this.d_AwardArmies = p_AwardArmies;
    }

    /**
     * To check if armies are credited or not
     *
     * @return true if armies are credited else false
     */
    public boolean isCredited() {
        return d_Credited;
    }

    /**
     * To set the credited armies
     *
     * @param p_Credited holds the credited armies
     */
    public void setCredited(boolean p_Credited) {
        this.d_Credited = p_Credited;
    }

    /**
     * Returns the list of countries belonging to the continent
     *
     * @return set of countries
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }

    /**
     * Set countries in the continents
     *
     * @param p_Countries holds the set of countries
     */
    public void setCountries(Set<Country> p_Countries) {
        this.d_Countries = p_Countries;
    }
}
