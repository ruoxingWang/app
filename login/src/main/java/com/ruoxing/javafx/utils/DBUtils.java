package com.ruoxing.javafx.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


/**
 * 项目:framedemo
 * 类型:Java类
 * 日期:2017/11/17 20:31 星期五
 * 作者:Administrator
 * 描述:
 */

public class DBUtils {
    //mybatis的配置文件
    private static SqlSessionFactory sessionFactory;

    /**
     * 描述:获取会话
     * @param autoCommit 是否自动提交
     * @return sqlSession
     */
    public static SqlSession getSession(boolean autoCommit) {
        if (sessionFactory == null){
            try {
                String resource = "mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory.openSession(autoCommit);
    }

    /**
     * 描述:获取会话对象(不自动提交事务)
     * @return sqlSession
     */
    public static SqlSession getSession(){
        return getSession(false);
    }

}