package org.team21.game.utils.logger;

import org.team21.game.interfaces.observers.Observable;
import org.team21.game.interfaces.observers.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The GameEventLogger class records and manages game events, acting as an Observable.
 * It follows the singleton pattern to ensure only one instance exists throughout the game.
 * This class provides functionality to log game events and notify registered observers.
 * Observers can subscribe to receive updates about game events.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public class GameEventLogger implements Observable, Serializable {
    /**
     * The singleton instance of the GameEventLogger.
     */
    private static GameEventLogger Logger;

    /**
     * A list of observers subscribed to receive notifications.
     */
    private List<Observer> d_ObserverList = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private GameEventLogger() {
    }

    /**
     * Retrieves the singleton instance of the GameEventLogger.
     *
     * @return The singleton instance of the GameEventLogger.
     */
    public static GameEventLogger getInstance() {
        Logger = Objects.isNull(Logger) ? new GameEventLogger() : Logger;
        return Logger;
    }

    /**
     * Logs the provided message and notifies registered observers.
     *
     * @param p_s The message to be logged and notified.
     */
    public void log(String p_s) {
        notifyObservers(p_s);
    }

    /**
     * Clears all logs and unsubscribes observers.
     */
    public void clear() {
        clearObservers();
    }

    /**
     * Notifies all registered observers with the provided message.
     *
     * @param p_s The message to be notified to observers.
     */
    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> p_observer.update(p_s));
    }

    /**
     * Adds an observer to the list of registered observers.
     *
     * @param p_Observer The observer to be added.
     */
    @Override
    public void addObserver(Observer p_Observer) {
        this.d_ObserverList.add(p_Observer);
    }

    /**
     * Clears all observers from the list, effectively unsubscribing them.
     */
    @Override
    public void clearObservers() {
        d_ObserverList.forEach(Observer::clearGameLogs);
    }
}