package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.aop.TraceLog;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.exception.NoDiaryException;
import zerobase.weather.repositpry.DiaryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void createDiary(DateWeather dateWeather, String text) {

        diaryRepository.save(Diary.createNewDiary(dateWeather, text));
    }

    @TraceLog
    @Transactional(readOnly = true)
    public List<DiaryDto> readDiary(LocalDate date) {

        return diaryRepository
                .findAllByDate(date).stream()
                .map(DiaryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @TraceLog
    public void updateDiary(LocalDate date, String text) {
        diaryRepository
                .findFirstByDate(date).orElseThrow(() -> new NoDiaryException("해당 날짜의 일기가 없습니다"))
                .updateText(text);
    }

    @TraceLog
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }
}
