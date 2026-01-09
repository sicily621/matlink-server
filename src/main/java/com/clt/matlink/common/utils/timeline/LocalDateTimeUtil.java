package com.clt.matlink.common.utils.timeline;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LocalDateTimeUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter DATETIME_FORMATTER_TO_HOUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");
    public static DateTimeFormatter DATETIME_FORMATTER_TO_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
    public static DateTimeFormatter DATETIME_FORMATTER_TO_MONTH = DateTimeFormatter.ofPattern("yyyy-MM-01 00:00:00");
    public static DateTimeFormatter DATETIME_FORMATTER_TO_YEAR = DateTimeFormatter.ofPattern("yyyy-01-01");

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();// An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();// A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();// An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();// A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalTime date2LocalTime(Date date) {
        Instant instant = date.toInstant();// An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();// A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalTime localTime = instant.atZone(zoneId).toLocalTime();
        return localTime;
    }

    /**
     * 获取当前系统日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前系统日期字符串
     *
     * @return
     */
    public static String getLocalDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前系统日期时间
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前系统日期时间字符串
     *
     * @return
     */
    public static String getLocalDateTimeString() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前系统时间字符串
     *
     * @return
     */
    public static String getLocalTimeString() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);// Combines this date-time with a time-zone to
        // create a
        // ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 字符串转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate string2LocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * 字符串转LocalDateTime
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }

    /**
     * 字符串转LocalTime
     *
     * @param time
     * @return
     */
    public static LocalTime string2LocalTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    public static int getHoursBetween(Date startTime, Date endTime) {
        LocalDateTime startLocalDateTime = date2LocalDateTime(startTime);
        String startStr = startLocalDateTime.format(DATETIME_FORMATTER_TO_HOUR);
        startLocalDateTime = string2LocalDateTime(startStr);

        LocalDateTime endLocalDateTime = date2LocalDateTime(endTime);
        String endStr = endLocalDateTime.format(DATETIME_FORMATTER_TO_HOUR);
        endLocalDateTime = string2LocalDateTime(endStr);

        Duration between = Duration.between(startLocalDateTime, endLocalDateTime);
        return Long.valueOf(between.toHours()).intValue();
    }

    public static int getDaysBetween(Date startTime, Date endTime) {
        LocalDateTime startLocalDateTime = date2LocalDateTime(startTime);
        String startStr = startLocalDateTime.format(DATETIME_FORMATTER_TO_DAY);
        startLocalDateTime = string2LocalDateTime(startStr);

        LocalDateTime endLocalDateTime = date2LocalDateTime(endTime);
        String endStr = endLocalDateTime.format(DATETIME_FORMATTER_TO_DAY);
        endLocalDateTime = string2LocalDateTime(endStr);

        Duration between = Duration.between(startLocalDateTime, endLocalDateTime);
        return Long.valueOf(between.toDays()).intValue();
    }

    public static int getMonthsBetween(Date startTime, Date endTime) {
        LocalDateTime startLocalDateTime = date2LocalDateTime(startTime);
        String startStr = startLocalDateTime.format(DATETIME_FORMATTER_TO_MONTH);
        startLocalDateTime = string2LocalDateTime(startStr);

        LocalDateTime endLocalDateTime = date2LocalDateTime(endTime);
        String endStr = endLocalDateTime.format(DATETIME_FORMATTER_TO_MONTH);
        endLocalDateTime = string2LocalDateTime(endStr);

        return Long.valueOf(ChronoUnit.MONTHS.between(startLocalDateTime, endLocalDateTime)).intValue();
    }

    public static int getYearsBetween(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01");
        String firstDtStr = sdf.format(startTime);
        String secentDtStr = sdf.format(endTime);
        LocalDate startLocalDate = LocalDate.parse(firstDtStr);
        LocalDate endLocalDate = LocalDate.parse(secentDtStr);

        return Long.valueOf(ChronoUnit.YEARS.between(startLocalDate, endLocalDate)).intValue();
    }
}