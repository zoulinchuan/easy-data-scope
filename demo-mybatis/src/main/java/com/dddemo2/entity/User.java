package com.dddemo2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author zoulinchuan
 * @description: 用户实体
 */
@Data
@TableName("`user`")
public class User {

    private int id;
    private String username;
    private String age;
}
