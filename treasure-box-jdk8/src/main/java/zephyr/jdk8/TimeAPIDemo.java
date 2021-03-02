package zephyr.jdk8;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;


@Slf4j
public class TimeAPIDemo {

    public static void main(String[] args) {
        log.info(">>>>>>>>>>>>>>>>>>>>> {} <<<<<<<<<<<<<<<<<<<<<", "LocalDate");
        LocalDate today = LocalDate.now();
        log.info("当前日期" + " = {}", today);
        LocalDate appointDay = LocalDate.of(2018, 12, 25);
        log.info("根据年月日构造日期，12月就是12" + " = {}", appointDay);
        LocalDate endOfFeb = LocalDate.parse("2018-02-28");
        log.info("根据字符串构造日期，严格按照ISO yyyy-MM-dd验证，无效日期会抛异常DateTimeParseException" + " = {}", endOfFeb);

        log.info(">>>>>>>>>>>>>>>>>>>>> {} <<<<<<<<<<<<<<<<<<<<<", "日期转换");
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        log.info("取本月第1天" + " = {}", firstDayOfThisMonth);
        LocalDate secondDayOfThisMonth = today.withDayOfMonth(2);
        log.info("取本月第2天" + " = {}", secondDayOfThisMonth);

        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,4);
        int weekOfWeekBasedYear = today.get(weekFields.weekOfWeekBasedYear());
        int weekOfMonth = today.get(weekFields.weekOfMonth());
        log.info("weekOfWeekBasedYear" + " = {}", weekOfWeekBasedYear);
        log.info("weekOfMonth" + " = {}", weekOfMonth);


        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        log.info("取本月最后一天，再也不用计算是28，29，30还是31" + " = {}", lastDayOfThisMonth);
        LocalDate nextDay = lastDayOfThisMonth.plusDays(1);
        log.info("取下一天" + " = {}", nextDay);
        LocalDate firstMondayOf2015 = LocalDate.parse("2015-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        log.info("取2015年1月第一个周一，这个计算用Calendar要死掉很多脑细胞" + " = {}", firstMondayOf2015);

        log.info(">>>>>>>>>>>>>>>>>>>>> {} <<<<<<<<<<<<<<<<<<<<<", "LocalTime");
        LocalTime now = LocalTime.now();
        log.info("当前时间，包含毫秒" + " = {}", now);
        LocalTime nowWithoutNano = LocalTime.now().withNano(0);
        log.info("当前时间，清除毫秒数" + " = {}", nowWithoutNano);
        LocalTime zero = LocalTime.of(0, 0, 0);
        log.info("根据时分秒构造时间" + " = {}", zero);
        LocalTime mid = LocalTime.parse("12:00:00");
        log.info("根据字符串构造时间，按照ISO格式识别，支持hh:mm，hh:mm:ss，hh:mm:ss.SSS三种格式" + " = {}", mid);

        log.info(">>>>>>>>>>>>>>>>>>>>> {} <<<<<<<<<<<<<<<<<<<<<", "DateTimeFormatter");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formatted = localDate.format(dateTimeFormatter);
        log.info("格式化日期" + " = {}", formatted);

        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("localDateTime" + " = {}", localDateTime);
        LocalDateTime next = localDateTime.plus(1, ChronoUnit.SECONDS);
        log.info("start" + " = {}", next);
        //
        //LocalDateTime end = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        //log.info(getContent("end"), end);
        //LocalDateTime result = end.plusNanos(1);
        //log.info(getContent("result"), result);

        LocalDate l = LocalDate.now();
        LocalDateTime start = l.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime end = LocalDateTime.of(l.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
        log.info("start" + " = {}", start);

        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        log.info("monday" + " = {}", monday);

        //LocalDateTime end = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        log.info("end" + " = {}", end);
        //LocalDateTime result = end.plusNanos(1);
        //log.info(getContent("result"), result);
        System.out.println(Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println(new Date());

        val offsetDateTime = OffsetDateTime.parse("2020-12-30T03:28:23.746683420Z");
        System.out.println(offsetDateTime);


        Instant epochHours = Instant.EPOCH.plus(420768, ChronoUnit.HOURS);
        System.out.println(epochHours);
        epochHours = Instant.EPOCH.plus(440477, ChronoUnit.HOURS);
        System.out.println(epochHours);
    }

}
