package org.team21.game.game_engine;

import org.team21.game.interfaces.game.Engine;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameConsoleWriter;
import org.team21.game.utils.logger.GameEventLogger;
import org.team21.game.utils.logger.GameLogFileWriter;

import java.util.Scanner;

/**
 * Class to implement the game
 * @author Kapil Soni
 */
public class Game {
    /**
     * game engine
     */
    private Engine d_Engine;
    /**
     * logger observable
     */
    private final GameEventLogger d_Logger = GameEventLogger.getInstance();
    /**
     * game phase
     */
    private GamePhase d_GamePhase;

    /**
     * Default Constructor
     */
    public Game() {
        d_Logger.addObserver(new GameLogFileWriter());
        d_Logger.addObserver(new GameConsoleWriter());
    }
    /**
     * method to implement main class to start game
     *
     * @param args the arguments
     * @throws Exception if it occurs
     */
    public static void main(String[] args) throws Exception {
        System.out.println(Constants.WELCOME_MESSAGE);
        new Game().start();
    }

    /**
     * method which starts each phase in the game
     *
     * @throws Exception when it occurs
     */
    public void start() throws Exception {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log("Main Menu");
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        d_Logger.log("1.Enter 1 for New Game");
        d_Logger.log("2.Enter 2 for Load Game");
        d_Logger.log("3.Enter 3 for Single Game Mode");
        d_Logger.log("4.Enter 4 for Tournament Mode");
        d_Logger.log("5.Enter 5 for Exit");
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        d_Logger.log("Select the option");
        d_Logger.log(Constants.SMALL_EQUAL_SEPARATOR);
        try {
            int option = l_Scanner.nextInt();
            d_Engine = new GameEngine();
            switch (option) {
                case 1: {
                    d_GamePhase = GamePhase.MapEditor;
                    break;
                }
                case 2: {
                    d_GamePhase = GamePhase.LoadGame;
                    break;
                }
                case 3: {
                    d_Engine = new SingleGameEngine();
                    break;
                }
                case 4: {
                    d_Engine = new TournamentGameEngine();
                    break;
                }
                case 5: {
                    d_GamePhase = GamePhase.ExitGame;
                    break;
                }
                default: {
                    throw new Exception();
                }
            }
        } catch (Exception p_Exception) {
            d_Logger.log("\n"+ Constants.INVALID_COMMAND);
            start();
        }
        d_Engine.setGamePhase(d_GamePhase);
        d_Engine.startEngine();
    }
}
