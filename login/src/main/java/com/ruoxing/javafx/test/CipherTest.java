package com.ruoxing.javafx.test;

import com.ruoxing.javafx.cipher.Encryption;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-07-29 星期日
 * 作者 :ruoxing
 * 描述 :
 */
public class CipherTest implements Encryption {

    public void test01() throws Exception {
        System.out.println(getCipherText(365));
    }


}
