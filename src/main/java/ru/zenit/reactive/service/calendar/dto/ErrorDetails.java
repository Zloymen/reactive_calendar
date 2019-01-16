package ru.zenit.reactive.service.calendar.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorDetails {

    private final String type;

    private final String description;
}
