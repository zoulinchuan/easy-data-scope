package com.dddemo;

import com.zou.anno.EnableFlexDataScope;
import com.zou.anno.EnableMybatisDataScope;
import com.dddemo.config.MyDataScopeFindRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author zoulinchuan
 * @description: demo
 */
@SpringBootApplication
@MapperScan("com.dddemo.mapper")
@EnableFlexDataScope // 开启flex数据权限支持
@EnableMybatisDataScope // 开启原生MyBatis数据权限支持
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        context.getBean(MyDataScopeFindRule.class).find(new String[]{"USER_LIST"}).stream().forEach(System.out::println);
    }
}