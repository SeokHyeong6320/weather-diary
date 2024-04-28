package zerobase.weather.domain;

import lombok.*;
import zerobase.weather.dto.DiaryDto;

import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryInfo {

    private String weather;

    private String icon;

    private Double temperature;

    private String text;

    private LocalDate date;

    public static DiaryInfo fromDto(DiaryDto diaryDto) {
        return DiaryInfo.builder()
                .weather(diaryDto.getWeather())
                .icon(diaryDto.getIcon())
                .temperature(diaryDto.getTemperature())
                .text(diaryDto.getText())
                .date(diaryDto.getDate())
                .build();
    }


}
