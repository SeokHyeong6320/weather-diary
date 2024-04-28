package zerobase.weather.dto;

import lombok.*;
import zerobase.weather.domain.Diary;

import java.time.LocalDate;


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
