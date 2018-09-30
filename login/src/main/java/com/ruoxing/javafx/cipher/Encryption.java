package com.ruoxing.javafx.cipher;

import com.ruoxing.javafx.utils.DateTimeUtils;
import com.ruoxing.javafx.utils.PropertiesUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-07-29 星期日
 * 作者 :ruoxing
 * 描述 :客户端加密算法
 */
public interface Encryption extends PropertiesUtils {
    /**
     * 描述:标准的Base64编码器
     */
    Base64.Encoder standardEncoder = Base64.getEncoder();

    /**
     * 描述:根据现在的时间进行加密
     */
    default void encode() {
        final Properties prop = getProperties("/properties/cipher.properties");
        final String date = prop.getProperty("before.date");
        final String times = prop.getProperty("before.times");
        final byte[] dateBytes = standardEncoder.encode(date.getBytes());
        final byte[] timesBytes = standardEncoder.encode(times.getBytes());

        System.out.println("dateBytes=" + Arrays.toString(dateBytes));
        System.out.println("timesBytes=" + Arrays.toString(timesBytes));
        System.out.println("dateString=" + new String(dateBytes));
        System.out.println("timesString=" + new String(timesBytes));
    }

    /**
     * 描述:根据日期生产一串密文
     * @param date  日期
     * @return  String密文(不等长)
     */
    default String getCipherText(LocalDateTime date) {
        final String dateTime = date.format(DateTimeUtils.DATE_FORMAT);
        final String[] split = dateTime.split("[- :]");
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            final String uuid = UUID.randomUUID().toString();
            builder.append(uuid).append("-").append(split[i]).append("-");
        }
        if (builder.charAt(builder.length() - 1) == '-') {
            builder.deleteCharAt(builder.length() - 1);
        }
        final String encode = standardEncoder.encodeToString(builder.toString().getBytes());
        return encode;
    }

    /**
     * 描述:获取当前日期的密文
     * @return  String密文穿(不等长)
     */
    default String getCipherText() {
        return getCipherText(LocalDateTime.now());
    }


    /**
     * 描述:获取当前日期后days天对应日期的密文
     * @return  String密文穿(不等长)
     */
    default String getCipherText(int days) {
        days = Math.min(365, days);
        return getCipherText(LocalDateTime.now().plus(days, ChronoUnit.DAYS));
    }


}
