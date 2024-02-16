package org.team25.game.models.map;

import org.team25.game.models.game_play.Player;
import org.team25.game.utils.validation.ValidationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Map class holds the details of map in the game
 * Consists of data structures to access countries and continents and their neighbours of the map
 * @author Tejasvi
 * @version 1.0.0
 */
public class GameMap {

    private static GameMap d_gameMap_single_instance = null;
    private String d_mapName;
    private boolean d_isValid;
    private HashMap<String, Continent> d_continents;
    private HashMap<String, Country> d_countries;

    private HashMap<String, Player> d_Players = new HashMap<>();

    /**
     * @return Singleton Instance of Map
     */
    public static synchronized GameMap getInstance()
    {
        if (d_gameMap_single_instance == null)
            d_gameMap_single_instance = new GameMap();

        return d_gameMap_single_instance;
    }


    /**
     * Map Constructor to initialise the Class
     */
    public GameMap(){
        this.d_mapName="";
        this.d_isValid=false;
        this.d_continents= new HashMap<>();
        this.d_countries= new HashMap<>();
    }

    /**
     * Map Constructor to initialise the class
     * @param p_mapName Map name parameter
     */
    public GameMap(String p_mapName){
        this.d_mapName=p_mapName;
        this.d_isValid=false;
        this.d_continents= new HashMap<>();
        this.d_countries= new HashMap<>();
    }

    /**
     * Getter Function for the map name
     * @return the name of the Map
     */
    public String get_mapName(){
        return this.d_mapName;
    }

    /**
     * Setter function of the map name
     * @param p_mapName function argument, means map name to be set
     */
    public void set_mapName(String p_mapName){
        this.d_mapName = p_mapName;
    }

    /**
     * Getter function of the d_isValid
     * @return True/false if the map is valid or not
     */
    public boolean get_isValid(){
        return this.d_isValid;
    }

    /**
     * Setter function for if the map is valid
     * @param p_isValid function argument to set the d_isValid data-member
     */
    public void set_isValid(boolean p_isValid){
        this.d_isValid = p_isValid;
    }

    /**
     * Getter function to get the hashmap of the continents in that map
     * @return hashmap of the continents
     */
    public HashMap<String, Continent> get_continents(){
        return this.d_continents;
    }

    /**
     * Setter function to set the continents of the map in a hashmap
     * @param p_continents function argument of hashmap of continents to be set
     */
    public void set_continents(HashMap<String, Continent> p_continents){
        this.d_continents = p_continents;
    }

    /**
     * Getter method to return HashMap maintaining the list of countries in the map.
     * @return return HashMap
     */
    public HashMap<String, Country> get_countries() {
        return this.d_countries;
    }

    /**
     * Setter method to set the d_countries HashMap to the given HashMap parameter.
     * @param p_countries HashMap for d_countries
     */
    public void set_countries(HashMap<String, Country> p_countries) {
        this.d_countries = p_countries;
    }

    public void clearMap(){
        this.d_mapName = null;
        this.d_continents = null;
        this.d_countries = null;
        this.d_isValid = false;
    }

    /**
     * Get the list of players
     *
     * @return d_Players List of players
     */
    public HashMap<String, Player> getPlayers() {
        return this.d_Players;
    }

    /**
     * Set The HashMap of Players
     * @param p_Players Players HashMap
     */
    public void setPlayers(HashMap<String, Player> p_Players){
        this.d_Players = p_Players;
    }

    /**
     * Get a single player
     *
     * @param p_Id Unique Player name
     * @return the required Player object
     */
    public Player getPlayer(String p_Id) {
        return d_Players.get(p_Id);
    }

    /**
     * Add Player to the Players Hashmap
     * @param p_player Player Object to set the Player
     */
    public void setPlayer(Player p_player){
        this.d_Players.put(p_player.getName().toLowerCase(), p_player);
    }

    public HashMap<String, Continent> getContinents(String continent) {
        return d_continents;
    }
    /**
     * Adds player to the game map.
     *
     * @param p_PlayerName Player name
     * @throws ValidationException if any input/output issue
     */
    public void addPlayer(String p_PlayerName) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        System.out.println("Successfully added Player: " + p_PlayerName);
    }

    /**
     * Removes player from game map.
     *
     * @param p_PlayerName Player name
     * @throws ValidationException if any input/output issue
     */
    public void removePlayer(String p_PlayerName) throws ValidationException {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player)) {
            throw new ValidationException("Player does not exist: " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        System.out.println("Successfully deleted the player: " + p_PlayerName);
    }

    /**
     * Get a single continent
     *
     * @param p_Id Unique Continent name
     * @return the required Continent object
     */
    public Continent getContinent(String p_Id) {
        return d_continents.get(p_Id);
    }

    /**
     * Get the list of countries
     *
     * @return d_Countries List of the countries
     */
    public HashMap<String, Country> getCountries() {
        return d_countries;
    }
    public void assignCountries() {
        int playerIndex = 0;
        List<Player> players = d_gameMap_single_instance.getPlayers().values().stream().collect(Collectors.toList());
        List<Country> countryList = d_gameMap_single_instance.getCountries().values().stream().collect(Collectors.toList());

        Collections.shuffle(countryList);

        for (Country country : countryList) {
            Player player = players.get(playerIndex);
            player.getCapturedCountries().add(country);
            country.setPlayer(player);
            System.out.println(country.get_countryId() + " assigned to " + player.getName());

            playerIndex = (playerIndex + 1) % players.size(); // Move to the next player in a circular manner
        }
    }
}
