package Lesson7.awesome_project.controller;

import Lesson7.awesome_project.model.AccuWeatherProvider;
import Lesson7.awesome_project.model.IWeatherProvider;
import Lesson7.awesome_project.model.Period;

public class WeatherController implements IWeatherController{

    private IWeatherProvider weatherProvider = new AccuWeatherProvider();

    @Override
    public void onUserInput(int command) {

        switch (command) {
            case 1:
                getCurrentWeather();
                break;
            case 2:
                getFivedaysWeather();
                break;
            default:
                System.out.println("НЕТ ТАКОЙ КОМАНДЫ!");
                System.exit(1);
        }
    }

    private void getCurrentWeather() {
        weatherProvider.getWeather(Period.NOW);
    }

    private void getFivedaysWeather() {
        weatherProvider.getWeather(Period.FIVE_DAYS);
    }
}
