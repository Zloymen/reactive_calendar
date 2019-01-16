package ru.zenit.reactive.service.calendar.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zenit.reactive.service.calendar.enums.TypeDay;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {

    @ApiModelProperty(notes = "Праздничный день")
    private LocalDate day;

    @ApiModelProperty(
            notes = "Тип праздничного дня",
            example = "HOLIDAY - праздничный, PREHOLIDAY - предпразничный, TRANSFER_HOLIDAY - перенесенный день")
    private TypeDay type;

}