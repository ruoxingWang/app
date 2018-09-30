package com.ruoxing.javafx.utils;

import java.io.File;

/**
 * 项目 :JavaFX-framework
 * 类型 :interface
 * 日期 :2018-07-28 星期六
 * 作者 :ruoxing
 * 描述 :封装JavaFX框架会用到的字符串处理方法
 */

public interface StringUtils {
    /**
     * 描述:判断字符串是否为空
     * @param sequence 字符串序列
     * @return 为空
     */
    default boolean emptyString(CharSequence sequence) {
        if (sequence == null) {
            return true;
        }
        return sequence.length() == 0;
    }
}
