package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.GameMap;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.utils.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Map editor controller test.
 *
 * @author Bharti
 */
public class MapEditorTest {

    /**
     * The Game phase.
     */
    GamePhase d_gamePhase;

    /**
     * The game map instance.
     */
    GameMap d_gameMap;

    /**
     * Editor object.
     */
    MapEditorController d_editor;

    /**
     * The next game phase.
     */
    GamePhase d_nextGamePhase = GamePhase.StartUp;

    /**
     * Sets up.
     */
    @Before
    public void setUp() throws Exception{
        d_editor = new MapEditorController();
        d_gameMap = GameMap.getInstance();
        d_gameMap.addContinent("Asia", "4");
        d_gameMap.addContinent("Europe", "3");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("China", "Asia");
        d_gameMap.addCountry("France", "Europe");
        d_gameMap.addPlayer("Player1");
        d_gameMap.addPlayer("Player2");
        d_gameMap.assignCountries();
        d_editor.d_GameMap= d_gameMap;

    }

    /**
     * Validate input.
     */
    @Test
    public void validateInput() {
        List<String> input =  new ArrayList<>();
        input.add("showmap");
        List<String> input_false =  new ArrayList<>();
        input_false.add("watchmap");
        assertTrue(d_editor.inputValidator(input));
        assertFalse(d_editor.inputValidator(input_false));
    }

    /**
     * Validate Add Continent Input.
     */
    @Test
    public void validateAddContinentInput() {
        String string="editContinent -add Japan 10";
        List<String> input =  new ArrayList<>();
        String[] l_Strings = string.split(" ");
        for(int l_Pos=0;l_Pos<l_Strings.length;l_Pos++){
           if (!l_Strings[l_Pos].isEmpty()) {
             input.add(l_Strings[l_Pos].trim());
           }
            }
        assertTrue(string.contains("-") && l_Strings.length == 4 && d_editor.inputValidator(input));
    }

    /**
     * Validate Add Continent Action .
     */
    @Test
    public void validateAddContinentAction() throws ValidationException {
        String l_Command="editcontinent -add Japan 10";
        String[] l_CommandArray = l_Command.split(" ");
        if (l_CommandArray.length > 0) {
            if (l_CommandArray.length == 4) {
                d_editor.d_GameMap.addContinent(l_CommandArray[2], l_CommandArray[3]);
            }
        }
        assertTrue(d_editor.d_GameMap.getContinents().containsKey("Japan"));
    }

    /**
     * Validate remove Continent Action .
     */
    @Test
    public void validateRemoveContinentAction() throws ValidationException {
        String l_Command="editcontinent -remove Europe";
        String[] l_CommandArray = l_Command.split(" ");
        if (l_CommandArray.length > 0) {
            if (l_CommandArray.length ==3) {
                d_editor.d_GameMap.removeContinent(l_CommandArray[2]);
            }
        }
        assertFalse(d_editor.d_GameMap.getContinents().containsKey("Europe"));
    }


    /**
     * Validate Add Country Action .
     */
    @Test
    public void validateAddCountryAction() throws ValidationException {
        String l_Command="editcountry -add Pakistan Asia";
        String[] l_CommandArray = l_Command.split(" ");
        if (l_CommandArray.length > 0) {
            if (l_CommandArray.length == 4) {
                d_editor.d_GameMap.addCountry(l_CommandArray[2], l_CommandArray[3]);
            }
        }
        assertTrue(d_editor.d_GameMap.getCountries().containsKey("Pakistan"));
    }


    /**
     * Validate Exit Action .
     */
    @Test
    public void validateExitAction() throws ValidationException {
        String l_Command="exit";
        String[] l_CommandArray = l_Command.split(" ");
        if (l_CommandArray.length > 0) {
            d_editor.d_GameMap.setGamePhase(d_nextGamePhase);
        }
        assertTrue(d_editor.d_GameMap.getGamePhase()== GamePhase.StartUp);
    }


    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        d_editor.d_GameMap.flushGameMap();
    }
}