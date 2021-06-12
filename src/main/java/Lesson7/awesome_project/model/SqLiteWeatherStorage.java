package Lesson7.awesome_project.model;

import Lesson7.awesome_project.GlobalState;
import Lesson7.awesome_project.entity.WeatherObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqLiteWeatherStorage implements ILocalStorageProvider {

    private static Connection connection;
    private static Statement statement;
    private List<WeatherObject> weatherList;

    static {
        try {
            GlobalState.getInstance()
                    .getConnection()
                    .createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS weather (id INTEGER PRIMARY KEY AUTOINCREMENT, city TEXT, date TEXT, weather TEXT, mintemp REAL, maxtemp REAL)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveWeather(WeatherObject weatherObject) throws SQLException {
        String query = String.format(Locale.US, "INSERT INTO weather (city, date, weather, mintemp, maxtemp) VALUES ('%s', '%s', '%s', '%2.1f', '%2.1f')",
                weatherObject.getCity(), weatherObject.getDate(), weatherObject.getWeather(), weatherObject.getMinTemperature(), weatherObject.getMaxTemperature());
        connection = DriverManager.getConnection("jdbc:sqlite:" + GlobalState.getInstance().DB_NAME);
        statement = connection.createStatement();
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        statement.close();
        connection.close();
    }

    @Override
    public List<WeatherObject> getAllWeather() throws SQLException {
        weatherList = new ArrayList<>();
        connection = DriverManager.getConnection("jdbc:sqlite:" + GlobalState.getInstance().DB_NAME);
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT city, date, weather, mintemp, maxtemp FROM weather");
        while (resultSet.next()) {
            String cityName = resultSet.getString(1);
            String date = resultSet.getString(2);
            String weather = resultSet.getString(3);
            Double minTemperature = resultSet.getDouble(4);
            Double maxTemperature = resultSet.getDouble(5);
            WeatherObject weatherObject = new WeatherObject(cityName, date, weather, minTemperature, maxTemperature);
            weatherList.add(weatherObject);
        }
        statement.close();
        connection.close();
        return weatherList;
    }
}
