package cn.zlinchuan.controller;

import cn.zlinchuan.mapper.UserMapper;
import cn.zlinchuan.service.UserService;
import cn.zlinchuan.store.UserSessionContext;
import cn.zlinchuan.store.UserSessionInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.zlinchuan.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author zoulinchuan
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

    @RequestMapping("/page")
    public Page<User> page() {
        mockLogin();

        return userService.page();
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
