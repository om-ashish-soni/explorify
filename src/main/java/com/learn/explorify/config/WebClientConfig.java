package com.learn.explorify.config;

import com.learn.explorify.client.WeatherClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(@Value("${weather-service.url}") String weatherServiceUrl){
        return WebClient.builder()
                .baseUrl(weatherServiceUrl)
                .build();
    }

    @Bean
    public WeatherClient weatherClient(WebClient webClient){
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(WeatherClient.class);
    }
}
