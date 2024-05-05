package zerobase.weather.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.repositpry.DiaryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @InjectMocks
    DiaryService diaryService;
    @Mock
    DiaryRepository diaryRepository;

    @Test
    @DisplayName("일기 작성 성공")
    void successCreateDiary() throws Exception {
        // given
        Diary diary = Diary.builder()
                .id(1)
                .weather("weather")
                .icon("icon")
                .temperature(11.11)
                .text("text")
                .date(now())
                .build();

        DateWeather dateWeather = DateWeather.builder()
                .date(now())
                .weather("weather")
                .icon("icon")
                .temperature(11.11)
                .build();

        MockedStatic<Diary> diaryMockedStatic = mockStatic(Diary.class);
        diaryMockedStatic.when(() -> Diary.createNewDiary(any(), anyString()))
                .thenReturn(diary);

        // when
        ArgumentCaptor<Diary> captor = ArgumentCaptor.forClass(Diary.class);
        diaryService.createDiary(dateWeather, "hello");

        // then
        verify(diaryRepository, times(1))
                .save(captor.capture());
        assertThat(captor.getValue().getWeather()).isEqualTo("weather");
        assertThat(captor.getValue().getIcon()).isEqualTo("icon");
        assertThat(captor.getValue().getDate()).isEqualTo(now());
        assertThat(captor.getValue().getTemperature()).isEqualTo(11.11);
        assertThat(captor.getValue().getText()).isEqualTo("text");
    }

    @Test
    void successReadDiary() throws Exception {
        // given
        Diary diary1 = Diary.builder()
                .weather("weather1")
                .temperature(11.11)
                .text("text1")
                .date(now())
                .build();
        Diary diary2 = Diary.builder()
                .weather("weather2")
                .temperature(22.22)
                .text("text2")
                .date(now())
                .build();
        List<Diary> list = new ArrayList<>(Arrays.asList(diary1, diary2));

        given(diaryRepository.findAllByDate(any()))
                .willReturn(list);

        // when
        List<DiaryDto> dtos = diaryService.readDiary(now());

        // then
        assertThat(dtos.get(0).getWeather()).isEqualTo("weather1");
        assertThat(dtos.get(0).getTemperature()).isEqualTo(11.11);
        assertThat(dtos.get(0).getText()).isEqualTo("text1");
        assertThat(dtos.get(0).getDate()).isEqualTo(now());

        assertThat(dtos.get(1).getWeather()).isEqualTo("weather2");
        assertThat(dtos.get(1).getTemperature()).isEqualTo(22.22);
        assertThat(dtos.get(1).getText()).isEqualTo("text2");
        assertThat(dtos.get(1).getDate()).isEqualTo(now());

    }

}