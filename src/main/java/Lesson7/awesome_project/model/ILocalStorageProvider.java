package Lesson7.awesome_project.model;

import Lesson7.awesome_project.entity.WeatherObject;

import java.sql.SQLException;
import java.util.List;

public interface ILocalStorageProvider {

    void saveWeather(WeatherObject weatherObject) throws SQLException;

    List<WeatherObject> getAllWeather() throws SQLException;
}
