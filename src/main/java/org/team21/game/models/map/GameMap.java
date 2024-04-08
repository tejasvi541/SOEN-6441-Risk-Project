package org.team21.game.models.map;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.utils.adapter.Adaptee;
import org.team21.game.utils.adapter.Adapter;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.MapValidation;
import org.team21.game.utils.validation.ValidationException;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Concrete class representing the game map. It stores information about continents, countries, players,
 * and game progress. This class provides methods for managing continents, countries, players, and game phases.
 * It also offers functionalities for saving and loading maps, assigning countries to players, and displaying map details.
 *
 * This class follows the Singleton design pattern to ensure only one instance of the game map exists throughout the game.
 *
 * @author Tejasvi
 * @author Bharti Bharti Chhabra
 * @version 1.0.0
 */
public class GameMap implements Serializable {
    /**
     * Serial version UID for serialization.
     */
    private static final long d_serialVersionUID = 45443434343L;
    /**
     * Singleton instance of the GameMap class.
     */
    private static GameMap d_GameMap;
    /**
     * The current game phase.
     */
    private GamePhase d_GamePhase;
    /**
     * A HashMap to store the continents in the game map.
     */
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    /**
     * A HashMap to store the countries in the game map.
     */
    private HashMap<String, Country> d_Countries = new HashMap<>();
    /**
     * A HashMap to store the players in the game map.
     */
    private HashMap<String, Player> d_Players = new HashMap<>();
    /**
     * The name of the game map.
     */
    private String d_Name;
    /**
     * Error message associated with the game map.
     */
    private String d_ErrorMessage;

    /**
     * Logger instance for logging game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * The winner of the game.
     */
    private Player d_Winner;

    /**
     * Number of tries attempted in the game.
     */
    private int d_Tries;

    /**
     * The current player in the game.
     */
    private Player d_CurrentPlayer;

    /**
     * Indicates whether the game has been loaded.
     */
    private Boolean d_GameLoaded = false;

    /**
     * Default constructor.
     */
    public GameMap() {
    }

    /**
     * Method to get instance of Game map class
     *
     * @return The instance of the GameMap class
     */
    public static GameMap getInstance() {
        if (Objects.isNull(d_GameMap)) {
            d_GameMap = new GameMap();
        }
        return d_GameMap;
    }

    /**
     * Returns the current game phase.
     *
     * @return The current game phase
     */
    public GamePhase getGamePhase() {
        return d_GamePhase;
    }

    /**
     * Sets the current game phase.
     *
     * @param p_GamePhase The game phase to set
     */
    public void setGamePhase(GamePhase p_GamePhase) {
        this.d_GamePhase = p_GamePhase;
    }

    /**
     * Gets the list of all continents.
     *
     * @return The list of continents
     */
    public HashMap<String, Continent> getContinents() {
        return d_Continents;
    }

    /**
     * Gets a single continent by its unique ID.
     *
     * @param p_Id The unique ID of the continent
     * @return The required Continent object
     */
    public Continent getContinent(String p_Id) {
        return d_Continents.get(p_Id);
    }

    /**
     * Gets the list of countries.
     *
     * @return The list of countries
     */
    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }

    /**
     * Gets a single country by its unique ID.
     *
     * @param p_Id The unique ID of the country
     * @return The required Country object
     */
    public Country getCountry(String p_Id) {
        return d_Countries.get(p_Id);
    }

    /**
     * Gets the list of players.
     *
     * @return The list of players
     */
    public HashMap<String, Player> getPlayers() {
        return d_Players;
    }

    /**
     * Gets a single player by their unique ID.
     *
     * @param p_Id The unique ID of the player
     * @return The required Player object
     */
    public Player getPlayer(String p_Id) {
        return d_Players.get(p_Id);
    }

    /**
     * Gets the error message.
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return d_ErrorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param p_ErrorMessage The error message to set
     */
    public void setErrorMessage(String p_ErrorMessage) {
        this.d_ErrorMessage = p_ErrorMessage;
    }

    /**
     * Gets the name of the map.
     *
     * @return The map name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Sets the name of the map.
     *
     * @param p_Name The map name to set
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Gets the current player.
     *
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return d_CurrentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @param p_CurrentPlayer The player to set as current
     */
    public void setCurrentPlayer(Player p_CurrentPlayer) {
        this.d_CurrentPlayer = p_CurrentPlayer;
    }

    /**
     * Gets the game loaded status.
     *
     * @return true if the game is loaded, false otherwise
     */
    public Boolean getGameLoaded() {
        return d_GameLoaded;
    }

    /**
     * Sets the game loaded status.
     *
     * @param p_GameLoaded The loaded status to set
     */
    public void setGameLoaded(Boolean p_GameLoaded) {
        this.d_GameLoaded = p_GameLoaded;
    }

    /**
     * Resets the game map by clearing all its elements.
     */
    public void flushGameMap() {
        GameMap.getInstance().getContinents().clear();
        GameMap.getInstance().getCountries().clear();
        GameMap.getInstance().getPlayers().clear();
    }

    /**
     * Adds a continent to the map's continent list.
     *
     * @param p_ContinentName The name of the continent to add
     * @param p_ControlValue  The control value of the continent
     * @throws ValidationException if any input or output issue occurs
     */
    public void addContinent(String p_ContinentName, String p_ControlValue) throws ValidationException {
        if (this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("Continent already exists");
        }
        Continent l_Continent = new Continent();
        l_Continent.setName(p_ContinentName);
        l_Continent.setAwardArmies(Integer.parseInt(p_ControlValue));
        this.getContinents().put(p_ContinentName, l_Continent);
        d_Logger.log("Successfully added Continent: " + p_ContinentName);
    }

    /**
     * Adds a country to the map's country list and to the specified continent's country list.
     *
     * @param p_CountryName   The name of the country to add
     * @param p_ContinentName The name of the continent to which the country belongs
     * @throws ValidationException if any input or output issue occurs
     */
    public void addCountry(String p_CountryName, String p_ContinentName) throws ValidationException {

        if (this.getCountries().containsKey(p_CountryName)) {
            throw new ValidationException("Country already exist");
        }
        Country l_Country = new Country();
        l_Country.setName(p_CountryName);
        l_Country.setContinent(p_ContinentName);
        this.getCountries().put(p_CountryName, l_Country);
        this.getContinent(p_ContinentName).getCountries().add(l_Country);
        d_Logger.log("Successfully added Country: " + p_CountryName);
    }

    /**
     * Removes a continent from the map's continent list along with its respective countries.
     *
     * @param p_ContinentName The name of the continent to remove
     * @throws ValidationException if any input/output issue occurs
     */
    public void removeContinent(String p_ContinentName) throws ValidationException {

        if (!this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("Continent does not exist");
        }
        Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName)
                .getCountries()
                .stream().map(Country::getName)
                .collect(Collectors.toSet());
        for (String l_CountryName : l_CountrySet) {
            this.getCountries().remove(l_CountryName);
        }
        d_Logger.log("Successfully deleted the continent: " + p_ContinentName);
    }

    /**
     * Removes a country from the map's country list and its corresponding continent's country list.
     *
     * @param p_CountryName The name of the country to remove
     * @throws ValidationException if any input/output issue occurs
     */
    public void removeCountry(String p_CountryName) throws ValidationException {
        Country l_Country = this.getCountry(p_CountryName);
        if (Objects.isNull(l_Country)) {
            throw new ValidationException("Country does not exist: " + p_CountryName);
        }
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getName());
        d_Logger.log("Successfully deleted the country");
    }

    /**
     * Adds a neighbor country to a particular country.
     *
     * @param p_CountryName The name of the country to which a neighbor is added
     * @param p_NeighborCountryName The name of the neighbor country
     * @throws ValidationException if any input/output issue occurs
     */
    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        }
        l_Country1.getNeighbors().add(l_Country2);
        d_Logger.log(String.format("Successfully connected routes between mentioned Countries: %s - %s", p_CountryName, p_NeighborCountryName));
    }


    /**
     * Removes a neighbor country from a particular country.
     *
     * @param p_CountryName The name of the country from which a neighbor is removed
     * @param p_NeighborCountryName The name of the neighbor country to remove
     * @throws ValidationException if any input/output issue occurs
     */
    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        } else if (!l_Country1.getNeighbors().contains(l_Country2) || !l_Country2.getNeighbors().contains(l_Country1)) {
            throw new ValidationException("Mentioned Countries are not neighbors");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_Country2);
            d_Logger.log(String.format("Successfully removed routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName));
        }
    }

    /**
     * Adds a player to the game map.
     *
     * @param p_PlayerName Player name
     * @throws ValidationException if any input/output issue occurs
     */
    public void addPlayer(String p_PlayerName) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player(PlayerStrategy.getStrategy("human"));
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        d_Logger.log("Successfully added Player: " + p_PlayerName);
    }

    /**
     * Adds a player to the game map with a specified strategy.
     *
     * @param p_PlayerName Player name
     * @param p_Strategy   Player strategy
     * @throws ValidationException if any input/output issue occurs
     */
    public void addPlayer(String p_PlayerName, String p_Strategy) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player(PlayerStrategy.getStrategy(p_Strategy));
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        d_Logger.log("Successfully added Player: " + p_PlayerName);
    }

    /**
     * Removes a player from the game map.
     *
     * @param p_PlayerName Player name to remove
     * @throws ValidationException if any input/output issue occurs
     */
    public void removePlayer(String p_PlayerName) throws ValidationException {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player)) {
            throw new ValidationException("Player does not exist: " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        d_Logger.log("Successfully deleted the player: " + p_PlayerName);
    }

    /**
     * Saves the map as a file, with the specified name.
     *
     * @param p_saveAsConquest Boolean value indicating if the map should be saved in Conquest format
     * @throws ValidationException if any input/output issue occurs
     * @throws IOException if an I/O error occurs
     */
    public void saveMap(boolean p_saveAsConquest) throws ValidationException, IOException {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)) {
            DominationMap l_SaveMap = p_saveAsConquest ? new Adapter(new Adaptee()) : new DominationMap();
            boolean l_Bool = true;
            while (l_Bool) {
                d_GameMap.getName();
                if (Objects.isNull(d_GameMap.getName()) || d_GameMap.getName().isEmpty()) {
                    throw new ValidationException("Please enter the file name:");
                } else {
                    if (l_SaveMap.saveMap(d_GameMap, d_GameMap.getName())) {
                        d_Logger.log("The map has been validated and is saved.");
                    } else {
                        throw new ValidationException("Map name already exists, enter different name.");
                    }
                    l_Bool = false;
                }
            }
        } else {
            throw new ValidationException("Invalid Map, can not be saved.");
        }
    }

    /**
     * Assigns countries to each player of the game randomly.
     */
    public void assignCountries() {
        int l_PlayerIndex = 0;
        List<Player> l_Players = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());

        List<Country> l_CountryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());
        Collections.shuffle(l_CountryList);
        for (Country l_Country : l_CountryList) {
            Player l_Player = l_Players.get(l_PlayerIndex);
            l_Player.getCapturedCountries().add(l_Country);
            l_Country.setPlayer(l_Player);
            d_Logger.log(l_Country.getName() + " Assigned to " + l_Player.getName());

            if (l_PlayerIndex < d_GameMap.getPlayers().size() - 1) {
                l_PlayerIndex++;
            } else {
                l_PlayerIndex = 0;
            }
        }
    }


    /**
     * Displays the details of the game map including continents, countries, neighbors, players, and their ownership.
     */
    public void showMap() {
        d_Logger.log("\nShowing the Map Details : \n");

        // Showing Continents in Map
        d_Logger.log("\nThe Continents in this Map are : \n");
        Iterator<Map.Entry<String, Continent>> l_IteratorForContinents = d_GameMap.getContinents().entrySet()
                .iterator();

        String l_Table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Continent's name |%n");
        System.out.format("+------------------+%n");

        while (l_IteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = l_IteratorForContinents.next();
            String l_ContinentId = (String) continentMap.getKey();
            Continent l_Continent = d_GameMap.getContinents().get(l_ContinentId); //Get the particular continent by its ID(Name)

            System.out.format(l_Table, l_Continent.getName());
        }
        System.out.format("+------------------+%n");


        // Showing Countries in the Continent and their details
        d_Logger.log("\nThe countries in this Map and their details are : \n");

        Iterator<Map.Entry<String, Continent>> l_IteratorForContinent = d_GameMap.getContinents().entrySet()
                .iterator();

        l_Table = "|%-23s|%-18s|%-60s|%n";

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+-%n");
        System.out.format(
                "     Country's name     | Continent's Name |   Neighbour Countries                                      |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+----------------+%n");


        while (l_IteratorForContinent.hasNext()) {
            Map.Entry<String, Continent> l_ContinentMap = (Map.Entry<String, Continent>) l_IteratorForContinent.next();
            String l_ContinentId = (String) l_ContinentMap.getKey();
            Continent l_Continent = d_GameMap.getContinents().get(l_ContinentId);
            Iterator<Country> l_ListIterator = l_Continent.getCountries().iterator();

            while (l_ListIterator.hasNext()) {

                Country l_Country = (Country) l_ListIterator.next();
                System.out.format(l_Table, l_Country.getName(), l_Continent.getName(), l_Country.createANeighborList(l_Country.getNeighbors()), l_Country.getArmies());
            }
        }

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+----------------+%n");


        HashMap<String, Player> l_Players = d_GameMap.getPlayers();
        d_Logger.log("\nPlayers in this game if the game has started are : ");
        if (l_Players != null) {
            l_Players.forEach((key, value) -> d_Logger.log(key));
            d_Logger.log("");
        }

        //Showing the Ownership of the players
        d_Logger.log("The Map ownership of the players are : ");


        System.out.format("+---------------+-------------------------------+%n");
        System.out.format("| Player's name |    Continent's Controlled    |%n");
        System.out.format("+---------------+-------------------------------+%n");

        String l_Table1 = "|%-15s|%-30s|%n";


        for (Player l_Player : d_GameMap.getPlayers().values()) {
            System.out.format(l_Table1, l_Player.getName(), l_Player.createACaptureList(l_Player.getCapturedCountries()), l_Player.getReinforcementArmies());
        }

        System.out.format("+---------------+-------------------------------+%n");

    }

    /**
     * Gets the number of tries.
     *
     * @return the number of tries
     */
    public int getTries() {
        return d_Tries;
    }

    /**
     * Sets the number of tries.
     *
     * @param p_Tries the number of tries to set
     */
    public void setTries(int p_Tries) {
        d_Tries = p_Tries;
    }

    /**
     * Increments the number of tries by 1.
     */
    public void nextTry() {
        d_Tries++;
    }

    /**
     * Gets the winner of the game.
     *
     * @return the winner of the game
     */
    public Player getWinner() {
        return d_Winner;
    }

    /**
     * Sets the winner of the game.
     *
     * @param p_Winner the winner of the game to set
     */
    public void setWinner(Player p_Winner) {
        d_Winner = p_Winner;
    }

    /**
     * Builds the game phase from the given game map instance.
     *
     * @param p_GameMap the game map instance
     * @return the game phase
     * @throws ValidationException if any validation error occurs
     */
    public GamePhase gamePlayBuilder(GameMap p_GameMap) throws ValidationException {
        this.flushGameMap();
        d_GameMap.setGameLoaded(true);
        for (Map.Entry<String, Continent> l_Continent : p_GameMap.getContinents().entrySet()) {
            this.addContinent(l_Continent.getKey(), String.valueOf(l_Continent.getValue().getAwardArmies()));
        }
        for (Map.Entry<String, Country> l_Country : p_GameMap.getCountries().entrySet()) {
            this.addCountry(l_Country.getKey(), l_Country.getValue().getContinent());
        }
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                for (Country l_Neighbour : l_Country.getNeighbors()) {
                    p_GameMap.addNeighbor(l_Country.getName(), l_Neighbour.getName());
                }
            }
        }
        for (Map.Entry<String, Player> l_Player : p_GameMap.getPlayers().entrySet()) {
            this.addPlayer(l_Player.getKey());
            this.getPlayer(l_Player.getKey()).setCapturedCountries(l_Player.getValue().getCapturedCountries());
            this.getPlayer(l_Player.getKey()).setReinforcementArmies(l_Player.getValue().getReinforcementArmies());
        }
        this.setGamePhase(p_GameMap.getGamePhase());
        this.setCurrentPlayer(p_GameMap.getCurrentPlayer());
        for (Map.Entry<String, Player> l_Player : p_GameMap.getPlayers().entrySet()) {
            this.getPlayer(l_Player.getKey()).setOrders(l_Player.getValue().getOrders());
            this.getPlayer(l_Player.getKey()).setPlayerCards(l_Player.getValue().getPlayerCards());
        }
        return p_GameMap.getGamePhase();
    }

    /**
     * Creates a new instance of the game map.
     *
     * @return a new instance of the game map
     */
    public static GameMap newInstance() {
        GameMap l_GameMap = d_GameMap;
        if (Objects.nonNull(l_GameMap)) {
            l_GameMap.d_GamePhase = null;
            l_GameMap.d_Name = "";
            l_GameMap.d_ErrorMessage = "";
            l_GameMap.d_Winner = null;
            l_GameMap.d_Tries = 0;
            l_GameMap.d_CurrentPlayer = null;
            l_GameMap.d_GameLoaded = false;
            l_GameMap.flushGameMap();
            return d_GameMap;
        } else {
            return getInstance();
        }
    }
}