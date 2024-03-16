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

import static org.junit.Assert.assertTrue;

/**
 * This class contains JUnit tests to verify the functionality of the {@link AirliftOrder} class.
 * @author Bharti
 */
public class AirliftOrderTest {

    GameMap d_GameMap;

    /**
     * Sets up the environment for testing.
     * Initializes necessary objects and data structures.
     */
    @Before
    public void setUp() {
        // Initialize GameMap instance
        d_GameMap = GameMap.getInstance();
        Continent l_Continent1 = new Continent("Asia", "10", 1);
        Continent l_Continent2 = new Continent("Africa", "15", 2);
        HashMap<String, Continent> l_Continents = new HashMap<>();
        l_Continents.put("asia", l_Continent1);
        l_Continents.put("africa", l_Continent2);
        d_GameMap.setContinents(l_Continents);

        Country l_Country1 = new Country("China", "Asia");
        Country l_Country2 = new Country("Nigeria", "Africa");
        Country l_Country3 = new Country("India", "Africa");
        HashMap<String, Country> l_Countries = new HashMap<>();
        l_Countries.put("china", l_Country1);
        l_Countries.put("nigeria", l_Country2);
        l_Countries.put("india", l_Country3);

        HashMap<String, Country> l_ChinaNeighbors = new HashMap<>();
        l_ChinaNeighbors.put("nigeria", l_Country2);
        l_Country1.setNeighbours(l_ChinaNeighbors);

        HashMap<String, Country> l_NigeriaNeighbors = new HashMap<>();
        l_NigeriaNeighbors.put("china", l_Country1);
        l_Country2.setNeighbours(l_NigeriaNeighbors);
        l_Country1.setArmies(9);

        d_GameMap.setCountries(l_Countries);

        Player l_Player1 = new Player();
        l_Player1.setName("p1");
        l_Player1.setReinforcementArmies(5);
        List<Country> l_Player1Captured = new ArrayList<>();
        l_Player1Captured.add(l_Country1);
        l_Player1Captured.add(l_Country3);
        l_Player1.setCapturedCountries(l_Player1Captured);
        Deque<Order> l_CurrentOrders1 = new ArrayDeque<>();
        l_Player1.setOrders(l_CurrentOrders1);
        d_GameMap.setPlayer(l_Player1);

        Player l_Player2 = new Player();
        l_Player2.setName("p2");
        l_Player2.setReinforcementArmies(5);
        List<Country> l_Player2Captured = new ArrayList<>();
        l_Player2Captured.add(l_Country2);
        l_Player2.setCapturedCountries(l_Player2Captured);
        Deque<Order> l_CurrentOrders2 = new ArrayDeque<>();
        l_Player2.setOrders(l_CurrentOrders2);
        d_GameMap.setPlayer(l_Player2);

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
     * Tests the validation of the Airlift order.
     * Checks if the Airlift order is valid for execution.
     */
    @Test
    public void validateCommand() {
        Player l_P1 = d_GameMap.getPlayer("p1");
        l_P1.addPlayerCard(new Card(CardType.AIRLIFT));
        String l_Command = "airlift " + d_GameMap.getPlayer("p1").getCapturedCountries().get(0).getCountryId() + " "+ d_GameMap.getPlayer("p1").getCapturedCountries().get(1).getCountryId()+" "+ 4 ;
        Order l_Order = OrderOwner.issueOrder(l_Command.split(" "),l_P1);
        l_P1.addOrder(l_Order);
        assertTrue(l_P1.nextOrder().validateCommand());

    }
    /**
     * Tests the execution of the Airlift order.
     * Checks if the Bomb order is successfully executed.
     */
    @Test
    public void execute() {
        Player l_P1 = d_GameMap.getPlayer("p1");
        l_P1.addPlayerCard(new Card(CardType.AIRLIFT));
        String l_Command = "airlift " + d_GameMap.getPlayer("p1").getCapturedCountries().get(0).getCountryId() + " "+ d_GameMap.getPlayer("p1").getCapturedCountries().get(1).getCountryId()+" "+ 4 ;
        Order l_Order = OrderOwner.issueOrder(l_Command.split(" "),l_P1);
        l_P1.addOrder(l_Order);
        assertTrue(l_P1.nextOrder().execute());
    }

}