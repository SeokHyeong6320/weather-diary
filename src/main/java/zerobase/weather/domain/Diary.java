package zerobase.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String weather;

    private String icon;

    private Double temperature;

    private String text;

    private LocalDate date;

    public static Diary createNewDiary
            (DateWeather dateWeather, String text) {

        return Diary.builder()
                .weather(dateWeather.getWeather())
                .icon(dateWeather.getIcon())
                .temperature(dateWeather.getTemperature())
                .text(text)
                .date(dateWeather.getDate())
                .build();
    }

    public void updateText(String text) {
        this.text = text;
    }


}
