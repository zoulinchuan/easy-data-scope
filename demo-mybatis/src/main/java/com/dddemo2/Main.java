package com.dddemo2;

import com.zou.anno.EnableMybatisDataScope;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author zoulinchuan
 * @Date 2025/3/19 10:27
 * @description:
 */
@SpringBootApplication
@EnableMybatisDataScope
@MapperScan("com.dddemo2.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}