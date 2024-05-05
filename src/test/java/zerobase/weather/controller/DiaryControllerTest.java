package zerobase.weather.controller;

import jakarta.persistence.Table;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.dto.DiaryDto;
import zerobase.weather.repositpry.DiaryRepository;
import zerobase.weather.service.DiaryService;
import zerobase.weather.service.WeatherService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.*;
import static java.time.format.DateTimeFormatter.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiaryController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DiaryControllerTest {

    @MockBean
    DiaryService diaryService;
    @MockBean
    WeatherService weatherService;
    @MockBean
    DiaryRepository diaryRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void successCreateDiaryController() throws Exception {
        // given
        DateWeather dateWeather = DateWeather.builder()
                .date(now())
                .weather("clear")
                .icon("icon")
                .temperature(25.0)
                .build();
        given(weatherService.getDateWeatherFromDb(any()))
                .willReturn(dateWeather);

        // when
        // then
        mockMvc.perform(post("/create/diary")
                        .param("date", now().format(ISO_DATE))
                        .param("text", "hello")
                ).andDo(print())
                .andExpect(status().isOk());
        verify(weatherService, times(1))
                .getDateWeatherFromDb(now());
        verify(diaryService, times(1))
                .createDiary(dateWeather, "hello");
    }

    @Test
    void successReadDiaryController() throws Exception {
        // given
        List<DiaryDto> list =
                new ArrayList<>(Arrays.asList(
                        DiaryDto.builder()
                                .weather("weather1")
                                .icon("icon1")
                                .temperature(11.11)
                                .text("text1")
                                .date(now()).build(),
                        DiaryDto.builder()
                                .weather("weather2")
                                .icon("icon2")
                                .temperature(22.22)
                                .text("text2")
                                .date(now()).build()
                ));

        given(diaryService.readDiary(any()))
                .willReturn(list);
        // when
        // then
        mockMvc.perform(get("/read/diary")
                .param("date", "2024-04-27"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weather")
                        .value("weather1"))
                .andExpect(jsonPath("$[0].icon")
                        .value("icon1"))
                .andExpect(jsonPath("$[0].temperature")
                        .value(11.11))
                .andExpect(jsonPath("$[0].text")
                        .value("text1"))
                .andExpect(jsonPath("$[0].date")
                        .value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].weather")
                        .value("weather2"))
                .andExpect(jsonPath("$[1].icon")
                        .value("icon2"))
                .andExpect(jsonPath("$[1].temperature")
                        .value(22.22))
                .andExpect(jsonPath("$[1].text")
                        .value("text2"))
                .andExpect(jsonPath("$[1].date")
                        .value(String.valueOf(LocalDate.now())));
    }


    @Test
    void successUpdateDiaryController() throws Exception {
        // given
        Diary diary = Diary.builder()
                .id(1)
                .date(now())
                .text("text1")
                .build();
        given(diaryRepository.findFirstByDate(any()))
                .willReturn(Optional.of(diary));


        // when

        // then
        mockMvc.perform(put("/update/diary")
                .param("date", String.valueOf(now()))
                .param("text", "hello"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(diaryService, times(1)).updateDiary(now(), "hello");
    }

    @Test
    void failUpdateDiaryController() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(put("/update/diary")
                        .param("date", String.valueOf(now()))
                        .param("text", "hello"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
        verify(diaryService, times(1)).updateDiary(now(), "hello");
    }

    @Test
    void successDeleteDiaryController() throws Exception {

        // when
        // then
        mockMvc.perform(delete("/delete/diary")
                        .param("date", String.valueOf(now())))
                .andDo(print())
                .andExpect(status().isOk());
        verify(diaryService, times(1)).deleteDiary(now());
    }


}