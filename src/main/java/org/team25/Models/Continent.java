package org.team25.Models;
import java.util.HashMap;

/**
 * Continent Class for the information storage of Continent IDs, their Control Values and Country within that continent
 * A Hashmap is used to store all the countries belonging to one Continent, Key is Country Id and the Value is its object.
 * @author Tejasvi
 */

public class Continent {
    private int d_controlValue;
    private String d_continentId;
    private int d_continentFileIndex;
    private HashMap<String, Country> d_countries;


    /**
     * Constructor to initialise the Continent class with the arguements passed below
     * Used during loading the map data
     * @param p_continentId Continent ID
     * @param p_controlValue Control Value of Continent
     */
    public Continent(String p_continentId, String p_controlValue){
        this.d_continentId = p_continentId;
        this.d_controlValue = Integer.parseInt(p_controlValue);
        this.d_countries = new HashMap<String, Country>();
    }

    /**
     * getter function to Continent Id
     * @return returns the Continent Id
     */
    public String get_continentId() {
        return this.d_continentId;
    }

    /**
     * Setter funtion to Continent Id
     * @param p_continentId Continent ID
     */
    public void set_continent(String p_continentId){
        this.d_continentId = p_continentId;
    }

    /**
     * Getter function of control value
     * @return control value of the continet
     */
    public int get_controlValue(){
        return this.d_controlValue;
    }

    /**
     * Setter funtion of the control value which saves it in integer from string
     * @param p_controlValue Control value of Continent
     */
    public void set_controlValue(String p_controlValue){
        this.d_controlValue = Integer.parseInt(p_controlValue);
    }

    /**
     * Getter function of the countries as HashMap that belong to this Continent
     * @return returns the HashMap containing countries for a continent
     */
    public HashMap<String, Country> get_countries() {
        return this.d_countries;
    }

    /**
     * Setter function to the Countries Hashmap, if Needed one
     * @param p_countries Countries in the Continent
     */
    public void set_Countries(HashMap<String, Country> p_countries){
        this.d_countries = p_countries;
    }

    /**
     * Getter funtion for the Continent File Index
     * @return The Continent's Index on the file
     */
    public int get_continentFileIndex(){
        return this.d_continentFileIndex;
    }

    /**
     * Setter function for the Continent File Index
     * @param p_continentFileIndex File Index of the Continent
     */
    public void set_continentFileIndex(String p_continentFileIndex){
        this.d_continentFileIndex = Integer.parseInt(p_continentFileIndex);
    }
}
