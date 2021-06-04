package lesson6;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HomeWork6 {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weather.yandex.ru")
                .addPathSegments("v2/forecast")
                .addQueryParameter("lat", "59.939099")
                .addQueryParameter("lon", "30.315877")
                .addQueryParameter("lang", "ru_RU")
                .addQueryParameter("limit", "5")
                .build();

        Request request = new Request.Builder()
                .addHeader("X-Yandex-API-Key", "0b26b18d-2c12-4270-a3b8-130232a74418")
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
