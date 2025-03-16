package com.zou;

import com.zou.anno.EnableFlexDataScope;
import com.zou.config.MyDataScopeFindRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author zoulinchuan
 * @description: demo
 */
@SpringBootApplication
@MapperScan("com.zou.mapper")
@EnableFlexDataScope
@EnableAsync
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        context.getBean(MyDataScopeFindRule.class).find(new String[]{"USER_LIST"}).stream().forEach(System.out::println);
    }
}