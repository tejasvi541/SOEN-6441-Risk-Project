package org.team25.game.controllers;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.models.game_play.GameCommands;
import org.team25.game.utils.validation.MapValidator;
import org.team25.game.utils.validation.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import org.team25.game.models.map.GameMap;
import org.team25.game.models.game_play.GamePhase;

//Todo refactor
public class StartGameController implements GameFlowManager {

    private GameMap d_GameMap;
    private GamePhase d_NextState = GamePhase.Reinforcement;
    private final Scanner SCANNER = new Scanner(System.in);
    private final List<GameCommands> CLI_COMMANDS = Arrays.asList(GameCommands.SHOW_MAP, GameCommands.LOAD_MAP, GameCommands.GAME_PLAYER, GameCommands.ASSIGN_COUNTRIES);

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
        while (true) {
            System.out.println("1. Enter help to view the set of commands\n2. Enter exit to end");
            String input = SCANNER.nextLine();
            List<String> inputList = null;
            if (input.contains("-")) {
                inputList = Arrays.stream(input.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } else {
                inputList = Arrays.stream(input.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidator(inputList)) {
                if (input.startsWith("exit")) {
                    inputList.addFirst("exit");
                } else {
                    inputList.clear();
                    inputList.add("help");
                    inputList.add("dummy");
                }
            }

            String mainCommand = inputList.get(0);
            inputList.remove(mainCommand);
            for (String command : inputList) {
                String[] commandArray = command.split(" ");
                GameCommands cmd = GameCommands.fromString(mainCommand.toLowerCase());
                switch (cmd) {
                    case LOAD_MAP: {
                        if (commandArray.length == 1) {
                            loadMap(commandArray[0]);
                        }
                        break;
                    }
                    case GAME_PLAYER: {
                        if (commandArray.length > 0) {
                            switch (commandArray[0]) {
                                case "add": {
                                    if (commandArray.length == 2) {
                                        d_GameMap.addPlayer(commandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (commandArray.length == 2) {
                                        d_GameMap.removePlayer(commandArray[1]);
                                    } else {
                                        throw new ValidationException();
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
                            System.out.println("================================End of Load Game Phase==================================");
                            return p_GamePhase.nextState(d_NextState);
                        } else {
                            throw new ValidationException("Create at least two players");
                        }
                    }
                    case SHOW_MAP: {
                        ShowMapController l_showMapController = new ShowMapController(d_GameMap);
                        l_showMapController.show();
                        break;
                    }
                    case EXIT: {
                        return p_GamePhase.nextState(d_NextState);
                    }
                    case null:
                        System.out.println("Order of game play commands:");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("To load the map : loadmap filename");
                        System.out.println("To show the loaded map : showmap");
                        System.out.println("To add or remove a player : gameplayer -add playername -remove playername");
                        System.out.println("To assign countries : assigncountries");
                        System.out.println("-----------------------------------------------------------------------------------------");
                    default: {
                        System.out.println("Order of game play commands:");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("To load the map : loadmap filename");
                        System.out.println("To show the loaded map : showmap");
                        System.out.println("To add or remove a player : gameplayer -add playername -remove playername");
                        System.out.println("To assign countries : assigncountries");
                        System.out.println("-----------------------------------------------------------------------------------------");
                    }
                }
            }
        }
    }

    /**
     * Loads the game map from the map file.
     *
     * @param filename The map file name.
     * @throws ValidationException When validation fails.
     */
    private void loadMap(String filename) throws ValidationException {
        new MapLoaderController().readMap(filename);
    }

    /**
     * Validates if the current CLI command is executable in the current phase.
     *
     * @param inputList The command list from the console.
     * @return True if the command is executable, else false.
     */
    public boolean inputValidator(List<String> inputList) {
        if (inputList.size() > 0) {
            String mainCommand = inputList.get(0);
            if (inputList.size() == 1) {
                inputList.add("dummy");
            }
            return CLI_COMMANDS.contains(GameCommands.fromString(mainCommand.toLowerCase()));
        }
        return false;
    }
}
