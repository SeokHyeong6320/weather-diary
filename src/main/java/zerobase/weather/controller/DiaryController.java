package zerobase.weather.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.DiaryInfo;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.service.DiaryService;
import zerobase.weather.service.WeatherService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final WeatherService weatherService;

    @PostMapping("/create/diary")
    public void createDiary(
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @RequestParam String text
    ) {

        DateWeather dateWeather = weatherService.getDateWeatherFromDb(date);
        diaryService.createDiary(dateWeather, text);
    }

    @GetMapping("/read/diary")
    public List<DiaryInfo> readDiary
            (@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {

        List<DiaryDto> diaryInfos =  diaryService.readDiary(date);
        return diaryInfos.stream()
                .map(DiaryInfo::fromDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update/diary")
    public void updateDiary(
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @RequestParam String text
    ) {

        diaryService.updateDiary(date, text);
    }

    @DeleteMapping("/delete/diary")
    public void deleteDiary
            (@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }

}
