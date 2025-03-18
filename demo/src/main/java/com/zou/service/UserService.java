package com.zou.service;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.zou.DataScopeContext;
import com.zou.anno.DataScope;
import com.zou.constant.DataScopeConsts;
import com.zou.constant.SqlConsts;
import com.zou.entity.User;
import com.zou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.select;

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
        flag = true,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    public List<User> selectAll() {
        // 数据权限中有使用到程序中的值
        // DataScopeContext.putDataScopeParam("key", "value");
        QueryWrapper queryWrapper = select()
            .from(select().from("user").and(DataScopeConsts.DATA_SCOPE_FLAG)).as("t")
            .where("id = 1")
            .orderBy("id");
        return userMapper.selectListByQuery(queryWrapper);
    }

    public List<User> selectAllNotDataScope() {
        return userMapper.selectAll();
    }
}
