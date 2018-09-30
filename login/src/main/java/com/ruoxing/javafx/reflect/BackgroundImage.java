package com.ruoxing.javafx.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目 :app
 * 类型 :annotation
 * 日期 :2018-08-02 星期四
 * 作者 :ruoxing
 * 描述 :
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BackgroundImage {
    String value() default "";
    String resourceDir() default "";
    String image() default "";
}
