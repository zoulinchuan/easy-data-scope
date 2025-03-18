package com.zou.util;

import com.mybatisflex.core.query.*;
import com.mybatisflex.core.util.CollectionUtil;

import java.util.List;
import java.util.function.Function;

/**
 * @Author zoulinchuan
 * @Date 2025/3/18 15:33
 * @description: QueryWrapper工具类
 */
public class QueryWrapperUtil {

    /**
     * 遍历所有的条件
     */
    public static void foreachQueryWrapper(QueryWrapper queryWrapper, Function<RawQueryCondition, Object> function) {
        List<QueryTable> queryTables = CPI.getQueryTables(queryWrapper);
        List<QueryTable> joinTables = CPI.getJoinTables(queryWrapper);
        List<QueryTable> allTables = CollectionUtil.merge(queryTables, joinTables);

        for (QueryTable queryTable : allTables) {
            if (queryTable instanceof SelectQueryTable) {
                SelectQueryTable selectQueryTable = (SelectQueryTable) queryTable;
                foreachQueryWrapper(selectQueryTable.getQueryWrapper(), function);
            }
        }

        QueryCondition whereQueryCondition = CPI.getWhereQueryCondition(queryWrapper);
        foreachCondition(whereQueryCondition, function);
    }

    public static void foreachCondition(QueryCondition whereQueryCondition, Function<RawQueryCondition, Object> function) {
        while (whereQueryCondition != null) {
            if (whereQueryCondition instanceof Brackets) {
                QueryCondition childCondition = ((Brackets) whereQueryCondition).getChildCondition();
                foreachCondition(childCondition, function);
            }else if (whereQueryCondition instanceof OperatorQueryCondition) {
                QueryCondition childCondition = ((OperatorQueryCondition) whereQueryCondition).getChildCondition();
                foreachCondition(childCondition, function);
            }else if (whereQueryCondition instanceof OperatorSelectCondition) {
                foreachQueryWrapper(((OperatorSelectCondition) whereQueryCondition).getQueryWrapper(), function);
            }else if (whereQueryCondition instanceof RawQueryCondition) {
                function.apply((RawQueryCondition) whereQueryCondition);
            }
            whereQueryCondition = CPI.getNextCondition(whereQueryCondition);
        }
    }
}
