package org.team25.Utils;
import org.team25.Models.Continent;
import org.team25.Models.Country;
import org.team25.Models.GMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class MapLoader {
    public static int d_mapIndex;
    private GMap d_gMap;
    private HashMap<Integer, Country> d_countriesList;

    /**
     * The main function of this file asks for the map name to load and calls a function to load it
     * @param args name of the map
     */
    public static void main(String args[]){
        Scanner l_sc = new Scanner(System.in);
        System.out.println("Enter Map file name:");
        String l_mapName = l_sc.nextLine();
        MapLoader l_loadedMap = new MapLoader();
        l_loadedMap.readMap(l_mapName);
        l_sc.close();
    }


    /**
     * This function read the map file and process the individual section of the map
     * @param p_mapName Name of the map to be loaded
     * @return The Populated Map object
     */
    public GMap readMap(String p_mapName){
        d_gMap = new GMap(p_mapName);
        d_countriesList = new HashMap<Integer, Country>();

        try{
            BufferedReader l_fileReader = new BufferedReader(new FileReader(p_mapName));
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
        return d_gMap;
    }

    /**
     *
     * @param p_fileReader
     * @return
     */
    private BufferedReader processCountries(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(!((l_lineString = p_fileReader.readLine()).isEmpty())){
                String[] l_countryString = l_lineString.split("\\s+");
                Country l_newCountry = new Country(l_countryString[0], l_countryString[1], l_countryString[2], l_countryString[3], l_countryString[4], d_gMap);
                try{
                    if(l_newCountry.get_parentContinent()==null){
                        System.out.println("Not Valid Map File");
                        System.exit(-1);
                    }
                    addToContinentMap(l_newCountry);
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
     *
     * @param p_fileReader
     * @return
     */
    private BufferedReader processContinents(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(!((l_lineString=p_fileReader.readLine()).isEmpty())){ //Assign line if not empty, compound statement
                String[] l_continentString = l_lineString.split("\\s+");

                if(Integer.parseInt(l_continentString[1])>=0) {
                    d_gMap.get_continents().put(l_continentString[0].toLowerCase(), new Continent(l_continentString[0], l_continentString[1]));
                    d_mapIndex++;
                }else{
                    System.out.println("Not Valid Map File");
                    System.exit(-1);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        d_mapIndex=1;
        return p_fileReader;
    }

    /**
     *
     * @param p_fileReader
     * @return
     */
    private BufferedReader processBorders(BufferedReader p_fileReader){
        String l_lineString;
        try{
            while(!((l_lineString=p_fileReader.readLine()).isEmpty())){ //Assign line if not empty, compound statement
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

    

}
