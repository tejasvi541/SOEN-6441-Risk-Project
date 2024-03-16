package org.team21.game.models.map;

import org.team21.game.models.game_play.Player;

import java.util.*;

/**
 * Map class holds the details of map in the game
 * Consists of data structures to access countries and continents and their neighbours of the map
 *
 * @author Tejasvi
 * @author Kapil Soni
 * @version 1.0.1
 */
public class GameMap {

    /**
     * Singleton Instance of Game map which will be
     * used by all the controllers
     */
    private static GameMap d_GameMap_Single_Instance = null;
    /**
     * Map Names
     */
    private String d_MapName;
    /**
     * Is Map valid or not
     */
    private boolean d_IsValid;
    /**
     * Continents of map
     */
    private HashMap<String, Continent> d_Continents;
    /**
     * Countries in map
     */
    private HashMap<String, Country> d_Countries;
    /**
     * Players in entire map
     */
    private HashMap<String, Player> d_Players = new HashMap<>();

    /**
     * Map Constructor to initialise the Class
     */
    public GameMap() {
        this.d_MapName = "";
        this.d_IsValid = false;
        this.d_Continents = new HashMap<>();
        this.d_Countries = new HashMap<>();
    }


    /**
     * Map Constructor to initialise the class
     *
     * @param p_MapName Map name parameter
     */
    public GameMap(String p_MapName) {
        this.d_MapName = p_MapName;
        this.d_IsValid = false;
        this.d_Continents = new HashMap<>();
        this.d_Countries = new HashMap<>();
    }

    /**
     * Gets instance.
     *
     * @return Singleton Instance of Map
     */
    public static synchronized GameMap getInstance() {
        if (d_GameMap_Single_Instance == null)
            d_GameMap_Single_Instance = new GameMap();

        return d_GameMap_Single_Instance;
    }

    /**
     * Getter Function for the map name
     *
     * @return the name of the Map
     */
    public String getMapName() {
        return this.d_MapName;
    }

    /**
     * Setter function of the map name
     *
     * @param p_MapName function argument, means map name to be set
     */
    public void setMapName(String p_MapName) {
        this.d_MapName = p_MapName;
    }

    /**
     * Getter function of the d_isValid
     *
     * @return True /false if the map is valid or not
     */
    public boolean getIsValid() {
        return this.d_IsValid;
    }

    /**
     * Setter function for if the map is valid
     *
     * @param p_IsValid function argument to set the d_isValid data-member
     */
    public void setIsValid(boolean p_IsValid) {
        this.d_IsValid = p_IsValid;
    }

    /**
     * Getter function to get the hashmap of the continents in that map
     *
     * @return hashmap of the continents
     */
    public HashMap<String, Continent> getContinents() {
        return this.d_Continents;
    }

    /**
     * Setter function to set the continents of the map in a hashmap
     *
     * @param p_Continents function argument of hashmap of continents to be set
     */
    public void setContinents(HashMap<String, Continent> p_Continents) {
        this.d_Continents = p_Continents;
    }

    /**
     * Getter method to return HashMap maintaining the list of countries in the map.
     *
     * @return return HashMap
     */
    public HashMap<String, Country> getCountries() {
        return this.d_Countries;
    }

    /**
     * Setter method to set the d_countries HashMap to the given HashMap parameter.
     *
     * @param p_Countries HashMap for d_countries
     */
    public void setCountries(HashMap<String, Country> p_Countries) {
        this.d_Countries = p_Countries;
    }

    /**
     * Clear map.
     */
    public void clearMap() {
        this.setMapName("");
        this.setPlayers(new HashMap<>());
        this.setCountries(new HashMap<>());
        this.setContinents(new HashMap<>());
        this.setIsValid(false);
        this.setPlayer(new Player());
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
     *
     * @param p_Players Players HashMap
     */
    public void setPlayers(HashMap<String, Player> p_Players) {
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
     *
     * @param p_Player Player Object to set the Player
     */
    public void setPlayer(Player p_Player) {
        this.d_Players.put(p_Player.getName().toLowerCase(), p_Player);
    }

    /**
     * Adds player to the game map.
     *
     * @param p_PlayerName Player name
     */
    public void addPlayer(String p_PlayerName) {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            System.out.println("Player already exists");
            return;
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
     */
    public void removePlayer(String p_PlayerName) {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player)) {
            System.out.println("Player not found : " + p_PlayerName);
            return;
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
        return d_Continents.get(p_Id);
    }

    /**
     * Assign countries.
     */
    public void assignCountries() {
        int playerIndex = 0;
        List<Player> l_Players = d_GameMap_Single_Instance.getPlayers().values().stream().toList();
        try {
            List<Country> l_CountryList = new ArrayList<>(d_GameMap_Single_Instance.getCountries().values());

            Collections.shuffle(l_CountryList);

            for (Country country : l_CountryList) {
                Player l_Player = l_Players.get(playerIndex);
                l_Player.getCapturedCountries().add(country);
                country.setPlayer(l_Player);
                System.out.println(country.getCountryId() + " assigned to " + l_Player.getName());

                playerIndex = (playerIndex + 1) % l_Players.size(); // Move to the next player in a circular manner
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
