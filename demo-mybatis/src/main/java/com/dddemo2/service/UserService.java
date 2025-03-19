package com.dddemo2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dddemo2.entity.User;
import com.dddemo2.mapper.UserMapper;
import com.zou.anno.DataScope;
import com.zou.constant.SqlConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static javafx.beans.binding.Bindings.select;

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
    @DataScope(keys = {"USER_LIST", "USER_LIST2"},
        merge = true,
        // flag = true,
        logical =SqlConsts.WHERE,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    public List<User> list() {
        // 数据权限中有使用到程序中的值
        // DataScopeContext.putDataScopeParam("key", "value");
        return userMapper.selectList(null);
    }

    // 单key情况不使用模板，默认通过AND拼接，关闭自动合并
    // @DataScope(keys = {"USER_LIST"}, merge = false)
    // 单key情况不使用模板，默认通过AND拼接，开启自动合并
    // @DataScope(keys = {"USER_LIST"}, merge = true)
    // 多key情况不使用模板，默认通过AND拼接
    // @DataScope(keys = {"USER_LIST", "USER_LIST2"}, merge = true)
    // 多key情况使用模板，通过模板构建
    @DataScope(keys = {"USER_LIST", "USER_LIST2"},
        merge = true,
        // flag = true,
        logical =SqlConsts.WHERE,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    public Page<User> page() {
        // 数据权限中有使用到程序中的值
        // DataScopeContext.putDataScopeParam("key", "value");
        Page<User> page = new Page<>();
        page.setCurrent(1);
        page.setSize(1);

        return userMapper.selectPage(page, null);
    }

    public List<User> selectAllNotDataScope() {
        return userMapper.selectList(null);
    }
}
