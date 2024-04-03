package org.team21.game.utils.logger;

import org.team21.game.interfaces.observers.Observable;
import org.team21.game.interfaces.observers.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class gets all the actions of the game. It is an Observable.
 * Singleton class
 * @author Kapil Soni
 */
public class GameEventLogger implements Observable, Serializable {
    /**
     * A static object of LogEntryBuffer
     */
    private static GameEventLogger Logger;
    /**
     * A list of observers
     */
    private List<Observer> d_ObserverList = new ArrayList<>();

    /**
     * A constructor for LogEntryBuffer
     */
    private GameEventLogger() {

    }

    /**
     * A function to get the instance of LogEntryBuffer
     * @return LogEntryBuffer Logger
     */
    public static GameEventLogger getInstance() {
        if (Objects.isNull(Logger)) {
            Logger = new GameEventLogger();
        }
        return Logger;
    }

    /**
     * This method gets the information from the game and notifies the Observer.
     *
     * @param p_s The message to be notified
     */
    public void log(String p_s) {
        notifyObservers(p_s);
    }


    /**
     * Clear logs
     */
    public void clear() {
        clearObservers();
    }

    /**
     * This method updates the Observer with the message.
     *
     * @param p_s The message to be updated
     */
    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> p_observer.update(p_s));
    }

    /**
     * A function to add an observer to the list of observers
     * @param p_Observer The observer to be added
     */
    @Override
    public void addObserver(Observer p_Observer) {
        this.d_ObserverList.add(p_Observer);
    }

    /**
     * A function to format the list of observers in the list.
     */
    @Override
    public void clearObservers() {
        d_ObserverList.forEach(Observer::clearLogs);
    }

}
