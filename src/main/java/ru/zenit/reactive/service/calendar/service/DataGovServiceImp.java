package ru.zenit.reactive.service.calendar.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.dao.HolidayRepository;
import ru.zenit.reactive.service.calendar.dto.AnswerDto;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;
import ru.zenit.reactive.service.calendar.entity.Holiday;
import ru.zenit.reactive.service.calendar.enums.TypeDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataGovServiceImp implements DataGovService {

    private final HolidayRepository holidayRepository;

    @Value("${app.data.url}")
    private String url;

    @Value("${app.data.token}")
    private String token;

    private WebClient client = WebClient.create();


    @CacheEvict(value = {"calendarByDayBetween", "checkDay"}, allEntries = true)
    @Override
    public void updateCalendarByYear(Integer year) {
        UriComponentsBuilder builder = fromHttpUrl(url).queryParam("access_token", token).queryParam("search", year);

        client.get().uri(uriBuilder -> builder.build().toUri())
                .exchange()
                .flatMap(item -> item.bodyToMono(new ParameterizedTypeReference<List<AnswerDto>>() {}))
                .flatMap(item -> item.isEmpty() ? Mono.empty() : Mono.just(item.get(0)))
                .subscribe(dto -> {

                    List<HolidayDto> newHolidayDto = new ArrayList<>();

                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth1().split(",")), year, 1));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth2().split(",")), year, 2));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth3().split(",")), year, 3));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth4().split(",")), year, 4));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth5().split(",")), year, 5));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth6().split(",")), year, 6));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth7().split(",")), year, 7));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth8().split(",")), year, 8));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth9().split(",")), year, 9));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth10().split(",")), year, 10));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth11().split(",")), year, 11));
                    newHolidayDto.addAll(getMonthHoliday(Arrays.stream(dto.getMonth12().split(",")), year, 12));

                    Set<LocalDate> newHolidayDate = newHolidayDto
                            .stream()
                            .map(HolidayDto::getDay)
                            .collect(Collectors.toSet());

                    Flux.fromIterable(newHolidayDto).subscribe(newHoliday ->
                        holidayRepository.getByDay(newHoliday.getDay())
                                .defaultIfEmpty(new Holiday())
                                .subscribe(item ->
                                        holidayRepository.save(item.refresh(newHoliday)).subscribe())
                    );


                    holidayRepository.getAllByDayBetween(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
                            .filter(item -> !newHolidayDate.contains(item.getDay()))
                            .subscribe(item -> holidayRepository.delete(item).subscribe());
                });

    }

    private List<HolidayDto> getMonthHoliday(Stream<String> days, Integer year, Integer month) {
        return days.map(item -> createHoliday(year, month, item)).collect(Collectors.toList());
    }

    private HolidayDto createHoliday(Integer year, Integer month, String day) {

        TypeDay type = TypeDay.typeDaysWithoutHoliday().stream()
                .filter(item -> day.contains(item.getSign()))
                .findFirst().orElse(TypeDay.HOLIDAY);

        return new HolidayDto(LocalDate.of(year, month, Integer.valueOf(day.replace(type.getSign(), ""))), type);
    }

}
