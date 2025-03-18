package com.zou.anno;

import com.zou.MyBatisDataScopeConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zoulinchuan
 * @Date 2025年03月15日 星期六 22:51
 * @description: 开启mybatis数据权限
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@EnableDataScope
@Import(MyBatisDataScopeConfiguration.class)
public @interface EnableMybatisDataScope {
}
