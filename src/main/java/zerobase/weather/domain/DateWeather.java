package zerobase.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "date_weather")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateWeather {

    @Id
    private LocalDate date;

    private String weather;

    private String icon;

    private double temperature;

    public static DateWeather fromWeatherInfo(Map<String, Object> weatherInfo) {
        return DateWeather.builder()
                .weather(weatherInfo.get("weather").toString())
                .icon(weatherInfo.get("icon").toString())
                .temperature((Double) weatherInfo.get("temp"))
                .date(LocalDate.now())
                .build();
    }
}
