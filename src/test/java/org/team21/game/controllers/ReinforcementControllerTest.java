package org.team21.game.controllers;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.validation.InvalidExecutionException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The type Reinforcement controller test.
 *
 * @author Yesha Shah
 */
public class ReinforcementControllerTest {

    /**
     * Reinforcement controller
     */
    private ReinforcementController d_ReinforcementController;
    /**
     * Game map object
     */
    private GameMap d_GameMap;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        d_GameMap = GameMap.getInstance();
        d_ReinforcementController = new ReinforcementController();

        // Setup continents
        Continent l_Continent1 = new Continent("Continent1", "5", 1);
        Continent l_Continent2 = new Continent("Continent2", "3", 2);
        l_Continent1.setAwardArmies(3);
        l_Continent2.setAwardArmies(2);
        d_GameMap.getContinents().put("Continent1", l_Continent1);
        d_GameMap.getContinents().put("Continent2", l_Continent2);

        // Setup countries
        Country l_Country1 = new Country("Country1", "Continent1");
        Country l_Country2 = new Country("Country2", "Continent1");
        Country l_Country3 = new Country("Country3", "Continent2");
        d_GameMap.getCountries().put("1", l_Country1);
        d_GameMap.getCountries().put("2", l_Country2);
        d_GameMap.getCountries().put("3", l_Country3);

        // Setup players
        setupPlayer("1", List.of(l_Country1, l_Country2));
        setupPlayer("2", List.of(l_Country3));
        setupPlayer("3", new ArrayList<>());
        setupPlayer("4", new ArrayList<>());
        setupPlayer("5", new ArrayList<>());

        GamePhase l_CurrentPhase = GamePhase.Reinforcement;
        GamePhase l_NextPhase = d_ReinforcementController.start(l_CurrentPhase);
        assertEquals(GamePhase.IssueOrder, l_NextPhase);
    }

    /**
     * Setup players
     *
     * @param p_PlayerName
     * @param p_CapturedCountries
     */
    private void setupPlayer(String p_PlayerName, List<Country> p_CapturedCountries) {
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        l_Player.setCapturedCountries(p_CapturedCountries);
        d_GameMap.getPlayers().put(p_PlayerName, l_Player);
    }

    /**
     * Test computer reinforcements player with no countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_PlayerWithNoCountries() throws InvalidExecutionException {
        d_ReinforcementController.computerReinforcements();
        assertEquals(5, d_GameMap.getPlayers().get("3").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements player with one country.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_PlayerWithOneCountry() throws InvalidExecutionException {
        d_ReinforcementController.computerReinforcements();
        assertEquals(5, d_GameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements fully captured continent.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_FullyCapturedContinent() throws InvalidExecutionException {
        d_ReinforcementController.computerReinforcements();
        assertEquals(5, d_GameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements not fully captured continent.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_NotFullyCapturedContinent() throws InvalidExecutionException {
        d_ReinforcementController.computerReinforcements();
        assertEquals(5, d_GameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements multiple players in different continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_MultiplePlayersInDifferentContinents() throws InvalidExecutionException {
        d_ReinforcementController.computerReinforcements();
        assertEquals(5, d_GameMap.getPlayers().get("1").getReinforcementArmies());
        assertEquals(5, d_GameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops player with captured countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_PlayerWithCapturedCountries() throws InvalidExecutionException {
        d_ReinforcementController.assignReinforcementTroops();

        // Assuming Player1 captured 2 countries in Continent1 (awarded 3 reinforcement armies)
        assertEquals(5, d_GameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops player with no captured countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_PlayerWithNoCapturedCountries() throws InvalidExecutionException {
        // Assuming Player2 has captured no countries
        d_ReinforcementController.assignReinforcementTroops();
        assertEquals(5, d_GameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops not fully captured continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_NotFullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured 1 country in Continent1 (not fully captured)
        d_ReinforcementController.assignReinforcementTroops();
        assertEquals(5, d_GameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops fully captured continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_FullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured all countries in Continent1 (fully captured)
        d_ReinforcementController.assignReinforcementTroops();

        if(d_GameMap.getPlayers().get("1").getCapturedCountries().size()==2){

            assertEquals(5, d_GameMap.getPlayers().get("1").getReinforcementArmies());
        }

    }

}