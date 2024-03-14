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
    private int d_controlValue;
    /**
     * ID of continent
     */
    private String d_continentId;
    /**
     * Continent File index
     */
    private int d_continentFileIndex;
    /**
     * Continent Countries
     */
    private HashMap<String, Country> d_countries;
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
     * @param p_continentId        Continent ID
     * @param p_controlValue       Control Value of Continent
     * @param p_continentFileIndex the p continent file index
     */
    public Continent(String p_continentId, String p_controlValue, int p_continentFileIndex) {
        this.d_continentId = p_continentId;
        this.d_controlValue = Integer.parseInt(p_controlValue);
        this.d_countries = new HashMap<>();
        this.d_continentFileIndex = p_continentFileIndex;
    }

    /**
     * getter function to Continent ID
     *
     * @return returns the Continent ID
     */
    public String getContinentId() {
        return this.d_continentId;
    }

    /**
     * Setter function to ContinentId
     *
     * @param p_continentId Continent ID
     */
    public void setContinentId(String p_continentId) {
        this.d_continentId = p_continentId;
    }

    /**
     * Getter function of control value
     *
     * @return control value of the content
     */
    public int getControlValue() {
        return this.d_controlValue;
    }

    /**
     * Setter function of the control value which saves it in integer from string
     *
     * @param p_controlValue Control value of Continent
     */
    public void setControlValue(String p_controlValue) {
        this.d_controlValue = Integer.parseInt(p_controlValue);
    }

    /**
     * Getter function of the countries as HashMap that belong to this Continent
     *
     * @return returns the HashMap containing countries for a continent
     */
    public HashMap<String, Country> getCountries() {
        return this.d_countries;
    }

    /**
     * Setter function to the Countries Hashmap, if Needed one
     *
     * @param p_countries Countries in the Continent
     */
    public void setCountries(HashMap<String, Country> p_countries) {
        this.d_countries = p_countries;
    }

    /**
     * Getter function for the Continent File Index
     *
     * @return The Continent's Index on the file
     */
    public int getContinentFileIndex() {
        return this.d_continentFileIndex;
    }

    /**
     * Setter function for the Continent File Index
     *
     * @param p_continentFileIndex File Index of the Continent
     */
    public void setContinentFileIndex(String p_continentFileIndex) {
        this.d_continentFileIndex = Integer.parseInt(p_continentFileIndex);
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
