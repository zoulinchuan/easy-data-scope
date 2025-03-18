package com.zou.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zou.anno.DataScope;
import com.zou.constant.SqlConsts;
import com.zou.entity.User;

import java.util.List;

/**
 * @Author zoulinchuan
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {

    @DataScope(keys = {"USER_LIST", "USER_LIST2"}, merge = true, logical = SqlConsts.OR, template = "/*这是使用template生成的SQL*/{USER_LIST} OR {USER_LIST2}")
    List<User> allMapper();
}
