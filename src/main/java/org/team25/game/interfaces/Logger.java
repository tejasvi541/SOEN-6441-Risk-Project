package org.team25.game.interfaces;

public interface Logger {

        /**
         * To update the message for the game observer
         *
         * @param p_m the message to be updated
         */
        default void update(String p_m){}

        /**
         * clear all logs
         */
        default void clearLogs(){}

}
