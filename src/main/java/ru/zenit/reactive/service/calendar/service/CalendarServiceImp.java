package ru.zenit.reactive.service.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.dao.HolidayRepository;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;

import java.time.LocalDate;

import static ru.zenit.reactive.service.calendar.enums.TypeDay.PREHOLIDAY;

@Service
@RequiredArgsConstructor
public class CalendarServiceImp implements CalendarService {

    private final HolidayRepository holidayDao;

    private final ConverterService converterService;

    @Cacheable("calendarByDayBetween")
    @Override
    public Flux<HolidayDto> getHolidayDtoBetween(LocalDate first, LocalDate last) {
        return holidayDao.getAllByDayBetween(first, last)
                .flatMap(item -> Mono.just(converterService.convertHolidayEntityToDto(item)));
    }

    @Cacheable("checkDay")
    @Override
    public Mono<Boolean> checkHoliday(LocalDate day) {
        return holidayDao
                .getByDayAndTypeNot(day, PREHOLIDAY)
                .flatMap(holiday -> Mono.just(Boolean.TRUE))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
