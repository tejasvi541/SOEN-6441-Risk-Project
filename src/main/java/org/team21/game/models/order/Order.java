package org.team21.game.models.order;

import java.io.Serializable;

/**
 * Represents a concrete class to manage player orders.
 * This class serves as the base class for specific order types.
 * It contains methods to set and retrieve order information, get and set order type,
 * execute orders, validate commands, and print executed commands.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public abstract class Order implements Serializable {

    /**
     * The type of the order.
     */
    private String d_Type;
    /**
     * The information related to the order.
     */
    private OrderInformation d_OrderInformation;

    /**
     * Retrieves the order information.
     *
     * @return the order information object.
     */
    public OrderInformation getOrderInfo() {
        return d_OrderInformation;
    }

    /**
     * Sets the order information.
     *
     * @param p_OrderInformation the order information to set.
     */
    public void setOrderInfo(OrderInformation p_OrderInformation) {
        this.d_OrderInformation = p_OrderInformation;
    }

    /**
     * Retrieves the type of the order.
     *
     * @return the type of the order.
     */
    public String getType() {
        return d_Type;
    }

    /**
     * Sets the type of the order.
     *
     * @param p_Type the type of the order to set.
     */
    public void setType(String p_Type) {
        this.d_Type = p_Type;
    }

    /**
     * Abstract method to execute the order.
     * Must be overridden by concrete order classes.
     *
     * @return true if the execution was successful, false otherwise.
     */
    public abstract boolean execute();

    /**
     * Abstract method to execute the order.
     * Must be overridden by concrete order classes.
     *
     * @return true if the execution was successful, false otherwise.
     */
    public abstract boolean validateCommand();

    /**
     * Abstract method to print the executed command.
     * Must be overridden by concrete order classes.
     */
    public abstract void printOrderCommand();

}

