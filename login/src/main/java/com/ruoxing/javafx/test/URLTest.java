package com.ruoxing.javafx.test;

import com.ruoxing.javafx.utils.HttpUtils;
import org.junit.Test;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-08-12 星期日
 * 作者 :ruoxing
 * 描述 :
 */
public class URLTest implements HttpUtils {

    @Test
    public void test01() throws Exception {
        final URL url = new URL("http://localhost/lol/welcome/app.do?usernam=ruoxing&password=root");
        final URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
    }

    @Test
    public void test02() throws Exception {
//        final String url = "http://localhost/lol/welcome/index.do";
        final String url = "http://localhost/lol/welcome/app.do";
        final Map<Object, Object> map = new HashMap<>((int)(10 / 0.75 + 1));
        map.put("username", "ruoxing");
        map.put("password", "root");
        System.out.println(requestByPostMethod(url, map, null));
    }

    @Test
    public void test03() throws Exception {
        System.out.println(sendPost("http://localhost/lol/welcome/app.do", "usernam=ruoxing&password=root"));
    }
}
