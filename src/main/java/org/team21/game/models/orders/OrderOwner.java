package org.team21.game.models.orders;

import org.team21.game.models.game_play.Player;
import org.team21.game.models.map.Country;
import org.team21.game.models.map.GameMap;
import org.team21.game.utils.Constants;

/**
 * OrderOwner model will be used by {Player}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */

public class OrderOwner {

    /**
     * Based on map it will evaluate the orders
     */
    public static GameMap d_GameMap = GameMap.getInstance();

    /**
     * A function to creaate an order
     *
     * @param p_commands the command entered
     * @param player     object parameter of type Player
     * @return the order
     */
    public static Order issueOrder(String[] p_commands, Player player) {
        String l_OrderType = p_commands[0].toLowerCase();
        Order l_Order;

        switch (l_OrderType) {
            case Constants.DEPLOY_COMMAND:
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(generateDeployInfo(p_commands, player));
                break;
            case Constants.AIRLIFT_COMMAND:
                l_Order = new AirliftOrder();
                l_Order.setOrderInfo(generateAirliftOrderInfo(p_commands, player));
                break;
            case Constants.BOMB_COMMAND:
                l_Order = new BombOrder();
                l_Order.setOrderInfo(generateBombOrderInfo(p_commands, player));
                break;
            case Constants.NEGOTIATE_COMMAND:
                l_Order = new NegotiateOrder();
                l_Order.setOrderInfo(generateNegotiateOrderInfo(p_commands, player));
                break;
            default:
                System.out.println("\nFailed to create an order due to invalid arguments");
                return null;
        }

        return l_Order;
    }

    /**
     * Generates the information for a Deploy order.
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information for deploying
     */
    private static OrderInformation generateDeployInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        Country l_Country = d_GameMap.getCountries().get(l_CountryID.toLowerCase());
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderInformation l_OrderInfo = new OrderInformation();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDestination(l_Country);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmy);

        return l_OrderInfo;
    }

    /**
     * Generates information for an Airlift Order.
     *
     * @param p_command the command entered
     * @param p_player  object Player
     * @return the order information for airlifting
     */
    private static OrderInformation generateAirliftOrderInfo(String[] p_command, Player p_player) {
        String l_FromCountryID = p_command[1];
        Country l_FromCountry = d_GameMap.getCountries().get(l_FromCountryID.toLowerCase());
        String l_ToCountryID = p_command[2];
        Country l_ToCountry = d_GameMap.getCountries().get(l_ToCountryID.toLowerCase());
        int l_NumberOfArmies = Integer.parseInt(p_command[3]);

        OrderInformation l_OrderInfo = new OrderInformation();
        l_OrderInfo.setPlayer(p_player);
        l_OrderInfo.setDeparture(l_FromCountry);
        l_OrderInfo.setDestination(l_ToCountry);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmies);

        return l_OrderInfo;
    }

    /**
     * Generates information for a Bomb Order.
     *
     * @param p_command the command entered
     * @param p_player  object parameter of type Player
     * @return the order information for bombing
     */
    private static OrderInformation generateBombOrderInfo(String[] p_command, Player p_player) {
        OrderInformation l_OrderInfo = new OrderInformation();
        l_OrderInfo.setPlayer(p_player);

        String l_CountryID = p_command[1];
        Country l_TargetCountry = d_GameMap.getCountries().get(l_CountryID.toLowerCase());
        l_OrderInfo.setTargetCountry(l_TargetCountry);

        return l_OrderInfo;
    }

    /**
     * A function to generate the information of Negotiate order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    private static OrderInformation generateNegotiateOrderInfo(String[] p_Command, Player p_Player) {
        OrderInformation l_OrderInfo = new OrderInformation();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setNeutralPlayer(d_GameMap.getPlayer(p_Command[1]));
        return l_OrderInfo;
    }

}
