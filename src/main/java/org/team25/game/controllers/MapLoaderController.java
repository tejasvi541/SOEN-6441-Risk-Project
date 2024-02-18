package org.team25.game.controllers;
import org.team25.game.models.map.Continent;
import org.team25.game.models.map.Country;
import org.team25.game.models.map.GameMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the main logic of the Map Loading here in this Map Loader class to load our maps and store in memory
 * to play or edit.
 * @author Tejasvi
 * @version 1.0.0
 */
public class MapLoaderController {
    public static int d_mapContinentIndex =1; // Tracking Continent Index
    private GameMap d_gameMap;
    private HashMap<Integer, Country> d_countriesList;

   public MapLoaderController(){
        d_gameMap = GameMap.getInstance();
    }
    /**
     * The main function of this file asks for the map name to load and calls a function to load it
     *
     */
    public static void init(){
        Scanner l_sc = new Scanner(System.in);
        System.out.println("Enter Map file name:");
        String l_mapName = l_sc.nextLine();
        MapLoaderController l_loadedMap = new MapLoaderController();
        l_loadedMap.readMap(l_mapName);
        l_sc.close();
    }


    /**
     * This function read the map file and process the individual section of the map
     * @param p_mapName Name of the map to be loaded
     * @return The Populated Map object
     */
    public GameMap readMap(String p_mapName){
        d_countriesList = new HashMap<Integer, Country>();

        try{
            BufferedReader l_fileReader = new BufferedReader(new FileReader("src/main/resources/maps/"+p_mapName+".map"));
            String l_lineString;
            while((l_lineString=l_fileReader.readLine())!=null){
                switch (l_lineString){
                    case "[continents]":
                        l_fileReader = processContinents(l_fileReader);
                        break;
                    case "[countries]":
                        l_fileReader = processCountries(l_fileReader);
                        break;
                    case "[borders]":
                        l_fileReader = processBorders(l_fileReader);
                        break;
                }
            }
            l_fileReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("The said file is not found, please try again");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Input/Output Problem");
            System.out.println(e.getMessage());
        }
        return d_gameMap;
    }

    /**
     * This is the main function to read and process the countries from the map file successfully, it creates a country object for
     * every new country and put them in the memory
     * @param p_fileReader Ongoing Buffer Reader
     * @return it returns the ongoing buffer reader
     */
    private BufferedReader processCountries(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(!((l_lineString = p_fileReader.readLine()).isEmpty())){
                String[] l_countryString = l_lineString.split("\\s+");
                Country l_newCountry = new Country(l_countryString[0], l_countryString[1], l_countryString[2], l_countryString[3], l_countryString[4], d_gameMap);
                try{
                    if(l_newCountry.get_parentContinent()==null){
                        System.out.println("Not Valid Map File");
                            System.exit(-1);
                    }
                    addCountryToContinentMap(l_newCountry);
                    d_countriesList.put(l_newCountry.get_countryFileIndex(), l_newCountry);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return p_fileReader;
    }

    /**
     * This function is to process the Continents and put them in Map object with desired index
     * @param p_fileReader Ongoing Buffer Reader
     * @return Buffer Reader after the Continents to read left over file
     */
    private BufferedReader processContinents(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(!((l_lineString=p_fileReader.readLine()).isEmpty())){ //Assign line if not empty, compound statement
                String[] l_continentString = l_lineString.split("\\s+");

                if(Integer.parseInt(l_continentString[1])>=0) {
                    d_gameMap.get_continents().put(l_continentString[0].toLowerCase(), new Continent(l_continentString[0], l_continentString[1], d_mapContinentIndex));
                    d_mapContinentIndex++;
                }else{
                    System.out.println("Not Valid Map File");
                    System.exit(-1);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        d_mapContinentIndex =1;
        return p_fileReader;
    }

    /**
     * This function read the adjacency list and put neighbours of the countries in the desired data structure
     * @param p_fileReader Ongoing Buffer Reader
     * @return Buffer Reader after the Borders which is basically null at thins point
     */
    private BufferedReader processBorders(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(((l_lineString=p_fileReader.readLine()) !=null)){ //Assign line if not empty, compound statement
                String[] l_borderString = l_lineString.split("\\s+");
                Country l_tempCountry= new Country();
                l_tempCountry = d_countriesList.get(Integer.parseInt(l_borderString[0]));
                for(int l_neighbour=1;l_neighbour<l_borderString.length;l_neighbour++){
                    addNeighbour(l_tempCountry, l_borderString[l_neighbour]);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return p_fileReader;
    }

    /**
     * This function adds the neighbours to the country using the adjacency list
     * This is the helper function to read borders
     * @param p_tempCountry Country to which neighbour is to be added
     * @param p_borderIndex Index of the country to be added
     */
    private void addNeighbour(Country p_tempCountry, String p_borderIndex){
        int l_borderIndex = Integer.parseInt(p_borderIndex);
        Country l_neighbourCountry = new Country();
        try{
            l_neighbourCountry = d_countriesList.get(l_borderIndex);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(!p_tempCountry.get_Neighbours().containsKey(l_neighbourCountry.get_countryId().toLowerCase())){
            p_tempCountry.get_Neighbours().put(l_neighbourCountry.get_countryId().toLowerCase(), l_neighbourCountry);
        }
    }

    /**
     * This adds a country to the Continent
     * @param p_newCountry  Country Name to be added
     */
    private void addCountryToContinentMap(Country p_newCountry){
        Continent tempContinent = d_gameMap.get_continents().get(p_newCountry.get_parentContinent().toLowerCase());
        tempContinent.get_countries().put(p_newCountry.get_countryId().toLowerCase(), p_newCountry);
        d_gameMap.get_countries().put(p_newCountry.get_countryId().toLowerCase(), p_newCountry);
    }


}
