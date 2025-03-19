package com.dddemo.store;

import lombok.Data;

import java.util.List;

/**
 * @Author zoulinchuan
 * @description: 用户session
 */
@Data
public class UserSessionInfo {
    private Integer id;
    private List<Integer> dataScopeIds;
}
