package com.cj.httpClient.test.answer.secondTest.thirdMode.subject;


import com.cj.httpClient.test.answer.secondTest.thirdMode.factory.AsyncEventBusFactory;
import com.cj.httpClient.test.answer.secondTest.thirdMode.observer.Observer;
import com.cj.httpClient.test.answer.secondTest.thirdMode.observer.WeatherDataInfrastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * One of the Subject.
 *
 * @author Michael
 */
public class WeatherDataSource implements Subject {

    //Define a List to maintain all the Observers.
    private List<Observer> observers = new ArrayList<>();

    //The main parameter of a weather data center.
    private float temperature;
    private float humidity;
    private float pressure;
    private float windForce;

    /**
     * Register specific observer into the List.
     *
     * @param o observer.
     * @author Michael
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Remove specific observer
     *
     * @param o observer.
     * @author Michael
     */
    @Override
    public void removeObserver(Observer o) {
        if (observers.indexOf(o) >= 0) {
            observers.remove(o);
        }
    }

    /**
     * Notify all observers
     *
     * @author Michael
     */
    @Override
    public void notifyObservers() {
        observers.forEach(o ->
                AsyncEventBusFactory.postEvent(o, this));
    }

    /**
     * @author Michael
     */
    private void changeMeasurements() {
        notifyObservers();
    }

    /**
     * When measurements changed,should notify all observers.
     *
     * @author Michael
     */
    public void setMeasurements(float temperature, float humidity,
                                float pressure, float windForce) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windForce = windForce;
        changeMeasurements();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindForce() {
        return windForce;
    }

    public static void main(String[] args) {
        WeatherDataSource weatherData = new WeatherDataSource();

        //Instantiate the observers.
        for (int i = 0; i < 50; i++) {
            new WeatherDataInfrastructure(weatherData).setIdentity("no." + (i + 1));
        }

        //Change the data of subject, the the observer will receive the notice.
        weatherData.setMeasurements(1, 2, 3, 4);

        //The main thread can transfer the job to eventBus and return quickly.
        System.out.println("WeatherData finish transferring data.");
    }
}
