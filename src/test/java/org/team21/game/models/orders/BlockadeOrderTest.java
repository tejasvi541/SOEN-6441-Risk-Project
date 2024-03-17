package org.team21.game.models.orders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.IssueOrderController;
import org.team21.game.models.cards.Card;
import org.team21.game.models.cards.CardType;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

import java.util.*;

import static org.junit.Assert.*;
/**
 * This class tests the Blockade Order
 *
 * @author Nishith
 */
public class BlockadeOrderTest {

    GameMap d_GameMap;
    List<Country> d_CountryList1;
    List<Country> d_CountryList2;


    /**
     * Setup for the test case
     *
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
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
        d_GameMap.assignCountries();
        d_CountryList1= d_GameMap.getPlayer("Player1").getCapturedCountries();
        d_CountryList2= d_GameMap.getPlayer("Player2").getCapturedCountries();
    }
    /**
     * Test to check that the blockade command works successfully
     *
     */
    @Test
    public void execute() {
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        l_Player1.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.d_IssueOrderCommand = Constants.BLOCKADE_COMMAND + " " + d_CountryList1.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
    }
    /**
     * Test the validation of Blockade command for when the target country belongs to same player
     *
     */
    @Test
    public void checkForCorrectCommand(){
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.d_IssueOrderCommand = Constants.BLOCKADE_COMMAND + " " + d_CountryList1.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }
    /**
     * Test the validation of Blockade command when the target country does not belong to player
     *
     */
    @Test
    public void checkForIncorrectCommand(){
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrderController.d_IssueOrderCommand = Constants.BLOCKADE_COMMAND + " " + d_CountryList2.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
    /**
     * Clear the instance
     *
     * @throws Exception in case of any exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.clearMap();
    }
}