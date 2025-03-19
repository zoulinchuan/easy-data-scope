package com.dddemo.controller;

import com.dddemo.entity.User;
import com.dddemo.mapper.UserMapper;
import com.dddemo.service.UserService;
import com.dddemo.store.UserSessionContext;
import com.dddemo.store.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author zoulinchuan
 * @description: userAPI
 */
@RestController
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    public UserMapper userMapper;

    @RequestMapping("/all")
    public List<User> selectAll() {
        mockLogin();

        return userService.selectAll();
    }

    @RequestMapping("/allMapper")
    public List<User> selectAll2() {
        mockLogin();

        return userMapper.allMapper();
    }

    @RequestMapping("/allNotDataScope")
    public List<User> allNotDataScope() {
        mockLogin();

        return userService.selectAllNotDataScope();
    }

    private static void mockLogin() {
        // 模拟登录
        UserSessionInfo userSessionInfo = new UserSessionInfo();
        userSessionInfo.setId(1);
        userSessionInfo.setDataScopeIds(Arrays.asList(1, 2, 3, 4));
        UserSessionContext.setUserSession(userSessionInfo);
    }
}
