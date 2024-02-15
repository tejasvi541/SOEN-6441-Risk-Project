package org.team25.game.models;

import java.util.HashMap;

/**
 * Represents a country in a world map.
 * Each country has an index, ID, parent continent, neighbors, number of armies, and coordinates for GUI display.
 * @author Tejasvi
 * @version 1.0.0
 */
public class Country {

    private int d_countryFileIndex;
    private String d_countryId;
    private String d_parentContinent;
    private HashMap<String, Country> d_neighbours;
    private int d_numberOfArmies;
    private int d_xCoordinate;
    private int d_yCoordinate;

    /**
     * Set Country object with default values.
     */
    public Country(){}

    /**
     * Create Country Constructor with values in argument parameters and set defaults for the rest.
     * @param p_countryId ID of the country
     * @param p_parentContinent Name of the continent in which this country belongs
     */
    public Country(String p_countryId, String p_parentContinent){
        this.d_countryId = p_countryId;
        this.d_parentContinent = p_parentContinent;
        this.d_neighbours = new HashMap<String, Country>();
        this.d_numberOfArmies = 0;
    }

    public void set_countryFileIndex(int d_countryFileIndex) {
        this.d_countryFileIndex = d_countryFileIndex;
    }

    public void set_countryId(String d_countryId) {
        this.d_countryId = d_countryId;
    }

    public void set_parentContinent(String d_parentContinent) {
        this.d_parentContinent = d_parentContinent;
    }

    public void set_neighbours(HashMap<String, Country> d_neighbours) {
        this.d_neighbours = d_neighbours;
    }

    public void set_xCoordinate(int d_xCoordinate) {
        this.d_xCoordinate = d_xCoordinate;
    }

    public void set_yCoordinate(int d_yCoordinate) {
        this.d_yCoordinate = d_yCoordinate;
    }

    /**
     * Creates Country object having more arguments
     * This constructor used while reading from ".map" files.
     * @param p_countryFileIndex index in map file
     * @param p_countryId ID of the Country
     * @param p_parentContinent Index of the parent Continent of the country
     * @param p_xCoordinate X-coordinate of the Country for GUI
     * @param p_yCoordinate Y-coordinate of the Country for GUI
     * @param p_gameMap Map object in which country is present
     */
    public Country(String p_countryFileIndex, String p_countryId, String p_parentContinent, String p_xCoordinate, String p_yCoordinate, GameMap p_gameMap){
        this.d_countryFileIndex = Integer.parseInt(p_countryFileIndex);
        this.d_countryId = p_countryId;
        for(Continent c: p_gameMap.get_continents().values()){
            if(c.get_continentFileIndex()==Integer.parseInt(p_parentContinent)) {
                this.d_parentContinent = c.get_continentId();
            }
        }
        this.d_neighbours = new HashMap<String, Country>();
        this.d_xCoordinate = Integer.parseInt(p_xCoordinate);
        this.d_yCoordinate = Integer.parseInt(p_yCoordinate);
        this.d_numberOfArmies = 0;
    }

    /**
     * Returns the index of this country in the ".map" file
     * @return returns d_index of this country in the ".map" file
     */
    public int get_countryFileIndex() {
        return d_countryFileIndex;
    }

    /**
     * Returns the name of the Continent in which this country belongs
     * @return returns d_inContinent in which this country belongs
     */
    public String get_parentContinent() {
        return this.d_parentContinent;
    }

    /**
     * Returns the name of the country
     * @return returns d_countryName
     */
    public String get_countryId() {
        return d_countryId;
    }

    /**
     * Returns the HashMap holding all the neighbors with their names in lower case as keys and their corresponding
     * Country object references as values.
     * @return returns d_neighbors of this country
     */
    public HashMap<String, Country> get_Neighbours(){
        return this.d_neighbours;
    }

    /**
     * Getter method to get number of armies in the country.
     * @return returns d_numberOfArmies
     */
    public int get_numberOfArmies() {
        return this.d_numberOfArmies;
    }

    /**
     * Set number of armies in the country
     * @param p_numberOfArmies number of armies to be set in the country
     */
    public void set_numberOfArmies(int p_numberOfArmies) {
        this.d_numberOfArmies = p_numberOfArmies;
    }

    /**
     * Overrides the String object with countryDetails
     */
    @Override
    public String toString() {
        return "Country [countryName=" + d_countryId + ", xCoOrdinate=" + d_xCoordinate + ", yCoOrdinate=" + d_yCoordinate
                + ", Parent Continent=" + d_parentContinent + "]";
    }
}
