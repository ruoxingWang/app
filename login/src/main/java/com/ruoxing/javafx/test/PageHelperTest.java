package com.ruoxing.javafx.test;

import com.ruoxing.javafx.mapper.ProvinceMapper;
import com.ruoxing.javafx.pojo.Province;
import com.ruoxing.javafx.utils.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-09-27 星期四
 * 作者 :ruoxing
 * 描述 :
 */
public class PageHelperTest {
    //private static final Logger LOGGER = Logger.getLogger("log4jConsole");
    private static final Logger rootLogger = Logger.getRootLogger();
    private SqlSession session = null;
    @Before
    public void before() throws Exception {
        session = DBUtils.getSession();
    }
    @After
    public void after() throws Exception {
        if (session != null) session.close();
    }



    @Test
    public void test01() throws Exception {
        final ProvinceMapper provinceMapper = session.getMapper(ProvinceMapper.class);
        final Province province = provinceMapper.selectByPrimaryKey("110000");

        rootLogger.info("province = " + province);
    }
}
