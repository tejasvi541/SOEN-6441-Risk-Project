package org.team21.game.game_engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.models.tournament.TournamentOptions;
import org.team21.game.models.tournament.TournamentResult;
import org.team21.game.utils.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * TournamentGameEngine object.
     */
    TournamentGameEngine d_tournament;

    /**
     * Player object.
     */
    Player d_Player;


    /**
     * Sets up.
     */
    @Before
    public void setUp() throws Exception{
        d_tournament = new TournamentGameEngine(true);
        d_gameMap = GameMap.getInstance();
        d_gameMap.addContinent("Asia", "4");
        d_gameMap.addContinent("Europe", "3");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_Player=d_gameMap.getPlayer("Player1");
        d_gameMap.assignCountries();

    }


    /**
     * Validate Tournament command.
     */
    @Test
    public void validateTournamentCommand() {
        String string="tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        d_Options = d_tournament.parseCommand(string);
        assertEquals(1, d_Options.getMap().size());
        assertEquals(2, d_Options.getGames());
        assertEquals(3, d_Options.getMaxTries());
        assertEquals("Australia.map", d_Options.getMap().get(0));
        }

    /**
     * Validate Invalid Tournament command.
     */
    @Test
    public void validateInvalidTournamentCommand() {
        String string="tournament -M Australia.map -P aggressive,random -G 2 -D -9";
        d_Options = d_tournament.parseCommand(string);
        assertNull(d_Options);
    }

    /**
     * Validate Tournament Game Winner.
     */
    @Test
    public void validateTournamentModeWinner()  {
        String string="tournament -M Australia.map -P aggressive,random -G 1 -D 3";
        d_Options = d_tournament.parseCommand(string);
        for (String l_File : d_Options.getMap()) {
            for (int l_game = 1; l_game <= d_Options.getGames(); l_game++) {
                GameSettings.getInstance().MAX_TRIES = d_Options.getMaxTries();
                TournamentResult l_Result = new TournamentResult();
                d_Results.add(l_Result);
                l_Result.setGame(l_game);
                l_Result.setMap(l_File);
                for (PlayerStrategy l_PlayerStrategy : d_Options.getPlayerStrategies()) {
                    Player l_Player = new Player(l_PlayerStrategy);
                    l_Player.setName(l_PlayerStrategy.getClass().getSimpleName());
                    d_gameMap.getPlayers().put(l_PlayerStrategy.getClass().getSimpleName(), l_Player);
                }
                GameEngine l_GameEngine = new GameEngine();
                l_GameEngine.setGamePhase(GamePhase.Reinforcement);
                HashMap<String, Country> l_ListOfAllCountries = d_gameMap.getCountries();
                for (Country l_Country : l_ListOfAllCountries.values()) {
                    if (!d_Player.isCaptured(l_Country)) {
                        d_Player.getCapturedCountries().add(l_Country);
                        l_Country.setPlayer(d_Player);
                    }
                }
                for (Player l_Player : d_gameMap.getPlayers().values()) {
                    if (l_Player.getCapturedCountries().size() == d_gameMap.getCountries().size()) {
                        d_gameMap.setGamePhase(GamePhase.ExitGame);
                        d_gameMap.setWinner(l_Player);

                    }
                }
                assertTrue(d_gameMap.getWinner().getName().equals("Player1") && d_gameMap.getGamePhase().equals(GamePhase.ExitGame));
            }
        }
    }


    /**
     * Check if Result of tournament is not null
     *
     * @throws ValidationException validation exception
     */
    @Test
    public void validateResultOfTournamentMode() throws ValidationException {
        String l_string = "tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        d_Options = d_tournament.parseCommand(l_string);
        d_tournament.startEngine();
        assertEquals(2,d_tournament.d_Results.size());
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
