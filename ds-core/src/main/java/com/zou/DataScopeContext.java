package com.zou;

import cn.hutool.core.collection.CollUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 16:01
 * @description: 数据权限上下文对象
 */
public class DataScopeContext {
    private static final ThreadLocal<List<DataScopeInfo>> DATA_SCOPE_INFO_LIST= new ThreadLocal<>();
    private static final ThreadLocal<DataScopeConfig> DATA_SCOPE_CONFIG = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>> DATA_SCOPE_PARAMS = new ThreadLocal<>();

    public static List<DataScopeInfo> getDataScopeInfoList() {
        return DATA_SCOPE_INFO_LIST.get();
    }

    public static void setDataScopeInfoList(List<DataScopeInfo> dataScopeInfo) {
        DATA_SCOPE_INFO_LIST.set(dataScopeInfo);
    }

    public static DataScopeConfig getDataScopeConfig() {
        return DATA_SCOPE_CONFIG.get();
    }

    public static void setDataScopeConfig(DataScopeConfig dataSourceHolder) {
        DATA_SCOPE_CONFIG.set(dataSourceHolder);
    }

    public static Object getDataScopeParam(String key) {
        Map<String, Object> map = DATA_SCOPE_PARAMS.get();
        if (CollUtil.isEmpty(map)) {
            return null;
        }

        return map.get(key);
    }

    public static Map<String, Object> getDataScopeParams() {
        return DATA_SCOPE_PARAMS.get();
    }

    public static Object putDataScopeParam(String key, Object value) {
        Map<String, Object> map = DATA_SCOPE_PARAMS.get();
        if (map == null) {
            map = new HashMap<>(5);
            DATA_SCOPE_PARAMS.set(map);
        }

        return map.put(key, value);
    }

    protected static void clear() {
        DATA_SCOPE_INFO_LIST.remove();
        DATA_SCOPE_CONFIG.remove();
        DATA_SCOPE_PARAMS.remove();
    }
}
