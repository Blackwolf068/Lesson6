package Lesson7.awesome_project.entity;

public class WeatherObject {
    private String city;
    private String date;
    private String weather;
    private Double minTemperature;
    private Double maxTemperature;

    public WeatherObject() {
    }

    public WeatherObject(String city, String date, String weather, Double minTemperature, Double maxTemperature) {
        this.city = city;
        this.date = date;
        this.weather = weather;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    @Override
    public String toString() {
        return "WeatherObject{" +
                "city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                '}';
    }
}
