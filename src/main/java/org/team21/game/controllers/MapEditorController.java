package org.team21.game.controllers;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.team21.game.interfaces.main_engine.GameFlowManager;
import org.team21.game.interfaces.maps.MapEditor;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.game_play.GamePhase;
import org.team21.game.utils.Constants;
import org.team21.game.utils.validation.MapValidator;
import org.team21.game.utils.validation.ValidationException;



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
    private final List<String> d_MAP_CLI_COMMANDS = Arrays.asList(Constants.SHOW_MAP,Constants.EDIT_MAP,Constants.EDIT_CONTINENT, Constants.EDIT_COUNTRY, Constants.EDIT_NEIGHBOUR,Constants.VALIDATE_MAP,Constants.SAVE_MAP);

    /**
     * A data member that will log the data for the class
     */
    private static final Logger d_Log = LogManager.getLogger(MapEditorController.class);

    /**
     * A data member to set continue of loop
     */
    private static boolean d_execute =true;

    /**
     * A data member to set status of editing phase
     */
    boolean d_editStatus =false;

    /**
     * This is the default constructor
     */
    public MapEditorController() {
        this.d_GameMap = GameMap.getInstance();
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

    static{
        System.out.println("****************************** Welcome to MAP EDITOR PHASE *********************************");
        System.out.println("Type the required option for taking action on map:" + "\n" );
        System.out.println("1. Type Help :to get list of commands for different actions  " + "\n" );
        System.out.println("2. Type Exit : to exit from map editor phase and continue playing game"+ "\n");
        System.out.println("*******************************************************************************************************");
    }

    public GamePhase run(GamePhase p_CurrentPhase)  {
        List<String> l_ListStream;
        while (d_execute) {
            l_ListStream=fetchUserInput();

            // validating the input stream passed by user
            boolean l_valid=validateUserInput(l_ListStream);
            if (!l_valid) {
                if (!(l_ListStream.getFirst().startsWith("help")) && !(l_ListStream.getFirst().startsWith("exit"))) {
                    System.out.println("Invalid Input,Try Again !");
                    run(p_CurrentPhase);
                }
                else if(l_ListStream.getFirst().startsWith("exit")) {
                    d_execute =false;
                    return p_CurrentPhase.nextState(GamePhase.StartUp);
                }
                else if(l_ListStream.getFirst().startsWith("help")){
                    //list of commands to assist user
                    System.out.println("Commands List for different operations:");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To edit map file  : editmap filename");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID {example: editcontinent -add Asia or editcontinent -remove Asia}");
                    System.out.println("To add or remove a country : editcountry -add countryID continentID -remove countryID {example: editcountry -add India Asia or editcountry -remove India }");
                    System.out.println("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID {example: editneighbor -add India Pakistan or editneighbor -remove India Pakistan}");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("To save map file : savemap filename {example: savemap Canada}");
                    System.out.println("****************************************************************************************************************************");
                    System.out.println("Additional map commands:");
                    System.out.println("To show the map: showmap {example: showmap}");
                    System.out.println("To validate map: validatemap {example: validatemap}");
                    System.out.println("****************************************************************************************************************************\n");
                }
            }
            else{
                if(l_ListStream.get(0).equals(Constants.SHOW_MAP)|| l_ListStream.get(0).equals(Constants.VALIDATE_MAP)||l_ListStream.get(0).equals(Constants.SAVE_MAP)||l_ListStream.get(0).equals(Constants.EDIT_MAP))
                    action(l_ListStream,p_CurrentPhase);
                else if(l_ListStream.get(1).split(" ").length>=2){
                    String l_commandOperation=l_ListStream.get(1).split(" ")[0];
                    if(l_commandOperation.equals(Constants.ADD)|| l_commandOperation.equals(Constants.REMOVE))
                        action(l_ListStream,p_CurrentPhase);
                }
                else
                    System.out.println("Invalid Command,Check Again!");

            }
        }
        return p_CurrentPhase.nextState(GamePhase.StartUp);
    }


    public GamePhase action(List<String> p_ListStream,GamePhase p_CurrentGamePhase) {
        for(int l_index=0;l_index<p_ListStream.size();l_index++) {
            String[] l_CommandsArray = p_ListStream.get(l_index).split(" ");
            switch (p_ListStream.getFirst().toLowerCase()) {

                // command to showcase map
                case Constants.SHOW_MAP: {
                    ShowMapController l_showMapController=new ShowMapController(d_GameMap);
                    l_showMapController.show();
                    break;
                }

                // command to editmap
                case Constants.EDIT_MAP: {
                    if (!Objects.equals(l_CommandsArray[0], "editmap")) {
                        if(new MapLoaderController().readMap(l_CommandsArray[0]).getMapName().toLowerCase() == l_CommandsArray[0].toLowerCase()){
                            this.d_GameMap = GameMap.getInstance();
                        }else {
                            this.d_GameMap = new GameMap();
                            System.out.println("No Map with the given Name Found so intialising a new one");
                        }
                        d_editStatus =true;
                        break;
                    }
                    break;
                }


                //command to edit country in map
                case Constants.EDIT_COUNTRY: {
                    switch (l_CommandsArray[0]) {
                        case Constants.ADD: {
                            if (l_CommandsArray.length == 3) {
                                HashMap<String, Country> l_countries=d_GameMap.getCountries();
                                if (l_countries.containsKey(l_CommandsArray[1].toLowerCase())) {
                                    try {
                                        throw new ValidationException("Provided country already exist in a map.Try Again with different country !");
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else{
                                    Country l_Country = new Country(l_CommandsArray[1], l_CommandsArray[2]);
                                    d_GameMap.getContinents().get(l_CommandsArray[2].toLowerCase()).getCountries().put(l_CommandsArray[1].toLowerCase(), l_Country);
                                    d_GameMap.getCountries().put(l_CommandsArray[1].toLowerCase(), l_Country);
                                    System.out.println("Country "+l_CommandsArray[1]+" is successfullY added .");
                                    d_editStatus =true;
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
                            if (l_CommandsArray.length == 2) {

                                Country l_Country = d_GameMap.getCountries().get(l_CommandsArray[1].toLowerCase());

                                //handling null values
                                if (l_Country==null) {
                                    try {
                                        throw new ValidationException("Provided country does not exist.Try Again with valid country ! " );
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else {
                                    Collection<Country> l_values=l_Country.get_Neighbours().values();
                                    Country[] l_neighbour= l_values.toArray(new Country[l_values.size()]);
                                    for(int l_position=0;l_position<l_neighbour.length;l_position++) {
                                       if(l_neighbour[l_position].get_Neighbours().containsKey(l_Country.get_countryId().toLowerCase()) && l_Country.get_Neighbours().containsKey(l_neighbour[l_position].get_countryId().toLowerCase())) {
                                           l_neighbour[l_position].get_Neighbours().remove(l_Country.get_countryId().toLowerCase());
                                           l_Country.get_Neighbours().remove(l_neighbour[l_position].get_countryId().toLowerCase());
                                    }}
                                    d_GameMap.getCountries().remove(l_CommandsArray[1].toLowerCase());
                                    d_GameMap.getContinents().get(l_Country.get_parentContinent().toLowerCase()).getCountries().remove(l_CommandsArray[1].toLowerCase());


                                    System.out.println( "Country " + l_CommandsArray[1] + " is successfullY removed .");
                                    d_editStatus =true;
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
                        switch (l_CommandsArray[0]) {
                            case Constants.ADD: {
                                HashMap<String, Continent> l_continents = d_GameMap.getContinents();
                                if (l_CommandsArray.length == 3) {
                                    if( l_CommandsArray[1]!= null){
                                        if (l_continents.containsKey(l_CommandsArray[1].toLowerCase())) {
                                            try {
                                                throw new ValidationException("Provided continent already exists in map,Try again with different continent !");
                                            } catch (ValidationException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        else{
                                            Continent l_Continent = new Continent(l_CommandsArray[1],l_CommandsArray[2], d_GameMap.getContinents().size()+1);
                                            d_GameMap.getContinents().put(l_CommandsArray[1].toLowerCase(), l_Continent);
                                            System.out.println("Continent "+l_CommandsArray[1]+" is successfullY added .");
                                            d_editStatus =true;
                                        }
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
                            case Constants.REMOVE: {
                                if (l_CommandsArray.length == 2) {
                                    HashMap<String, Continent> l_continents = d_GameMap.getContinents();

                                    if (!l_continents.containsKey(l_CommandsArray[1].toLowerCase())) {
                                        try {
                                            throw new ValidationException("Provided Continent does not exist in map.Try Again with valid continent !");
                                        } catch (ValidationException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    else{
                                        HashMap<String,Country> l_countriesMap=l_continents.get(l_CommandsArray[1].toLowerCase()).getCountries();
                                        l_countriesMap.clear();
                                        l_continents.remove(l_CommandsArray[1].toLowerCase());
                                        System.out.println("All countries from continent "+l_CommandsArray[1]+" are successfulLY removed .");
                                        System.out.println("Continent "+l_CommandsArray[1]+" is successfullY removed .");
                                        d_editStatus =true;
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
                    switch (l_CommandsArray[0]) {
                        case Constants.ADD: {
                            if (l_CommandsArray.length == 3) {
                                Country l_Country1 = d_GameMap.getCountries().get(l_CommandsArray[1].toLowerCase());
                                Country l_Country2 = d_GameMap.getCountries().get(l_CommandsArray[2].toLowerCase());

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
                                else {
                                    l_Country1.get_Neighbours().put(l_Country2.get_countryId().toLowerCase(), l_Country2);
                                    l_Country2.get_Neighbours().put(l_Country1.get_countryId().toLowerCase(), l_Country1);
                                    System.out.println( l_Country1.get_countryId()+" and "+ l_Country2.get_countryId() + " are neighbors of each other now .");
                                    d_editStatus =true;
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
                                Country l_Country1 = d_GameMap.getCountries().get(l_CommandsArray[1].toLowerCase());
                                Country l_Country2 = d_GameMap.getCountries().get(l_CommandsArray[2].toLowerCase());

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
                                else if (l_Country1.get_Neighbours().containsKey(l_Country2.get_countryId().toLowerCase()) && l_Country2.get_Neighbours().containsKey(l_Country1.get_countryId().toLowerCase())) {
                                    l_Country1.get_Neighbours().remove(l_Country2.get_countryId().toLowerCase());
                                    l_Country2.get_Neighbours().remove(l_Country1.get_countryId().toLowerCase());
                                    System.out.println("Neighbour "+l_Country2.get_countryId()+" is successfullY removed .");
                                    d_editStatus =true;
                                }
                                else{
                                    try {
                                        throw new ValidationException("Provided mentioned countries are not neighbors of each other.Try Again with neighboring countries !");
                                    } catch (ValidationException e) {
                                        System.out.println(e.getMessage());
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
                case Constants.VALIDATE_MAP: {
                    MapValidator l_mapValidator=new MapValidator();
                    if (l_mapValidator.ValidateMapObject(d_GameMap)) {
                        System.out.println("Map Validation successful");
                        d_editStatus =true;
                    } else {
                        System.out.println("Map Validation failed ! check the provided inputs again.");
                    }
                    break;
                }

                // command to save map
                case Constants.SAVE_MAP: {
                    MapValidator l_mapValidator=new MapValidator();
                    if(!Objects.equals(l_CommandsArray[0], "savemap")){
                        if (l_mapValidator.ValidateMapObject(d_GameMap)) {
                            new SaveMapController(d_GameMap, l_CommandsArray[0]).saveMap();
                            System.out.println("Map Validation successful,Saving the map");
                            d_editStatus =true;
                            break;
                        } else {
                            System.out.println("Map Validation failed ! check the provided inputs again.");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return p_CurrentGamePhase.nextState(GamePhase.StartUp);
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
            int l_index= d_MAP_CLI_COMMANDS.indexOf(l_InputCommand);
            if(l_index!=-1)
                return true;
        }
        return false;
    }

    public List<String> fetchUserInput()
    {
        String l_UserInput = d_sc.nextLine();
        List<String> l_list = new ArrayList<>();
        if (!(l_UserInput.contains("-"))){
            //adding values that are not for editing
            l_list.addAll(Arrays.asList(l_UserInput.toLowerCase().split(" ")));
        }
        else {
            //adding values that are for editing
            String[] l_Strings=l_UserInput.split(" ",2);
            for(int l_pos=0;l_pos<l_Strings.length;l_pos++){
                if (!l_Strings[l_pos].isEmpty()) {
                    l_list.add(l_Strings[l_pos].trim());
                }
            }
        }
        return l_list;
    }

    /**
     * Starts the game controller.
     *
     * @param p_CurrentPhase The current phase of the game.
     * @return The next phase of the game.
     */
    @Override
    public GamePhase start(GamePhase p_CurrentPhase) {
        return run(p_CurrentPhase);
    }

}
