package com.dddemo.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zou.anno.DataScope;
import com.zou.constant.SqlConsts;
import com.dddemo.entity.User;

import java.util.List;

/**
 * @Author zoulinchuan
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {

    @DataScope(keys = {"USER_LIST", "USER_LIST2"},
        merge = true,
        flag = true,
        logical = SqlConsts.OR,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    List<User> allMapper();
}
