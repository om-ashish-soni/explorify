package com.learn.explorify.client;

import com.learn.explorify.model.Weather;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;
public interface WeatherClient {
    @GetExchange("/weather/get")
    public Weather getWeather();
}
