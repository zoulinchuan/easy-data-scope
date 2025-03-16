package com.zou;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zoulinchuan
 * @description: DataScope表述信息
 * [表名].[列名称] [运算符] [数据权限 SQL|数据权限值]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataScopeInfo {
    /**
     * 标识key
     */
    private String key;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 运算符（=、!=、in、not in）
     */
    private String operator;

    /**
     * 数据权限 SQL
     */
    private String sql;

    /**
     * 数据权限值
     */
    private String value;

    /**
     * 排序
     */
    private int sort;
}
