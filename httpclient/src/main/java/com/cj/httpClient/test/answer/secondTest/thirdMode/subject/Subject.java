package com.cj.httpClient.test.answer.secondTest.thirdMode.subject;


import com.cj.httpClient.test.answer.secondTest.thirdMode.observer.Observer;

/**
 * The interface of Subject.
 *
 * @author Michael
 */
public interface Subject {

    /**
     * Register observer
     *
     * @param o observer
     * @author Michael
     */
    void registerObserver(Observer o);

    /**
     * Remove specific observer
     *
     * @param o observer
     * @author Michael
     */
    void removeObserver(Observer o);

    /**
     * Notify all observers
     *
     * @author Michael
     */
    void notifyObservers();
}
