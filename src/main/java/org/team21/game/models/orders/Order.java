package org.team21.game.models.orders;

import org.team21.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Order model will be used for order's work and
 * extended by {Deploy}
 *
 * @author Kapil Soni
 * @version 1.0.0
 */

/**
 * An abstract class representing an order, which can be extended to create specific types of orders.
 * This class encapsulates basic functionalities and information related to an order.
 */
public abstract class Order {

    /**
     * Default constructor
     */
    Order(){

    }
    /**
     * The type of the order, represented as a String.
     */
    private String d_Type;

    /**
     * Information related to the order, encapsulated in an object of type OrderInfo.
     */
    private OrderInformation d_OrderInfo;

    /**
     * Retrieves the order information.
     *
     * @return An OrderInfo object containing information about the order.
     */
    public OrderInformation getOrderInfo() {
        return d_OrderInfo;
    }

    /**
     * Sets the order information based on the provided OrderInfo object.
     *
     * @param p_OrderInfo The OrderInfo object containing information to be set for the order.
     */
    public void setOrderInfo(OrderInformation p_OrderInfo) {
        this.d_OrderInfo = p_OrderInfo;
    }

    /**
     * Retrieves the type of the order.
     *
     * @return A String indicating the type of the order.
     */
    public String getType() {
        return d_Type;
    }

    /**
     * Sets the type of the order.
     *
     * @param p_Type A String representing the type of the order.
     */
    public void setType(String p_Type) {
        this.d_Type = p_Type;
    }

    /**
     * Executes the order. This method is intended to be overridden by child classes.
     *
     * @return true if the order is executed successfully, false otherwise.
     */
    public abstract boolean execute();

    /**
     * Validates the command associated with the order. This method is intended to be overridden by child classes.
     *
     * @return true if the command associated with the order is valid, false otherwise.
     */
    public abstract boolean validateCommand();

    /**
     * Prints the command that is executed successfully. This method is intended to be overridden by child classes.
     */
    public abstract void printOrderCommand();

}
