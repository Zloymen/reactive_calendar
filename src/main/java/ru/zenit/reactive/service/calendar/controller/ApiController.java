package ru.zenit.reactive.service.calendar.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;
import ru.zenit.reactive.service.calendar.service.CalendarService;
import ru.zenit.reactive.service.calendar.service.DataGovService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/json")
public class ApiController {

    private final DataGovService govService;

    private final CalendarService calendarService;

    @ApiOperation(value = "Обновить справочник за год")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Обновление прошло успешно"),
            @ApiResponse(code = 500, message = "Произошла ошибка"),
    })
    @PostMapping("/update/{year}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCalendarByYear(@PathVariable Integer year){
        govService.updateCalendarByYear(year);
    }


    @ApiOperation(value = "получить список праздничных дней за заданный период")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 500, message = "Произошла ошибка"),
    })
    @GetMapping("/day/between")
    public Flux<HolidayDto> getDayBetween(@RequestParam LocalDate first, @RequestParam LocalDate last){
        return calendarService.getHolidayDtoBetween(first, last);
    }

    @ApiOperation(value = "праздничный день?")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 500, message = "Произошла ошибка"),
    })
    @GetMapping("/isHoliday")
    public Mono<Boolean> isHoliday(@RequestParam LocalDate day){
        return calendarService.checkHoliday(day);
    }

}
