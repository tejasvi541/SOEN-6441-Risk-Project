package org.team21.game.game_engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.controllers.MapEditorController;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.MapReader;
import org.team21.game.models.map.Player;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.models.tournament.TournamentOptions;
import org.team21.game.models.tournament.TournamentResult;
import org.team21.game.utils.validation.MapValidation;
import org.team21.game.utils.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * A class to test the functionalities of tournament mode .
 * @author Bharti
 */
public class TournamentModeTest {
    /**
     * List to hold the tournament results
     */
    List<TournamentResult> d_Results = new ArrayList<>();
    /**
     * Instance of the game map
     */
    GameMap d_gameMap;

    /**
     * Tournament options variable
     */
    TournamentOptions d_Options;


    /**
     * TournamentEngine object.
     */
    TournamentEngine d_tournament;

    /**
     * Sets up.
     */
    @Before
    public void setUp() throws Exception{
        d_tournament = new TournamentEngine(true);
        d_gameMap = GameMap.getInstance();
        d_gameMap.addContinent("Asia", "4");
        d_gameMap.addContinent("Europe", "3");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addCountry("France", "Europe");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.assignCountries();

    }

    /**
     * Validate input.
     */
    @Test
    public void validateTournamentCommand() {
        String string="tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3";
        d_Options = d_tournament.parseCommand(string);
        assertNotNull(d_Options);
        }



    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        d_gameMap.flushGameMap();
    }
}
