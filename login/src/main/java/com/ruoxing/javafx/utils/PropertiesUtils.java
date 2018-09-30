package com.ruoxing.javafx.utils;

import java.io.*;
import java.util.Properties;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-07-29 星期日
 * 作者 :ruoxing
 * 描述 :Properties工具类
 */
public interface PropertiesUtils {


    /**
     * 描述:获取Properties
     * @param resource  properties文件类路径
     * @return Properties
     */
    default Properties getProperties(String resource) {
        try (InputStream is = this.getClass().getResourceAsStream(resource)) {
            final Properties prop = new Properties();
            prop.load(is);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    /**
     * 描述:保存Properties内容到文件中
     * @param prop  Properties
     * @param resource  properties文件类路径
     */
    default void setProperties(Properties prop, String resource) {
        try {
            final File file = new File(this.getClass().getResource(resource).toURI());
            final FileWriter fw = new FileWriter(file);
            prop.store(fw, "");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述:一次性获取属性值
     * @param resource  properties文件类路径
     * @param property  属性名称
     * @return 属性值
     */
    default String getPropertyValue(String resource, String property) {
        return getProperties(resource).getProperty(property);
    }

    /**
     * 描述:永久性添加(覆盖)属性
     * @param resource  properties文件类路径
     * @param property  属性名称
     * @param value 属性值
     */
    default void setPropertyValue(String resource, String property, String value) {
        final Properties prop = getProperties(resource);
        prop.put(property, value);
        setProperties(prop, resource);
    }


    /**
     * 描述:从属性文件中获取里面的字符串内容
     * @param resource  text文件
     * @return  StringBuilder
     */
    default StringBuilder getText(String resource) {
        final StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader
                (new InputStreamReader
                        (this.getClass().getResourceAsStream(resource)))) {
            final char[] buffer = new char[1024];
            int len = -1;
            while ((len = br.read(buffer)) != -1) {
                builder.append(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder;
    }
}
