package com.zou.anno;

import com.zou.constant.SqlConsts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zoulinchuan
 * @description: 数据权限注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
    /**
     * 通过传递给DataScopeFindRule.find方法来获取指定的数据权限实体
     * @return
     */
    String[] keys();

    /**
     * 构建模板
     * TODO 注意：当key为多个时此值生效
     * key1 ==SQL==> table1.column1 = 1
     * key2 ==SQL==> table2.column2 = 2
     * 示例：template = "{key1} OR {key2}"
     * 通过template生成后的SQL：table1.column1 = 1 OR table2.column2 = 2
     * @return
     */
    String template() default "";

    /**
     * 是否对数据权限进行自动合并
     * 当操作符为 =、!= 时间如果TableName、ColumnName、操作符一样，并且使用的是 Value 形式将会对数据权限进行合并为 IN、NOT IN
     * 示例：
     * 权限1：=、table1、column1、Value1 >>> table1.column1 = Value1
     * 权限2：=、table1、column1、Value2 >>> table1.column1 = Value2
     * 最终合并 in table1、column1、“Value1, Value2" >>> table1.column1 in (Value1, Value2)
     * @return
     */
    boolean merge() default false;

    /**
     * 逻辑符
     * 决定数据权限SQL拼接到当前执行的SQL中用的使用的是 AND还是OR..
     * TODO 注意：在flag为true时此值将会失效
     * @return
     */
    String logical() default SqlConsts.AND;

    /**
     * 是否使用数据权限标记位标记位，true是 false否
     * @return
     */
    boolean flag() default false;
}
