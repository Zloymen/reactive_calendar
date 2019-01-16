package ru.zenit.reactive.service.calendar.error;

public enum  CalendarErrorEnum {

    GOV_DATA_NOT_FOUND("данные на госуслугах не найдены")
    ;


    private String desc;

    public String getDesc() {
        return desc;
    }

    CalendarErrorEnum(String desc){
        this.desc = desc;
    }
}
