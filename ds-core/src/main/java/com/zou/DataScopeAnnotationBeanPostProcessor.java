package com.zou;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 15:07
 * @description: DataScope 注解处理器
 */
@Slf4j
// @Component
public class DataScopeAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

    private final DataScopeFindRule dataScopeFindRule;

    public DataScopeAnnotationBeanPostProcessor(DataScopeFindRule dataScopeFindRule) {
        log.info("数据权限拦截已启用");
        this.dataScopeFindRule = dataScopeFindRule;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.advisor = new DataScopeAnnotationAdvisor(this.dataScopeFindRule, beanFactory);
    }
}
