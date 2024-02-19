package org.team21.game.controllers;

import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.models.game_play.GameCommands;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.validation.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The StartGameController will perform main load game actions and associated controllers are
 * {ExecuteOrderController,IssueOrderController,ReinforcementController,MapLoaderController,ShowMapController}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public class StartGameController implements GameFlowManager {

    /**
     * List of commands are
     */
    private final static List<GameCommands> CLI_COMMANDS = Arrays.asList(GameCommands.SHOW_MAP, GameCommands.LOAD_MAP, GameCommands.GAME_PLAYER, GameCommands.ASSIGN_COUNTRIES);
    /**
     * Game Map for performing map related options
     */
    private final GameMap d_GameMap;
    /**
     * Upcoming game phase to run that
     */
    private final GamePhase d_UpcomingGamePhase = GamePhase.Reinforcement;
    /**
     * Scanner to scan user inputs from CMD.
     */
    private final Scanner d_Scanner = new Scanner(System.in);

    /**
     * Default constructor initializing the game map.
     */
    public StartGameController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Starts the game phase and performs tasks based on the commands given.
     *
     * @param p_GamePhase The current game phase.
     * @return The next game phase.
     * @throws ValidationException When validation fails.
     */
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        return run(p_GamePhase);
    }

    /**
     * Run is entry point of the StartGameController
     *
     * @param p_UpcomingGamePhase : Upcoming Game Phase
     * @return : Next GamePhase to execute
     * @throws ValidationException
     */
    private GamePhase run(GamePhase p_UpcomingGamePhase)  {
        while (true) {
            System.out.println(Constants.HELP_COMMAND_MESSAGE+"\n"+Constants.EXIT_COMMAND_MESSAGE);
            String l_StartUpCommand = d_Scanner.nextLine();
            List<String> l_InputList = null;
            if (l_StartUpCommand.contains("-")) {
                l_InputList = Arrays.stream(l_StartUpCommand.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } else {
                l_InputList = Arrays.stream(l_StartUpCommand.split(" ")).collect(Collectors.toList());
            }

            if (!checkCommandValidator(l_InputList)) {
                if (l_StartUpCommand.startsWith(Constants.EXIT)) {
                    l_InputList.addFirst(Constants.EXIT);
                } else {
                    l_InputList.clear();
                    l_InputList.add(Constants.HELP);
                    l_InputList.add(Constants.DUMMY);
                }
            }

            String l_FinalCommand = l_InputList.get(0);
            l_InputList.remove(l_FinalCommand);
            for (String l_CommandFromInput : l_InputList) {
                String[] commandArray = l_CommandFromInput.split(" ");
                GameCommands l_GameCommand = GameCommands.fromString(l_FinalCommand.toLowerCase());
                switch (l_GameCommand) {
                    case LOAD_MAP: {
                        if (commandArray.length == 1) {
                            loadMapForStaringGame(commandArray[0]);
                        }
                        break;
                    }
                    case GAME_PLAYER: {
                        if (commandArray.length > 0) {
                            switch (commandArray[0]) {
                                case Constants.SIMPLE_ADD: {
                                    if (commandArray.length == 2) {
                                        d_GameMap.addPlayer(commandArray[1]);
                                    } else {
                                        System.out.println(Constants.INVALID_COMMAND);
                                    }
                                    break;
                                }
                                case Constants.SIMPLE_REMOVE: {
                                    if (commandArray.length == 2) {
                                        d_GameMap.removePlayer(commandArray[1]);
                                    } else {
                                        System.out.println(Constants.INVALID_COMMAND);
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    case ASSIGN_COUNTRIES: {
                        if (d_GameMap.getPlayers().size() > 1) {
                            d_GameMap.assignCountries();
                            System.out.println(Constants.SEPERATER);
                            return p_UpcomingGamePhase.nextState(d_UpcomingGamePhase);
                        } else {
                            System.out.println(Constants.ADD_TWO_PLAYERS);
                        }
                    }
                    case SHOW_MAP: {
                        new ShowMapController(d_GameMap).show();
                        break;
                    }
                    case EXIT: {
                        return p_UpcomingGamePhase.nextState(d_UpcomingGamePhase);
                    }
                    case null:
                        System.out.println(Constants.ENTER_CORRECT_COMMAND);
                    default: {
                        Constants.showStartGameCommand();
                    }
                }
            }
        }
    }

    /**
     * Loads the game map from the map file.
     *
     * @param p_Filename The map file name.
     */
    private void loadMapForStaringGame(String p_Filename) {
        new MapLoaderController().readMap(p_Filename);
    }

    /**
     * Validates if the current CLI command is executable in the current phase.
     *
     * @param p_InputList The command list from the console.
     * @return True if the command is executable, else false.
     */
    public boolean checkCommandValidator(List<String> p_InputList) {
        if (!p_InputList.isEmpty()) {
            String mainCommand = p_InputList.getFirst();
            if (p_InputList.size() == 1) {
                p_InputList.add(Constants.DUMMY);
            }
            return CLI_COMMANDS.contains(GameCommands.fromString(mainCommand.toLowerCase()));
        }
        return false;
    }
}
