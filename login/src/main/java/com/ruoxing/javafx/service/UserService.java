package com.ruoxing.javafx.service;

import com.ruoxing.javafx.mapper.UserMapper;
import com.ruoxing.javafx.pojo.User;
import com.ruoxing.javafx.pojo.UserExample;
import com.ruoxing.javafx.utils.DBUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-07-29 星期日
 * 作者 :ruoxing
 * 描述 :
 */
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private UserMapper userDao;

    public UserService() {
        userDao = DBUtils.getSession().getMapper(UserMapper.class);
    }

    /**
     * 描述:登录
     * @param username  传入的账号
     * @param password  传入的密码
     * @return boolean
     */
    public boolean login(String username, String password) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        final StringBuffer loginInfo = new StringBuffer();
        loginInfo.append(username).append(",正在登录...");
        logger.info(loginInfo.toString());
        final UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        final List<User> userList = userDao.selectByExample(example);
        return !userList.isEmpty();
    }

}
