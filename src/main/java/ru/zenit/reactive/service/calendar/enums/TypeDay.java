package ru.zenit.reactive.service.calendar.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TypeDay {

    HOLIDAY(""),
    PREHOLIDAY("*"),
    TRANSFER_HOLIDAY("+"),
    ;


    public static TypeDay getTypeDayBySign(String sign){
        return Arrays.stream(TypeDay.values()).filter(typeDay -> typeDay.sign.equals(sign)).findFirst().orElse(HOLIDAY);
    }

    public static List<TypeDay> typeDaysWithoutHoliday(){
        return Arrays.stream(TypeDay.values()).filter(item -> item != HOLIDAY).collect(Collectors.toList());
    }

    public boolean isType(String sign){
        return this.sign.equals(sign);
    }

    private String sign;

    public String getSign() {
        return sign;
    }

    TypeDay(String sign){
        this.sign = sign;
    }
}
