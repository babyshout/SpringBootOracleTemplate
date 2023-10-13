package kopo.poly.service.impl;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {
    @Override
    public WeatherDTO todayWeather() throws IOException {
        log.info(this.getClass().getName() + ".todayWeather() START!!!!!!!!!!!!!!!");

        String url = "https://weather.naver.com/";

        WeatherDTO rDTO = null;

        Document HTML = Jsoup.connect(url).get();

        Elements weather_area = HTML.select("div.weather_area");
        log.info("weather_area : " + weather_area.text());

        Element el = weather_area.get(0);



        log.info(this.getClass().getName() + ".todayWeather() END!!!!!!!!!!!!!!!");
        return rDTO;
    }
}
