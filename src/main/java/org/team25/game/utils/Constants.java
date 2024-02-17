package org.team25.game.utils;

/**
 * This class handles defined constants
 *
 * @author Bharti Chhabra
 * @author Kapil Soni
 * @version 1.0.1
 */
public final class Constants {

    /**
     * Instantiates a new Constants.
     */
    Constants(){}

    /**
     * The constant TEAM_NAME.
     */
    public static String TEAM_NAME = "Team25";
    /**
     * The constant EDIT_COUNTRY.
     */
    public final static String EDIT_COUNTRY="editcountry";
    /**
     * The constant EDIT_CONTINENT.
     */
    public final static String EDIT_CONTINENT="editcontinent";
    /**
     * The constant EDIT_NEIGHBOUR.
     */
    public final static String EDIT_NEIGHBOUR="editneighbor";
    /**
     * The constant EXIT.
     */
    public final static String EXIT="exit";
    /**
     * The constant HELP.
     */
    public final static String HELP="help";
    /**
     * The constant VALIDATE_MAP.
     */
    public final static String VALIDATE_MAP="validatemap";
    /**
     * The constant SHOW_MAP.
     */
    public final static String SHOW_MAP="showmap";
    /**
     * The constant SAVE_MAP.
     */
    public final static String SAVE_MAP="savemap";
    /**
     * The constant EDIT_MAP.
     */
    public final static String EDIT_MAP="editmap";
    /**
     * The constant ADD.
     */
    public final static String ADD="-add";
    /**
     * The constant REMOVE.
     */
    public final static String REMOVE="-remove";

    /**
     * The constant EXECUTE_ORDER_SUCCESS.
     */
    public final static String EXECUTE_ORDER_SUCCESS = "All mentioned orders have been executed successfully.";


    /**
     * The constant EXECUTE_ORDER_FAIL.
     */
    public final static String EXECUTE_ORDER_FAIL = "Orders can't be executed due to some reason.";

    /**
     * The constant SEPERATER.
     */
    public final static String SEPERATER = "******************************************************************************";

    /**
     * The constant ENTER_CORRECT_COMMAND.
     */
    public final static String ENTER_CORRECT_COMMAND = "Please enter the correct command";

    /**
     * The constant DEPLOY_COMMAND_MESSAGE.
     */
    public final static String DEPLOY_COMMAND_MESSAGE = "List of game loop commands\nTo deploy the armies : deploy countryID armies\n"+ENTER_CORRECT_COMMAND;
    /**
     * The constant DEPLOY_COMMAND.
     */
    public final static String DEPLOY_COMMAND = "deploy";
    /**
     * The constant HELP_COMMAND_MESSAGE.
     */
    public final static String HELP_COMMAND_MESSAGE = "1. Enter help to view the set of command.";
    /**
     * The constant EXIT_COMMAND_MESSAGE.
     */
    public final static String EXIT_COMMAND_MESSAGE = "2. Enter exit to end the phase.";
    /**
     * The constant ISSUE_COMMAND_MESSAGE.
     */
    public final static String ISSUE_COMMAND_MESSAGE = "To issue your orders: \n" + HELP_COMMAND_MESSAGE;
    /**
     * The constant ELIGIBLE_NATIONS_ARMY.
     */
    public final static String ELIGIBLE_NATIONS_ARMY = "The nations eligible for army allocation are:";
    /**
     * The constant ARMY_DEPLETED.
     */
    public final static String ARMY_DEPLETED = "All of your armies have been depleted. Transitioning to the next phase.";
    /**
     * The constant WELCOME_MESSAGE.
     */
    public final static String WELCOME_MESSAGE = SEPERATER+"\n"+TEAM_NAME + " welcomes you to warzone game" +"\n" +SEPERATER+"\n";
    /**
     * The constant INVALID_GAME_PHASE.
     */
    public final static String INVALID_GAME_PHASE = "Invalid game phase";

    /**
     * The constant DUMMY.
     */
    public final static String DUMMY="dummy";

    /**
     * Show Start Game Commands
     * {org.team25.game.controllers.StartGameController}
     */
    public static void showStartGameCommand(){
        System.out.println(SEPERATER);
        System.out.println("Order of Game play commands are Listed below ::");
        System.out.println("To load the map : loadmap filename" + "  e.g. loadmap canada");
        System.out.println("To show the loaded map : showmap"+"  e.g. showmap");
        System.out.println("To add or remove a player : gameplayer -add playername -remove playername"+"  e.g. gameplayer -add nisha");
        System.out.println("To assign countries : assigncountries");
    }

    /**
     * The constant ADD_TWO_PLAYERS.
     */
    public final static String ADD_TWO_PLAYERS="Create at least two players";
    /**
     * The constant INVALID_COMMAND.
     */
    public final static String INVALID_COMMAND="The command which you have added is invalid.";
    /**
     * The constant SIMPLE_ADD.
     */
    public final static String SIMPLE_ADD="add";
    /**
     * The constant SIMPLE_REMOVE.
     */
    public final static String SIMPLE_REMOVE="remove";
    /**
     * The constant CONTROLLERS_NOT_FOUND.
     */
    public final static String CONTROLLERS_NOT_FOUND="No controller associated to this phase/feature found.";
    /**
     * The constant COUNTRIES_DOES_NOT_BELONG.
     */
    public final static String COUNTRIES_DOES_NOT_BELONG="The country does not belong to you.";
    /**
     * The constant NOT_ENOUGH_REINFORCEMENTS.
     */
    public final static String NOT_ENOUGH_REINFORCEMENTS="You do have enough Reinforcement Armies to deploy.";

}
