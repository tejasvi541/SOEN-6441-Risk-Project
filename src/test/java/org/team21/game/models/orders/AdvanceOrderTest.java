package org.team21.game.models.orders;

import org.team21.game.models.game_play.GameSettings;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.strategy.DiceStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.team21.game.models.game_play.GamePhase.IssueOrder;

/**
 * Test cases for Advance Order
 *
 * @author Madhuvanthi Hemanathan
 */
public class AdvanceOrderTest {
    /**
     * Game map object
     */
    GameMap d_GameMap;

    /**
     * Basic setup before each test case runs
     *
     * @throws Exception if an exception occurs
     */
    @Before
    public void setUp() throws Exception {
        /**
         * Singleton game map instance
         */
        d_GameMap = GameMap.getInstance();

        /**
         * Singleton game settings instance
         */
        GameSettings l_GameSettings = GameSettings.getInstance();
        l_GameSettings.setStrategy(new DiceStrategy());

        Continent continent1 = new Continent("Continent1", "5", 1);
        Continent continent2 = new Continent("Continent2", "3", 2);
        d_GameMap.getContinents().put("Continent1", continent1);
        d_GameMap.getContinents().put("Continent2", continent2);
        //Add Continent
       // d_GameMap.addContinent("Asia", "4");
        //Add Countries

        Country country1 = new Country("Country1", "Continent1");
        Country country2 = new Country("Country2", "Continent1");
        Country country3 = new Country("Country3", "Continent2");
        d_GameMap.getCountries().put("1", country1);
        d_GameMap.getCountries().put("2", country2);
        d_GameMap.getCountries().put("3", country3);
//        d_GameMap.addCountry("India", "Asia");
//        d_GameMap.addCountry("China", "Asia");
//        d_GameMap.addCountry("Vietnam", "Asia");
        //Add Neighbors
//        Country l_Country1 = d_GameMap.getCountry("India");
//        Country l_Country2 = d_GameMap.getCountry("China");
//        Country l_Country3 = d_GameMap.getCountry("Vietnam");
//        l_Country1.setNeighbors(l_Country2);
//        l_Country2.setNeighbors(l_Country3);
//        l_Country3.setNeighbors(l_Country1);

        Country neighbor1 = new Country("Country1","Continent1");
        Country neighbor2 = new Country("Country2","Continent1");
        Country neighbor3 = new Country("Country3","Continent2");
        country1.getNeighbours().put("Country1",neighbor1);
        country2.getNeighbours().put("Country2",neighbor2);
        country3.getNeighbours().put("Country3",neighbor3);
        setupPlayer("1", List.of(country1, country2));
        setupPlayer("2", List.of(country3));
//        d_GameMap.addPlayer("1");
//        d_GameMap.addPlayer("2");
//        //Add Countries to players
//        d_GameMap.getPlayer("1").getCapturedCountries().add(d_GameMap.getCountries("1",country1));
//        d_GameMap.getPlayer("1").getCapturedCountries().add(d_GameMap.getCountries("2",country2));
//        d_GameMap.getPlayer("2").getCapturedCountries().add(d_GameMap.getCountries("3",country3));
        //Add Reinforcements to players
        d_GameMap.getPlayer("1").setReinforcementArmies(10);
        d_GameMap.getPlayer("2").setReinforcementArmies(10);
    }

    private void setupPlayer(String playerName, List<Country> capturedCountries) {
        Player player = new Player();
        player.setName(playerName);
        player.setCapturedCountries(capturedCountries);
        d_GameMap.getPlayers().put(playerName, player);
    }

    /**
     * Tear down the setup after each test runs
     *
     * @throws Exception if an exception occurs
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.clearMap();
    }

    /**
     * Test to check if advance execution fails on no troops deployed
     */
    @Test
    public void checkExecutionFailOnNoTroopsDeployed() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        List<Country> l_Countries2 = d_GameMap.getPlayer("2").getCapturedCountries();

        //System.out.println(l_Countries1);
        Player l_1 = d_GameMap.getPlayer("1");
        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries2.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertFalse(l_1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution fails, if destination is not neighbor
     */
    @Test
    public void checkExecutionFailOnNotANeighbor() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        l_Countries1.get(0).setArmies(6);
        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries1.get(1).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertFalse(l_1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed.
     */
    @Test
    public void checkExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        l_Countries1.get(1).setArmies(6);
        IssueOrder.Commands = "advance " + l_Countries1.get(1).getCountryId() + " " + l_Countries1.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertTrue(l_1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and no king exists. Also proper armies distribution
     * after successful attack.
     */
    @Test
    public void checkAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        List<Country> l_Countries2 = d_GameMap.getPlayer("2").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        // Set Armies to each country
        l_Countries1.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries2.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertTrue(l_1.nextOrder().execute());
        assertEquals("Armies Depleted from source and deployed to target",5,d_GameMap.getCountries().get(l_Countries2).getArmies());
        assertTrue(l_Countries1.get(0).getArmies() > 0);
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void checkAttackSuccessOnNeighborWithKing() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        List<Country> l_Countries2 = d_GameMap.getPlayer("2").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        Player l_2 = d_GameMap.getPlayer("2");
        // Set Players to countries
        l_Countries1.get(0).setPlayer(l_1);
        l_Countries2.get(0).setPlayer(l_2);
        // Set Armies to each country
        l_Countries1.get(0).setArmies(6);
        l_Countries2.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries2.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertTrue(l_1.nextOrder().execute());
    }

    /**
     * Test to check if attack execution succeeds and ownership changed, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void checkOwnershipChangeOnAdvanceSuccess() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        List<Country> l_Countries2 = d_GameMap.getPlayer("2").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        Player l_2 = d_GameMap.getPlayer("2");

        //Set Players to Countries
        l_Countries1.get(0).setPlayer(l_1);
        l_Countries2.get(0).setPlayer(l_2);
        // Set Armies to each country
        l_Countries1.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries2.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertTrue(l_1.nextOrder().execute());
        assertEquals("Ownership changed",l_1,d_GameMap.getCountries().get(l_Countries2).getPlayer());
    }

    /**
     * Test to check if advance command gets skipped if on neutral player
     */
    @Test
    public void checkExecutionSkipsOnNeutralPlayer() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        List<Country> l_Countries2 = d_GameMap.getPlayer("2").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        Player l_2 = d_GameMap.getPlayer("2");
        // Set Players to countries
        l_Countries1.get(0).setPlayer(l_1);
        l_Countries2.get(0).setPlayer(l_2);
        // Set Armies to each country
        l_Countries1.get(0).setArmies(6);
        l_Countries2.get(0).setArmies(6);
        //Set Neutral Players
        l_1.getNeutralPlayers().add(l_2);
        l_2.getNeutralPlayers().add(l_1);

        IssueOrder.Commands = "advance " + l_Countries1.get(0).getCountryId() + " " + l_Countries2.get(0).getCountryId() + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertTrue(l_1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution fails and gets skipped if invalid command
     */
    @Test
    public void checkExecutionFailOnInvalidCommand() {
        List<Country> l_Countries1 = d_GameMap.getPlayer("1").getCapturedCountries();
        Player l_1 = d_GameMap.getPlayer("1");
        l_Countries1.get(1).setArmies(6);
        IssueOrder.Commands = "advance " + l_Countries1.get(1).getCountryId() + " " + "Thailand" + " " + (l_1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrder.Commands.split(" "), l_1);
        l_1.addOrder(l_Order1);
        assertFalse(l_1.nextOrder().execute());
    }

}