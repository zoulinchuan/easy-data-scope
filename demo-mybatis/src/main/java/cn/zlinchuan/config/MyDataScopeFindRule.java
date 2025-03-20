package cn.zlinchuan.config;

import cn.zlinchuan.entity.AuthDataScope;
import cn.zlinchuan.mapper.AuthDataScopeMapper;
import cn.zlinchuan.store.UserSessionContext;
import cn.zlinchuan.store.UserSessionInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.zlinchuan.DataScopeFindRule;
import cn.zlinchuan.DataScopeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zoulinchuan
 */
@Component
public class MyDataScopeFindRule implements DataScopeFindRule {

    @Autowired
    private AuthDataScopeMapper authDataSocpeMapper;

    @Override
    public List<DataScopeInfo> find(String[] key) {
        UserSessionInfo userSession = UserSessionContext.getUserSession();
        if (userSession != null) {

            List<AuthDataScope> authDatascopes =
                authDataSocpeMapper.selectList(new QueryWrapper<AuthDataScope>()
                    .in("id", userSession.getDataScopeIds()));
            List<DataScopeInfo> dataScopeInfos = new ArrayList<>(authDatascopes.size());
            for (AuthDataScope authDatascope : authDatascopes) {
                DataScopeInfo dataScopeInfo = new DataScopeInfo();
                dataScopeInfo.setKey(authDatascope.getDatascopeKey());
                dataScopeInfo.setOperator(authDatascope.getDatascopeOpName());
                dataScopeInfo.setTableName(authDatascope.getDatascopeTbName());
                dataScopeInfo.setColumnName(authDatascope.getDatascopeColName());
                dataScopeInfo.setSql(authDatascope.getDatascopeSql());
                dataScopeInfo.setValue(authDatascope.getDatascopeValue());
                dataScopeInfo.setSort(authDatascope.getDatascopeSort());
                dataScopeInfos.add(dataScopeInfo);
            }
            return dataScopeInfos;
        }

        return Collections.emptyList();
    }
}
