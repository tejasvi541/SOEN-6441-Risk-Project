package org.team25.game.models.orders;

import org.team25.game.models.game_play.Player;
import org.team25.game.utils.Constants;

//Todo refactor

public class OrderOwner {
    /**
     * A function to creaate an order
     *
     * @param p_commands the command entered
     * @param player     object parameter of type Player
     * @return the order
     */
    public static Order issueOrder(String[] p_commands, Player player) {
        String l_Type = p_commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case Constants.DEPLOY_COMMAND:
                l_Order = new Deploy();
                l_Order.setOrderInfo(createDeployOrderInfo(p_commands, player));
                break;
            default:
                System.out.println(Constants.INVALID_COMMAND);
                l_Order = new Order();
        }
        return l_Order;
    }

    /**
     * A function to generate the information of Deploying the order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    private static OrderInformation createDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderInformation l_OrderInformation = new OrderInformation();
        l_OrderInformation.setPlayer(p_Player);
        l_OrderInformation.setDestination(l_CountryID);
        l_OrderInformation.setNumberOfArmy(l_NumberOfArmy);
        return l_OrderInformation;
    }

}
