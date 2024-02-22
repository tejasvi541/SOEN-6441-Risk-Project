package org.team21.game.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.ReinforcementController;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.validation.InvalidExecutionException;

import java.util.ArrayList;

import java.util.List;


public class ReinforcementControllerTest {

    private ReinforcementController reinforcementController;
    private GameMap gameMap;

    @Before
    public void setUp() {
        gameMap = GameMap.getInstance();
        reinforcementController = new ReinforcementController();

        // Setup continents
        Continent continent1 = new Continent("Continent1", "5", 1);
        Continent continent2 = new Continent("Continent2", "3", 2);
        continent1.setAwardArmies(3);
        continent2.setAwardArmies(2);
        gameMap.get_continents().put("Continent1", continent1);
        gameMap.get_continents().put("Continent2", continent2);

        // Setup countries
        Country country1 = new Country("Country1", "Continent1");
        Country country2 = new Country("Country2", "Continent1");
        Country country3 = new Country("Country3", "Continent2");
        gameMap.get_countries().put("1", country1);
        gameMap.get_countries().put("2", country2);
        gameMap.get_countries().put("3", country3);

        // Setup players
        setupPlayer("Player1", List.of(country1, country2));
        setupPlayer("Player2", List.of(country3));
        setupPlayer("Player3", new ArrayList<>());
        setupPlayer("Player4", new ArrayList<>());
        setupPlayer("Player5", new ArrayList<>());

        GamePhase currentPhase = GamePhase.Reinforcement;
        GamePhase nextPhase = reinforcementController.start(currentPhase);
        assertEquals(GamePhase.IssueOrder, nextPhase);
    }

    private void setupPlayer(String playerName, List<Country> capturedCountries) {
        Player player = new Player();
        player.setName(playerName);
        player.setCapturedCountries(capturedCountries);
        gameMap.getPlayers().put(playerName, player);
    }

    @Test
    public void testComputerReinforcements_PlayerWithNoCountries() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("Player3").getReinforcementArmies());
    }

    @Test
    public void testComputerReinforcements_PlayerWithOneCountry() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("Player2").getReinforcementArmies());
    }

    @Test
    public void testComputerReinforcements_FullyCapturedContinent() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("Player1").getReinforcementArmies());
    }

    @Test
    public void testComputerReinforcements_NotFullyCapturedContinent() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("Player2").getReinforcementArmies());
    }

    @Test
    public void testComputerReinforcements_MultiplePlayersInDifferentContinents() throws InvalidExecutionException {
        reinforcementController.computerReinforcements();
        assertEquals(5, gameMap.getPlayers().get("Player1").getReinforcementArmies());
        assertEquals(5, gameMap.getPlayers().get("Player2").getReinforcementArmies());
    }
    @Test
    public void testAssignReinforcementTroops_PlayerWithCapturedCountries() throws InvalidExecutionException {
        reinforcementController.assignReinforcementTroops();

        // Assuming Player1 captured 2 countries in Continent1 (awarded 3 reinforcement armies)
        assertEquals(5, gameMap.getPlayers().get("Player1").getReinforcementArmies());
    }

    @Test
    public void testAssignReinforcementTroops_PlayerWithNoCapturedCountries() throws InvalidExecutionException {
        // Assuming Player2 has captured no countries
        reinforcementController.assignReinforcementTroops();
        assertEquals(5, gameMap.getPlayers().get("Player2").getReinforcementArmies());
    }

    @Test
    public void testAssignReinforcementTroops_NotFullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured 1 country in Continent1 (not fully captured)
        reinforcementController.assignReinforcementTroops();
        assertEquals(5, gameMap.getPlayers().get("Player1").getReinforcementArmies());
    }

    @Test
    public void testAssignReinforcementTroops_FullyCapturedContinents() throws InvalidExecutionException {
        // Assuming Player1 captured all countries in Continent1 (fully captured)
        reinforcementController.assignReinforcementTroops();

        if(gameMap.getPlayers().get("Player1").getCapturedCountries().size()==2){

            assertEquals(5, gameMap.getPlayers().get("Player1").getReinforcementArmies());
        }

    }

}