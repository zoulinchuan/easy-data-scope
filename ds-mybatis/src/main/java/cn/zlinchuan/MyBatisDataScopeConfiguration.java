package cn.zlinchuan;

import cn.zlinchuan.interceptor.DataScopeInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * MyBatis数据权限配置
 *
 * @author zoulinchuan
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
