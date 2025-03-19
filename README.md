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
```

##### 开启数据权限处理
<img width="1020" alt="image" src="https://github.com/user-attachments/assets/0f7e8bc8-cfe1-43b8-8863-501d37761de9" />

### 使用方式1(直接拼接)
> 直通过获取注解的logical属性, 在原SQL中以AND或OR的形式直接拼接在SQL末尾
> 
> 优势：简单、无代码侵入
> 
> 缺点：对复杂查询显得力不从心

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

### 使用方式2(占位符替换)
> 通过将生成好的数据权限SQL替换到在SQL中所定义的特殊占位
> 
> 优势：能够支持相较于【直接拼接】方案更复杂的SQL
> 
> 缺点：对SQL有一定侵入性

##### 使用1: 任意方法
<img width="925" alt="image" src="https://github.com/user-attachments/assets/e9c1ccc0-a2d0-4ec1-9df5-53ce7f2684e6" />

生成SQL

``` SQL
SELECT * FROM (SELECT `id`, `username`, `age` FROM `user` WHERE  user.id   IN  (1, 2, 3) OR user.age > 10/*这是使用template生成的SQL*/ ) AS `t` WHERE  id = 1  ORDER BY id
```

##### 使用2:MyBatis

<img width="824" alt="image" src="https://github.com/user-attachments/assets/378b99c7-7d95-42ab-b3a8-e1c1abc54da0" />
<img width="776" alt="image" src="https://github.com/user-attachments/assets/0d5f7a89-7f0b-43c8-ab44-1d332bf93f55" />

生成SQL

``` SQL
select * from (select * from user where user.id   IN  (1, 2, 3) OR user.age > 10/*这是使用template生成的SQL*/) t where id = 1
```


### 支持情况
- 原生mybatis ✅
- mybatis-plus （开发中... 目前可以通过开启原生MyBatis支持达到效果，单仅能使用直接拼接方案，对于page情况兼容不好）
- mybatis-flex ✅
