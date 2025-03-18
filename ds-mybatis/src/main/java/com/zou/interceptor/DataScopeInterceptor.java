package com.zou.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zou.*;
import com.zou.constant.SqlConsts;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
})
public class DataScopeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 获取 BoundSql 对象，包含原始 SQL
        BoundSql boundSql = statementHandler.getBoundSql();
        String originalSql = boundSql.getSql();

        // 自定义修改逻辑（例如替换占位符）
        DataScopeHolder dataScopeHolder = DataScopeContext.getDataScopeHolder();
        if (dataScopeHolder != null) {
            // 构建条件SQL
            String conditionSql = AnalysisDataScope.buildCondition(DataScopeContext.getDataScopeInfoList(),
                dataScopeHolder.getTemplate(), DataScopeContext.getDataScopeParams());
            // 注入原始查询
            if (StrUtil.isNotBlank(conditionSql)) {
                StringBuilder newSql = new StringBuilder(originalSql);
                if ("OR".equalsIgnoreCase(dataScopeHolder.getLogical().trim())) {
                    newSql.append(SqlConsts.OR)
                        .append(SqlConsts.BRACKET_LEFT)
                        .append(conditionSql)
                        .append(SqlConsts.BRACKET_RIGHT);
                } else {
                    newSql.append(SqlConsts.AND)
                        .append(SqlConsts.BRACKET_LEFT)
                        .append(conditionSql)
                        .append(SqlConsts.BRACKET_RIGHT);
                }

                // 修改 BoundSql 中的 SQL
                Field sqlField = boundSql.getClass().getDeclaredField("sql");
                sqlField.setAccessible(true);
                sqlField.set(boundSql, newSql.toString());
            }
        }

        return invocation.proceed();
    }
}

