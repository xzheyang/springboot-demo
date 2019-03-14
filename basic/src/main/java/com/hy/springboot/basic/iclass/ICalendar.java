package com.hy.springboot.basic.iclass;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 *     代替date的时间类
 *
 **/
public class ICalendar {



    public static void main(String[] args) {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());


        c2.set(Calendar.MONTH,Calendar.AUGUST); //推荐用这个
        c2.set( Calendar.MONTH, 8-1);           //月份要减1,因为初始化为0,但date直接转calendar就不用

        c2.add(Calendar.MONTH,1);   //加1月
        c2.set(Calendar.DAY_OF_MONTH,-1);   //这样获得前一个月末
        c2.get(Calendar.MONTH);


        DateUtils.isSameDay(c1,c2);



        String calendarString = DateFormatUtils.format(c2,"yyyy-MM-dd");

        System.out.println(calendarString);
    }

}
