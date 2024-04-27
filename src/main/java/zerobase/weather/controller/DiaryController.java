package zerobase.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import zerobase.weather.service.DiaryService;

@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;



}
