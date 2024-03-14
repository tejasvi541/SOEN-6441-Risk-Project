package org.team21.game.interfaces.observer;

/**
 * The interface Observable.
 *
 * @author Kapil Soni
 * @version 1.0.0
 */
public interface Observable {

    /**
     * A function to notify to Observer.
     *
     * @param p_s the observable
     */
    void notifyObservers(String p_s);
}
