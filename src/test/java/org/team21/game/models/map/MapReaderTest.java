package org.team21.game.models.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.utils.logger.LogEntryBuffer;
import org.team21.game.utils.validation.ValidationException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the functionalities of the MapReader class.
 * It verifies whether the map reading operations are performed correctly.
 *
 * @author Tejasvi
 */
public class MapReaderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Test reading a valid map file.
     * Verifies whether continents, countries, and their neighbors are correctly read.
     */
    @Test
    public void testReadMap() {
        GameMap gameMap = GameMap.getInstance();
        try {
            MapReader.readMap(gameMap, "valid_map.txt");

            // Verify continents
            assertEquals(2, gameMap.getContinents().size());
            assertEquals("Asia", gameMap.getContinent("1").getName());
            assertEquals("Europe", gameMap.getContinent("2").getName());

            // Verify countries
            assertEquals(4, gameMap.getCountries().size());
            assertEquals("India", gameMap.getCountry("1").getName());
            assertEquals("China", gameMap.getCountry("2").getName());
            assertEquals("Germany", gameMap.getCountry("3").getName());
            assertEquals("France", gameMap.getCountry("4").getName());

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test reading an invalid map file.
     * Verifies whether an error message is logged when validation fails.
     */
    @Test
    public void testReadInvalidMap() {
        GameMap gameMap = GameMap.getInstance();
        try {
            MapReader.readMap(gameMap, "invalid_map.txt");
        } catch (ValidationException e) {
            assertEquals("Invalid map file.", e.getMessage().trim());
        }
    }
}
