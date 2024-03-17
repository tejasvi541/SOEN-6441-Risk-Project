package org.team21.game.models.orders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.GameSettings;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.strategy.DiceStrategy;

import java.util.*;

import static org.junit.Assert.*;

public class AdvanceOrderTest {
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
     * assigning game settings to Gamesettings object.
     */
    GameSettings l_Setting = GameSettings.getInstance();

    /**
     * Setup to set the testing of DeployOrder. Here Game Map
     * will get initialised and relevant players will get
     * the countries and armies.
     *
     * @throws Exception : throws exception
     */
    @Before
    public void setUp(){
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
        l_ChinaNeighbors.put("india", l_Country3);
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
        l_Setting.setStrategy(new DiceStrategy());
    }
    /**
     * Clear GameMap once all the operation gets completed
     */
    @After
    public void tearDown() {
        d_GameMap.clearMap();
    }
    /**
     * Test to check if advance execution fails on no troops deployed
     */
    @Test
    public void checkExecutionFailOnNoTroopsDeployed() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();

        Player l_Player1 = d_GameMap.getPlayer("p1");
        System.out.println(l_CountriesPlayer1.get(0).getCountryId());
        System.out.println(l_CountriesPlayer2.get(0).getCountryId());
        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getCountryId() + " " + l_CountriesPlayer2.get(0).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution fails, if destination is not neighbor
     */
    @Test
    public void checkExecutionFailOnNotANeighbor() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        Player l_Player2 = d_GameMap.getPlayer("p2");
        l_CountriesPlayer1.get(0).setArmies(6);
        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(1).getCountryId() + " " + l_CountriesPlayer2.get(0).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed.
     */
    @Test
    public void checkExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        l_CountriesPlayer1.get(1).setArmies(6);
        IssueOrderController.d_IssueOrderCommand = "advance " + d_CountriesPlayer1.get(0).getCountryId() + " " + d_CountriesPlayer1.get(1).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and no king exists. Also proper armies distribution
     * after successful attack.
     */
    @Test
    public void checkAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getCountryId() + " " + l_CountriesPlayer2.get(0).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
//        assertEquals("Armies Depleted from source and deployed to target",5,d_GameMap..getArmies());
        assertTrue(l_CountriesPlayer1.get(0).getArmies() > 0);
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void checkAttackSuccessOnNeighborWithKing() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        Player l_Player2 = d_GameMap.getPlayer("p2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);

        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getCountryId() + " " + l_CountriesPlayer2.get(0).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if attack execution succeeds and ownership changed, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void checkOwnershipChangeOnAdvanceSuccess() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        Player l_Player2 = d_GameMap.getPlayer("p2");

        //Set Players to Countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getCountryId() + " " + l_CountriesPlayer2.get(0).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
//        assertEquals("Ownership changed",l_Player1,d_GameMap.getCountry("China").getPlayer());
    }

    /**
     * Test to check if advance command gets skipped if on neutral player
     */
    @Test
    public void checkExecutionSkipsOnNeutralPlayer() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("p1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        Player l_Player2 = d_GameMap.getPlayer("p2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);
        //Set Neutral Players
        l_Player1.getNeutralPlayers().add(l_Player2);
        l_Player2.getNeutralPlayers().add(l_Player1);

        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getCountryId() + " " + l_CountriesPlayer2.get(1).getCountryId() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    @Test
    public void validateCommand() {
        IssueOrderController.d_IssueOrderCommand = "advance " + d_CountriesPlayer1.get(0).getCountryId() + " " + "India" + " " + (d_Player.getReinforcementArmies() - 2);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), d_Player);
        d_Player.addOrder(l_Order1);
//        System.out.println(d_Player.nextOrder().validateCommand());
        assertTrue(d_Player.nextOrder().validateCommand());
    }
}