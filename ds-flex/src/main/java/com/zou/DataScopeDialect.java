package com.zou;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.RawQueryCondition;
import com.zou.constant.DataScopeConsts;
import com.zou.util.QueryWrapperUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

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
        DataScopeConfig dataScopeConfig = DataScopeContext.getDataScopeConfig();
        if (dataScopeConfig != null) {
            // 构建条件SQL
            String conditionSql = AnalysisDataScope.buildCondition(DataScopeContext.getDataScopeInfoList(),
                    dataScopeConfig.getTemplate(), DataScopeContext.getDataScopeParams());
            if (StrUtil.isNotBlank(conditionSql)) {
                doAppendConditionSql(queryWrapper, dataScopeConfig, conditionSql);
            }
        }

        // （原生MyBatis和Flex同时使用情况）已经处理了数据权限，清除掉数据以防MyBatis拦截器再次拦截
        DataScopeContext.clear();

        return super.forSelectByQuery(queryWrapper);
    }

    private static void doAppendConditionSql(QueryWrapper queryWrapper, DataScopeConfig dataScopeConfig, String conditionSql) {
        // 标记位情况
        if (dataScopeConfig.isFlag()) {
            QueryWrapperUtil.foreachQueryWrapper(queryWrapper, rawQueryCondition -> {
                if (DataScopeConsts.DATA_SCOPE_FLAG.equals(rawQueryCondition.getContent())) {
                    try {
                        Field field = RawQueryCondition.class.getDeclaredField("content");
                        field.setAccessible(true);
                        field.set(rawQueryCondition, conditionSql);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                return "";
            });
        }
        // 直接拼接情况
        else {
            // 注入原始查询
            if (StrUtil.isNotBlank(conditionSql)) {
                if ("OR".equalsIgnoreCase(dataScopeConfig.getLogical().trim())) {
                    queryWrapper.or(conditionSql);
                } else {
                    queryWrapper.and(conditionSql);
                }
            }
        }
    }
}
