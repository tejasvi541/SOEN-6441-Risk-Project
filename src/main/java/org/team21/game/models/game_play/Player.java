package org.team21.game.models.game_play;

import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.orders.Order;
import org.team21.game.models.orders.OrderOwner;
import org.team21.game.utils.Constants;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Player for Game Map.
 * {org.team25.game.models.map.GameMap}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class Player {

    /**
     * Initialising List to hold orders
     */
    public static List<Order> d_PlayerOrderList = new ArrayList<>();
    /**
     * Player ID
     */
    private int d_PlayerId;
    /**
     * Player's name
     */
    private String d_PlayerName = "";

    /**
     * Captured countries by players.
     */
    private List<Country> d_PlayerCapturedCountries = new ArrayList<>();
    /**
     * Orders provided by players
     */
    private Deque<Order> d_CurrentOrders = new ArrayDeque<>();

    /**
     * Reinforcement armies
     */
    private int d_PlayerReinforcementArmies;

    /**
     * A list of cards for the player
     */
    private final List<Card> d_PlayerCards = new ArrayList<>();
    /**
     * A list of neutral players
     */
    private final List<Player> d_NeutralPlayers = new ArrayList<>();
    /**
     * A function to get the player ID
     *
     * @return the ID of player
     */
    public int getId() {
        return d_PlayerId;
    }

    /**
     * A function to set the player ID
     *
     * @param p_Id Player ID value
     */
    public void setId(int p_Id) {
        this.d_PlayerId = p_Id;
    }

    /**
     * A function to get the name of the Player
     *
     * @return player name
     */
    public String getName() {
        return d_PlayerName;
    }

    /**
     * A function to set the name of the player
     *
     * @param p_Name Name of the player
     */
    public void setName(String p_Name) {
        this.d_PlayerName = p_Name;
    }

    /**
     * A function to get the list of captured countries
     *
     * @return The list of captured countries
     */
    public List<Country> getCapturedCountries() {
        return d_PlayerCapturedCountries;
    }

    /**
     * A function to record/set the captured countries
     *
     * @param p_CapturedCountries List of the captured countries
     */
    public void setCapturedCountries(List<Country> p_CapturedCountries) { this.d_PlayerCapturedCountries = p_CapturedCountries; }

    /**
     * A function to get the list of orders
     *
     * @return list of orders
     */
    public Deque<Order> getOrders() {
        return d_CurrentOrders;
    }

    /**
     * A function to set the orders
     *
     * @param p_Orders the list of orders
     */
    public void setOrders(Deque<Order> p_Orders) {
        this.d_CurrentOrders = p_Orders;
    }

    /**
     * A function to add the orders to the issue order list
     *
     * @param p_Order The order to be added
     */
    public void addOrder(Order p_Order) {
        d_CurrentOrders.add(p_Order);
    }

    /**
     * A function to get the reinforcement armies for each player
     *
     * @return armies assigned to player of type int
     */
    public int getReinforcementArmies() {
        return d_PlayerReinforcementArmies;
    }

    /**
     * A function to set the reinforcement armies for each player
     *
     * @param p_AssignedArmies armies assigned to player of type int
     */
    public void setReinforcementArmies(int p_AssignedArmies) {
        this.d_PlayerReinforcementArmies = p_AssignedArmies;
    }

    /**
     * A function to get the issue order from player and add to the order list
     *
     * @param p_Commands the type of order issued
     */
    public void issueOrder(String p_Commands) {
        boolean l_IssueCommand = true;
        String[] l_CommandArr = p_Commands.split(" ");
        if (l_CommandArr.length > 2) {
            int l_ReinforcementArmies = Integer.parseInt(l_CommandArr[2]);
            if (!checkIfCountryExists(l_CommandArr[1], this)) {
                System.out.println(Constants.COUNTRIES_DOES_NOT_BELONG);
                l_IssueCommand = false;
            }
            if (!deployReinforcementArmiesFromPlayer(l_ReinforcementArmies)) {
                System.out.println(Constants.NOT_ENOUGH_REINFORCEMENTS);
                l_IssueCommand = false;
            }
            if (l_IssueCommand) {
                Order l_Order = OrderOwner.issueOrder(l_CommandArr, this);
                d_PlayerOrderList.add(l_Order);
                addOrder(l_Order);
                System.out.println("Your Order has been added to the list: deploy " + l_Order.getOrderInfo().getDestination() + " with " + l_Order.getOrderInfo().getNumberOfArmy() + " armies");
                System.out.println(Constants.SEPERATER);
            }
        }
    }

    /**
     * A function to check if the country exists in the list of player assigned countries
     *
     * @param p_Country The country to be checked if present
     * @param p_Player  The Player for whom the function is checked for
     * @return true if country exists in the assigned country list else false
     */
    public boolean checkIfCountryExists(String p_Country, Player p_Player) {
        List<Country> l_ListOfCountries = p_Player.getCapturedCountries();
        for (Country l_Country : l_ListOfCountries) {
            if (l_Country.getCountryId().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A function to check if the army to deployed is valid
     *
     * @param p_ArmyCount The armies to be deployed to the country
     * @return true if the armies are valid and deducted from the assigned army pool else false
     */
    public boolean deployReinforcementArmiesFromPlayer(int p_ArmyCount) {
        if (p_ArmyCount > d_PlayerReinforcementArmies || p_ArmyCount < 0) {
            return false;
        }
        d_PlayerReinforcementArmies -= p_ArmyCount;
        return true;
    }

    /**
     * A function to create a list of countries assigned to player in a formatted string
     *
     * @param p_Capture The list of countries of the player
     * @return the formatted string
     */
    public String createACaptureList(List<Country> p_Capture) {
        String l_Result = "";
        for (Country l_Capture : p_Capture) {
            l_Result += l_Capture.getCountryId() + "-";
        }
        return !l_Result.isEmpty() ? l_Result.substring(0, l_Result.length() - 1) : "";
    }

    public static Order nextOrder() {
        Order l_firstOrder = d_PlayerOrderList.getFirst();
        d_PlayerOrderList.removeFirst();
        return l_firstOrder;
    }

    /**
     * A function to get list of all cards for the player
     *
     * @return list of all cards
     */
    public List<Card> getPlayerCards() {
        return d_PlayerCards;
    }

    /**
     * Method to check if particular card is available in the player's card list
     *
     * @param p_cardType The type of card
     * @return true if card is available else false
     */
    public boolean checkIfCardAvailable(CardType p_cardType) {
        return d_PlayerCards.stream().anyMatch(p_card -> p_card.getCard().equals(p_cardType));
    }

    /**
     * Remove the card for the player
     *
     * @param p_CardType card to be removed
     * @return the player cards
     */
    public boolean removeCard(CardType p_CardType) {
        return d_PlayerCards.remove(new Card(p_CardType));
    }

    /**
     * A function to remove the all cards from the player
     */
    public void removeCards() {
        d_PlayerCards.clear();
    }

    /**
     * Add the card to the player on conquering the territory
     *
     * @param p_Card card to be added to player
     */
    public void addPlayerCard(Card p_Card) {
        d_PlayerCards.add(p_Card);
    }


    /**
     * Get the list of all players you cannot attack
     *
     * @return list of players
     */
    public List<Player> getNeutralPlayers() {
        return d_NeutralPlayers;
    }

    /**
     * Add the neutral player to the list
     *
     * @param p_NeutralPlayer The player you cannot attack
     */
    public void addNeutralPlayers(Player p_NeutralPlayer) {
        if (!d_NeutralPlayers.contains(p_NeutralPlayer)) {
            d_NeutralPlayers.add(p_NeutralPlayer);
        }
    }

    /**
     * Remove all the neutral players from list
     */
    public void removeNeutralPlayer() {
        if (!d_NeutralPlayers.isEmpty()) {
            d_NeutralPlayers.clear();
        }
    }





    /**
     * Calculate the number of the armies to be assigned in reinforcement phase.
     *
     * @param p_gameMap The game map object
     */
    public void calculateReinforcementArmies(GameMap p_gameMap) {
        if (getCapturedCountries().size() > 0) {
            int reinforcements = (int) Math.floor(getCapturedCountries().size() / 3f);
            reinforcements += getBonusIfKingOfContinents(p_gameMap);
            setReinforcementArmies(reinforcements > 2 ? reinforcements : 3);
        } else {
            setReinforcementArmies(3);
        }
        System.out.println("The Player:" + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
    }

    /**
     * Add bonus armies to reinforcement armies if a player owns the continent.
     *
     * @param p_gameMap The game map object
     * @return reinforcements armies added with bonus armies
     */
    private int getBonusIfKingOfContinents(GameMap p_gameMap) {
        int reinforcements = 0;
        Map<String, List<Country>> l_CountryMap = getCapturedCountries()
                .stream()
                .collect(Collectors.groupingBy(Country::getParentContinent));
        for (String continent : l_CountryMap.keySet()) {
            if (p_gameMap.getContinent(continent).getCountries().size() == l_CountryMap.get(continent).size()) {
                reinforcements += p_gameMap.getContinent(continent).getAwardArmies();
            }
        }
        return reinforcements;
    }

    /**
     * A function to check if the country exists in the list of player captured countries
     *
     * @param p_Country The country to be checked if present
     * @return true if country exists in the assigned country list else false
     */
    public boolean isCaptured(Country p_Country) {
        return d_PlayerCapturedCountries.contains(p_Country);
    }

}
