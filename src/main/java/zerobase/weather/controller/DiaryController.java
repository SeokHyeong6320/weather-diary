package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.aop.TraceLog;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.DiaryInfo;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.service.DiaryService;
import zerobase.weather.service.WeatherService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final WeatherService weatherService;

    @TraceLog
    @Parameters({
            @Parameter(name = "date", description = "날짜", example = "2024-04-27"),
            @Parameter(name = "text", description = "일기내용", example = "안녕하세요")
    })
    @Operation(summary = "일기 등록", description = "일기 등록")
    @PostMapping("/create/diary")
    public void createDiary(
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @RequestParam String text
    ) {

        DateWeather dateWeather = weatherService.getDateWeatherFromDb(date);
        diaryService.createDiary(dateWeather, text);
    }

    @TraceLog
    @Parameter(name = "date", description = "날짜", example = "2024-04-27")
    @Operation(summary = "일기 조회", description = "일기 조회")
    @GetMapping("/read/diary")
    public List<DiaryInfo> readDiary
            (@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {

        List<DiaryDto> diaryInfos = diaryService.readDiary(date);
        return diaryInfos.stream()
                .map(DiaryInfo::fromDto)
                .collect(Collectors.toList());
    }

    @TraceLog
    @Parameters({
            @Parameter(name = "date", description = "날짜", example = "2024-04-27"),
            @Parameter(name = "text", description = "일기내용", example = "안녕하세요")
    })
    @Operation(summary = "일기 수정", description = "일기 수정")
    @PutMapping("/update/diary")
    public void updateDiary(
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @RequestParam String text
    ) {

        diaryService.updateDiary(date, text);
    }

    @TraceLog
    @Parameter(name = "date", description = "날짜", example = "2024-04-27")
    @Operation(summary = "일기 삭제", description = "일기 삭제")
    @DeleteMapping("/delete/diary")
    public void deleteDiary
            (@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }

}
