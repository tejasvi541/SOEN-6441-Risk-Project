package org.team21.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.team21.game.controllers.*;
import org.team21.game.game_engine.TournamentModeTest;
import org.team21.game.models.map.ContinentTest;
import org.team21.game.models.map.CountryTest;
import org.team21.game.models.map.GameMapTest;
import org.team21.game.models.map.PlayerTest;
import org.team21.game.models.order.*;
import org.team21.game.utils.validation.MapValidationTest;


/**
 * A class for test suites
 */
@RunWith(Suite.class)
/**
 * Run all test cases
 */
@Suite.SuiteClasses({
        ExecuteOrderTest.class,
        IssueOrderTest.class,
        LoadGameControllerTest.class,
        MapEditorTest.class,
        ReinforcementTest.class,
        StartGameControllerTest.class,
        TournamentModeTest.class,
        ContinentTest.class,
        CountryTest.class,
        GameMapTest.class,
        PlayerTest.class,
        AdvanceOrderTest.class,
        AirliftOrderTest.class,
        BlockadeOrderTest.class,
        BombOrderTest.class,
        DeployOrderTest.class,
        NegotiateOrderTest.class,
        MapValidationTest.class,
        GameEngineTest.class

})

/**
 * A class for test suites
 */
public class TestSuite {

}
