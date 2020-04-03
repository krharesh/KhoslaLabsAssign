package com.khosla.assignment.Model;

public class WeatherPoJo {
    public String temp;
    public String weather_date;
    public String weather_desc;

    public WeatherPoJo(String temp, String weather_date, String weather_desc) {
        this.temp = temp;
        this.weather_date = weather_date;
        this.weather_desc = weather_desc;
    }

    public String getWeather_desc() {
        return weather_desc;
    }

    public void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather_date() {
        return weather_date;
    }

    public void setWeather_date(String weather_date) {
        this.weather_date = weather_date;
    }
}
