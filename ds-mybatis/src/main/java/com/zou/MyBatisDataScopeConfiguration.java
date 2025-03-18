package com.zou;

import com.zou.interceptor.DataScopeInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @Author zoulinchuan
 * @Date 2025/3/18 11:26
 * @description: MyBatis数据权限配置
 */
public class MyBatisDataScopeConfiguration {
    public MyBatisDataScopeConfiguration(List<SqlSessionFactory> sqlSessionFactoryList) {
        if (sqlSessionFactoryList != null && !sqlSessionFactoryList.isEmpty()) {
            DataScopeInterceptor dataScopeInterceptor = new DataScopeInterceptor();
            for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
                sqlSessionFactory.getConfiguration().addInterceptor(dataScopeInterceptor);
            }
        }
    }
}
