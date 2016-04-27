package com.lewiswon.engadget.Utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;

/**
 * Created by Lordway on 16/4/27.
 */
public class DateUtils {
    private static HashMap<String,String> months;
    static{
        months =new HashMap<>();
        months.put("Jan","01");
        months.put("Feb","02");
        months.put("Mar","03");
        months.put("Apr","04");
        months.put("May","05");
        months.put("Jun","06");
        months.put("Jul","07");
        months.put("Aug","08");
        months.put("Sep","09");
        months.put("Oct","10");
        months.put("Nov","11");
        months.put("Dec","12");
    }

    public static String getTimeStr(String time){
        try {
            time = time.substring(4, time.length() - 6).trim();
            String timeArr[] = time.split(" ");
            time = time.replaceAll(timeArr[1], months.get(timeArr[1]));
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MM yyyy HH:mm:ss");
            DateTime dateTime = DateTime.parse(time, formatter);
            dateTime = dateTime.plusHours(12);

            int timeRemain=Minutes.minutesBetween(dateTime,DateTime.now()).getMinutes();
            String suffix="分钟";
            if (timeRemain>=60){
                timeRemain= Hours.hoursBetween(dateTime,DateTime.now()).getHours();
                suffix="小时";
                if (timeRemain>=24){
                    timeRemain= Days.daysBetween(dateTime,DateTime.now()).getDays();
                    suffix="天";
                    if (timeRemain>=30){
                        timeRemain=-1;
                    }

                }

            }
            return fixTimeZone(timeRemain,suffix,dateTime);
        }catch (Exception e){
            return "";
        }

    }

    private static String fixTimeZone(int time,String suffix,DateTime  dateTime){
        if (time<0){
            return dateTime.toString("yyyy.MM.dd HH:mm:ss");
        }
        return time+suffix+"前";
    }
}
