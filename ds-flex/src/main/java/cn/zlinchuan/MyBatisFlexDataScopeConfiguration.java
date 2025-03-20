package cn.zlinchuan;

import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import com.mybatisflex.core.dialect.DbType;
import com.mybatisflex.core.dialect.DialectFactory;
import org.springframework.stereotype.Component;

/**
 * MyBatisFlex数据权限配置类
 *
 * @author zoulinchuan
 */
@Component
public class MyBatisFlexDataScopeConfiguration{
    public MyBatisFlexDataScopeConfiguration() {
        DialectFactory.registerDialect(DbType.MYSQL, new DataScopeDialect());

        AuditManager.setAuditEnable(true);
        //设置 SQL 审计收集器
        MessageCollector collector = new ConsoleMessageCollector();
        AuditManager.setMessageCollector(collector);
    }
}
