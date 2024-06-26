package org.team21.game.controllers;

import org.team21.game.game_engine.GamePhase;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.MapReader;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.MapValidation;
import org.team21.game.utils.validation.ValidationException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class is used to create map using game console commands.
 *
 * @author Tejasvi
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class MapEditorController implements GameFlowManager {
    /**
     * A data member for scanner
     */
    private final Scanner d_SCANNER = new Scanner(System.in);
    /**
     * A data member that stores the list of commands for mapeditor as list
     */
    private final List<String> d_CLI_COMMANDS = Arrays.asList(Constants.EDIT_CONTINENT, Constants.EDIT_COUNTRY, Constants.EDIT_NEIGHBOUR, Constants.SHOW_MAP, Constants.SAVE_MAP, Constants.EDIT_MAP, Constants.VALIDATE_MAP);
    GameMap d_GameMap;
    GamePhase d_NextState = GamePhase.StartUp;
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * This is the default constructor
     */
    public MapEditorController() {
        this.d_GameMap = GameMap.getInstance();
    }

    /**
     * The start method of MapEditor phase that handles creation, validation
     * save of map from console commands.
     *
     * @param p_GamePhase Parameter of the enum GamePhase is passed
     * @throws ValidationException when validation fails
     */
    @Override
    public GamePhase startPhase(GamePhase p_GamePhase) throws ValidationException, IOException {
        d_Logger.clear();
        d_Logger.log(Constants.WELCOME_MESSAGE_MAP_EDITOR);
        while (true) {
            d_Logger.log("Enter your map operation:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end map creation and save phase");
            d_Logger.log(Constants.EQUAL_SEPARATOR);
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

            /*
              Handle editcontinent command from console
             */

            String l_MainCommand = l_InputList.get(0);
            l_InputList.remove(l_MainCommand);
            for (String l_Command : l_InputList) {
                String[] l_CommandArray = l_Command.split(" ");
                switch (l_MainCommand.toLowerCase()) {
                    case Constants.EDIT_CONTINENT: {
                        if (l_CommandArray.length > 0) {
                            switch (l_CommandArray[0]) {
                                case Constants.SIMPLE_ADD: {
                                    if (l_CommandArray.length == 3) {
                                        d_GameMap.addContinent(l_CommandArray[1], l_CommandArray[2]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case Constants.SIMPLE_REMOVE: {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.removeContinent(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    /*
                      Handle editcountry command from console
                     */

                    case Constants.EDIT_COUNTRY: {
                        switch (l_CommandArray[0]) {
                            case Constants.SIMPLE_ADD: {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.addCountry(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case Constants.SIMPLE_REMOVE: {
                                if (l_CommandArray.length == 2) {
                                    d_GameMap.removeCountry(l_CommandArray[1]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }

                    /*
                      Handle editneighbor command from console
                     */

                    case Constants.EDIT_NEIGHBOUR: {
                        switch (l_CommandArray[0]) {
                            case Constants.SIMPLE_ADD: {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.addNeighbor(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case Constants.SIMPLE_REMOVE: {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.removeNeighbor(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }


                    // Handle showmap command from console
                    case Constants.SHOW_MAP: {
                        d_GameMap.showMap();
                        break;
                    }
                    //Handle validatemap command from console
                    case Constants.VALIDATE_MAP: {
                        if (MapValidation.validateMap(d_GameMap, 0)) {
                            d_Logger.log("Validation successful");
                        } else {
                            d_Logger.log("Validation failed");
                        }
                        break;
                    }


                    //Handle savemap command from console
                    case Constants.SAVE_MAP: {
                        if (l_CommandArray.length == 1) {
                            d_GameMap.setName(l_CommandArray[0]);
                            d_Logger.log(" Which format do you want to save the file? Type the number.");
                            d_Logger.log("1. Domination map \n2. Conquest map");
                            Scanner l_Scanner = new Scanner(System.in);
                            String l_UserInput = l_Scanner.nextLine();
                            if (l_UserInput.equals("1")) {
                                d_GameMap.saveMap(false);
                                d_Logger.log("The loaded file is of the format Domination map");
                            } else if (l_UserInput.equals("2")) {
                                d_GameMap.saveMap(true);
                                d_Logger.log("The loaded file is of the format Conquest map");
                            } else
                                d_Logger.log("Please enter the right value");

                        }
                        break;
                    }


                    //Handle editmap command from console
                    case Constants.EDIT_MAP: {
                        if (l_CommandArray.length == 1) {
                            MapReader.readMap(d_GameMap, l_CommandArray[0]);
                        }
                        break;
                    }


                    //To exit the map creation phase type "exit"
                    case Constants.EXIT: {
                        d_GameMap.flushGameMap();
                        d_GameMap.setGamePhase(d_NextState);
                        return p_GamePhase.nextState(d_NextState);
                    }
                    //Print the commands for help
                    default: {
                        d_Logger.log("List of user map creation commands from console:");
                        d_Logger.log("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID");
                        d_Logger.log("To add or remove a country : editcountry -add countryID continentID -remove countryID");
                        d_Logger.log("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                        d_Logger.log(Constants.EQUAL_SEPARATOR);
                        d_Logger.log("Map Commands(Edit/Save)");
                        d_Logger.log("To edit map: editmap filename");
                        d_Logger.log("To save map: savemap filename");
                        d_Logger.log(Constants.EQUAL_SEPARATOR);
                        d_Logger.log("Additional map commands:");
                        d_Logger.log("To show the map: showmap");
                        d_Logger.log("To validate map: validatemap");
                        d_Logger.log(Constants.EQUAL_SEPARATOR);

                    }
                }
            }
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
                p_InputList.add("dummy");
            }
            return d_CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
        }
        return false;
    }

}