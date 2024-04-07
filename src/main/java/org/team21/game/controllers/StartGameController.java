package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.game_engine.GameProgress;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.DominationMap;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;
import org.team21.game.utils.adapter.Adaptee;
import org.team21.game.utils.adapter.Adapter;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.MapValidation;
import org.team21.game.utils.validation.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the controller for starting and managing the phases of the game.
 * It facilitates user interaction through a command-line interface and executes various game commands.
 * Implements GameFlowManager interface.
 *
 * @author : Nishith Soni
 * @version : 1.0.0
 */
public class StartGameController implements GameFlowManager {
    /**
     * A data member for scanner used for user input.
     */
    private final Scanner d_SCANNER = new Scanner(System.in);

    /**
     * A list of valid commands for the command-line interface.
     */
    private final List<String> CLI_COMMANDS = Arrays.asList(
            Constants.SHOW_MAP, Constants.LOAD_MAP, Constants.GAMEPLAYER,
            Constants.ASSIGNCOUNTRIES, Constants.SAVEGAME_COMMAND,
            Constants.LOADGAME_COMMAND, Constants.TOURNAMENT
    );

    /**
     * Instance of the game map.
     */
    GameMap d_GameMap;

    /**
     * The reinforcement phase of the game.
     */
    GamePhase d_ReinforcementPhase = GamePhase.Reinforcement;

    /**
     * The map editor phase of the game.
     */
    GamePhase d_MapEditorPhase = GamePhase.MapEditor;

    /**
     * Instance of the game event logger for logging events.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Default constructor initializing the game map instance.
     */
    public StartGameController() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This function starts the game phase and passes through the tasks in the game phase
     * depending on the command given
     *
     * @param p_GamePhase current Game Phase
     * @return the next Game Phase
     * @throws ValidationException when validation fails
     */
    public GamePhase startPhase(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            d_Logger.log("-----------------------------------------------------------------------------------------");
            d_Logger.log("Enter Command: ");
            d_Logger.log("1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end");
            d_Logger.log("-----------------------------------------------------------------------------------------");
            String l_Input = d_SCANNER.nextLine();
            List<String> l_InputList;
            if (l_Input.contains("-")) {
                l_InputList = Arrays.stream(l_Input.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } else {
                l_InputList = Arrays.stream(l_Input.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidator(l_InputList)) {
                if (l_Input.startsWith(Constants.EXIT)) {
                    l_InputList.add(0, Constants.EXIT);
                } else {
                    l_InputList.clear();
                    // if not available in command list forcing to call help
                    l_InputList.add(Constants.HELP);
                    l_InputList.add(Constants.DUMMY);
                }
            }
            String l_MainCommand = l_InputList.get(0);
            l_InputList.remove(l_MainCommand);
            for (String l_Command : l_InputList) {
                String[] l_CommandArray = l_Command.split(" ");
                switch (l_MainCommand.toLowerCase()) {
                    case Constants.LOAD_MAP: {
                        if (l_CommandArray.length == 1) {
                            loadMap(l_CommandArray[0]);
                        }
                        break;
                    }
                    case Constants.GAMEPLAYER: {
                        if (l_CommandArray.length > 0) {
                            switch (l_CommandArray[0]) {
                                case Constants.SIMPLE_ADD: {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.addPlayer(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case Constants.SIMPLE_REMOVE: {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.removePlayer(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    //Handle assigncountries command from console

                    case Constants.ASSIGNCOUNTRIES: {
                        if (d_GameMap.getPlayers().size() > 1) {
                            d_GameMap.assignCountries();
                        } else {
                            d_Logger.log("Game ended as the minimum players are not there.");
                            throw new ValidationException("Create atleast two players");
                        }
                        break;
                    }
                    //Handle showmap command from console
                    case Constants.SHOW_MAP: {
                        d_GameMap.showMap();
                        break;
                    }
                    case Constants.SAVEGAME_COMMAND: {
                        if (l_CommandArray.length == 1) {
                            GameProgress.SaveGameProgress(d_GameMap, l_CommandArray[0]);
                            d_GameMap.setGamePhase(d_MapEditorPhase);
                            return d_MapEditorPhase;
                        }
                        break;
                    }
                    case Constants.LOADGAME_COMMAND: {
                        if (l_CommandArray.length == 1) {
                            GamePhase l_GameLoaded = GameProgress.LoadGameProgress(l_CommandArray[0]);
                            if (!l_GameLoaded.equals(GamePhase.StartUp)) {
                                return l_GameLoaded;
                            }
                        }
                        break;
                    }
                    case Constants.EXIT: {
                        d_GameMap.setGamePhase(d_ReinforcementPhase);
                        return p_GamePhase.nextState(d_ReinforcementPhase);
                    }
                    //Print the commands for help
                    default: {
                        d_Logger.log("-----------------------------------------------------------------------------------------");
                        d_Logger.log("Order of game play commands:");
                        d_Logger.log("-----------------------------------------------------------------------------------------");
                        d_Logger.log("To load the map : loadmap filename");
                        d_Logger.log("To show the loaded map : showmap");
                        d_Logger.log("To add or remove a player : gameplayer -add playername -remove playername");
                        d_Logger.log("To assign countries : assigncountries");
                        d_Logger.log("To save the game : savegame filename");
                        d_Logger.log("To load the game : loadgame filename");

                    }
                }
            }
        }
    }

    /**
     * This method loads the game map from the map file
     *
     * @param p_Filename the map file name
     * @throws ValidationException when validation fails
     */
    private void loadMap(String p_Filename) throws ValidationException {
        boolean l_ShouldUseConquestAdapter = true;
        try {
            File l_File = new File("maps/" + p_Filename);
            BufferedReader l_BufferedReader = new BufferedReader(new FileReader(l_File));
            while (l_BufferedReader.ready()) {
                String l_FirstLine = l_BufferedReader.readLine();
                if (!l_FirstLine.isEmpty()) {
                    if (l_FirstLine.contains(";")) {
                        l_ShouldUseConquestAdapter = false;
                    }
                    l_BufferedReader.close();
                }
            }
        } catch (IOException l_E) {
            // Do nothing.
        }
        DominationMap l_MapReader = l_ShouldUseConquestAdapter ? new Adapter(new Adaptee()) : new DominationMap();
        l_MapReader.readMap(d_GameMap, p_Filename);
        if (!MapValidation.validateMap(d_GameMap, 0)) {
            throw new ValidationException("Invalid Map");
        }
    }

    /**
     * This method validates to check if the current cli command is executable
     * in the current phase
     *
     * @param p_InputList the command list from console
     * @return true if command is executable else false
     */
    public boolean inputValidator(List<String> p_InputList) {
        if (p_InputList.size() > 0) {
            String l_MainCommand = p_InputList.get(0);
            if (p_InputList.size() == 1) {
                p_InputList.add(Constants.DUMMY);
            }
            return CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
        }
        return false;
    }
}