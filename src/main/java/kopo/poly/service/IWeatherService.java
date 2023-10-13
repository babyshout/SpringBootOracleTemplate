package kopo.poly.service;

import kopo.poly.dto.WeatherDTO;

import java.io.IOException;

public interface IWeatherService {
    WeatherDTO todayWeather() throws IOException;
}
