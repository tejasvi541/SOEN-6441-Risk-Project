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

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class AdvanceOrderTest {
    /**
     * Created gamemap instance.
     */
    GameMap d_GameMap;

    /**
     * Basic setup before each test case runs
     *
     * @throws Exception if an exception occurs
     */
    @Before
    public void setUp() throws Exception {

        d_GameMap = GameMap.getInstance();

        d_GameMap = GameMap.getInstance();
        Continent l_Continent1 = new Continent("Asia", "10", 1);
        Continent l_Continent2 = new Continent("Africa", "15", 2);
        HashMap<String, Continent> l_Continents = new HashMap<>();
        l_Continents.put("asia", l_Continent1);
        l_Continents.put("africa", l_Continent2);
        d_GameMap.setContinents(l_Continents);

        Country l_Country1 = new Country("China", "Asia");
        Country l_Country2 = new Country("Nigeria", "Africa");
        HashMap<String, Country> l_countries = new HashMap<>();
        l_countries.put("china", l_Country1);
        l_countries.put("nigeria", l_Country2);

        HashMap<String, Country> l_ChinaNeighbors = new HashMap<>();
        l_ChinaNeighbors.put("nigeria", l_Country2);
        l_Country1.setNeighbours(l_ChinaNeighbors);

        HashMap<String, Country> l_NigeriaNeighbors = new HashMap<>();
        l_NigeriaNeighbors.put("china", l_Country1);
        l_Country2.setNeighbours(l_NigeriaNeighbors);

        d_GameMap.setCountries(l_countries);

        Player l_Player1 = new Player();
        l_Player1.setName("p1");
        l_Player1.setReinforcementArmies(5);
        d_GameMap.setPlayer(l_Player1);

        Player l_Player2 = new Player();
        l_Player2.setName("p2");
        l_Player2.setReinforcementArmies(5);
        d_GameMap.setPlayer(l_Player2);

        //Add Countries to players
        d_GameMap.getPlayer("p1").getCapturedCountries().add(d_GameMap.getCountries().get("china"));
        d_GameMap.getPlayer("p2").getCapturedCountries().add(d_GameMap.getCountries().get("nigeria"));
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
    @Test
    public void execute() {
    }

    @Test
    public void validateCommand() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("p1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("p1");
        l_CountriesPlayer1.get(0).setArmies(5);
        IssueOrderController.d_IssueOrderCommand = "advance " + l_CountriesPlayer1.get(0).getPlayer() + " " + "Thailand" + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderOwner.issueOrder(IssueOrderController.d_IssueOrderCommand.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
//        assertFalse(l_Player1.nextOrder().execute());
    }
}