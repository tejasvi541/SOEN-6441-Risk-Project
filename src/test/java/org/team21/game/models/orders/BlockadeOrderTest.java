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
import org.team21.game.utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    Continent d_Continent;
    Country d_Country1;
    Country d_Country2;
    HashMap<String, Continent> d_ContinentHashMap;
    HashMap<String, Country> d_CountryHashMap;

    /**
     * Setup for the test case
     *
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_CountryList1 = new ArrayList<>();
        d_CountryList2 = new ArrayList<>();
        d_ContinentHashMap = new HashMap<>();
        d_CountryHashMap = new HashMap<>();
        d_Continent = new Continent("asia", "5", 1);
        d_Country1 = new Country("india", "asia");
        d_Country2 = new Country("china", "asia");
        d_ContinentHashMap.put("asia", d_Continent);
        d_CountryHashMap.put("india", d_Country1);
        d_CountryHashMap.put("china", d_Country2);
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.setContinents(d_ContinentHashMap);
        d_GameMap.setCountries(d_CountryHashMap);
        d_GameMap.assignCountries();
        d_CountryList1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        d_CountryList2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
    }
    /**
     * Test to check that the blockade command works successfully
     *
     */
    @Test
    public void execute() {
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        l_Player1.addPlayerCard(new Card(CardType.BLOCKADE));
        String l_Command = Constants.BLOCKADE_COMMAND + " " + d_CountryList1.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(l_Command.split(" "), l_Player1);
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
        String l_Command = Constants.BLOCKADE_COMMAND + " " + d_CountryList1.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(l_Command.split(" "), l_Player);
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
        String l_Command = Constants.BLOCKADE_COMMAND + " " + d_CountryList2.get(0).getCountryId();
        Order l_Order1 = OrderOwner.issueOrder(l_Command.split(" "), l_Player);
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
        d_GameMap = null;
        d_CountryList1 = null;
        d_CountryList2 = null;
        d_Continent = null;
        d_Country1 = null;
        d_Country2 = null;
        d_CountryHashMap.clear();
        d_ContinentHashMap.clear();
    }
}