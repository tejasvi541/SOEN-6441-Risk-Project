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
    private ReinforcementController reinforcementController;
    /**
     * Game map object
     */
    private GameMap gameMap;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        gameMap = GameMap.getInstance();
        reinforcementController = new ReinforcementController();

        // Setup continents
        Continent continent1 = new Continent("Continent1", "5", 1);
        Continent continent2 = new Continent("Continent2", "3", 2);
        continent1.setAwardArmies(3);
        continent2.setAwardArmies(2);
        gameMap.getContinents().put("Continent1", continent1);
        gameMap.getContinents().put("Continent2", continent2);

        // Setup countries
        Country country1 = new Country("Country1", "Continent1");
        Country country2 = new Country("Country2", "Continent1");
        Country country3 = new Country("Country3", "Continent2");
        gameMap.getCountries().put("1", country1);
        gameMap.getCountries().put("2", country2);
        gameMap.getCountries().put("3", country3);

        // Setup players
        setupPlayer("1", List.of(country1, country2));
        setupPlayer("2", List.of(country3));
        setupPlayer("3", new ArrayList<>());
        setupPlayer("4", new ArrayList<>());
        setupPlayer("5", new ArrayList<>());

        GamePhase currentPhase = GamePhase.Reinforcement;
        GamePhase nextPhase = reinforcementController.start(currentPhase);
        assertEquals(GamePhase.IssueOrder, nextPhase);
    }

    /**
     * Setup players
     *
     * @param playerName
     * @param capturedCountries
     */
    private void setupPlayer(String playerName, List<Country> capturedCountries) {
        Player player = new Player();
        player.setName(playerName);
        player.setCapturedCountries(capturedCountries);
        gameMap.getPlayers().put(playerName, player);
    }

    /**
     * Test computer reinforcements player with no countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_PlayerWithNoCountries() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("3").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements player with one country.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_PlayerWithOneCountry() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements fully captured continent.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_FullyCapturedContinent() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements not fully captured continent.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_NotFullyCapturedContinent() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test computer reinforcements multiple players in different continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testComputerReinforcements_MultiplePlayersInDifferentContinents() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("1").getReinforcementArmies());
        assertEquals(5, gameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops player with captured countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_PlayerWithCapturedCountries() throws InvalidExecutionException {
        reinforcementController.assignReinforcementTroops();

        // Assuming Player1 captured 2 countries in Continent1 (awarded 3 reinforcement armies)
        assertEquals(5, gameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops player with no captured countries.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_PlayerWithNoCapturedCountries() throws InvalidExecutionException {
        // Assuming Player2 has captured no countries
        reinforcementController.assignReinforcementTroops();
        assertEquals(5, gameMap.getPlayers().get("2").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops not fully captured continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_NotFullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured 1 country in Continent1 (not fully captured)
        reinforcementController.assignReinforcementTroops();
        assertEquals(5, gameMap.getPlayers().get("1").getReinforcementArmies());
    }

    /**
     * Test assign reinforcement troops fully captured continents.
     *
     * @throws InvalidExecutionException the invalid execution exception
     */
    @Test
    public void testAssignReinforcementTroops_FullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured all countries in Continent1 (fully captured)
        reinforcementController.assignReinforcementTroops();

        if(gameMap.getPlayers().get("1").getCapturedCountries().size()==2){

            assertEquals(5, gameMap.getPlayers().get("1").getReinforcementArmies());
        }

    }

}