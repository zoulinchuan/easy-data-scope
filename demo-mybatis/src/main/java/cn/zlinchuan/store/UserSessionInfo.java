package cn.zlinchuan.store;

import lombok.Data;

import java.util.List;

/**
 * 用户session
 *
 * @author zoulinchuan
 */
@Data
public class UserSessionInfo {
    private Integer id;
    private List<Integer> dataScopeIds;
}
