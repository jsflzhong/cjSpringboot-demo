package com.cj.httpClient.test.answer.secondTest.firstMode.observer;

import com.cj.httpClient.test.answer.secondTest.firstMode.display.Displayment;
import com.cj.httpClient.test.answer.secondTest.firstMode.subject.Subject;

/**
 * The weather observer.
 *
 * @author Michael
 */
public class WeatherDataInfrastructure implements Observer, Displayment {

    //Maintain the interface of Subject.
    private Subject weatherData;

    //Not necessary.
    private float temperature;
    private float humidity;
    private float pressure;
    private float windForce;

    //Nothing but an id flag
    private String identity;

    /**
     * Register into Subject.
     *
     * @param weatherDataSource Subject
     * @author Michael
     */
    public WeatherDataInfrastructure(Subject weatherDataSource) {
        this.weatherData = weatherDataSource;
        weatherData.registerObserver(this);
    }

    public WeatherDataInfrastructure() {

    }

    /**
     * Supply a interface for Subject to call.
     *
     * @param temp      temp
     * @param humidity  humidity
     * @param pressure  pressure
     * @param windForce windForce
     * @author Michael
     */
    @Override
    public void update(float temp, float humidity,
                       float pressure, float windForce) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windForce = windForce;
        display();
    }

    /**
     * Behavior of observer, normally can handle the business data here.
     *
     * @author Michael
     */
    @Override
    public void display() {
        System.out.println("The observer :["
                + identity
                + "] receive the msg from Subject! The data is :"
                + toString());
    }

    @Override
    public String toString() {
        return "WeatherDataInfrastructure{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windForce=" + windForce +
                '}';
    }

    public String getIdentity() {
        return identity;
    }

    public WeatherDataInfrastructure setIdentity(String identity) {
        this.identity = identity;
        return this;
    }
}
