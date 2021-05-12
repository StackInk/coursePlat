package com.bywlstudio.course.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;


public class DateTimeUtils {

    ZoneId defaultZone = ZoneId.systemDefault();


    public static boolean isAfter(LocalDateTime currentTime, LocalDateTime target) {
        return currentTime.isAfter(target);
    }

    public static boolean isBefore(LocalDateTime currentTime, LocalDateTime target) {
        return currentTime.isBefore(target);
    }


    public static boolean isAfter(Long currentTime, Long target) {
        Instant instant1 = Instant.ofEpochMilli(currentTime);
        Instant instant2 = Instant.ofEpochMilli(target);
        return instant1.isAfter(instant2);
    }

    public static boolean isBefore(Long currentTime, Long target) {
        Instant instant1 = Instant.ofEpochMilli(currentTime);
        Instant instant2 = Instant.ofEpochMilli(target);
        return instant1.isBefore(instant2);
    }

    public static Long coverDayToMill() {
        return TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS);
    }

}
