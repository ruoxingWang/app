package com.ruoxing.javafx.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * 项目:app
 * 类型:interface
 * 日期:2018/7/31 星期二
 * 作者:ruoxing
 * 描述:日期事件工具类
 */

public interface DateTimeUtils {
    String URL = "https://www.baidu.com";
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 描述:从网络中获取北京时间
     * @return  LocalDateTime(北京时间)
     * @throws IOException
     */
    default LocalDateTime beijingDateTime() throws IOException {
        final LocalDateTime gmtTime = GMT();
        final LocalDateTime beijingTime = gmtTime.plus(8, ChronoUnit.HOURS);
        return beijingTime;
    }

    /**
     * 描述:从网络中获取格林威治之间(GMT)
     * @return  LocalDateTime(GMT)
     * @throws IOException
     */
    default LocalDateTime GMT() throws IOException {
        final URL dateUrl = new URL(URL);
        final URLConnection urlConnection = dateUrl.openConnection();
        urlConnection.setConnectTimeout(3000);
        urlConnection.connect();
        final long milliseconds = urlConnection.getDate();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.of("GMT"));
    }

}
