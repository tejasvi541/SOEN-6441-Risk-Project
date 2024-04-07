package org.team21.game.utils.adapter;

import org.team21.game.models.map.Continent;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.ValidationException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Adaptee class is responsible for reading and writing map files in the Conquest format.
 * It provides methods to read map data from files, parse the contents, and load them into the game map.
 * Additionally, it offers functionality to save the game map data into a file in Conquest format.
 * This class serves as an intermediary between the Conquest map file format and the game map data structure.
 * It ensures that map files are properly processed and loaded into the game map, and vice versa.
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public class Adaptee {
    /**
     * Logger Observable instance
     */
    private GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Creates a space-separated string containing the names of neighboring countries.
     *
     * @param p_Neighbors The set of neighboring countries
     * @return The string containing neighboring country names
     */
    public static String createANeighborList(Set<Country> p_Neighbors) {
        String l_Result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_Result += l_Neighbor.getName() + " ";
        }
        return l_Result.length() > 0 ? l_Result.substring(0, l_Result.length() - 1) : "";
    }

    /**
     * Reads map data from the given file in Conquest format and loads it into the game map.
     *
     * @param p_GameMap  The game map object to load the map data into
     * @param p_FileName The name of the map file to read
     * @throws ValidationException if file reading or map validation fails
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Logger.clear();
        d_Logger.log("Conquest map is loaded \n");

        try {
            // Read and parse the map file contents
            p_GameMap.flushGameMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_Read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }

            // Process and load continents and countries
            readContinentsFromFile(p_GameMap, l_MapFileContents.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap, l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Reads continent data from the file and adds continents to the game map.
     *
     * @param p_GameMap        The game map object to add continents to
     * @param p_ContinentArray The list of continent data read from the file
     * @throws ValidationException if continent data is invalid
     */
    public void readContinentsFromFile(GameMap p_GameMap, List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split("=");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * Reads country data from the file and adds countries to the game map.
     *
     * @param p_GameMap      The game map object to add countries to
     * @param p_CountryArray The list of country data read from the file
     * @return A map of country names to lists of neighboring countries
     * @throws ValidationException if country data is invalid
     */
    public Map<String, List<String>> readCountriesFromFile(GameMap p_GameMap, List<String> p_CountryArray) throws ValidationException {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                p_GameMap.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
        return l_CountryNeighbors;
    }

    /**
     * Adds neighboring countries to the game map based on the provided data.
     *
     * @param p_GameMap      The game map object to add neighboring countries to
     * @param p_NeighborList A map of country names to lists of neighboring country names
     * @throws ValidationException if neighbor data is invalid
     */
    public void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }

    /**
     * Save map into file as continent and country
     *
     * @param p_FileName name of file
     * @param p_Map      parameter o GameMap class
     * @return boolean true if written
     */


    public boolean saveMap(GameMap p_Map, String p_FileName) {
        String l_MapData = "[Map]\nauthor=Anonymous\n[Continents]\\n";
        for (Continent l_Continent : p_Map.getContinents().values()) {
            l_MapData += l_Continent.getName() + "=" + l_Continent.getAwardArmies();
            l_MapData += "\n";
        }

        l_MapData += "[Territories]\n";
        for (Continent l_Continent : p_Map.getContinents().values()) {
            for (Country l_Country : p_Map.getCountries().values()) {
                l_MapData += l_Country.getName() + " " + l_Country.getContinent() + " " + createANeighborList(l_Country.getNeighbors()) + "\n";
            }
            PrintWriter l_WriteData = null;
            try {
                final String PATH = "maps/";
                l_WriteData = new PrintWriter(PATH + p_FileName + ".map");
                l_WriteData.println(l_MapData);
                return true;
            } catch (Exception p_Exception) {
                return false;
            } finally {
                l_WriteData.close();
            }
        }
        return true;

    }


}
