package org.team21.game.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.team21.game.game_engine.GamePhase;
import org.team21.game.models.map.GameMap;

/**
 * Test class for verifying the behavior of the IssueOrder class.
 * This class tests various functionalities related to issuing orders in the game.
 * @author Tejasvi
 */
public class IssueOrderTest {
    /**
     * The instance of the GameMap.
     */
    private GameMap d_GameMap;
    /**
     * The instance of the IssueOrder class to be tested.
     */
    private IssueOrderController d_IssueOrder;

    /**
     * Sets up the test environment before each test case execution.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addCountry("Pakistan", "Asia");
        d_GameMap.addCountry("Srilanka", "Asia");
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.assignCountries();
        d_IssueOrder = new IssueOrderController();
        d_IssueOrder.d_GamePhase = GamePhase.IssueOrder;
    }

    /**
     * Cleans up the test environment after each test case execution.
     *
     * @throws Exception if an error occurs during teardown
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    /**
     * Tests the validation of command syntax.
     */
    @Test
    public void testValidateCommand() {
        assert d_IssueOrder.validateCommand("deploy Pakistan 10", d_GameMap.getPlayer("Player1"));
        assert !d_IssueOrder.validateCommand("deploye Pakistan 10", d_GameMap.getPlayer("Player2"));
    }
}
