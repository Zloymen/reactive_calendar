package ru.zenit.reactive.service.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerDto {

    @JsonProperty(value="Январь")
    private String month1;

    @JsonProperty(value="Февраль")
    private String month2;

    @JsonProperty(value="Март")
    private String month3;

    @JsonProperty(value="Апрель")
    private String month4;

    @JsonProperty(value="Май")
    private String month5;

    @JsonProperty(value="Июнь")
    private String month6;

    @JsonProperty(value="Июль")
    private String month7;

    @JsonProperty(value="Август")
    private String month8;

    @JsonProperty(value="Сентябрь")
    private String month9;

    @JsonProperty(value="Октябрь")
    private String month10;

    @JsonProperty(value="Ноябрь")
    private String month11;

    @JsonProperty(value="Декабрь")
    private String month12;

}
