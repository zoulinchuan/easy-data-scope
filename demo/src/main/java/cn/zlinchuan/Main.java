package cn.zlinchuan;

import cn.zlinchuan.anno.EnableFlexDataScope;
import cn.zlinchuan.anno.EnableMybatisDataScope;
import cn.zlinchuan.config.MyDataScopeFindRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zoulinchuan
 */
@SpringBootApplication
@MapperScan("cn.zlinchuan.mapper")
@EnableFlexDataScope // 开启flex数据权限支持
@EnableMybatisDataScope // 开启原生MyBatis数据权限支持
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        context.getBean(MyDataScopeFindRule.class).find(new String[]{"USER_LIST"}).stream().forEach(System.out::println);
    }
}