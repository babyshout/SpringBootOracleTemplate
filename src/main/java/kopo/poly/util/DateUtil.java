package kopo.poly.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    /**
     * 날짜, 시간 출력하기
     * @param fm 날짜 출력 형식
     * @return date
     */
    public static String getDateTime(String fm) {

        Date today = new Date();
        System.out.println(today);

        SimpleDateFormat date = new SimpleDateFormat(fm);

        return date.format(today);
    }

    /**
     * 날짜, 시간 출력하기
     * @return 기본값은 년.월.일
     */
    public static String getDateTime() {
        return getDateTime("yyyy.MM.dd");

    }

    public static String getLongDateTime(Object time) {
        return getLongDateTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getLongDateTime(Integer time) {
        return getLongDateTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getLongDateTime(Object time, String dateFormat) {
        return getLongDateTime((Integer) time, dateFormat);
    }

    public static String getLongDateTime(Integer time, String dateFormat) {
        Instant instant = Instant.ofEpochSecond(time);
        return DateTimeFormatter.ofPattern(dateFormat)
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

}
