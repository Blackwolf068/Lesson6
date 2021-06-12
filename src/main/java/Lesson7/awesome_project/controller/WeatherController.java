package Lesson7.awesome_project.controller;

import Lesson7.awesome_project.model.*;

import java.sql.SQLException;

public class WeatherController implements IWeatherController{

    private IWeatherRepository weatherRepository = new WeatherRepository();

    @Override
    public void onUserInput(int command) throws SQLException {

        switch (command) {
            case 1:
                getCurrentWeather();
                break;
            case 2:
                getFivedaysWeather();
                break;
            case 3:
                getAllFromDb();
                break;
            default:
                System.out.println("НЕТ ТАКОЙ КОМАНДЫ!");
                System.exit(1);
        }
    }

    private void getCurrentWeather() throws SQLException {
        weatherRepository.fetchWeatherFromApi(Period.NOW);
    }

    private void getFivedaysWeather() throws SQLException {
        weatherRepository.fetchWeatherFromApi(Period.FIVE_DAYS);
    }

    private void getAllFromDb() throws SQLException {
        weatherRepository.readWeatherFromDb();
    }
}
