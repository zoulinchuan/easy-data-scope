package cn.zlinchuan.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        return new PaginationInterceptor();
    }
}
