package cn.zlinchuan.store;

import lombok.Data;

import java.util.List;

/**
 *
 * mock登陆用户Session
 *
 * @author zoulinchuan
 */
@Data
public class UserSessionInfo {
    private Integer id;
    private List<Integer> dataScopeIds;
}
