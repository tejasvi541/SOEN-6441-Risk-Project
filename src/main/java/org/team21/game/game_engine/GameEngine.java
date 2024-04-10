package org.team21.game.game_engine;

import org.team21.game.interfaces.game.Engine;
import org.team21.game.interfaces.game.GameFlowManager;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.validation.InvalidExecutionException;
import org.team21.game.utils.validation.ValidationException;

import java.util.Objects;

/**
 * A class to start the game with the Map Editor Phase
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class GameEngine implements Engine {

    /**
     * Game Settings for warzone game
     */
    private static GameSettings d_GameSettings;

    /**
     * Creating Logger Observable
     * Single Instance needs to be maintained (Singleton)
     */
    private static GameEventLogger d_Logger;

    /**
     * gamephase instance for the state
     */
    public GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     * constructor for game engine
     */
    public GameEngine() {
        d_GameSettings = GameSettings.getInstance();
        d_GameSettings.setStrategy("default");
        d_Logger = GameEventLogger.getInstance();
        d_Logger.clear();
    }

    /**
     * The function which runs the whole game in phases
     */
    public void startEngine() {
        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameFlowManager l_GameFlowManager = d_GamePhase.getController();
                if (Objects.isNull(l_GameFlowManager)) {
                    throw new Exception("No Controller found");
                }
                d_GamePhase = l_GameFlowManager.startPhase(d_GamePhase);
                GameMap.getInstance().setGamePhase(d_GamePhase);
                d_Logger.log("===================== You have entered the " + d_GamePhase + " Phase ===================");
                startEngine();
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            p_Exception.printStackTrace();
            startEngine();
        } catch (Throwable p_Exception) {
            System.err.println(p_Exception.getMessage());
            p_Exception.printStackTrace();
            System.err.println("Please try again with valid data");
            if (d_GamePhase.equals(GamePhase.MapEditor)) {
                startEngine();
            }
        }
    }

    /**
     * method to set game phase
     *
     * @param p_GamePhase the game phase
     */
    public void setGamePhase(GamePhase p_GamePhase) {
        d_GamePhase = p_GamePhase;
    }

}
