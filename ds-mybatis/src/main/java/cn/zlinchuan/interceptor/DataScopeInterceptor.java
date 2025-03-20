package cn.zlinchuan.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.zlinchuan.AnalysisDataScope;
import cn.zlinchuan.DataScopeConfig;
import cn.zlinchuan.DataScopeContext;
import cn.zlinchuan.TemplateUtils;
import cn.zlinchuan.constant.DataScopeConsts;
import cn.zlinchuan.constant.SqlConsts;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;

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
        DataScopeConfig dataScopeConfig = DataScopeContext.getDataScopeConfig();
        if (dataScopeConfig != null) {
            // 构建条件SQL
            String conditionSql = AnalysisDataScope.buildCondition(DataScopeContext.getDataScopeInfoList(),
                dataScopeConfig.getTemplate(), DataScopeContext.getDataScopeParams());
            if (StrUtil.isNotBlank(conditionSql)) {
                StringBuilder newSql = doAppendConditionSql(originalSql, dataScopeConfig, conditionSql);
                // 修改 BoundSql 中的 SQL
                Field sqlField = boundSql.getClass().getDeclaredField("sql");
                sqlField.setAccessible(true);
                sqlField.set(boundSql, newSql.toString());
                DataScopeContext.clear();
            }
        }

        return invocation.proceed();
    }

    private static StringBuilder doAppendConditionSql(String originalSql, DataScopeConfig dataScopeConfig, String conditionSql) {
        if (StrUtil.isBlank(conditionSql)) {
            return null;
        }
        StringBuilder newSql = new StringBuilder();
        if (dataScopeConfig.isFlag()) {
            DataScopeContext.putDataScopeParam(DataScopeConsts.DATA_SCOPE_PARAM_KEY, conditionSql);
            newSql = new StringBuilder(TemplateUtils.replacePlaceholders(originalSql, DataScopeContext.getDataScopeParams()));
        } else {
            newSql.append(originalSql);
            // 注入原始查询
            if (StrUtil.isNotBlank(conditionSql)) {
                if ("OR".equalsIgnoreCase(dataScopeConfig.getLogical().trim())) {
                    newSql.append(SqlConsts.OR);
                } else if ("WHERE".equalsIgnoreCase(dataScopeConfig.getLogical().trim())) {
                    newSql.append(SqlConsts.WHERE);
                }  else {
                    newSql.append(SqlConsts.AND);
                }
                newSql.append(SqlConsts.BRACKET_LEFT)
                    .append(conditionSql)
                    .append(SqlConsts.BRACKET_RIGHT);
            }
        }
        return newSql;
    }
}

