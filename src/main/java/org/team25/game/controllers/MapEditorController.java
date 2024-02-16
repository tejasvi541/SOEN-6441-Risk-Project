package org.team25.game.controllers;

import java.util.*;

import org.team25.game.interfaces.main_engine.GameFlowManager;
import org.team25.game.interfaces.maps.MapEditor;
import org.team25.game.models.map.Continent;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;
import org.team25.game.models.game_play.GamePhase;
import org.team25.game.utils.Constants;
import org.team25.game.utils.validation.MapValidator;
import org.team25.game.utils.validation.ValidationException;



/**
 * This class is used to create map through different commands for different operations.
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class MapEditorController implements MapEditor, GameFlowManager {
    /**
     * A data member for scanner
     */
    private final Scanner d_sc = new Scanner(System.in);

    /**
     * A data member that stores map object
     */
    GameMap d_GameMap;

    /**
     * A data member that stores the list of commands used for editing,validating or saving a map
     */
    private final List<String> d_MAP_CLI_COMMANDS = Arrays.asList(Constants.SHOW_MAP, Constants.HELP, Constants.EDIT_MAP, Constants.EDIT_CONTINENT, Constants.EDIT_COUNTRY, Constants.EDIT_NEIGHBOUR, Constants.VALIDATE_MAP, Constants.SAVE_MAP);

    /**
     * A data member to set status of editing phase
     */
    boolean d_status = true;

    /**
     * This is the default constructor
     */
    public MapEditorController() {
        this.d_GameMap = new GameMap();
    }

    /**
     * This is the parametrised constructor
     *
     * @param p_GameMap Parameter of the GamePhase is passed
     */
    public MapEditorController(GameMap p_GameMap) {
        this.d_GameMap = p_GameMap;
    }

    /**
     * This run method of MapEditor phase handles editing on map
     *
     */

    public GameMap run()  {
        System.out.println("****************************** Welcome to MAP EDITOR PHASE *********************************");
        List<String> l_ListStream = new ArrayList<>();
        while (true) {
            System.out.println("Type the required option for taking action on map:" + "\n");
            System.out.println("1. Type Help :to get list of commands for different actions  " + "\n");
            System.out.println("2. Type Exit : to exit from map editor phase and continue playing game" + "\n");
            System.out.println("*******************************************************************************************************\n");
            String l_UserInput = d_sc.nextLine();
            List<String> l_tempList = new ArrayList<>();
            boolean tempStatus = false;

            if (l_UserInput.startsWith("Help") || l_UserInput.startsWith("help")) {
                l_ListStream.add("help");
                tempStatus = action(l_ListStream);
                l_ListStream.clear();
            } else if (l_UserInput.startsWith("Exit") || l_UserInput.startsWith("exit")) {
                if(new MapValidator().ValidateMapObject(d_GameMap)){
                    return d_GameMap;
                }
            }


            if (!(l_UserInput.contains("-add")) || !(l_UserInput.contains("-remove"))) {
                //adding values that are not for editing
                l_tempList.addAll(Arrays.asList(l_UserInput.split(" ")));
            } else {
                if(l_UserInput.contains("-add")){
                    l_tempList.addAll(List.of(l_UserInput.split(" ")));
                }
                else if(l_UserInput.contains("-remove")){
                    l_tempList.addAll(List.of(l_UserInput.split(" ")));
                }
            }
            l_ListStream = l_tempList;
            // validating the input stream passed by user
            boolean valid = validateUserInput(l_ListStream);

            if (valid){
                boolean l_status = action(l_ListStream);
            }
        }
    }


    public boolean action(List<String> p_ListStream) {
        String[] l_CommandsArray = p_ListStream.toArray(new String[0]);
        switch (p_ListStream.getFirst().toLowerCase()) {

            // command to showcase map
            case Constants.SHOW_MAP: {
                new ShowMapController(d_GameMap).show(d_GameMap);
                break;
            }

            // command to showcase map
            case Constants.SAVE_MAP: {
                if(new MapValidator().ValidateMapObject(d_GameMap)) {
                    new SaveMapController(d_GameMap, l_CommandsArray[1]).saveMap();
                    break;
                }
                break;
            }

            //command to edit country in map
            case Constants.EDIT_COUNTRY: {
                switch (l_CommandsArray[1]) {
                    case Constants.ADD: {
                        if (l_CommandsArray.length == 4) {
                            HashMap<String, Country> l_countries = d_GameMap.get_countries();
                            if (l_countries.containsKey(l_CommandsArray[2].toLowerCase())) {
                                try {
                                    throw new ValidationException("Provided country already exist in a map.Try Again with different country !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                Country l_Country = new Country(l_CommandsArray[2], l_CommandsArray[3]);
                                d_GameMap.get_continents().get(l_CommandsArray[3].toLowerCase()).get_countries().put(l_CommandsArray[2].toLowerCase(), l_Country);
                                d_GameMap.get_countries().put(l_CommandsArray[2].toLowerCase(), l_Country);
                                System.out.println("Country " + l_CommandsArray[2] + " is successfullY added .");
                                d_status = true;
                            }
                        } else {
                            try {
                                throw new ValidationException();
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                    case Constants.REMOVE: {
                        if (l_CommandsArray.length == 3) {

                            Country l_Country = d_GameMap.get_countries().get(l_CommandsArray[2].toLowerCase());
                            ArrayList<Country> l_tempList = new ArrayList<Country>();


                            //handling null values
                            if (l_Country == null) {
                                try {
                                    throw new ValidationException("Provided country does not exist.Try Again with valid country ! ");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {

                                for (Country l_neighbour : l_Country.get_Neighbours().values()) {
                                    l_tempList.add(l_neighbour);
                                }
                                Iterator<Country> l_itr = l_tempList.listIterator();
//                                    while(l_itr.hasNext()) {
//                                        Country l_neighbor = l_itr.next();
//                                        if(!removeNeighbor(d_GameMap, l_Country.get_countryId(), l_neighbor.get_countryId()))
//                                            return false;
//                                    }
                                d_GameMap.get_countries().remove(l_CommandsArray[2].toLowerCase());
                                d_GameMap.get_continents().get(l_Country.get_parentContinent().toLowerCase()).get_countries().remove(l_CommandsArray[2].toLowerCase());


                                System.out.println("Country " + l_CommandsArray[2] + " is successfullY removed .");
                                d_status = true;
                            }
                        } else {
                            try {
                                throw new ValidationException();
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
            }

            // command to edit continent in map
            case Constants.EDIT_CONTINENT: {
                if (l_CommandsArray.length > 0) {
                    switch (l_CommandsArray[1]) {
                        case Constants.ADD: {
                            HashMap<String, Continent> continents = d_GameMap.get_continents();
                            if (l_CommandsArray.length == 4) {
                                if (l_CommandsArray[2] != null) {
                                    if (continents.containsKey(l_CommandsArray[2].toLowerCase())) {
                                        try {
                                            throw new ValidationException("Provided continent already exists in map,Try again with different continent !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } else {
                                        Continent l_Continent = new Continent(l_CommandsArray[2], l_CommandsArray[3], d_GameMap.get_continents().size() + 1);
                                        d_GameMap.get_continents().put(l_CommandsArray[2].toLowerCase(), l_Continent);
                                        System.out.println("Continent " + l_CommandsArray[2] + " is successfullY added .");
                                        d_status = true;
                                    }
                                } else {
                                    try {
                                        throw new ValidationException("Provided continent does not exists .Try again with different continent !");
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }

                            } else {
                                try {
                                    throw new ValidationException();
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        }
                        case Constants.REMOVE: {
                            if (l_CommandsArray.length == 3) {
                                HashMap<String, Continent> l_continents = d_GameMap.get_continents();

                                if (!l_continents.containsKey(l_CommandsArray[2].toLowerCase())) {
                                    try {
                                        throw new ValidationException("Provided Continent does not exist in map.Try Again with valid continent !");
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else {
                                    HashMap<String, Country> l_countriesMap = l_continents.get(l_CommandsArray[2].toLowerCase()).get_countries();
                                    l_countriesMap.clear();
                                    l_continents.remove(l_CommandsArray[2].toLowerCase());
                                    System.out.println("All countries from continent " + l_CommandsArray[2] + " are successfulLY removed .");
                                    System.out.println("Continent " + l_CommandsArray[2] + " is successfullY removed .");
                                    d_status = true;
                                }
                            } else {
                                try {
                                    throw new ValidationException();
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        }
                    }
                }
                break;
            }

            // command to edit neighbor in map
            case Constants.EDIT_NEIGHBOUR: {
                switch (l_CommandsArray[1]) {
                    case Constants.ADD: {
                        if (l_CommandsArray.length == 4) {
                            Country l_Country1 = d_GameMap.get_countries().get(l_CommandsArray[2].toLowerCase());
                            Country l_Country2 = d_GameMap.get_countries().get(l_CommandsArray[3].toLowerCase());

                            //handling null values
                            if (l_Country1 == null && l_Country2 == null) {
                                try {
                                    throw new ValidationException("Provided both mentioned countries does not exist to add in map.Try Again with valid names !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else if (l_Country1 == null || l_Country2 == null) {
                                try {
                                    throw new ValidationException("Provided one of the mentioned countries does not exist to add in map.Try Again with valid names !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                l_Country1.get_Neighbours().put(l_Country2.get_countryId().toLowerCase(), l_Country2);
                                l_Country2.get_Neighbours().put(l_Country1.get_countryId().toLowerCase(), l_Country1);
                                System.out.println("Neighbour " + l_Country2 + " is successfullY added .");
                                d_status = true;
                            }
                        } else {
                            try {
                                throw new ValidationException();
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                    case Constants.REMOVE: {
                        if (l_CommandsArray.length == 4) {
                            Country l_Country1 = d_GameMap.get_countries().get(l_CommandsArray[2].toLowerCase());
                            Country l_Country2 = d_GameMap.get_countries().get(l_CommandsArray[3].toLowerCase());

                            //handling null values
                            if (l_Country1 == null && l_Country2 == null) {
                                try {
                                    throw new ValidationException("Provided both mentioned countries does not exist to add in map.Try Again with valid names !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else if (l_Country1 == null || l_Country2 == null) {
                                try {
                                    throw new ValidationException("Provided one of the mentioned countries does not exist to add in map.Try Again with valid names !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else if (l_Country1.get_Neighbours().containsKey(l_Country2.get_countryId().toLowerCase()) && l_Country2.get_Neighbours().containsKey(l_Country1.get_countryId().toLowerCase())) {
                                l_Country1.get_Neighbours().remove(l_Country2.get_countryId().toLowerCase());
                                l_Country2.get_Neighbours().remove(l_Country1.get_countryId().toLowerCase());
                                System.out.println("Neighbour " + l_Country2 + " is successfullY removed .");
                                d_status = true;
                            } else {
                                try {
                                    throw new ValidationException("Provided mentioned countries are not neighbors of each other.Try Again with neighboring countries !");
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } else {
                            try {
                                throw new ValidationException();
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
            }

                // command to validate map
                case Constants.VALIDATE_MAP: {
                    MapValidator l_mapValidator=new MapValidator();
                    if (l_mapValidator.ValidateMapObject(d_GameMap)) {
                        System.out.println("Map Validation successful");
                        d_status=true;
                    } else {
                        System.out.println("Map Validation failed ! check the provided inputs again.");
                    }
                    break;
                }

                //list of commands for assist Player
                case Constants.HELP: {
                    System.out.println("Commands List for different operations:\n");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To edit map file  : editmap filename\n");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID\n");
                    System.out.println("To add or remove a country : editcountry -add countryID continentID -remove countryID\n");
                    System.out.println("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID\n");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To save map file : savemap filename\n");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("Additional map commands:\n");
                    System.out.println("To show the map: showmap\n");
                    System.out.println("To validate map: validatemap\n");
                    System.out.println("****************************************************************************************************************************");
                    break;
                }
                default:{
                    System.out.println("Add a Valid Command Please");
                }
            }
//        }
        return d_status;
    }


    /**
     * This method validates the given command
     *
     * @param p_InputList the command list from console
     * @return true if command is available in the commands list else false
     */
    public boolean validateUserInput(List<String> p_InputList) {
        if (!(p_InputList.isEmpty())) {
            String l_InputCommand = p_InputList.getFirst();
            int index= d_MAP_CLI_COMMANDS.indexOf(l_InputCommand.toLowerCase());
            return index != -1;
        }
        return false;
    }

    /**
     * Starts the game controller.
     *
     * @param currentPhase The current phase of the game.
     * @return The next phase of the game.
     * @throws Exception If an issue occurs during execution.
     */
    @Override
    public GamePhase start(GamePhase currentPhase) throws Exception {
        run();
        return currentPhase;
    }
}
