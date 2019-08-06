package com.cj.httpClient.test.answer.secondTest.thirdMode.observer;

import com.cj.httpClient.test.answer.secondTest.thirdMode.subject.Subject;

/**
 * The interface of Observer.
 *
 * @author Michael
 */
public interface Observer {

    void update(Subject event);
}
