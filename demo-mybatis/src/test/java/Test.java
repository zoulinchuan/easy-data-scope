import cn.zlinchuan.Main;
import cn.zlinchuan.mapper.UserMapper;
import cn.zlinchuan.service.UserService;
import cn.zlinchuan.store.UserSessionContext;
import cn.zlinchuan.store.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author zoulinchuan
 */
@SpringBootTest(classes = Main.class)
public class Test {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public UserService userService;

    // @Before
    public void mockLogin() {
        // 模拟登录
        UserSessionInfo userSessionInfo = new UserSessionInfo();
        userSessionInfo.setId(1);
        userSessionInfo.setDataScopeIds(Arrays.asList(1, 2, 3, 4));
        UserSessionContext.setUserSession(userSessionInfo);
    }

    @org.junit.jupiter.api.Test
    public void test1() {
        mockLogin();
        // userMapper.selectList(null).forEach(System.out::println);
        userService.page().getRecords().forEach(System.out::println);
    }
}
