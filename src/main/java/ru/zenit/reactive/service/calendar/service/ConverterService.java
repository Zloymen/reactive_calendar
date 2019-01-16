package ru.zenit.reactive.service.calendar.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;
import ru.zenit.reactive.service.calendar.entity.Holiday;

@Mapper(componentModel="spring")
public interface ConverterService {

    @Mappings({
            @Mapping(target="day", source="entity.day"),
            @Mapping(target="type", source="entity.type")
    })
    HolidayDto convertHolidayEntityToDto(Holiday entity);
    @Mappings({
            @Mapping(target="day", source="dto.day"),
            @Mapping(target="type", source="dto.type")
    })
    Holiday convertHolidayDtoToEntity(HolidayDto dto);
}
