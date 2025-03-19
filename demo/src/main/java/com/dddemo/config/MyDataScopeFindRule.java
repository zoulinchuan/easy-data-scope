package com.dddemo.config;

import com.dddemo.store.UserSessionContext;
import com.dddemo.store.UserSessionInfo;
import com.zou.DataScopeFindRule;
import com.zou.DataScopeInfo;
import com.dddemo.entity.AuthDatascope;
import com.dddemo.mapper.AuthDataSocpeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author zoulinchuan
 * @description:
 */
@Component
public class MyDataScopeFindRule implements DataScopeFindRule {

    @Autowired
    private AuthDataSocpeMapper authDataSocpeMapper;

    @Override
    public List<DataScopeInfo> find(String[] key) {
        UserSessionInfo userSession = UserSessionContext.getUserSession();
        if (userSession != null) {
            List<AuthDatascope> authDatascopes = authDataSocpeMapper.selectListByIds(userSession.getDataScopeIds());
            List<DataScopeInfo> dataScopeInfos = new ArrayList<>(authDatascopes.size());
            for (AuthDatascope authDatascope : authDatascopes) {
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
