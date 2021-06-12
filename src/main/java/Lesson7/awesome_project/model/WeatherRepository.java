package Lesson7.awesome_project.model;

import Lesson7.awesome_project.entity.WeatherObject;

import java.sql.SQLException;
import java.util.List;

public class WeatherRepository implements IWeatherRepository{

    IWeatherProvider apiWeatherProvider = new AccuWeatherProvider();
    ILocalStorageProvider localStorageProvider = new SqLiteWeatherStorage();

    @Override
    public List<WeatherObject> fetchWeatherFromApi(Period period) throws SQLException {
        List<WeatherObject> weather = apiWeatherProvider.getWeather(period);
        saveWeatherInfoInDb(weather);
        return weather;
    }

    @Override
    public List<WeatherObject> readWeatherFromDb() throws SQLException {
        List<WeatherObject> dbResult = localStorageProvider.getAllWeather();
        for (int i = 0; i < dbResult.size(); i++) {
            System.out.printf("В городе %s на дату %s погода %s мин. температура %2.1f, макс. температура %2.1f\n",
                    dbResult.get(i).getCity(), dbResult.get(i).getDate(), dbResult.get(i).getWeather(),
                    dbResult.get(i).getMinTemperature(), dbResult.get(i).getMaxTemperature());
        }
        return dbResult;
    }

    @Override
    public void saveWeatherInfoInDb(List<WeatherObject> weatherObjects) throws SQLException {
        for (WeatherObject weatherObject: weatherObjects) {
            localStorageProvider.saveWeather(weatherObject);
        }
    }
}
