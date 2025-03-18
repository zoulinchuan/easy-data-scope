### 简介
这是一个可以通过注解方式快速实现数据权限的项目，他可以让你在项目中使用注解变可以快速实现数据权限
- 配置简单
- 使用便捷

### 核心注解

``` java
public @interface DataScope {
    /**
     * 通过传递给DataScopeFindRule.find方法来获取指定的数据权限实体
     * @return
     */
    String[] keys();

    /**
     * 构建模板
     * 当key为多个时此值生效
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
     * @return
     */
    String logical() default SqlConsts.AND;
}
```

### 使用方式
##### 开启数据权限处理
<img width="1020" alt="image" src="https://github.com/user-attachments/assets/0f7e8bc8-cfe1-43b8-8863-501d37761de9" />

##### 使用1: 任意方法
<img width="1288" alt="image" src="https://github.com/user-attachments/assets/2c38d69a-eada-4af7-98db-12205bbb8975" />

生成SQL

``` SQL
 SELECT `id`, `username`, `age` FROM `user` WHERE  /*这是使用template生成的SQL*/user.id   IN  (1, 2, 3) OR user.age > 10  AND (/*这是使用template生成的SQL*/user.id   IN  (1, 2, 3) OR user.age > 10)
```

##### 使用2:MyBatis
<img width="1286" alt="image" src="https://github.com/user-attachments/assets/444f16aa-3382-4913-9a45-60f3c83cd989" />
<img width="688" alt="image" src="https://github.com/user-attachments/assets/b201dc0a-ce7a-4a96-b658-b8f697555d10" />

生成SQL

``` SQL
select * from user where id = ? OR (/*这是使用template生成的SQL*/user.id   IN  (1, 2, 3) OR user.age > 10)
```

### 支持情况
- 原生mybatis ✅
- mybatis-plus （开发中）
- mybatis-flex ✅
