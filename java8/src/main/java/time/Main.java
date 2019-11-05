package time;

import java.time.*;
import java.util.Date;

import org.apache.tomcat.jni.Local;

public class Main {

    private static Clock clock = Clock.systemDefaultZone();

    private static LocalTime contractActiveBlockTimeStart = LocalTime.parse("22:00");

    private static LocalTime contractActiveBlockTimeEnd =  LocalTime.parse("00:00");;

    static long tolerance = 30;

    public static void main(String[] args) {
        //testDate4();

        //LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        //System.out.println(localDateTime);  //2019-10-18T19:27:16.632

        LocalDate now = LocalDate.now();
        System.out.println(now);//2019-10-18

        LocalDateTime localDateTime = now.atTime(LocalTime.MIN);
        System.out.println(localDateTime); //2019-10-18T00:00

        ZonedDateTime of = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        System.out.println(of);//2019-10-18T00:00+08:00[Asia/Shanghai]

        LocalDate localDate = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate); //2019-10-18
    }

    /**
     * 测试java8的时间函数.
     * 当前时间: 2019-08-01
     *
     * 有些函数可以加lock参数, 需要先把lock对象作为bean配置进IOC中:
     *     @Bean
     *     public Clock clock() {
     *         return Clock.systemDefaultZone();
     *     }
     *
     *  然后就可以使用了:
     *   private void processXclContract(JlData jlData) {
     *         LocalDate lastDayOfMonth = YearMonth.now(clock).atEndOfMonth();
     *         LocalDateTime startDate = LocalDateTime.of(lastDayOfMonth, blockTimeStart).minusDays(10);
     *         LocalDateTime endDate = LocalDateTime.of(lastDayOfMonth, blockTimeEnd).plusDays(1);
     *         LocalDateTime now = LocalDateTime.now(clock);
     *         if (now.equals(startDate) || now.equals(endDate) || (now.isAfter(startDate) && now.isBefore(endDate))) {
     *             jlData.getJlRequest().setStatus(JlRequestConstant.BLOCK_STATUS);
     *         }
     *     }
     */
    public static void testDate() {
        //拿到当前时间的当月的最后一天.
        //如果要拿指定时间的当月的最后一天,则调用: YearMonth.from(dateTime).atEndOfMonth();
        LocalDate lastDayOfMonth = YearMonth.now().atEndOfMonth();
        System.out.println(lastDayOfMonth); //2019-08-31 当月底

        LocalTime blockTimeStart = LocalTime.parse("22:00");
        LocalTime blockTimeEnd = LocalTime.parse("00:00");

        LocalDateTime startDate = LocalDateTime.of(lastDayOfMonth, blockTimeStart).minusDays(1);
        System.out.println("@@@startDate:" + startDate); //@@@startDate:2019-08-30T22:00 "当前月底"减1天的22:00

        LocalDateTime endDate = LocalDateTime.of(lastDayOfMonth, blockTimeEnd).plusDays(1);
        System.out.println("@@@endDate:" + endDate); //@@@endDate:2019-09-01T00:00 "当前月底"加1天的00:00

        LocalDateTime date = LocalDateTime.of(lastDayOfMonth, blockTimeStart);
        System.out.println("@@@date: " + date); //@@@date: 2019-08-31T22:00  "当月底"的 22:00

        LocalDateTime startDate2 = LocalDateTime.of(lastDayOfMonth, blockTimeStart).minusDays(10);
        System.out.println("@@@startDate2:" + startDate2); //@@@startDate2:2019-08-21T22:00 "当前月底"减10天的22:00

        LocalDateTime now = LocalDateTime.now();
        System.out.println("@@@now:" + now); //@@@now:2019-08-01T16:05:00.693 当前时间, 年月日时分秒和毫秒
    }

    /**
     * 测试: 目标时间,是否和当前时间, 属于同一个月份内.
     * 当前时间: 2019-08-02 13:37
     */
    static void testDate2() {
        //模拟目标时间
        Date responseReceived = new Date();
        //拿到目标时间的年月日.
        LocalDate responseDate = responseReceived.toInstant() // 2019-08-02T05:37:31.793Z
                .atZone(ZoneId.systemDefault()) //Asia/Shanghai
                .toLocalDate();//2019-08-02 如果要的是年月日和时间,则调用: .toLocalDateTime()
        Month currentMonth = LocalDate.now(clock).getMonth();//当前时间所属的月份: AUGUST
        Month responseMonth = responseDate.getMonth();//目标时间所属的月份: AUGUST
        boolean result = currentMonth.equals(responseMonth);
        System.out.println("@@@result is : " + result);
    }

    /**
     * 测试: 判断"指定时间"是否在该时间所在的当月的月底(的指定时分秒) 至 该月底+1天(的指定时分秒) 这两个时间之间.
     */
    static void testDate3() {
        //模拟"指定时间"
        Date contractActiveDate = new Date(); //Fri Aug 02 13:53:32 CST 2019
        LocalDateTime dateTime = contractActiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); //2019-08-02T13:53:32.380
        LocalDate lastDayOfMonth = YearMonth.from(dateTime).atEndOfMonth(); //2019-08-31

        //获得目标时间的当月的月底(的指定时分秒)
        LocalDateTime startDate = LocalDateTime.of(lastDayOfMonth, contractActiveBlockTimeStart); //2019-08-31T22:00
        //获得目标时间的当月的月底+1天(的指定时分秒)
        LocalDateTime endDate = LocalDateTime.of(lastDayOfMonth, contractActiveBlockTimeEnd).plusDays(1); //2019-09-01T00:00

        boolean result = dateTime.equals(startDate) || dateTime.equals(endDate) || (dateTime.isAfter(startDate) && dateTime.isBefore(endDate)); //false
        System.out.println("@@@result:" + result);
    }

    static void testDate4() {
        //2019-04-18T10:00+08:00[Asia/Shanghai]
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                LocalDateTime.parse("2019-04-18T10:00:00"), ZoneId.systemDefault());
        //2019-04-18T02:00:00Z,Asia/Shanghai
        Clock clock = Clock.fixed(zonedDateTime.toInstant(), ZoneId.systemDefault());
        System.out.println("@@@clock:" + clock);
    }


    static void LocalDateTime2ZoneDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                LocalDateTime.parse("2019-04-18T10:00:00"), ZoneId.systemDefault());

    }

    static void Date2ZoneDateTime() {
        Date date = new Date();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        System.out.println(zonedDateTime);
    }

    static void Date2LocalDateTime() {
        Date contractActiveDate = new Date(); //Fri Aug 02 13:53:32 CST 2019
        LocalDateTime dateTime = contractActiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); //2019-08-02T13:53:32.380

        //模拟目标时间
        Date responseReceived = new Date();
        //拿到目标时间的年月日.
        LocalDate responseDate = responseReceived.toInstant() // 2019-08-02T05:37:31.793Z
                .atZone(ZoneId.systemDefault()) //Asia/Shanghai
                .toLocalDate();//2019-08-02
    }




    ///////////////////////////////from internet/////////////////
    // 01. java.util.Date --> java.time.LocalDateTime
    public void date2LocalDateTime() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
    }

    // 02. java.util.Date --> java.time.LocalDate
    public void date2LocalDate() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
    }

    // 03. java.util.Date --> java.time.LocalTime
    public void date2LocalTime() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
    }


    // 04. java.time.LocalDateTime --> java.util.Date
    public void localDateTime2Date() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
    }


    // 05. java.time.LocalDate --> java.util.Date
    public void localDate2Date() {
        LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
    }

    // 06. java.time.LocalTime --> java.util.Date
    public void localTime2Date() {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
    }

    // 07、计算2个 LocalDate 之间相差多少天，如果是同一天则会返回0
    public long differLocalDateDay(LocalDate before,LocalDate after){
        before = LocalDate.of(2017, 12, 15);
        after = LocalDate.of(2017, 12, 15);
        long differDay = after.toEpochDay() - before.toEpochDay();
        return Math.abs(differDay);
    }

    // 08、就算2个 LocalDateTime 之间相差多少天，如果是同一天返回0
    public long differDayOfLocalDateTime(){
        LocalDateTime first = LocalDateTime.of(2018, 8, 15, 1, 1, 1);
        LocalDateTime second = LocalDateTime.of(2018, 9, 16, 1, 5, 1);
        return Duration.between(first, second).toDays();
    }

}
