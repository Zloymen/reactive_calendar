package ru.zenit.reactive.service.calendar.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;

import java.time.LocalDate;

public interface CalendarService {

    Flux<HolidayDto> getHolidayDtoBetween(LocalDate first, LocalDate last);

    Mono<Boolean> checkHoliday(LocalDate day);
}
