package ru.zenit.reactive.service.calendar.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.entity.Holiday;
import ru.zenit.reactive.service.calendar.enums.TypeDay;

import java.time.LocalDate;

@Repository
public interface HolidayRepository extends ReactiveMongoRepository<Holiday, Long> {

    Flux<Holiday> getAllByDayBetween(LocalDate first, LocalDate last);

    Mono<Holiday> getByDayAndTypeNot(LocalDate day, TypeDay type);

    Mono<Holiday> getByDay(LocalDate day);

}
