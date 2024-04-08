package org.team21.game.models.map;

import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.order.Order;
import org.team21.game.models.order.OrderOwner;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a player in the game, storing information such as player ID, name, captured countries,
 * reinforcement armies, player cards, and orders.
 * The class also provides methods for managing player actions, such as issuing orders, deploying reinforcement armies,
 * and calculating reinforcement armies based on captured countries and continent ownership.
 * <p>
 * This class implements the Serializable interface to support serialization.
 * </p>
 *
 * @author Tejasvi
 * @author Bharti Chabbra
 * @version 1.0.0
 */

public class Player implements Serializable {

    /**
     * The strategy for creating player commands.
     */
    private final PlayerStrategy d_PlayerStrategy;

    /**
     * The ID of the player.
     */
    private int d_Id;

    /**
     * The name of the player.
     */
    private String d_Name;
    /**
     * The list of countries captured by the player.
     */
    private List<Country> d_CapturedCountries = new ArrayList<>();
    /**
     * The deque to manage the list of orders.
     */
    private Deque<Order> d_Orders = new ArrayDeque<>();
    /**
     * The number of reinforcement armies available for the player.
     */
    private int d_ReinforcementArmies;

    /**
     * The number of armies to issue for the player.
     */
    private int d_ArmiesToIssue = 0;
    /**
     * The list of cards held by the player.
     */
    private List<Card> d_PlayerCards = new ArrayList<>();
    /**
     * The list of neutral players that the player cannot attack.
     */
    private final List<Player> d_NeutralPlayers = new ArrayList<>();
    /**
     * The logger for recording game events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Constructs a player with the specified player strategy.
     *
     * @param p_PlayerStrategy The player strategy to use.
     */
    public Player(PlayerStrategy p_PlayerStrategy) {
        this.d_PlayerStrategy = p_PlayerStrategy;
    }

    /**
     * method to get armies issued
     *
     * @return issues armies
     */
    public int getIssuedArmies() {
        return d_ArmiesToIssue;
    }
    /**
     * method to set the armies issued
     * @param p_ArmiesToIssue armies to issue to player
     */
    public void setIssuedArmies(int p_ArmiesToIssue) {
        d_ArmiesToIssue = p_ArmiesToIssue;
    }

    /**
     * Gets the ID of the player.
     *
     * @return The ID of the player.
     */
    public int getId() {
        return d_Id;
    }

    /**
     * Sets the ID of the player.
     *
     * @param p_Id The ID of the player.
     */
    public void setId(int p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Sets the name of the player.
     *
     * @param p_Name The name of the player.
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Retrieves the list of countries captured by the player.
     *
     * @return The list of captured countries.
     */
    public List<Country> getCapturedCountries() {
        return d_CapturedCountries;
    }

    /**
     * Sets the list of countries captured by the player.
     *
     * @param p_CapturedCountries The list of captured countries.
     */
    public void setCapturedCountries(List<Country> p_CapturedCountries) {
        this.d_CapturedCountries = p_CapturedCountries;
    }

    /**
     * Retrieves the list of orders issued by the player.
     *
     * @return The list of orders.
     */
    public Deque<Order> getOrders() {
        return d_Orders;
    }

    /**
     * Sets the list of orders for the player.
     *
     * @param p_Orders The list of orders.
     */
    public void setOrders(Deque<Order> p_Orders){
        this.d_Orders = p_Orders;
    }

    /**
     * Adds an order to the list of orders issued by the player.
     *
     * @param p_Order The order to be added.
     */
    public void addOrder(Order p_Order) {
        this.d_Orders.add(p_Order);
    }

    /**
     * Retrieves the number of reinforcement armies assigned to the player.
     *
     * @return The number of reinforcement armies.
     */
    public int getReinforcementArmies() {
        return d_ReinforcementArmies;
    }

    /**
     * Sets the number of reinforcement armies assigned to the player.
     *
     * @param p_AssignedArmies The number of reinforcement armies.
     */
    public void setReinforcementArmies(int p_AssignedArmies) {
        this.d_ReinforcementArmies = p_AssignedArmies;
    }

    /**
     * Retrieves the list of cards held by the player.
     *
     * @return The list of player cards.
     */
    public List<Card> getPlayerCards() {
        return d_PlayerCards;
    }

    /**
     * Checks if a particular card is available in the player's card list.
     *
     * @param p_cardType The type of card.
     * @return True if the card is available, false otherwise.
     */
    public boolean checkIfCardAvailable(CardType p_cardType) {
        return d_PlayerCards.stream().anyMatch(p_card -> p_card.getCardType().equals(p_cardType));
    }

    /**
     * Removes a specified card from the player's card list.
     *
     * @param p_CardType The type of card to be removed.
     * @return True if the card is successfully removed, false otherwise.
     */
    public boolean removeCard(CardType p_CardType) {
        return d_PlayerCards.remove(new Card(p_CardType));
    }

    /**
     * Sets the list of cards held by the player.
     *
     * @param p_Cards The list of player cards.
     */
    public void setPlayerCards(List<Card> p_Cards) {
        d_PlayerCards = p_Cards;
    }

    /**
     * Adds a card to the player's card list.
     *
     * @param p_Card The card to be added.
     */
    public void addPlayerCard(Card p_Card) {
        d_PlayerCards.add(p_Card);
    }


    /**
     * Retrieves the list of neutral players that the player cannot attack.
     *
     * @return The list of neutral players.
     */
    public List<Player> getNeutralPlayers() {
        return d_NeutralPlayers;
    }

    /**
     * Adds a neutral player to the list of players that the player cannot attack.
     *
     * @param p_NeutralPlayer The neutral player to be added.
     */
    public void addNeutralPlayers(Player p_NeutralPlayer) {
        if (!d_NeutralPlayers.contains(p_NeutralPlayer)) {
            d_NeutralPlayers.add(p_NeutralPlayer);
        }
    }

    /**
     * Removes all neutral players from the list of players that the player cannot attack.
     */
    public void removeNeutralPlayer() {
        if (!d_NeutralPlayers.isEmpty()) {
            d_NeutralPlayers.clear();
        }
    }

    /**
     * Issues an order for the player.
     */
    public void issueOrder() {
        Order l_Order = OrderOwner.CreateOrder(IssueOrderController.d_Commands.split(" "), this);
        addOrder(l_Order);
    }

    /**
     * Reads commands entered by the player.
     *
     * @return The command entered by the player.
     */
    public String readFromPlayer() {
        return this.d_PlayerStrategy.createCommand();
    }


    /**
     * Retrieves the next order for execution.
     *
     * @return The next order for execution.
     */
    public Order nextOrder() {
        return d_Orders.poll();
    }

    /**
     * Checks if the specified number of armies to be deployed is valid.
     *
     * @param p_ArmyCount The number of armies to be deployed.
     * @return True if the armies are valid and deducted from the assigned army pool, false otherwise.
     */
    public boolean deployReinforcementArmiesFromPlayer(int p_ArmyCount) {
        if (p_ArmyCount > d_ReinforcementArmies || p_ArmyCount <= 0) {
            return false;
        }
        d_ReinforcementArmies -= p_ArmyCount;
        return true;
    }

    /**
     * Creates a formatted string listing the countries assigned to the player.
     *
     * @param p_Capture The list of countries assigned to the player.
     * @return The formatted string listing the countries.
     */
    public String createACaptureList(List<Country> p_Capture) {
        String l_Result = "";
        for (Country l_Capture : p_Capture) {
            l_Result += l_Capture.getName() + "-";
        }
        return l_Result.length() > 0 ? l_Result.substring(0, l_Result.length() - 1) : "";
    }


    /**
     * Calculates the number of armies to be assigned in the reinforcement phase.
     *
     * @param p_gameMap The game map object.
     */
    public void calculateReinforcementArmies(GameMap p_gameMap) {
        if (getCapturedCountries().size() > 0) {
            int l_reinforcements = (int) Math.floor(getCapturedCountries().size() / 3f);
            l_reinforcements += getBonusIfKingOfContinents(p_gameMap);
            setReinforcementArmies(l_reinforcements > 2 ? l_reinforcements : 3);
        } else {
            setReinforcementArmies(3);
        }
        d_ArmiesToIssue = getReinforcementArmies();
        d_Logger.log("The Player " + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
    }

    /**
     * Adds bonus armies to reinforcement armies if a player owns the continent.
     *
     * @param p_gameMap The game map object.
     * @return The reinforcement armies added with bonus armies.
     */
    private int getBonusIfKingOfContinents(GameMap p_gameMap) {
        int l_reinforcements = 0;
        Map<String, List<Country>> l_CountryMap = getCapturedCountries()
                .stream()
                .collect(Collectors.groupingBy(Country::getContinent));
        for (String l_continent : l_CountryMap.keySet()) {
            if (p_gameMap.getContinent(l_continent).getCountries().size() == l_CountryMap.get(l_continent).size()) {
                l_reinforcements += p_gameMap.getContinent(l_continent).getAwardArmies();
            }
        }
        return l_reinforcements;
    }

    /**
     * Checks if the country exists in the list of player-captured countries.
     *
     * @param p_Country The country to be checked for presence.
     * @return True if the country exists in the assigned country list, false otherwise.
     */
    public boolean isCaptured(Country p_Country) {
        return d_CapturedCountries.contains(p_Country);
    }

}
