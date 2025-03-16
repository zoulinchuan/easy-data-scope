package com.zou.service;

import com.zou.DataScopeContext;
import com.zou.anno.DataScope;
import com.zou.constant.SqlConsts;
import com.zou.entity.User;
import com.zou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zoulinchuan
 * @description:
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    // 单key情况不使用模板，默认通过AND拼接，关闭自动合并
    // @DataScope(keys = {"USER_LIST"}, merge = false)
    // 单key情况不使用模板，默认通过AND拼接，开启自动合并
    // @DataScope(keys = {"USER_LIST"}, merge = true)
    // 多key情况不使用模板，默认通过AND拼接
    // @DataScope(keys = {"USER_LIST", "USER_LIST2"}, merge = true)
    // 多key情况使用模板，通过模板构建
     @DataScope(keys = {"USER_LIST", "USER_LIST2"}, merge = true, template = "/*这是使用template生成的SQL*/{USER_LIST} OR {USER_LIST2}")
    public List<User> selectAll() {
        // 数据权限中有使用到程序中的值
        // DataScopeContext.putDataScopeParam("key", "value");
        return userMapper.selectAll();
    }

    public List<User> selectAllNotDataScope() {
        return userMapper.selectAll();
    }
}
