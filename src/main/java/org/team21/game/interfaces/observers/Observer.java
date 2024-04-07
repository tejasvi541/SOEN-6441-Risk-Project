package org.team21.game.interfaces.observers;

/**
 * The Observer interface defines the contract for implementing observer objects.
 * Observers are notified of changes in the subject and provide functionality to update their state.
 * This interface includes methods for updating the observer with a message and clearing all logs.
 *
 * @author Nishith Soni
 * @version 1.0.0
 */
public interface Observer {

    /**
     * Updates the observer with the provided message.
     *
     * @param p_S The message to be updated.
     */
    void update(String p_S);

    /**
     * Clears all logs associated with the observer.
     */
    void clearGameLogs();
}