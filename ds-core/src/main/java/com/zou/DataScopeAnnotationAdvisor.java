package com.zou;

import com.zou.anno.DataScope;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 15:00
 * @description: @DataScope Advisor
 */
@Slf4j
public class DataScopeAnnotationAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;
    private final Pointcut pointcut;
    private final DataScopeFindRule dataScopeFindRule;
    private final BeanFactory beanFactory;

    public DataScopeAnnotationAdvisor(DataScopeFindRule dataScopeFindRule, BeanFactory beanFactory) {
        this.advice = buildAdvice();
        Set<Class<? extends Annotation>> scopes = new LinkedHashSet<Class<? extends Annotation>>();
        scopes.add(DataScope.class);
        this.pointcut = buildPointcut(scopes);
        this.dataScopeFindRule = dataScopeFindRule;
        this.beanFactory = beanFactory;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    private Advice buildAdvice() {
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                log.info("=======数据权限拦截=======");
                DataScope dataScope = getMethodAnnotation(invocation, DataScope.class);
                DataScopeHolder dataScopeHolder = new DataScopeHolder(Arrays.stream(dataScope.keys()).collect(Collectors.toSet()), dataScope.template(), dataScope.merge(), dataScope.logical());
                DataScopeContext.setDataScopeHolder(dataScopeHolder);
                // 通过用户定义规则获取到数据权限实体
                List<DataScopeInfo> dataScopeInfos = beanFactory.getBean(DataScopeFindRule.class).find(dataScope.keys());
                if (dataScope.merge()) {
                    dataScopeInfos = AnalysisDataScope.merge(dataScopeInfos);
                }
                DataScopeContext.setDataScopeInfo(dataScopeInfos);
                return invocation.proceed();
            }
        };
    }

    private Pointcut buildPointcut(Set<Class<? extends Annotation>> annotations) {
        ComposablePointcut result = null;

        for (Class<? extends Annotation> annotation : annotations) {
            // 类级别注解匹配
            Pointcut cpc = new AnnotationMatchingPointcut(annotation, true);
            // 方法级别注解匹配
            Pointcut mpc = new AnnotationMatchingPointcut(null, annotation, true);
            if (result == null) {
                // 初始化一定要传入参数，不然将代理所有的类、方法
                result = new ComposablePointcut(cpc);
            } else {
                result.union(cpc);
            }
            result.union(mpc);
        }

        return result;
    }

    /**
     * 获取方法、类上的注解
     * @param invocation
     * @param annotationType
     * @return
     * @param <T>
     */
    public <T extends Annotation> T getMethodAnnotation(MethodInvocation invocation, Class<T> annotationType) {
        // 获取被拦截的方法
        Method method = invocation.getMethod();

        // 处理代理类方法（如 JDK 动态代理）
        method = AopUtils.getMostSpecificMethod(method, invocation.getThis().getClass());

        // 查找方法上的注解（包括元注解和继承的注解）
        T annotation = AnnotationUtils.findAnnotation(method, annotationType);

        // 若方法未找到，检查类级别的注解
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType);
        }

        return annotation;
    }
}
