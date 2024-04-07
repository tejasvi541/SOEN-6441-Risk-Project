package org.team21.game.utils.adapter;

import org.team21.game.models.map.DominationMap;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.validation.ValidationException;

import java.io.Serializable;

/**
 * This class serves as an adapter to implement the Adapter Pattern for converting map files
 * between different map formats, specifically between Domination and Conquest map formats.
 * It extends the DominationMap class and utilizes an instance of the Adaptee class for map file operations.
 * The Adapter class facilitates the interoperability between different map formats by providing
 * methods to read and save map files in the Domination format, using the functionality of the Adaptee class
 * which is designed to handle Conquest map files.
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public class Adapter extends DominationMap implements Serializable {
    /**
     * The target map type for adaptation
     */
    public static final String d_mapType = "Domination";

    /**
     * The Adaptee instance for performing map file operations
     */
    Adaptee d_adp = new Adaptee();

    /**
     * Constructs an Adapter instance with the provided Adaptee object.
     *
     * @param p_adp The Adaptee object to use for map file operations
     */
    public Adapter(Adaptee p_adp) {
        this.d_adp = p_adp;
    }

    /**
     * Loads a map file into the game map using the Adaptee object.
     *
     * @param p_GameMap  The game map object to load the map into
     * @param p_FileName The name of the map file to load
     * @throws ValidationException If there is an issue with file reading or map validation
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_adp.readMap(p_GameMap, p_FileName);
    }

    /**
     * Saves the input map to a file with the given name, overwriting any existing map with the same name.
     * The map will only be saved if it is valid.
     *
     * @param p_map      The map to save
     * @param p_fileName The name of the file to save to, including the extension
     * @return True if the file was successfully saved, otherwise false
     */
    public boolean saveMap(GameMap p_map, String p_fileName) {
        return d_adp.saveMap(p_map, p_fileName);
    }
}
