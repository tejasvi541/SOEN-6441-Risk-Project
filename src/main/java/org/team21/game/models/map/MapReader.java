package org.team21.game.models.map;


import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A utility class for reading data from map files and populating the game map.
 * Provides methods to read continents, countries, and their neighbors from a map file.
 * The map file format is expected to have sections for Continents and Territories.
 * Continents are defined with their names and corresponding control values separated by an equal sign.
 * Territories are defined with their names and corresponding continent names, followed by their neighbors.
 * Neighbors are listed after the country name, separated by spaces.
 * The MapReader class provides methods to parse these sections and populate the game map.
 *
 * @author Tejasvi
 */
public class MapReader {

    /**
     * The singleton instance of the game event logger.
     */
    private static GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Reads a map file and populates the provided game map with continents, countries, and their neighbors.
     *
     * @param p_GameMap   The game map object to populate.
     * @param p_FileName  The file name of the map to read.
     * @throws ValidationException Thrown if validation fails during the map reading process.
     */
    public static void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Logger.clear();
        d_Logger.log("Reading Map \n");

        try {
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
            readContinentsFromFile(p_GameMap, l_MapFileContents.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap, l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Reads continent information from the given list and adds continents to the game map.
     *
     * @param p_GameMap        The game map object to populate with continents.
     * @param p_ContinentArray The list of continent information from the map file.
     * @throws ValidationException Thrown if validation fails during continent reading.
     */
    public static void readContinentsFromFile(GameMap p_GameMap, List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split("=");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * Reads country information from the given list and adds countries to the game map.
     *
     * @param p_GameMap      The game map object to populate with countries.
     * @param p_CountryArray The list of country information from the map file.
     * @return A map containing country names as keys and lists of neighboring country names as values.
     * @throws ValidationException Thrown if validation fails during country reading.
     */
    public static Map<String, List<String>> readCountriesFromFile(GameMap p_GameMap, List<String> p_CountryArray) throws ValidationException {
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
     * Adds neighboring countries to the game map based on the provided map of country neighbors.
     *
     * @param p_GameMap      The game map object to populate with neighboring country information.
     * @param p_NeighborList The map containing country names as keys and lists of neighboring country names as values.
     * @throws ValidationException Thrown if validation fails during neighbor addition.
     */
    public static void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}
