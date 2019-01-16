package ru.zenit.reactive.service.calendar.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalendarError extends RuntimeException {

    @Getter
    private final CalendarErrorEnum errorEnum;
}
