package org.team21.game.controllers;
import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.team21.game.utils.Constants.MAP_FILE_DIRECTORY;

/**
 * This is the main logic of the Map Loading here in this Map Loader class to load our maps and store in memory
 * to play or edit.
 * @author Tejasvi
 * @version 1.0.0
 */
public class MapLoaderController {
    /**
     * Value 1 initialized to d_mapContinentIndex.
     */
    public static int d_MapContinentIndex =1; // Tracking Continent Index
    /**
     * The d_GameMap is game map.
     */
    private GameMap d_GameMap;
    /**
     * Created a Hashmap name d_countriesList.
     */
    private HashMap<Integer, Country> d_CountriesList;

    /**
     * Logger Initialisation
     */
    GameEventLogger logger = new GameEventLogger();

    /**
     * It will initialise the MapLoaderController
     */
    public MapLoaderController(){
        d_GameMap = GameMap.getInstance();
    }
    /**
     * The main function of this file asks for the map name to load and calls a function to load it
     *
     */
    public static void init(){
        Scanner l_Sc = new Scanner(System.in);
        System.out.println("Enter Map file name:");
        String l_MapName = l_Sc.nextLine();
        MapLoaderController l_LoadedMap = new MapLoaderController();
        l_LoadedMap.readMap(l_MapName);
        l_Sc.close();
    }


    /**
     * This function read the map file and process the individual section of the map
     * @param p_MapName Name of the map to be loaded
     * @return The Populated Map object
     */
    public GameMap readMap(String p_MapName){
        d_CountriesList = new HashMap<>();

        try{
            BufferedReader l_FileReader = new BufferedReader(new FileReader(MAP_FILE_DIRECTORY+p_MapName+".map"));
            String l_LineString;
            while((l_LineString=l_FileReader.readLine())!=null){
                switch (l_LineString) {
                    case "[continents]" -> processContinents(l_FileReader);
                    case "[countries]" -> processCountries(l_FileReader);
                    case "[borders]" -> processBorders(l_FileReader);
                    default -> {
                    }
                }
            }
            l_FileReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("The said file is not found.");
            logger.logEvent("The Files is not found");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Input/Output Problem");
            System.out.println(e.getMessage());
        }
        d_GameMap.setMapName(p_MapName);
        if(d_GameMap.getContinents().isEmpty()){
            System.out.println("No Such Map Exists So Creating a New One");
            logger.logEvent("No Such Map Exists So Creating a New One");

        }else{
            System.out.println("\n*********Your Map " + d_GameMap.getMapName() + " is Loaded*********");
            logger.logEvent("\n*********Your Map " + d_GameMap.getMapName() + " is Loaded*********");
        }
        return d_GameMap;
    }

    /**
     * This is the main function to read and process the countries from the map file successfully, it creates a country object for
     * every new country and put them in the memory
     * @param p_FileReader Ongoing Buffer Reader
     * @return it returns the ongoing buffer reader
     */
    private BufferedReader processCountries(BufferedReader p_FileReader){
        String l_LineString;
        try{
            while(!((l_LineString = p_FileReader.readLine()).isEmpty())){
                String[] l_CountryString = l_LineString.split("\\s+");
                Country l_NewCountry = new Country(l_CountryString[0], l_CountryString[1], l_CountryString[2], l_CountryString[3], l_CountryString[4], d_GameMap);
                try{
                    if(l_NewCountry.getParentContinent()==null){
                        System.out.println("Not Valid Map File");
                            System.exit(-1);
                    }
                    addCountryToContinentMap(l_NewCountry);
                    d_CountriesList.put(l_NewCountry.getCountryFileIndex(), l_NewCountry);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return p_FileReader;
    }

    /**
     * This function is to process the Continents and put them in Map object with desired index
     * @param p_FileReader Ongoing Buffer Reader
     * @return Buffer Reader after the Continents to read left over file
     */
    private BufferedReader processContinents(BufferedReader p_FileReader){
        String l_LineString;
        try{
            while(!((l_LineString=p_FileReader.readLine()).isEmpty())){ //Assign line if not empty, compound statement
                String[] l_ContinentString = l_LineString.split("\\s+");

                if(Integer.parseInt(l_ContinentString[1])>=0) {
                    d_GameMap.getContinents().put(l_ContinentString[0].toLowerCase(), new Continent(l_ContinentString[0], l_ContinentString[1], d_MapContinentIndex));
                    d_MapContinentIndex++;
                }else{
                    System.out.println("Not Valid Map File");
                    System.exit(-1);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        d_MapContinentIndex =1;
        return p_FileReader;
    }

    /**
     * This function read the adjacency list and put neighbours of the countries in the desired data structure
     * @param p_FileReader Ongoing Buffer Reader
     * @return Buffer Reader after the Borders which is basically null at thins point
     */
    private BufferedReader processBorders(BufferedReader p_FileReader){
        String l_LineString;
        try{
            while(((l_LineString=p_FileReader.readLine()) !=null)){ //Assign line if not empty, compound statement
                String[] l_BorderString = l_LineString.split("\\s+");
                Country l_TempCountry;
                l_TempCountry = d_CountriesList.get(Integer.parseInt(l_BorderString[0]));
                for(int l_Neighbour=1;l_Neighbour<l_BorderString.length;l_Neighbour++){
                    addNeighbour(l_TempCountry, l_BorderString[l_Neighbour]);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return p_FileReader;
    }

    /**
     * This function adds the neighbours to the country using the adjacency list
     * This is the helper function to read borders
     * @param p_TempCountry Country to which neighbour is to be added
     * @param p_BorderIndex Index of the country to be added
     */
    private void addNeighbour(Country p_TempCountry, String p_BorderIndex){
        int l_BorderIndex = Integer.parseInt(p_BorderIndex);
        Country l_NeighbourCountry = new Country();
        try{
            l_NeighbourCountry = d_CountriesList.get(l_BorderIndex);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        try {
            if (!p_TempCountry.getNeighbours().containsKey(l_NeighbourCountry.getCountryId().toLowerCase())) {
                p_TempCountry.getNeighbours().put(l_NeighbourCountry.getCountryId().toLowerCase(), l_NeighbourCountry);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This adds a country to the Continent
     * @param p_NewCountry  Country Name to be added
     */
    private void addCountryToContinentMap(Country p_NewCountry){
        
        try {
            Continent l_TempContinent = d_GameMap.getContinents().get(p_NewCountry.getParentContinent().toLowerCase());
            l_TempContinent.getCountries().put(p_NewCountry.getCountryId().toLowerCase(), p_NewCountry);
            d_GameMap.getCountries().put(p_NewCountry.getCountryId().toLowerCase(), p_NewCountry);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
