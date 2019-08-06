package com.cj.httpClient.test.answer.secondTest.secondMode.subject;

import com.cj.httpClient.test.answer.secondTest.secondMode.observer.WeatherDataInfrastructure;

import java.util.Observable;

/**
 * One of the Subject used API in JDK.
 *
 * @author Michael
 */
public class WeatherDataSource extends Observable {

    //The main parameter of a weather data center.
    private float temperature;
    private float humidity;
    private float pressure;
    private float windForce;


    /**
     * @author Michael
     */
    private void changeMeasurements() {
        setChanged();
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
        new WeatherDataInfrastructure(weatherData).setIdentity("no.1");
        new WeatherDataInfrastructure(weatherData).setIdentity("no.2");

        //Change the data of subject, the the observer will receive the notice.
        weatherData.setMeasurements(1, 2, 3, 5);
    }
}
