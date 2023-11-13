package kopo.poly.service;

import kopo.poly.dto.WeatherDTO;

import java.io.IOException;

public interface IWeatherCrawlingService {
    WeatherDTO todayWeather() throws IOException;
}
