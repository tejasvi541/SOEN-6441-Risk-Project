package org.team21.game.models.order;

import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.models.map.Player;
import org.team21.game.utils.Constants;
import org.team21.game.utils.logger.GameEventLogger;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * A class responsible for creating orders in the game.
 * This class contains methods to generate different types of orders based on the input command.
 *
 * @author Kapil Soni
 */
public class OrderOwner implements Serializable {

    /**
     * Static object of Game Map to hold instance of game map.
     */
    public static GameMap d_GameMap = GameMap.getInstance();

    /**
     * Logger Observable
     */
    private static GameEventLogger d_Logger = GameEventLogger.getInstance();

    /**
     * Creates an order based on the input command and player.
     *
     * @param p_Commands the command entered
     * @param p_Player the player issuing the order
     * @return the created order
     */
    public static Order createOrder(String[] p_Commands, Player p_Player) {
        String l_Type = p_Commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case Constants.DEPLOY_COMMAND:
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(generateDeployOrderInfo(p_Commands, p_Player));
                break;
            case Constants.ADVANCE_COMMAND:
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(generateAdvanceOrderAndAirliftOrderInfo(p_Commands, p_Player));
                break;
            case Constants.NEGOTIATE_COMMAND:
                l_Order = new NegotiateOrder();
                l_Order.setOrderInfo(generateNegotiateOrderInfo(p_Commands, p_Player));
                break;
            case Constants.BLOCKADE_COMMAND:
                l_Order = new BlockadeOrder();
                l_Order.setOrderInfo(generateBlockadeOrderInfo(p_Commands, p_Player));
                break;
            case Constants.AIRLIFT_COMMAND:
                l_Order = new AirliftOrder();
                l_Order.setOrderInfo(generateAdvanceOrderAndAirliftOrderInfo(p_Commands, p_Player));
                break;
            case Constants.BOMB_COMMAND:
                l_Order = new BombOrder();
                l_Order.setOrderInfo(generateBombOrderInfo(p_Commands, p_Player));
                break;
            default:
                d_Logger.log("\nFailed to create an order due to invalid arguments");
                l_Order = null;

        }
        return l_Order;
    }

    /**
     * Generates order information for deploying armies.
     *
     * @param p_Command the command entered
     * @param p_Player the player issuing the order
     * @return the order information for deployment
     */
    public static OrderInformation generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        Country l_Country = d_GameMap.getCountry(p_Command[1]);
        int l_NumberOfArmies = Integer.parseInt(p_Command[2]);
        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setCommand(convertToString(p_Command));
        l_OrderInformation.setPlayer(p_Player);
        l_OrderInformation.setDestination(l_Country);
        l_OrderInformation.setNumberOfArmy(l_NumberOfArmies);
        if(p_Player.getReinforcementArmies() > 0 && l_NumberOfArmies <= p_Player.getIssuedArmies() && l_NumberOfArmies > 0){
            p_Player.setIssuedArmies(p_Player.getIssuedArmies() - l_NumberOfArmies);
        }
        return l_OrderInformation;
    }

    /**
     * Generates order information for advance or airlift orders.
     *
     * @param p_Command the command entered
     * @param p_Player the player issuing the order
     * @return the order information for advance or airlift
     */
    public static OrderInformation generateAdvanceOrderAndAirliftOrderInfo(String[] p_Command, Player p_Player) {
        String l_FromCountryID = p_Command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_Command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_Command[3]);
        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setCommand(convertToString(p_Command));
        l_OrderInformation.setPlayer(p_Player);
        l_OrderInformation.setDeparture(l_FromCountry);
        l_OrderInformation.setDestination(l_ToCountry);
        l_OrderInformation.setNumberOfArmy(l_NumberOfArmies);
        return l_OrderInformation;
    }


    /**
     * Generates order information for a negotiate order.
     *
     * @param p_Command the command entered
     * @param p_Player the player issuing the order
     * @return the order information for negotiate
     */
    public static OrderInformation generateNegotiateOrderInfo(String[] p_Command, Player p_Player) {
        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setPlayer(p_Player);
        l_OrderInformation.setCommand(convertToString(p_Command));
        l_OrderInformation.setNeutralPlayer(d_GameMap.getPlayer(p_Command[1]));
        return l_OrderInformation;
    }

    /**
     * Generates order information for a blockade order.
     *
     * @param p_Command the command entered
     * @param p_Player the player issuing the order
     * @return the order information for blockade
     */
    public static OrderInformation generateBlockadeOrderInfo(String[] p_Command, Player p_Player) {
        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setCommand(convertToString(p_Command));
        l_OrderInformation.setPlayer(p_Player);
        String l_CountryID = p_Command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderInformation.setTargetCountry(l_TargetCountry);
        return l_OrderInformation;
    }

    /**
     * Generates order information for a bomb order.
     *
     * @param p_Command the command entered
     * @param p_Player the player issuing the order
     * @return the order information for bomb
     */
    public static OrderInformation generateBombOrderInfo(String[] p_Command, Player p_Player) {
        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setCommand(convertToString(p_Command));
        l_OrderInformation.setPlayer(p_Player);
        String l_CountryID = p_Command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderInformation.setTargetCountry(l_TargetCountry);
        return l_OrderInformation;
    }

    /**
     * Converts an array of strings into a single string.
     *
     * @param p_Commands the array of strings
     * @return the concatenated string
     */
    private static String convertToString(String[] p_Commands) {
        StringJoiner l_Joiner = new StringJoiner(" ");
        for (int l_Index = 0; l_Index < p_Commands.length; l_Index++) {
            l_Joiner.add(p_Commands[l_Index]);
        }
        return l_Joiner.toString();
    }
}
