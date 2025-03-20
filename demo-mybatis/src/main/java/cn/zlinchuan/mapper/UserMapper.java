package cn.zlinchuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.zlinchuan.entity.User;
import cn.zlinchuan.anno.DataScope;
import cn.zlinchuan.constant.SqlConsts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zoulinchuan
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
