package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.models.map.GameMap;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.utils.validation.InvalidExecutionException;
import org.team21.game.utils.validation.ValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    GamePhase gamePhase;

    /**
     * The game map instance.
     */
    GameMap gameMap;

    /**
     * Editor object.
     */
    MapEditorController editor;

    /**
     * The next game phase.
     */
    GamePhase nextGamePhase = GamePhase.StartUp;

    /**
     * Sets up.
     */
    @Before
    public void setUp() throws Exception{
        editor = new MapEditorController();
        gameMap = GameMap.getInstance();
        gameMap.addContinent("Asia", "4");
        gameMap.addContinent("Europe", "3");
        gameMap.addCountry("India", "Asia");
        gameMap.addCountry("China", "Asia");
        gameMap.addCountry("France", "Europe");
        gameMap.addPlayer("Player1");
        gameMap.addPlayer("Player2");
        gameMap.assignCountries();
        editor.d_GameMap=gameMap;

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
        assertTrue(editor.inputValidator(input));
        assertFalse(editor.inputValidator(input_false));
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
        assertTrue(string.contains("-") && l_Strings.length == 4 && editor.inputValidator(input));
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
                editor.d_GameMap.addContinent(l_CommandArray[2], l_CommandArray[3]);
            }
        }
        assertTrue(editor.d_GameMap.getContinents().containsKey("Japan"));
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
                editor.d_GameMap.removeContinent(l_CommandArray[2]);
            }
        }
        assertFalse(editor.d_GameMap.getContinents().containsKey("Europe"));
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
                editor.d_GameMap.addCountry(l_CommandArray[2], l_CommandArray[3]);
            }
        }
        assertTrue(editor.d_GameMap.getCountries().containsKey("Pakistan"));
    }


    /**
     * Validate Exit Action .
     */
    @Test
    public void validateExitAction() throws ValidationException {
        String l_Command="exit";
        String[] l_CommandArray = l_Command.split(" ");
        if (l_CommandArray.length > 0) {
            editor.d_GameMap.setGamePhase(nextGamePhase);
        }
        assertTrue(editor.d_GameMap.getGamePhase()== GamePhase.StartUp);
    }


    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        // Clean up resources or release objects
        editor.d_GameMap.flushGameMap();
    }
}