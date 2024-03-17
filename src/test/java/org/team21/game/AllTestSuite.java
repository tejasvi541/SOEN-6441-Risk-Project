package org.team21.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.team21.game.controllers.*;
import org.team21.game.models.cards.CardTest;
import org.team21.game.models.game_play.GameCommandsTest;
import org.team21.game.models.game_play.PlayerTest;
import org.team21.game.models.map.ContinentTest;
import org.team21.game.models.map.CountryTest;
import org.team21.game.models.map.GameMapTest;
import org.team21.game.models.orders.*;
import org.team21.game.utils.validation.MapValidatorTest;

@RunWith(Suite.class)

/**
 * Suite to test all cases
 */
@Suite.SuiteClasses({
        IssueOrderControllerTest.class, MapEditorControllerTest.class,
        MapLoaderControllerTest.class, ReinforcementControllerTest.class,
        StartGameControllerTest.class, CardTest.class,
        GameCommandsTest.class, PlayerTest.class,
        ContinentTest.class, CountryTest.class,
        GameMapTest.class, AirliftOrderTest.class,
        BombOrderTest.class, NegotiateOrderTest.class,
        MapValidatorTest.class, DeployOrderTest.class,
        BlockadeOrderTest.class, AdvanceOrderTest.class
})
/**
 * Class for test suite
 */
public class AllTestSuite {
}
