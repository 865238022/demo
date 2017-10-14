package top.huzhurong.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 竹 on 2017/10/14.
 */
public class LocateDate {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        //除此以外，还有plusWeek,plusMouth,plusYear，当然有+就有-
        //更加的方便进行date timer 的操作，最为重要的也是线程安全的
        System.out.println(localDate.plusDays(2));//2017-10-16
        System.out.println(localDate.minusDays(2));//2017-10-12
        //格式化，如果不满足可以自己去定制
        System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));//2017-10-14
        System.out.println(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));//20171014

        //同理，对于时间的操作同时也能使用+ - 进行操纵
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);//16:15:27.630
        System.out.println(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));//16:15:27
        System.out.println(localTime.plusHours(2));//18:15:27.630


        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        Instant  instant = Instant.now();
        System.out.println(instant);

    }
}
