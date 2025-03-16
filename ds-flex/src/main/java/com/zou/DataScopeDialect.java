package com.zou;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.dialect.IDialect;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.table.TableInfo;
import com.zou.constant.SqlConsts;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zoulinchuan
 * @Date 2025年03月15日 星期六 23:07
 * @description: MyBatisFlex 方言处理器
 */
@Slf4j
public class DataScopeDialect extends CommonsDialectImpl {
    @Override
    public String forSelectByQuery(QueryWrapper queryWrapper) {
        // 数据权限
        log.info("数据权限注入...");
        DataScopeHolder dataScopeHolder = DataScopeContext.getDataScopeHolder();
        if (dataScopeHolder != null) {
            // 构建条件SQL
            String conditionSql = AnalysisDataScope.buildCondition(DataScopeContext.getDataScopeInfo(),
                    dataScopeHolder.getTemplate(), DataScopeContext.getDataScopeParams());
            // 注入原始查询
            if (StrUtil.isNotBlank(conditionSql)) {
                if ("OR".equalsIgnoreCase(dataScopeHolder.getLogical().trim())) {
                    queryWrapper.or(conditionSql);
                } else {
                    queryWrapper.and(conditionSql);
                }
            }
        }

        return super.forSelectByQuery(queryWrapper);
    }
}
