package org.team21.game.models.map;


import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.ValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Domination map for the game.
 * This class provides functionality to read, write, and manipulate Domination map files.
 *
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class DominationMap {
    /**
     * Logger Observable instance.
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();
    /**
     * Current line being processed while reading the map file.
     */
    private String d_CurrentLine;
    /**
     * Buffered reader for file reading.
     */
    private BufferedReader d_Buffer;
    /**
     * List of continents read from the map file.
     */
    private List<String> d_Continents = new ArrayList<>();
    /**
     * HashMap containing country names and their corresponding continents.
     */
    private HashMap<String, String> d_Country = new HashMap<>();

    /**
     * Reads the map file and populates the game map with its contents.
     *
     * @param p_GameMap  The game map instance to populate.
     * @param p_FileName The name of the map file to read.
     * @throws ValidationException If validation fails while reading the map file.
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Logger.clear();
        d_Logger.log("Domination map is loaded \n");
        try {
            p_GameMap.flushGameMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();

            d_Buffer = new BufferedReader(l_FileReader);
            while ((d_CurrentLine = d_Buffer.readLine()) != null) {
                if (d_CurrentLine.contains("[continents]")) {
                    readContinentsFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[countries]")) {
                    readCountriesFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[borders]")) {
                    addNeighborsFromFile(p_GameMap);
                }

            }
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Reads continents from the map file and adds them to the game map.
     *
     * @param p_GameMap The game map instance.
     * @throws ValidationException If validation fails while reading continents from the map file.
     * @throws IOException If an I/O error occurs while reading the map file.
     */
    public void readContinentsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_ContinentDetails = d_CurrentLine.split(" ");
            p_GameMap.addContinent(l_ContinentDetails[0], l_ContinentDetails[1]);
            d_Continents.add(l_ContinentDetails[0]);
        }
    }

    /**
     * Reads countries from the map file and adds them to the game map.
     *
     * @param p_GameMap The game map instance.
     * @throws ValidationException If validation fails while reading countries from the map file.
     * @throws IOException If an I/O error occurs while reading the map file.
     */

    public void readCountriesFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_CountryDetails = d_CurrentLine.split(" ");
            p_GameMap.addCountry(l_CountryDetails[1], d_Continents.get((Integer.parseInt(l_CountryDetails[2]) - 1)));
            d_Country.put(l_CountryDetails[0], l_CountryDetails[1]);
        }
    }

    /**
     * Adds neighboring countries to the game map.
     *
     * @param p_GameMap The game map instance.
     * @throws ValidationException If validation fails while adding neighbors from the map file.
     * @throws IOException If an I/O error occurs while reading the map file.
     */
    public void addNeighborsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_NeighbourDetails = d_CurrentLine.split(" ");
            for (int i = 1; i < l_NeighbourDetails.length; i++) {
                p_GameMap.addNeighbor(d_Country.get(l_NeighbourDetails[0]), d_Country.get(l_NeighbourDetails[i]));
            }
        }
    }

    /**
     * Saves the game map as a map file.
     *
     * @param p_GameMap  The game map instance to save.
     * @param p_FileName The name of the map file to save.
     * @return true if the map file is saved successfully, false otherwise.
     * @throws IOException If an I/O error occurs while saving the map file.
     */
    public boolean saveMap(GameMap p_GameMap, String p_FileName) throws IOException {
        String l_Message = " ";
        l_Message = "yura.net Risk 1.0.9.2";
        String l_CurrentPath = System.getProperty("user.dir") + File.separator + "maps" + File.separator;
        String l_MapPath = l_CurrentPath + p_FileName + ".map";
        BufferedWriter bwFile = new BufferedWriter(new FileWriter(l_MapPath));
        String d_Content = ";Map ";
        d_Content += (p_FileName + ".map" + "");
        d_Content += ("\rname " + p_FileName + ".map" + " Map");
        d_Content += ("\r" + l_Message + "\r");
        d_Content += ("\r\n[continents]\r\n");
        HashMap<Integer, String> l_ContinentMap = createContinentList(p_GameMap);
        for (Continent continent : p_GameMap.getContinents().values()) {
            d_Content += (continent.getName() + " " + continent.getAwardArmies() + " 00000\r\n");
        }
        d_Content += ("\r\n[countries]\r\n");
        String l_Borders = "";
        HashMap<Integer, String> l_CountryMap = createCountryList(p_GameMap);
        for (Map.Entry<Integer, String> l_Country : l_CountryMap.entrySet()) {
            for(Map.Entry<Integer, String> l_Continent : l_ContinentMap.entrySet()) {
                if(l_Continent.getValue().equals(p_GameMap.getCountry(l_Country.getValue()).getContinent())) {
                    d_Content += (l_Country.getKey() + " " + l_Country.getValue() + " " + l_Continent.getKey() + "\r\n");
                    break;
                }
            }
            l_Borders += (l_Country.getKey() + "");
            for (Country l_Neighbor : p_GameMap.getCountry(l_Country.getValue()).getNeighbors()) {
                for(Map.Entry<Integer, String> l_CountryList : l_CountryMap.entrySet()){
                    if(l_Neighbor.getName().equals(l_CountryList.getValue())){
                        l_Borders += (" " + l_CountryList.getKey());
                    }
                }
            }
            l_Borders += ("\r\n");
        }
        d_Content += ("\r\n[borders]\r\n" + l_Borders);
        bwFile.write(d_Content);
        bwFile.close();
        System.out.println("Map file saved as: " + p_FileName + ".map");
        return true;
    }

    /**
     * Creates a HashMap of countries with their index.
     *
     * @param p_GameMap The game map instance.
     * @return A HashMap containing country indices and names.
     */
    public HashMap<Integer, String> createCountryList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int l_Counter = 1;
        for(Country l_Country : p_GameMap.getCountries().values()){
            l_CountryMap.put(l_Counter++, l_Country.getName());
        }
        return l_CountryMap;
    }

    /**
     * Creates a HashMap of continents with their index.
     *
     * @param p_GameMap The game map instance.
     * @return A HashMap containing continent indices and names.
     */
    public HashMap<Integer, String> createContinentList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int l_Counter = 1;
        for(Continent l_Continent : p_GameMap.getContinents().values()){
            l_CountryMap.put(l_Counter++, l_Continent.getName());
        }
        return l_CountryMap;
    }


}
