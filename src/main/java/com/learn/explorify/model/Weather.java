package com.learn.explorify.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
    private int temperature;
    private boolean rainy;
    private boolean sunny;
    private boolean cool;
    private boolean cloudy;
}
