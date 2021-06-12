package Lesson7.awesome_project.model;

import Lesson7.awesome_project.entity.WeatherObject;

import java.sql.SQLException;
import java.util.List;

public interface IWeatherRepository {

    List<WeatherObject> fetchWeatherFromApi(Period period) throws SQLException;

    List<WeatherObject> readWeatherFromDb() throws SQLException;

    void saveWeatherInfoInDb(List<WeatherObject> weatherObjects) throws SQLException;
}
