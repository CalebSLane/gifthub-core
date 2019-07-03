package ca.csl.gifthub.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private DateUtil() {}

    public static Calendar getCalendarNow() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public static Calendar getCalendarToday() {
        Calendar calendar = getCalendarNow();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date getDateNow() {
        return getCalendarNow().getTime();
    }

    public static Date getDateToday() {
        return getCalendarToday().getTime();
    }

    public static Date getDateFromToday(int day, int month, int year) {
        Calendar calendar = getCalendarToday();
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
