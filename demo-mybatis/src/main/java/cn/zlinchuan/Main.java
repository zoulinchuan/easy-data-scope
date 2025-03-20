package cn.zlinchuan;

import cn.zlinchuan.anno.EnableMybatisDataScope;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main 启动类
 *
 * @author zoulinchuan
 */
@SpringBootApplication
@EnableMybatisDataScope
@MapperScan("cn.zlinchuan.mapper")
public class Main {

    /**
     * Main
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}