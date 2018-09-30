package com.ruoxing.javafx.cipher;

import com.ruoxing.javafx.utils.DateTimeUtils;
import com.ruoxing.javafx.utils.PropertiesUtils;

import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-07-29 星期日
 * 作者 :ruoxing
 * 描述 :客户端解码算法
 */
public interface Decryption extends PropertiesUtils {
    /**
     * 描述:标准的Base64解码器
     */
    Base64.Decoder standardDecoder = Base64.getDecoder();
    String cipherTextPath = "/properties/cipher.txt";

    /**
     * 描述:解密,获取时间
     * @return LocalDateTime
     */
    default LocalDateTime decode() {
        //获取密文
        final String cipherText = getText(cipherTextPath).toString();
        //获取明文
        final String plainText = new String(standardDecoder.decode(cipherText));
        //格式解析
        final String[] split = plainText.split("-");
        final StringBuilder builder = new StringBuilder();
        builder.append(split[5]).append('-');
        builder.append(split[11]).append('-');
        builder.append(split[17]);
        builder.append(" ");
        builder.append(split[23]).append(':');
        builder.append(split[29]).append(':');
        builder.append(split[35]);
        final LocalDateTime localDateTime = LocalDateTime.parse(builder, DateTimeUtils.DATE_FORMAT);
        return localDateTime;
    }



}
