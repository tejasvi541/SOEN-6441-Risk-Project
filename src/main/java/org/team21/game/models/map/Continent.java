package org.team21.game.models.map;

import java.util.HashMap;

/**
 * Continent Class for the information storage of Continent IDs, their Control Values and Country within that continent
 * A Hashmap is used to store all the countries belonging to one Continent, Key is CountryId and the Value is its object.
 *
 * @author Tejasvi
 * @author Kapil
 * @version 1.0.0
 */
public class Continent {
    /**
     * Continent control value
     */
    private int d_ControlValue;
    /**
     * ID of continent
     */
    private String d_ContinentId;
    /**
     * Continent File index
     */
    private int d_ContinentFileIndex;
    /**
     * Continent Countries
     */
    private HashMap<String, Country> d_Countries;
    /**
     * Armies to award to player when captured
     */
    private int d_AwardArmies;
    /**
     * Armies are credited or not
     */
    private boolean d_Credited;


    /**
     * Constructor to initialise the Continent class with the arguments passed below
     * Used during loading the map data
     *
     * @param p_ContinentId        Continent ID
     * @param p_ControlValue       Control Value of Continent
     * @param p_ContinentFileIndex the p continent file index
     */
    public Continent(String p_ContinentId, String p_ControlValue, int p_ContinentFileIndex) {
        this.d_ContinentId = p_ContinentId;
        this.d_ControlValue = Integer.parseInt(p_ControlValue);
        this.d_Countries = new HashMap<>();
        this.d_ContinentFileIndex = p_ContinentFileIndex;
    }

    /**
     * getter function to Continent ID
     *
     * @return returns the Continent ID
     */
    public String getContinentId() {
        return this.d_ContinentId;
    }

    /**
     * Setter function to ContinentId
     *
     * @param p_ContinentId Continent ID
     */
    public void setContinentId(String p_ContinentId) {
        this.d_ContinentId = p_ContinentId;
    }

    /**
     * Getter function of control value
     *
     * @return control value of the content
     */
    public int getControlValue() {
        return this.d_ControlValue;
    }

    /**
     * Setter function of the control value which saves it in integer from string
     *
     * @param p_ControlValue Control value of Continent
     */
    public void setControlValue(String p_ControlValue) {
        this.d_ControlValue = Integer.parseInt(p_ControlValue);
    }

    /**
     * Getter function of the countries as HashMap that belong to this Continent
     *
     * @return returns the HashMap containing countries for a continent
     */
    public HashMap<String, Country> getCountries() {
        return this.d_Countries;
    }

    /**
     * Setter function to the Countries Hashmap, if Needed one
     *
     * @param p_Countries Countries in the Continent
     */
    public void setCountries(HashMap<String, Country> p_Countries) {
        this.d_Countries = p_Countries;
    }

    /**
     * Getter function for the Continent File Index
     *
     * @return The Continent's Index on the file
     */
    public int getContinentFileIndex() {
        return this.d_ContinentFileIndex;
    }

    /**
     * Setter function for the Continent File Index
     *
     * @param p_ContinentFileIndex File Index of the Continent
     */
    public void setContinentFileIndex(String p_ContinentFileIndex) {
        this.d_ContinentFileIndex = Integer.parseInt(p_ContinentFileIndex);
    }

    //Todo refactor

    /**
     * Get the Awarded armies
     *
     * @return d_AwardArmies The Awarded armies assigned to the continent
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
}
