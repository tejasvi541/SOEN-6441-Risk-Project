package org.team21.game.interfaces.observers;

/**
 * This interface defines the contract for an Observable object, which can be observed by multiple Observer objects.
 * It provides methods to notify observers of changes, add new observers, and clear existing observers.
 * Observables can send messages or notifications to their observers, allowing for communication between
 * different components of the system.
 * Implementing classes should use the {@code notifyObservers} method to send messages to registered observers,
 * {@code addObserver} to add new observers, and {@code clearObservers} to remove all observers.
 * Observers registered with an Observable will receive notifications when the Observable's state changes,
 * facilitating the implementation of the Observer pattern.
 *
 * @author Meet Boghani
 * @version 1.0.0
 */
public interface Observable {

    /**
     * Notifies all registered observers with the given message.
     *
     * @param p_String The message or notification to send to observers
     */
    public void notifyObservers(String p_String);

    /**
     * Adds a new observer to the list of registered observers.
     *
     * @param p_Observer The observer object to add
     */
    public void addObserver(Observer p_Observer);

    /**
     * Removes all observers from the list of registered observers.
     * After calling this method, the Observable will not notify any observers until new ones are added.
     */
    public void clearObservers();
}