package com.dddemo2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dddemo2.entity.User;
import com.zou.anno.DataScope;
import com.zou.constant.SqlConsts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author zoulinchuan
 * @description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @DataScope(keys = {"USER_LIST", "USER_LIST2"},
        merge = true,
        flag = true,
        logical = SqlConsts.OR,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    List<User> allMapper();
}
