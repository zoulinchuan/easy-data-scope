package com.dddemo2.controller;

import com.dddemo2.entity.User;
import com.dddemo2.mapper.UserMapper;
import com.dddemo2.service.UserService;
import com.dddemo2.store.UserSessionContext;
import com.dddemo2.store.UserSessionInfo;
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

        return userService.list();
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
