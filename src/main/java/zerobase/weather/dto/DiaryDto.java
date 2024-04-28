package zerobase.weather.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zerobase.weather.domain.Diary;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDto {

    private String weather;

    private String icon;

    private Double temperature;

    private String text;

    private LocalDate date;


    public static DiaryDto fromEntity(Diary diary) {
        return DiaryDto.builder()
                .weather(diary.getWeather())
                .icon(diary.getIcon())
                .temperature(diary.getTemperature())
                .text(diary.getText())
                .date(diary.getDate())
                .build();
    }




}
