package cn.zlinchuan;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.zlinchuan.constant.SqlConsts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据权限解析
 *
 * @author zoulinchuan
 */
public class AnalysisDataScope {

    /**
     * 转换为SQL
     *
     * @param dataScope
     * @return
     */
    public static String toSql(DataScopeInfo dataScope) {
        StringBuilder sql = new StringBuilder();

        if (StrUtil.isNotBlank(dataScope.getTableName())) {
            sql.append(dataScope.getTableName())
                .append(SqlConsts.REFERENCE);
        }
        if (StrUtil.isNotBlank(dataScope.getColumnName())) {
            sql.append(dataScope.getColumnName());
        }

        // condition
        if (StrUtil.isNotBlank(dataScope.getOperator())) {
            sql.append(SqlConsts.BLANK).append(dataScope.getOperator()).append(SqlConsts.BLANK);
        }
        if (SqlConsts.IN.equalsIgnoreCase(dataScope.getOperator()) || SqlConsts.NOT_IN.equalsIgnoreCase(dataScope.getOperator())) {
            sql.append(SqlConsts.BRACKET_LEFT);
        }
        if (StrUtil.isNotBlank(dataScope.getValue())) {
            sql.append(dataScope.getValue());
        } else {
            sql.append(dataScope.getSql());
        }
        if (SqlConsts.IN.equalsIgnoreCase(dataScope.getOperator()) || SqlConsts.NOT_IN.equalsIgnoreCase(dataScope.getOperator())) {
            sql.append(SqlConsts.BRACKET_RIGHT);
        }

        return sql.toString();
    }

    /**
     * 合并数据权限
     * 1 2 3 ==> (1、2、3)
     *
     * @param dataScopeList
     * @return
     */
    public static List<DataScopeInfo> merge(List<DataScopeInfo> dataScopeList) {
        List<DataScopeInfo> result = new ArrayList<>();

        Map<String, List<DataScopeInfo>> groupedDataScopeMap = dataScopeList.stream().collect(Collectors.groupingBy(AnalysisDataScope::buildGroupKey));
        for (Map.Entry<String, List<DataScopeInfo>> entry : groupedDataScopeMap.entrySet()) {
            List<DataScopeInfo> value = entry.getValue();
            List<DataScopeInfo> sortedList = sortDataScope(value);
            if (sortedList.size() <= 1) {
                result.addAll(value);
                continue;
            }

            // 合并, 并将新值赋值回sql表达式
            DataScopeInfo first = sortedList.iterator().next();
            if (!Objects.equals(first.getOperator().trim(), "=") &&
                !Objects.equals(first.getOperator().trim(), "!=")) {
                result.addAll(value);
                continue;
            }
            StringBuilder newSql = new StringBuilder();
            // newSql.append(SqlConsts.BRACKET_LEFT);
            boolean isFirst = true;
            for (DataScopeInfo datascope : sortedList) {
                if (!isFirst) {
                    newSql.append(SqlConsts.DELIMITER);
                }
                if (StrUtil.isNotBlank(datascope.getSql())) {
                    newSql.append(SqlConsts.BRACKET_LEFT);
                    newSql.append(datascope.getSql());
                    newSql.append(SqlConsts.BRACKET_RIGHT);
                } else if (StrUtil.isNotBlank(datascope.getValue())) {
                    newSql.append(datascope.getValue());
                }
                isFirst = false;
            }
            // newSql.append(SqlConsts.BRACKET_RIGHT);
            first.setSql(newSql.toString());
            first.setValue(StrUtil.EMPTY);

            // 修改条件符号
            if (Objects.equals(first.getOperator().trim(), "=")) {
                first.setOperator(SqlConsts.IN);
            } else if (Objects.equals(first.getOperator().trim(), "!=")) {
                first.setOperator(SqlConsts.NOT_IN);
            }
            result.add(first);
        }

        return result;
    }

    /**
     * 构建分组 key
     *
     * @param dataScope
     * @return
     */
    public static String buildGroupKey(DataScopeInfo dataScope) {
        return dataScope.getOperator() + "-" +
            dataScope.getTableName() + "-" +
            dataScope.getColumnName();
    }

    /**
     * 对数据权限排序
     *
     * @param dataScope
     * @return
     */
    public static List<DataScopeInfo> sortDataScope(List<DataScopeInfo> dataScope) {
        if (CollUtil.isEmpty(dataScope)) {
            return Collections.emptyList();
        }
        return dataScope.stream().sorted((o1, o2) -> o1.getSort() - o2.getSort()).collect(Collectors.toList());
    }

    /**
     * 构建SQL
     *
     * @param dataScopeInfo 数据权限实体
     * @return
     */
    public static String buildCondition(List<DataScopeInfo> dataScopeInfo) {
        return buildCondition(dataScopeInfo, null, null);

    }

    /**
     * 构建SQL
     *
     * @param dataScopeInfo 数据权限实体
     * @param template      构建SQL模板
     * @param params        SQL模板替换参数
     * @return
     */
    public static String buildCondition(List<DataScopeInfo> dataScopeInfo, String template, Map<String, Object> params) {
        if (CollUtil.isEmpty(dataScopeInfo)) {
            return null;
        }
        String conditionSql = null;
        if (StrUtil.isNotBlank(template)) {
            conditionSql = doBuildConditionByTemplate(dataScopeInfo, template, params);
        } else {
            conditionSql = doBuildCondition(dataScopeInfo);
        }
        return conditionSql;
    }

    /**
     * 构建SQL
     *
     * @param dataScopeInfo
     * @return
     */
    private static String doBuildCondition(List<DataScopeInfo> dataScopeInfo) {
        boolean first = true;
        StringBuilder result = new StringBuilder();
        for (DataScopeInfo scopeInfo : dataScopeInfo) {
            String sql = toSql(scopeInfo);
            if (!first) {
                result.append(SqlConsts.AND);
            }
            result.append(SqlConsts.BLANK)
                .append(sql);
            first = false;
        }

        return result.toString();
    }

    /**
     * 构建SQL根据模板
     *
     * @param dataScopeInfo
     * @param template
     * @param params
     * @return
     */
    private static String doBuildConditionByTemplate(List<DataScopeInfo> dataScopeInfo, String template, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>(6);
        }
        for (DataScopeInfo scopeInfo : dataScopeInfo) {
            String dataScopeSql = toSql(scopeInfo);
            // 构建的SQL也可作为替换文本
            params.put(scopeInfo.getKey(), dataScopeSql);
        }
        return TemplateUtils.replacePlaceholders(template, params);
    }
}
