package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import zerobase.weather.repositpry.DateWeatherRepository;
import zerobase.weather.repositpry.DiaryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final DateWeatherRepository dateWeatherRepository;

    @Value("${openweathermap.key}")
    private String apiKey;

    public void createDiary(LocalDate date, String text) {

        DateWeather dateWeather = getDateWeatherFromDb(date);

        diaryRepository.save(Diary.createNewDiary(dateWeather, text));
    }

    private DateWeather getDateWeatherFromDb(LocalDate date) {
        return dateWeatherRepository
                .findByDate(date)
                .orElse(getDateWeatherFromApi());
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void saveDailyWeather() {
        dateWeatherRepository.save(getDateWeatherFromApi());
    }

    private DateWeather getDateWeatherFromApi() {
        String json = getJsonFromApi();

        return DateWeather.fromWeatherInfo(parseJson(json));
    }

    private String getJsonFromApi() {
        String apiUrl =
                "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="
                        + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if (responseCode / 100 == 2) {
                br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> parseJson(String json) {
        Map<String, Object> map = new HashMap<>();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(json);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherObject = (JSONObject) weatherArray.get(0);

        map.put("weather", weatherObject.get("main"));
        map.put("icon", weatherObject.get("icon"));
        map.put("temp", ((JSONObject) jsonObject.get("main")).get("temp"));

        return map;
    }


}
