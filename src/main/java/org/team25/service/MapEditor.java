package org.team25.service;

import java.io.IOException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.team25.Utils.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * This class is used to create map through different commands for different operations.
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class MapEditor {
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
    private final List<String> d_MAP_CLI_COMMANDS = Arrays.asList("showmap","editmap","editcontinent", "editcountry", "editneighbor", "validatemap","savemap");

    private static final Logger d_Logger = (Logger) LogManager.getLogger(MapEditor.class);
    Level d_logLevel=Level.parse("INFO");

    /**
     * This is the default constructor
     */
    public MapEditor() {
        this.d_GameMap = new GameMap();
    }

    /**
     * This is the parametrised constructor
     *
     * @param p_GameMap
     */
    public MapEditor(GameMap p_GameMap) {
        this.d_GameMap = p_GameMap;
    }

    /**
     * The start method of MapEditor phase that handles different modes for action on map
     * create,edit and save of map from commands.
     */

    public GamePhase run() throws IOException {
        d_Logger.log(Level.parse("INFO"),"/----------------------------- Welcome to MAP EDITOR PHASE --------------------------------/");
        List<String> l_ListStream;
        while (true) {
            d_Logger.log(d_logLevel,"Type the required option for taking action on map:" + "\n" );
            d_Logger.log(d_logLevel,"1. Type Help :to get list of commands for different actions  " + "\n" + "2. Type Exit : to exit from map editor phase and continue playing game");
            d_Logger.log(d_logLevel,"------------------------------------------------------------------------------------------------------");
            String l_UserInput = d_sc.nextLine();
            List<String> l_list = new ArrayList<>();
            if (!(l_UserInput.contains("-"))){
                //adding values that are not for editing
                l_list.addAll(Arrays.asList(l_UserInput.split("")));
                l_ListStream= l_list;
            }
            else {
                //adding values that are for editing
                for (String l_s : l_UserInput.split("-")) {
                    if (!l_s.isEmpty()) {
                        l_list.add(l_s.trim());
                    }
                }
                l_ListStream= l_list;
            }

            // validating the input stream passed by user
            boolean valid=validateUserInput(l_ListStream);

            if (!valid) {
                if (l_UserInput.startsWith("Help")) {
                    // if not available in list of commands ,trying to ask for help again
                    l_ListStream.add("Help");

                } else {
                    l_ListStream.addFirst("Exit");
                }
            }

            for(int l_index=0;l_index<l_ListStream.size();l_index++) {
                String[] l_CommandsArray = l_ListStream.get(l_index).split(" ");
                switch (l_ListStream.getFirst().toLowerCase()) {

                    // command to showcase map
                    case "showmap": {
                        //d_GameMap.showMap();
                        break;
                    }

                    // command to editmap
                    case "editmap": {
                        if (l_CommandsArray.length == 1) {
                            //MapLoader.readMapObject(d_GameMap);
                            d_GameMap.showMap();
                        }
                        break;
                    }


                    //command to edit country in map
                    case "editcountry": {
                        switch (l_CommandsArray[0]) {
                            case "add": {
                                if (l_CommandsArray.length == 3) {
                                    HashMap<String, Country> l_countries=d_GameMap.getCountries();
                                    if (l_countries.containsKey(l_CommandsArray[1])) {
                                        try {
                                            throw new ValidationException("Provided country already exist in a map.Try Again with different country !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    Continent l_continent=d_GameMap.getContinent(l_CommandsArray[2]);
                                    Set<Country> contriesSet=l_continent.getCountries();
                                    Country l_Country = new Country();
                                    l_Country.setName(l_CommandsArray[1]);
                                    l_Country.setContinent(l_CommandsArray[2]);
                                    l_countries.put(p_CountryName, l_Country);
                                    contriesSet.add(l_Country);
                                    d_Logger.log(d_logLevel,"Country "+l_CommandsArray[1]+" is successfullY added .");
                                } else {
                                    try {
                                        throw new ValidationException();
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case "remove": {
                                if (l_CommandsArray.length == 2) {

                                    //  d_GameMap.removeCountry(l_CommandsArray[1]);
                                    Country l_Country = d_GameMap.getCountry(l_CommandsArray[1]);

                                    //handling null values
                                    if (l_Country==null) {
                                        try {
                                            throw new ValidationException("Provided country does not exist.Try Again with valid country ! " );
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }

                                    //removing from countries data
                                    HashMap<String, Country> l_countries= d_GameMap.getCountries();
                                    l_countries.remove(l_Country.getName());

                                    //removing from Continent data
                                    Continent l_continent=d_GameMap.getContinent(l_Country.getContinent());
                                    Set<Country> l_countriesSet=l_continent.getCountries();
                                    l_countriesSet.remove(l_Country.getName());
                                    d_Logger.log(d_logLevel,"Country "+l_CommandsArray[1]+" is successfullY removed .");

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
                    case "edit_Continent": {
                        if (l_CommandsArray.length > 0) {
                            switch (l_CommandsArray[0]) {
                                case "add": {
                                    HashMap<String, Continent> continents = d_GameMap.getContinents();
                                    if (l_CommandsArray.length == 3) {
                                        if( l_CommandsArray[1]!= null){
                                            if (continents.containsKey(l_CommandsArray[1])) {
                                                try {
                                                    throw new ValidationException("Provided continent already exists in map,Try again with different continent !");
                                                } catch (ValidationException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                            Continent l_Continent = new Continent();
                                            l_Continent.setName(l_CommandsArray[1]);
                                            l_Continent.set_controlValue(Integer.parseInt(l_CommandsArray[2]));
                                            continents.put(l_CommandsArray[1], l_Continent);
                                            d_Logger.log(d_logLevel,"Continent "+l_CommandsArray[1]+" is successfullY added .");
                                        }
                                        else {
                                            try {
                                                throw new ValidationException("Provided continent does not exists .Try again with different continent !");
                                            } catch (ValidationException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }

                                    }else {
                                        try {
                                            throw new ValidationException();
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_CommandsArray.length == 2) {
                                        HashMap<String, Continent> l_continents = d_GameMap.getContinents();
                                        HashMap<String, Country> l_countries=d_GameMap.getCountries();

                                        if (!l_continents.containsKey(l_CommandsArray[1])) {
                                            try {
                                                throw new ValidationException("Provided Continent does not exist in map.Try Again with valid continent !");
                                            } catch (ValidationException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        Set<String> l_CountriesSet = l_continents.remove(l_CommandsArray[1])
                                                .getCountries()
                                                .stream().map(L_country->L_country.getName)
                                                .collect(Collectors.toSet());
                                        for (String l_CountryName : l_CountriesSet) {
                                            l_countries.remove(l_CountryName);
                                        }
                                        d_Logger.log(d_logLevel,"All countries from continent "+l_CommandsArray[1]+" are successfulLY removed .");
                                        d_Logger.log(d_logLevel,"Continent "+l_CommandsArray[1]+" is successfullY removed .");
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
                    case "editneighbor": {
                        switch (l_CommandsArray[0]) {
                            case "add": {
                                if (l_CommandsArray.length == 3) {
                                    Country l_Country1 = d_GameMap.getCountry(l_CommandsArray[1]);
                                    Country l_Country2 = d_GameMap.getCountry(l_CommandsArray[2]);

                                    //handling null values
                                    if (l_Country1==null && l_Country2==null) {
                                        try {
                                            throw new ValidationException("Provided both mentioned countries does not exist to add in map.Try Again with valid names !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    else if (l_Country1==null || l_Country2==null) {
                                        try {
                                            throw new ValidationException("Provided one of the mentioned countries does not exist to add in map.Try Again with valid names !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    else
                                        HashMap<String, Country> l_neighbours =l_Country1.getNeighbors();
                                    l_neighbours.add(l_Country2);
                                    d_Logger.log(d_logLevel,"Neighbour "+l_Country2+" is successfullY added .");

                                } else {
                                    try {
                                        throw new ValidationException();
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case "remove": {
                                if (l_CommandsArray.length == 3) {
                                    Country l_Country1 = d_GameMap.getCountry(l_CommandsArray[1]);
                                    Country l_Country2 = d_GameMap.getCountry(l_CommandsArray[2]);

                                    //handling null values
                                    if (l_Country1==null && l_Country2==null) {
                                        try {
                                            throw new ValidationException("Provided both mentioned countries does not exist to add in map.Try Again with valid names !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    else if (l_Country1==null || l_Country2==null) {
                                        try {
                                            throw new ValidationException("Provided one of the mentioned countries does not exist to add in map.Try Again with valid names !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    else if (l_Country1.getNeighbors().contains(l_Country2) || l_Country2.getNeighbors().contains(l_Country1)) {
                                        l_Country1.getNeighbors().remove(l_Country2);
                                        d_Logger.log(d_logLevel,"Neighbour "+l_Country2+" is successfullY removed .");
                                    }
                                    else{
                                        try {
                                            throw new ValidationException("Provided mentioned countries are not neighbors of each other.Try Again with neighboring countries !");
                                        } catch (ValidationException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                                else {
                                    try {
                                        throw new ValidationException();
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }

                    // command to validate map
                    case "validatemap": {
                        MapValidator l_mapValidator=new MapValidator();
                        if (l_mapValidator.ValidateMapObject(d_GameMap)) {
                            d_Logger.log(d_logLevel,"Map Validation successful");
                        } else {
                            d_Logger.log(d_logLevel,"Map Validation failed ! check the provided inputs again.");
                        }
                        break;
                    }

                    //command to save map
                    case "savemap": {
                        MapValidator l_mapValidator=new MapValidator();
                        if (l_mapValidator.ValidateMapObject(d_GameMap)){
                            if (l_CommandsArray.length == 1) {
                                d_GameMap.setName(l_CommandsArray[0]);
                                d_Logger.log(d_logLevel," Enter type for save the file? Type the number.");
                                d_Logger.log(d_logLevel,"1. Domination map \n2. Conquest map");
                                Scanner l_Scanner = new Scanner(System.in);
                                String l_Input = l_Scanner.nextLine();
                                if (l_Input.equals("1")){
                                    d_GameMap.saveMap(false);
                                    d_Logger.log(d_logLevel,"The loaded file is of the format Domination map");
                                }
                                else if (l_Input.equals("2")) {
                                    d_GameMap.saveMap(true);
                                    d_Logger.log(d_logLevel,"The loaded file is of the format Conquest map");
                                }
                                else
                                    d_Logger.log(d_logLevel,"Please enter the right value");
                            }}
                        break;
                    }

                    //To exit from map editing phase
                    case "exit": {
                        d_GameMap.clearGameMap();
                        d_GameMap.setGamePhase(GamePhase.StartUp);
                        return p_GamePhase.nextState(GamePhase.StartUp);
                    }
                    //list of commands for assist Player
                    default: {
                        d_Logger.log(d_logLevel,"Commands List for different operations:");
                        d_Logger.log(d_logLevel,"-----------------------------------------------------------------------------------------");
                        d_Logger.log(d_logLevel,"To edit map file  : editmap filename");
                        d_Logger.log(d_logLevel,"-----------------------------------------------------------------------------------------");
                        d_Logger.log(d_logLevel,"To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID");
                        d_Logger.log(d_logLevel,"To add or remove a country : editcountry -add countryID continentID -remove countryID");
                        d_Logger.log(d_logLevel,"To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                        d_Logger.log(d_logLevel,"-----------------------------------------------------------------------------------------");
                        d_Logger.log(d_logLevel,"To save map file : savemap filename");
                        d_Logger.log(d_logLevel,"-----------------------------------------------------------------------------------------");
                        d_Logger.log(d_logLevel,"Additional map commands:");
                        d_Logger.log(d_logLevel,"To show the map: showmap");
                        d_Logger.log(d_logLevel,"To validate map: validatemap");
                        d_Logger.log(d_logLevel,"-----------------------------------------------------------------------------------------");

                    }
                }
            }
        }
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
            return d_MAP_CLI_COMMANDS.contains(l_InputCommand.toLowerCase());
        }
        return false;
    }

}
