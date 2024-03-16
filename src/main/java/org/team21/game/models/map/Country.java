package org.team21.game.models.map;

import org.team21.game.models.game_play.Player;

import java.util.HashMap;

/**
 * Represents a country in a world map.
 * Each country has an index, ID, parent continent, neighbors, number of armies, and coordinates for GUI display.
 *
 * @author Tejasvi
 * @author Kapil
 * @version 1.0.0
 */
public class Country {

    /**
     * Countries index
     */
    private int d_CountryFileIndex;
    /**
     * ID of country
     */
    private String d_CountryId;
    /**
     * Continent of country
     */
    private String d_ParentContinent;
    /**
     * Neighbours of country
     */
    private HashMap<String, Country> d_Neighbours;
    /**
     * Armies residing in country
     */
    private int d_NumberOfArmies;
    /**
     * X-Coordinates of Country
     */
    private int d_XCoordinate;
    /**
     * Y-Coordinates of Country
     */
    private int d_YCoordinate;
    /**
     * Player/Owner of country
     */
    private Player d_Player = new Player();
    /**
     * Armies of country
     */
    private int d_Armies;

    /**
     * Set Country object with default values.
     */
    public Country() {
    }

    /**
     * Create Country Constructor with values in argument parameters and set defaults for the rest.
     *
     * @param p_CountryId       ID of the country
     * @param p_ParentContinent Name of the continent in which this country belongs
     */
    public Country(String p_CountryId, String p_ParentContinent) {
        this.d_CountryId = p_CountryId;
        this.d_ParentContinent = p_ParentContinent;
        this.d_Neighbours = new HashMap<>();
        this.d_NumberOfArmies = 0;
        this.d_Armies = 0;
    }

    /**
     * Creates Country object having more arguments
     * This constructor used while reading from ".map" files.
     *
     * @param p_CountryFileIndex index in map file
     * @param p_CountryId        ID of the Country
     * @param p_ParentContinent  Index of the parent Continent of the country
     * @param p_XCoordinate      X-coordinate of the Country for GUI
     * @param p_YCoordinate      Y-coordinate of the Country for GUI
     * @param p_GameMap          Map object in which country is present
     */
    public Country(String p_CountryFileIndex, String p_CountryId, String p_ParentContinent, String p_XCoordinate, String p_YCoordinate, GameMap p_GameMap) {
        this.d_CountryFileIndex = Integer.parseInt(p_CountryFileIndex);
        this.d_CountryId = p_CountryId;

        try {
            for (Continent l_C : p_GameMap.getContinents().values()) {
                if (l_C.getContinentFileIndex() == Integer.parseInt(p_ParentContinent)) {
                    this.d_ParentContinent = l_C.getContinentId();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.d_Neighbours = new HashMap<>();
        this.d_XCoordinate = Integer.parseInt(p_XCoordinate);
        this.d_YCoordinate = Integer.parseInt(p_YCoordinate);
        this.d_NumberOfArmies = 0;
    }

    /**
     * Set X coordinate of country
     *
     * @param d_XCoordinate X-Coordinate
     */
    public void setXCoordinate(int d_XCoordinate) {
        this.d_XCoordinate = d_XCoordinate;
    }

    /**
     * set Y coordinate of the country
     *
     * @param d_YCoordinate Y coordinate
     */
    public void setYCoordinate(int d_YCoordinate) {
        this.d_YCoordinate = d_YCoordinate;
    }

    /**
     * Get the player instance for the game play
     *
     * @return d_Player Player instance
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Set the player instance for the game play
     *
     * @param p_Player Player instance
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * Get the number of armies for the country
     *
     * @return d_Armies Number of armies for the country
     */
    public int getArmies() {
        return d_Armies;
    }

    /**
     * Set the armies for the country
     *
     * @param p_Armies Number of armies for the country
     */
    public void setArmies(int p_Armies) {
        this.d_Armies = p_Armies;
    }

    /**
     * deploy the armies for the player
     *
     * @param p_Armies number of armies to be deployed
     */
    public void deployArmies(int p_Armies) {
        this.d_Armies += p_Armies;
    }

    /**
     * Returns the index of this country in the ".map" file
     *
     * @return returns d_index of this country in the ".map" file
     */
    public int getCountryFileIndex() {
        return d_CountryFileIndex;
    }

    /**
     * Set the countries file index
     *
     * @param d_CountryFileIndex File Index
     */
    public void setCountryFileIndex(int d_CountryFileIndex) {
        this.d_CountryFileIndex = d_CountryFileIndex;
    }

    /**
     * Returns the name of the Continent in which this country belongs
     *
     * @return returns d_inContinent in which this country belongs
     */
    public String getParentContinent() {
        return this.d_ParentContinent;
    }

    /**
     * set Parent Continent of country
     *
     * @param d_ParentContinent parent continent of country
     */
    public void setParentContinent(String d_ParentContinent) {
        this.d_ParentContinent = d_ParentContinent;
    }

    /**
     * Returns the name of the country
     *
     * @return returns d_countryName
     */
    public String getCountryId() {
        return d_CountryId;
    }

    /**
     * Set the name of country
     *
     * @param d_CountryId Name of country Parameter
     */
    public void setCountryId(String d_CountryId) {
        this.d_CountryId = d_CountryId;
    }

    /**
     * Returns the HashMap holding all the neighbors with their names in lower case as keys and their corresponding
     * Country object references as values.
     *
     * @return returns d_neighbors of this country
     */
    public HashMap<String, Country> getNeighbours() {
        return this.d_Neighbours;
    }

    /**
     * Set neighbor of the country
     *
     * @param d_Neighbours neighbor hashmap
     */
    public void setNeighbours(HashMap<String, Country> d_Neighbours) {
        this.d_Neighbours = d_Neighbours;
    }

    /**
     * Getter method to get number of armies in the country.
     *
     * @return returns d_numberOfArmies
     */
    public int getNumberOfArmies() {
        return this.d_NumberOfArmies;
    }

    /**
     * Set number of armies in the country
     *
     * @param p_NumberOfArmies number of armies to be set in the country
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Overrides the String object with countryDetails
     */
    @Override
    public String toString() {
        return "Country [countryName=" + d_CountryId + ", xCoOrdinate=" + d_XCoordinate + ", yCoOrdinate=" + d_YCoordinate
                + ", Parent Continent=" + d_ParentContinent + "]";
    }

    /**
     * To check if current "this" is neighbour of given country
     *
     * @param p_NeighbourCountry : Neighbour country
     * @return : return true if current country is neighbour of p_NeighbourCountry else return false
     */
    public boolean isNeighbor(Country p_NeighbourCountry) {
        return this.getNeighbours().containsKey(p_NeighbourCountry.d_CountryId.toLowerCase());
    }
}
