package com.zou.anno;

import com.zou.MyBatisFlexDataScopeConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zoulinchuan
 * @Date 2025年03月15日 星期六 22:51
 * @description: 开启flex数据权限
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(MyBatisFlexDataScopeConfiguration.class)
public @interface EnableFlexDataScope {
}
