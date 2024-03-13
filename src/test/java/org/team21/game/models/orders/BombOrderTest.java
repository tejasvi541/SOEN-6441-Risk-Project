package org.team21.game.models.orders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;

import java.util.*;

import static org.junit.Assert.*;
/**
 * Test class for {@link BombOrder}.
 * This class contains JUnit tests to verify the functionality of the {@link BombOrder} class.
 * @author Tejasvi
 */
public class BombOrderTest {

    GameMap d_GameMap;

    /**
     * Sets up the environment for testing.
     * Initializes necessary objects and data structures.
     */
    @Before
    public void setUp() {
        // Initialize GameMap instance
        d_GameMap = GameMap.getInstance();
        Continent continent1 = new Continent("Asia", "10", 1);
        Continent continent2 = new Continent("Africa", "15", 2);
        HashMap<String, Continent> d_continents = new HashMap<>();
        d_continents.put("asia", continent1);
        d_continents.put("africa", continent2);
        d_GameMap.setContinents(d_continents);

        Country country1 = new Country("China", "Asia");
        Country country2 = new Country("Nigeria", "Africa");
        HashMap<String, Country> d_countries = new HashMap<>();
        d_countries.put("china", country1);
        d_countries.put("nigeria", country2);

        HashMap<String, Country> d_chinaNeighbors = new HashMap<>();
        d_chinaNeighbors.put("nigeria", country2);
        country1.setNeighbours(d_chinaNeighbors);

        HashMap<String, Country> d_nigeriaNeighbors = new HashMap<>();
        d_nigeriaNeighbors.put("china", country1);
        country2.setNeighbours(d_nigeriaNeighbors);

        d_GameMap.setCountries(d_countries);

        Player player1 = new Player();
        player1.setName("p1");
        player1.setReinforcementArmies(5);
        List<Country> player1Captured = new ArrayList<>();
        player1Captured.add(country1);
        player1.setCapturedCountries(player1Captured);
        Deque<Order> d_CurrentOrders1 = new ArrayDeque<>();
        player1.setOrders(d_CurrentOrders1);
        d_GameMap.setPlayer(player1);

        Player player2 = new Player();
        player2.setName("p2");
        player2.setReinforcementArmies(5);
        List<Country> player2Captured = new ArrayList<>();
        player2Captured.add(country2);
        player2.setCapturedCountries(player2Captured);
        Deque<Order> d_CurrentOrders2 = new ArrayDeque<>();
        player2.setOrders(d_CurrentOrders2);
        d_GameMap.setPlayer(player2);

    }

    /**
     * Cleans up the environment after testing.
     * Clears the GameMap instance.
     *
     */
    @After
    public void tearDown() {
        d_GameMap.clearMap();
    }
    /**
     * Tests the validation of the Bomb order.
     * Checks if the Bomb order is valid for execution.
     */
    @Test
    public void validateCommand() {
        Player l_p1 = d_GameMap.getPlayer("p1");
        l_p1.addPlayerCard(new Card(CardType.BOMB));
        String l_command = "bomb " + d_GameMap.getPlayer("p2").getCapturedCountries().get(0).getCountryId();
        Order l_order = OrderOwner.issueOrder(l_command.split(" "),l_p1);
        l_p1.addOrder(l_order);
        assertTrue(l_p1.nextOrder().validateCommand());

    }
    /**
     * Tests the execution of the Bomb order.
     * Checks if the Bomb order is successfully executed.
     */
    @Test
    public void execute() {
        Player l_p1 = d_GameMap.getPlayer("p1");
        l_p1.addPlayerCard(new Card(CardType.BOMB));
        String l_command = "bomb " + d_GameMap.getPlayer("p2").getCapturedCountries().get(0).getCountryId();
        Order l_order = OrderOwner.issueOrder(l_command.split(" "),l_p1);
        l_p1.addOrder(l_order);
        assertTrue(l_p1.nextOrder().execute());
    }

}