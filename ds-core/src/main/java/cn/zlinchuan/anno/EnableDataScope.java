package cn.zlinchuan.anno;

import cn.zlinchuan.DataScopeAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启数据权限
 *
 * @author zoulinchuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DataScopeAnnotationBeanPostProcessor.class)
public @interface EnableDataScope {
}
