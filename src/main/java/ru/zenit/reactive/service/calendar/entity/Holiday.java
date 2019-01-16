package ru.zenit.reactive.service.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.zenit.reactive.service.calendar.dto.HolidayDto;
import ru.zenit.reactive.service.calendar.enums.TypeDay;

import java.time.LocalDate;

@Document(collection = "holidays")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {

    @Id
    private String id;

    private LocalDate day;

    private TypeDay type;

    public Holiday refresh(HolidayDto dto){
        this.day = dto.getDay();
        this.type = dto.getType();
        return this;
    }

}
