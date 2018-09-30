package com.ruoxing.javafx.utils;

import javafx.scene.control.TextInputControl;
import javafx.stage.Screen;

/**
 * 项目 :JavaFX-framework
 * 类型 :class
 * 日期 :2018-07-28 星期六
 * 作者 :ruoxing
 * 描述 :
 */
public interface JavaFxUtils {
    Screen SCREEN = Screen.getPrimary();


    /**
     * 描述:设置输入框控件的最大指定长度
     * @param textInputControl  TextInputControl
     * @param maxLength 允许输入的最大长度
     */
    default void setTextInputControlMaxLength(TextInputControl textInputControl, int maxLength, String name) {
        textInputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                textInputControl.setText(oldValue);
                System.out.println(name + "长度不能超过" + maxLength);
            }
        });
    }
    default void setTextInputControlMaxLength(TextInputControl textInputControl, int maxLength) {
        setTextInputControlMaxLength(textInputControl, maxLength, "");
    }
}
