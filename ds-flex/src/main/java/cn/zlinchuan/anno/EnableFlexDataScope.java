package cn.zlinchuan.anno;

import cn.zlinchuan.MyBatisFlexDataScopeConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启flex数据权限
 *
 * @author zoulinchuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@EnableDataScope
@Import(MyBatisFlexDataScopeConfiguration.class)
public @interface EnableFlexDataScope {
}
