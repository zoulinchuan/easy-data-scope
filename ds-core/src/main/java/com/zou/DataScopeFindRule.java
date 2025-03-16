package com.zou;

import java.util.List;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 18:14
 * @description: 数据权限查找规则
 */
public interface DataScopeFindRule {

    List<DataScopeInfo> find(String[] key);
}
