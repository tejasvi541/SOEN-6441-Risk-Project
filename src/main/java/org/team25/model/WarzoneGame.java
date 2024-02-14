package org.team25.model;

import org.apache.logging.log4j.LogManager;
import org.team25.Utils.ValidationException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to initiate the gamePlay.
 * @author Bharti Chhabra
 * @version 1.0.0
 */
public class WarzoneGame {


    /**
     * A data member that will log the data for the class
     */
    private static final Logger d_Logger = (Logger) LogManager.getLogger(WarzoneGame.class);

    /**
     * A data member to set the log level
     */
    static Level d_logLevel;

    /**
     * A data member to store game phase
     */
    private static GamePhase d_GamePhase;

    /**
     * A data member to hold GameEngine
     */
    private GameEngine d_GameEngine;

    /**
     * main method to start game
     *
     * @param args the arguments
     * @throws ValidationException if it occurs
     */
    public static void main(String[] args) throws Exception {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log(d_logLevel,"");
        d_Logger.log(d_logLevel,"************************************");
        d_Logger.log(d_logLevel,"Welcome to Warzone Game,Let's Play !");
        d_Logger.log(d_logLevel,"************************************");
        d_Logger.log(d_logLevel,"Choose option from Main-Menu to play");
        d_Logger.log(d_logLevel,"------------------------------------");
        d_Logger.log(d_logLevel,"1. New Game to play");
        d_Logger.log(d_logLevel,"2. Load Game to continue");
        d_Logger.log(d_logLevel,"5. Exit");
        d_Logger.log(d_logLevel,"------------------------------------");
        try {
            int option = l_Scanner.nextInt();
            switch (option) {
                case 1: {
                    d_GameEngine = new GameEngine(GamePhase.MapEditor);
                    break;
                }
                case 2: {
                    d_GameEngine = new GameEngine(GamePhase.LoadGame);
                    break;
                }
                case 3: {
                    d_GameEngine = new GameEngine(GamePhase.ExitGame);
                    break;
                }
                default: {
                    throw new ValidationException();
                }
            }
        } catch (ValidationException p_Exception) {
            System.out.println("Invalid option from above-mentioned list.Try Again !");
        }
        finally{
            l_Scanner.close();
        }
        d_GameEngine.run();
    }
}
