package cn.zlinchuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zoulinchuan
 */
@Data
@TableName("`auth_datascope`")
public class AuthDataScope {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 数据权限标识
     */
    private String datascopeKey;
    /**
     * 数据权限名称
     */
    private String datascopeName;
    /**
     * 数据权限表别名
     */
    private String datascopeTbName;
    /**
     * 数据权限字段名
     */
    private String datascopeColName;
    /**
     * 数据权限操作符
     */
    private String datascopeOpName;
    /**
     * 数据权限sql
     */
    private String datascopeSql;
    /**
     * 数据权限值
     */
    private String datascopeValue;
    /**
     * 数据权限描述
     */
    private String datascopeDes;
    /**
     * 数据权限排序
     */
    private Integer datascopeSort;
}

