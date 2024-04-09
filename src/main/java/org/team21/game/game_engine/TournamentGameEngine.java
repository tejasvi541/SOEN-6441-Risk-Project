package org.team21.game.game_engine;

import org.team21.game.interfaces.game.Engine;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.MapReader;
import org.team21.game.models.map.Player;
import org.team21.game.models.strategy.player.PlayerStrategy;
import org.team21.game.models.tournament.TournamentOptions;
import org.team21.game.models.tournament.TournamentResult;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.MapValidation;
import org.team21.game.utils.validation.ValidationException;

import java.util.*;

/**
 * Class to implement the tournament mode game
 *
 * @author Kapil Soni
 */
public class TournamentGameEngine implements Engine {
    /**
     * The set of options defining the parameters and rules of the tournament.
     */
    TournamentOptions d_Options;

    /**
     * A collection storing the outcomes and statistics of completed tournaments.
     */
    List<TournamentResult> d_Results = new ArrayList<>();

    /**
     * The current game map instance utilized within the tournament context.
     */
    GameMap d_CurrentMap;

    /**
     * The observable entity responsible for logging game events and tournament proceedings.
     */
    private GameEventLogger d_Logger;

    /**
     * default constructor
     */
    public TournamentGameEngine() {
        d_Logger = GameEventLogger.getInstance();
        init();
    }


    /**
     * parameterised constructor
     */
    public  TournamentGameEngine(boolean test) {
        d_Logger = GameEventLogger.getInstance();
    }

    /**
     * method to check if object is null
     */
    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            d_Logger.log(Constants.INVALID_COMMAND);
            init();
        }
    }

    /**
     * Method to read the tournament command
     *
     * @return parsed command
     */
    //tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    public TournamentOptions getTournamentOptions() {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log(Constants.SEPARATOR);
        d_Logger.log("You are in Tournament Mode");
        d_Logger.log("Enter the tournament command:");
        d_Logger.log("Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns");
        d_Logger.log(Constants.SEPARATOR);
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            getTournamentOptions();
        }
        return d_Options;
    }

    /**
     * method to parse the tournament command
     *
     * @param p_TournamentCommand the tournament command
     * @return tournament options
     */
    public TournamentOptions parseCommand(String p_TournamentCommand) {
        try {
            d_Options = new TournamentOptions();
            if (!p_TournamentCommand.isEmpty() &&
                    p_TournamentCommand.contains("-M") && p_TournamentCommand.contains("-P")
                    && p_TournamentCommand.contains("-G") && p_TournamentCommand.contains("-D")) {
                List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
                String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
                String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
                String l_GameCount = l_CommandList.get(l_CommandList.indexOf("-G") + 1);
                String l_maxTries = l_CommandList.get(l_CommandList.indexOf("-D") + 1);
                d_Options.getMap().addAll(Arrays.asList(l_MapValue.split(",")));
                if (l_PlayerTypes.contains("human")) {
                    d_Logger.log("Human player not supported in tournament mode: Switch to Single Game Mode");
                    return null;
                }
                for (String l_Strategy : l_PlayerTypes.split(",")) {
                    d_Options.getPlayerStrategies().add(PlayerStrategy.getStrategy(l_Strategy));
                }
                if (d_Options.getPlayerStrategies().size() < 2) {
                    return null;
                }
                int l_NumOfGames = Integer.parseInt(l_GameCount);
                int l_NumofTurns = Integer.parseInt(l_maxTries);
                if (l_NumOfGames > 0 && l_NumOfGames <= 5 && l_NumofTurns > 0 && l_NumofTurns <= 50) {
                    d_Options.setGames(l_NumOfGames);
                    d_Options.setMaxTries(l_NumofTurns);
                } else {
                    d_Logger.log("Provide accurate number of games and turns");
                    return null;
                }
            } else {
                return null;
            }
            return d_Options;
        } catch (Exception e) {
            d_Logger.log(Constants.COMMAND_CHECK);
            d_Logger.log("command format to be followed: tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
            return null;
        }
    }

    /**
     * the start method to start the tournamend mode
     *
     * @throws ValidationException if exception occurs
     */
    public void startEngine() throws ValidationException {
        for (String l_File : d_Options.getMap()) {
            for (int l_game = 1; l_game <= d_Options.getGames(); l_game++) {
                GameSettings.getInstance().MAX_TRIES = d_Options.getMaxTries();
                d_CurrentMap = GameMap.newInstance();
                d_CurrentMap.flushGameMap();
                TournamentResult l_Result = new TournamentResult();
                d_Results.add(l_Result);
                l_Result.setGame(l_game);
                l_Result.setMap(l_File);
                MapReader.readMap(d_CurrentMap, l_File);
                if (!MapValidation.validateMap(d_CurrentMap, 0)) {
                    throw new ValidationException("Invalid Map");
                }
                for (PlayerStrategy l_PlayerStrategy : d_Options.getPlayerStrategies()) {
                    Player l_Player = new Player(l_PlayerStrategy);
                    l_Player.setName(l_PlayerStrategy.getClass().getSimpleName());
                    d_CurrentMap.getPlayers().put(l_PlayerStrategy.getClass().getSimpleName(), l_Player);
                }
                d_CurrentMap.assignCountries();
                GameEngine l_GameEngine = new GameEngine();
                l_GameEngine.setGamePhase(GamePhase.Reinforcement);
                l_GameEngine.startEngine();
                Player l_Winner = d_CurrentMap.getWinner();
                if (Objects.nonNull(l_Winner)) {
                    l_Result.setWinner(l_Winner.getName());
                } else {
                    l_Result.setWinner("Draw");
                }
            }
        }

        String l_Table = "|%-15s|%-28s|%-19s|%n";
        System.out.format(Constants.DASH_SEPARATOR);
        System.out.format("|     Map      | Winner                     |   Game Number      |%n");
        System.out.format(Constants.DASH_SEPARATOR);

        for (TournamentResult l_Result : d_Results) {

            System.out.format(l_Table, l_Result.getMap(), l_Result.getWinner(), l_Result.getGame());

        }
        System.out.format(Constants.DASH_SEPARATOR);
    }

    /**
     * Overrided method to set the game phase
     *
     * @param p_GamePhase the game phase
     */
    //tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    //tournament -M Australia.map,conaus.map -P aggressive,random,benevolent,cheater -G 5 -D 50
    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
