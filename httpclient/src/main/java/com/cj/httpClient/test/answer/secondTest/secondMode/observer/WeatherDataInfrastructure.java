package com.cj.httpClient.test.answer.secondTest.secondMode.observer;

import com.cj.httpClient.test.answer.secondTest.firstMode.display.Displayment;
import com.cj.httpClient.test.answer.secondTest.secondMode.subject.WeatherDataSource;

import java.util.Observable;
import java.util.Observer;

/**
 * The weather observer used API in JDK.
 *
 * @author Michael
 */
public class WeatherDataInfrastructure implements Observer, Displayment {

    //Maintain the interface of Subject.
    private Observable weatherData;

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
    public WeatherDataInfrastructure(Observable weatherDataSource) {
        this.weatherData = weatherDataSource;
        weatherData.addObserver(this);
    }

    public WeatherDataInfrastructure() {

    }

    /**
     * Supply a interface for Subject to call.
     *
     * @author Michael
     */
    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherDataSource) {
            WeatherDataSource weatherDataSource = (WeatherDataSource) obs;
            this.humidity = weatherDataSource.getHumidity();
            this.pressure = weatherDataSource.getPressure();
            this.temperature = weatherDataSource.getTemperature();
            this.windForce = weatherDataSource.getWindForce();
            display();
        }
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
