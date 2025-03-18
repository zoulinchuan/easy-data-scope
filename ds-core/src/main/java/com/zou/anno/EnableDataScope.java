package com.zou.anno;

import com.zou.DataScopeAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zoulinchuan
 * @Date 2025/3/18 14:09
 * @description: 开启数据权限
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DataScopeAnnotationBeanPostProcessor.class)
public @interface EnableDataScope {
}
