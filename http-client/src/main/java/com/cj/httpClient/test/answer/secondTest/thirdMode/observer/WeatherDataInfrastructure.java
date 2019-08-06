package com.cj.httpClient.test.answer.secondTest.thirdMode.observer;


import com.cj.httpClient.test.answer.secondTest.firstMode.display.Displayment;
import com.cj.httpClient.test.answer.secondTest.thirdMode.subject.Subject;
import com.cj.httpClient.test.answer.secondTest.thirdMode.subject.WeatherDataSource;
import com.google.common.eventbus.Subscribe;

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
     * @author Michael
     */
    @Subscribe
    //@AllowConcurrentEvents //Open this annotation when the Thread-safe is needed.
    public void subscribe(Subject event) {
        update(event);
    }

    /**
     * Supply a interface for Subject to call.
     *
     * @author Michael
     */
    @Override
    public void update(Subject event) {
        if (event instanceof WeatherDataSource) {
            WeatherDataSource weatherDataSource = (WeatherDataSource) event;
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
        try {
            //I am working.
            Thread.sleep(130);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
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
