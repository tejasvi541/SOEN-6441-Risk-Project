package org.team21.game.interfaces.observer;

public interface Observable {

    /**
     * A function to notify to Observer.
     * @param p_s the observable
     */
    public void notifyObservers(String p_s);
}
