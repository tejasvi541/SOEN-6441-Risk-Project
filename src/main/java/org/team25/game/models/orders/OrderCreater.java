package org.team25.game.models.orders;

import org.team25.game.models.game_play.Player;

//Todo refactor
public class OrderCreater {
    /**
     * A function to creaate an order
     * @param p_commands the command entered
     * @param player object parameter of type Player
     * @return the order
     */
    public static Order createOrder(String[] p_commands, Player player){
        String l_Type = p_commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case "deploy":
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(generateDeployOrderInfo(p_commands, player));
                break;
            default:
                System.out.println("\nFail to create an order due to invalid arguments");
                l_Order = new Order();
        }
        return l_Order;
    }

    /**
     * A function to generate the information of Deploying the order
     * @param p_Command the command entered
     * @param p_Player object parameter of type Player
     * @return the order information of deploy
     */
    private static OrderInfo generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDestination(l_CountryID);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmy);
        return l_OrderInfo;
    }

}
