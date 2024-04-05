package org.team21.game.utils.adapter;

import org.team21.game.models.map.DominationMap;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.validation.ValidationException;

import java.io.IOException;
/**
 * This class is used to implement Adapter Pattern
 * @author Meet Boghani
 */
public class Adapter extends DominationMap {
    /**
     *  target map type
     */
    public static final String d_mapType="Domination";

    Adaptee d_adp = new Adaptee();

    /**
     * Constructor to initialize adaptee object
     * @param p_adp Object of adaptee class
     */
    public Adapter(Adaptee p_adp) {
        this.d_adp = p_adp;
    }

    /**
     * This method loads the map file
     * @param p_GameMap  the game map
     * @param p_FileName the map file name
     * @throws ValidationException files exception
     */

    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_adp.readMap(p_GameMap, p_FileName);
    }

    /**
     *Saves the input map to a given file name. Overwrites any existing map with the same name.
     * The map will only save if it is valid.
     * @param p_map The map to save.
     * @param p_fileName The name of the file to save to, including the extension.
     * @return Whether the file was successfully saved.
     */
    public boolean saveMap(GameMap p_map, String p_fileName) {
        return d_adp.saveMap(p_map, p_fileName);
    }

}