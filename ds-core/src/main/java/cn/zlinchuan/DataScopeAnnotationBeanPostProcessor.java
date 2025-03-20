package cn.zlinchuan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;

/**
 * DataScope 注解处理器
 *
 * @author zoulinchuan
 */
@Slf4j
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
