package cn.zlinchuan;

import java.util.List;

/**
 * 数据权限查找规则
 *
 * @author zoulinchuan\
 */
public interface DataScopeFindRule {

    List<DataScopeInfo> find(String[] key);
}
