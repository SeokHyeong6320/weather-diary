package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.repositpry.DateWeatherRepository;
import zerobase.weather.repositpry.DiaryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void createDiary(DateWeather dateWeather, String text) {

        diaryRepository.save(Diary.createNewDiary(dateWeather, text));
    }

    public List<DiaryDto> readDiary(LocalDate date) {

        return diaryRepository
                .findAllByDate(date).stream()
                .map(DiaryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void updateDiary(LocalDate date, String text) {
        diaryRepository
                .findFirstByDate(date).orElseThrow()
                .updateText(text);
    }

    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }
}
