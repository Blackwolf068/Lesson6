package Lesson7.awesome_project.model;

import Lesson7.awesome_project.GlobalState;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AccuWeatherProvider implements IWeatherProvider {

    private final String BASE_HOST = "dataservice.accuweather.com";
    private final String VERSION = "v1";
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String days;
    private String cityName;
    private String date;
    private String weather;
    private String minTemperature;
    private String maxTemperature;


    private Request requestBuilder(HttpUrl url) {
        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(url)
                .build();
        return request;
    }

    @Override
    public void getWeather(Period period) {
        String key = detectCityKeyByName();
        if (period.equals(Period.NOW)) {
            days = "1day";
        } else if (period.equals(Period.FIVE_DAYS)){
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
            Response response = okHttpClient.newCall(request).execute();
            String respStr = response.body().string();
            System.out.println(respStr);

            date = objectMapper.readTree(respStr).at("/DailyForecasts/0/Date").asText();
            weather = objectMapper.readTree(respStr).at("/DailyForecasts/0/Day/IconPhrase").asText();
            minTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Minimum/Value").asText();
            maxTemperature = objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Maximum/Value").asText();
            if (period.equals(Period.NOW)) {
                System.out.printf("В городе %s на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n", cityName, date, weather, minTemperature, maxTemperature);
            } else if (period.equals(Period.FIVE_DAYS)) {
                System.out.printf("В городе %s", cityName);
                System.out.printf("на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n",
                        objectMapper.readTree(respStr).at("/DailyForecasts/0/Date").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/0/Day/IconPhrase").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Minimum/Value").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/0/Temperature/Maximum/Value").asText());
                System.out.printf("на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n",
                        objectMapper.readTree(respStr).at("/DailyForecasts/1/Date").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/1/Day/IconPhrase").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/1/Temperature/Minimum/Value").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/1/Temperature/Maximum/Value").asText());
                System.out.printf("на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n",
                        objectMapper.readTree(respStr).at("/DailyForecasts/2/Date").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/2/Day/IconPhrase").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/2/Temperature/Minimum/Value").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/2/Temperature/Maximum/Value").asText());
                System.out.printf("на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n",
                        objectMapper.readTree(respStr).at("/DailyForecasts/3/Date").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/3/Day/IconPhrase").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/3/Temperature/Minimum/Value").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/3/Temperature/Maximum/Value").asText());
                System.out.printf("на дату %s ожидается %s, температура от %s до %s градусов Цельсия\n",
                        objectMapper.readTree(respStr).at("/DailyForecasts/4/Date").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/4/Day/IconPhrase").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/4/Temperature/Minimum/Value").asText(),
                        objectMapper.readTree(respStr).at("/DailyForecasts/4/Temperature/Maximum/Value").asText());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        Request request = requestBuilder(detectLocationURL);

        Response locationResponse = null;
        try {
            locationResponse = okHttpClient.newCall(request).execute();

            if (!locationResponse.isSuccessful()) {
                throw new RuntimeException("Сервер ответил " + locationResponse.code());
            }

            String jsonResponse = locationResponse.body().string();

            if (objectMapper.readTree(jsonResponse).size() > 0) {
                String code = objectMapper.readTree(jsonResponse).get(0).at("/Key").asText();
                cityName = objectMapper.readTree(jsonResponse).get(0).at("/LocalizedName").asText();
                String countryName = objectMapper.readTree(jsonResponse).get(0).at("/Country/LocalizedName").asText();

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
