package cn.zlinchuan.mapper;

import com.mybatisflex.core.BaseMapper;
import cn.zlinchuan.anno.DataScope;
import cn.zlinchuan.constant.SqlConsts;
import cn.zlinchuan.entity.User;

import java.util.List;

/**
 * @author zoulinchuan
 */
public interface UserMapper extends BaseMapper<User> {

    @DataScope(keys = {"USER_LIST", "USER_LIST2"},
        merge = true,
        flag = true,
        logical = SqlConsts.OR,
        template = "{{USER_LIST}} OR {{USER_LIST2}}/*这是使用template生成的SQL*/")
    List<User> allMapper();
}
