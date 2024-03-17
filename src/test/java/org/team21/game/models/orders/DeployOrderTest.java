package org.team21.game.models.orders;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.controllers.IssueOrderController;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * The type Deploy order test for testing deployment of troops.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class DeployOrderTest {
    /**
     * The D game map.
     */
    GameMap d_GameMap;
    /**
     * The D countries player 1.
     */
    List<Country> d_CountriesPlayer1;
    /**
     * The D countries player 2.
     */
    List<Country> d_CountriesPlayer2;
    /**
     * The D player.
     */
    Player d_Player;

    /**
     * Setup to set the testing of DeployOrder. Here Game Map
     * will get initialised and relevant players will get
     * the countries and armies.
     *
     * @throws Exception : throws exception
     */
    @Before
    public void setUp() throws Exception {
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
        d_CountriesPlayer1 = d_GameMap.getPlayer(l_Player1.getName()).getCapturedCountries();

        Player l_Player2 = new Player();
        l_Player2.setName("p2");
        l_Player2.setReinforcementArmies(5);
        List<Country> l_Player2Captured = new ArrayList<>();
        l_Player2Captured.add(l_Country2);
        l_Player2.setCapturedCountries(l_Player2Captured);
        Deque<Order> l_CurrentOrders2 = new ArrayDeque<>();
        l_Player2.setOrders(l_CurrentOrders2);
        d_GameMap.setPlayer(l_Player2);
        d_CountriesPlayer2 = d_GameMap.getPlayer(l_Player2.getName()).getCapturedCountries();

        d_Player = d_GameMap.getPlayer(l_Player1.getName());
    }

    /**
     * Clear GameMap once all the operation gets completed
     */
    @After
    public void tearDown() {
        d_GameMap.clearMap();
    }

    /**
     * Test execute of DeployOrder class.
     */
    @Test
    public void testExecute() {
        IssueOrderController.d_IssueOrderCommand = STR."deploy \{d_CountriesPlayer1.getFirst().getCountryId()} \{d_Player.getReinforcementArmies()}";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), d_Player);
        d_Player.addOrder(l_Order1);
        assertTrue(d_Player.nextOrder().execute());
    }

    /**
     * Test validate command of DeployOrder class.
     */
    @Test
    public void testValidateCommand() {
        IssueOrderController.d_IssueOrderCommand = STR."deploy \{d_CountriesPlayer1.getFirst().getCountryId()} \{d_Player.getReinforcementArmies()}";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), d_Player);
        d_Player.addOrder(l_Order1);
        assertTrue(d_Player.nextOrder().validateCommand());
    }

    /**
     * Test print order command of DeployOrder class.
     */
    @Test
    public void testPrintOrderCommand() {
        d_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();
        IssueOrderController.d_IssueOrderCommand = STR."deploy \{d_CountriesPlayer2.getFirst().getCountryId()} \{d_Player.getReinforcementArmies()}";
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), d_Player);
        d_Player.addOrder(l_Order1);
        d_Player.nextOrder().printOrderCommand();
        IssueOrderController.d_IssueOrderCommand = STR."deploy \{d_CountriesPlayer1.getFirst().getCountryId()} 10";
        Order l_Order2 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), d_Player);
        d_Player.addOrder(l_Order2);
        d_Player.nextOrder().printOrderCommand();
    }
}
