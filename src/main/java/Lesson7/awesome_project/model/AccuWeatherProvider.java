package Lesson7.awesome_project.model;

import Lesson7.awesome_project.GlobalState;
import Lesson7.awesome_project.entity.WeatherObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccuWeatherProvider implements IWeatherProvider {

    private final String BASE_HOST = "dataservice.accuweather.com";
    private final String VERSION = "v1";
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String cityName, days, date, weather;
    private Double minTemperature, maxTemperature;
    private List<WeatherObject> weatherList;


    private Request requestBuilder(HttpUrl url) {
        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(url)
                .build();
        return request;
    }

    @Override
    public List<WeatherObject> getWeather(Period period) {
        String key = detectCityKeyByName();
        cityName = GlobalState.getInstance().getSelectedCity();
        if (period.equals(Period.NOW)) {
            days = "1day";
        } else if (period.equals(Period.FIVE_DAYS)) {
            days = "5day";
        }
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("forecasts")
                .addPathSegment(VERSION)
                .addPathSegment("daily")
                .addPathSegment(days)
                .addPathSegment(key)
                .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                .addQueryParameter("language", "ru-RU")
                .addQueryParameter("metric", "true")
                .build();

        Request request = requestBuilder(url);

        try {
//            String response = "{\"Headline\":{\"EffectiveDate\":\"2021-06-12T07:00:00+03:00\",\"EffectiveEpochDate\":1623470400,\"Severity\":5,\"Text\":\"Суббота, утро: ожидаются ливни\",\"Category\":\"rain\",\"EndDate\":\"2021-06-12T13:00:00+03:00\",\"EndEpochDate\":1623492000,\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/extended-weather-forecast/296079?unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?unit=c\"},\"DailyForecasts\":[{\"Date\":\"2021-06-12T07:00:00+03:00\",\"EpochDate\":1623470400,\"Temperature\":{\"Minimum\":{\"Value\":12.8,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":20.5,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":12,\"IconPhrase\":\"Ливни\",\"HasPrecipitation\":true,\"PrecipitationType\":\"Rain\",\"PrecipitationIntensity\":\"Light\"},\"Night\":{\"Icon\":36,\"IconPhrase\":\"Переменная облачность\",\"HasPrecipitation\":true,\"PrecipitationType\":\"Rain\",\"PrecipitationIntensity\":\"Light\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=1&unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=1&unit=c\"},{\"Date\":\"2021-06-13T07:00:00+03:00\",\"EpochDate\":1623556800,\"Temperature\":{\"Minimum\":{\"Value\":13.6,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":24.6,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":14,\"IconPhrase\":\"Небольшая облачность с дождями\",\"HasPrecipitation\":true,\"PrecipitationType\":\"Rain\",\"PrecipitationIntensity\":\"Light\"},\"Night\":{\"Icon\":35,\"IconPhrase\":\"Облачно с прояснениями\",\"HasPrecipitation\":false},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=2&unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=2&unit=c\"},{\"Date\":\"2021-06-14T07:00:00+03:00\",\"EpochDate\":1623643200,\"Temperature\":{\"Minimum\":{\"Value\":13.6,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":23.3,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":3,\"IconPhrase\":\"Небольшая облачность\",\"HasPrecipitation\":false},\"Night\":{\"Icon\":35,\"IconPhrase\":\"Облачно с прояснениями\",\"HasPrecipitation\":false},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=3&unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=3&unit=c\"},{\"Date\":\"2021-06-15T07:00:00+03:00\",\"EpochDate\":1623729600,\"Temperature\":{\"Minimum\":{\"Value\":13.8,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":25.0,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":14,\"IconPhrase\":\"Небольшая облачность с дождями\",\"HasPrecipitation\":true,\"PrecipitationType\":\"Rain\",\"PrecipitationIntensity\":\"Light\"},\"Night\":{\"Icon\":36,\"IconPhrase\":\"Переменная облачность\",\"HasPrecipitation\":false},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=4&unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=4&unit=c\"},{\"Date\":\"2021-06-16T07:00:00+03:00\",\"EpochDate\":1623816000,\"Temperature\":{\"Minimum\":{\"Value\":11.1,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":22.7,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":3,\"IconPhrase\":\"Небольшая облачность\",\"HasPrecipitation\":false},\"Night\":{\"Icon\":34,\"IconPhrase\":\"Преимущественно ясно\",\"HasPrecipitation\":false},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=5&unit=c\",\"Link\":\"http://www.accuweather.com/ru/ru/tver/296079/daily-weather-forecast/296079?day=5&unit=c\"}]}";
//            String respStr = response;
            Response response = okHttpClient.newCall(request).execute();
            String respStr = response.body().string();
            weatherList = new ArrayList<>();
            if (period.equals(Period.NOW)) {
                date = objectMapper.readTree(respStr).at("/DailyForecasts/0/Date").asText().substring(0, 10);
                weather = objectMapper.readTree(respStr).at("/DailyForecasts/0/Day/IconPhrase").asText();
                minTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Minimum/Value").asDouble();
                maxTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Maximum/Value").asDouble();
                WeatherObject weatherObject = new WeatherObject(cityName, date, weather, minTemperature, maxTemperature);
                weatherList.add(weatherObject);
                System.out.printf("В городе %s на дату %s ожидается %s, температура от %2.1f до %2.1f градусов Цельсия\n", cityName, date, weather, minTemperature, maxTemperature);
            } else if (period.equals(Period.FIVE_DAYS)) {
                System.out.printf("В городе %s\n", cityName);
                for (int i = 0; i < 5; i++) {
                    date = objectMapper.readTree(respStr).at("/DailyForecasts/" + i + "/Date").asText().substring(0, 10);
                    weather = objectMapper.readTree(respStr).at("/DailyForecasts/" + i + "/Day/IconPhrase").asText();
                    minTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/" + i + "/Temperature/Minimum/Value").asDouble();
                    maxTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/" + i + "/Temperature/Maximum/Value").asDouble();
                    WeatherObject weatherObject = new WeatherObject(cityName, date, weather, minTemperature, maxTemperature);
                    weatherList.add(weatherObject);
                    System.out.printf("на дату %s ожидается %s, температура от %2.1f до %2.1f градусов Цельсия\n", date, weather, minTemperature, maxTemperature);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherList;
    }

    private String detectCityKeyByName() {
        String selectedCity = GlobalState.getInstance().getSelectedCity();

        HttpUrl detectLocationURL = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("locations")
                .addPathSegment(VERSION)
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(detectLocationURL)
                .build();

        Response locationResponse = null;
        try {
            locationResponse = okHttpClient.newCall(request).execute();

            if (!locationResponse.isSuccessful()) {
                throw new RuntimeException("Сервер ответил " + locationResponse.code());
            }

            String jsonResponse = locationResponse.body().string();

            if (objectMapper.readTree(jsonResponse).size() > 0) {
                String code = objectMapper.readTree(jsonResponse).get(0).at("/Key").asText();
                String cityName = objectMapper.readTree(jsonResponse).get(0).at("/LocalizedName").asText();
                String countryName = objectMapper.readTree(jsonResponse).get(0).at("/Country/LocalizedName").asText();

                System.out.printf("Найден город %s в стране %s, код - %s\n", cityName, countryName, code);
                return code;
            } else {
                throw new RuntimeException(selectedCity + " - такой город не найден");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
