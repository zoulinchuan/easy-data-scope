import com.dddemo2.Main;
import com.dddemo2.mapper.UserMapper;
import com.dddemo2.service.UserService;
import com.dddemo2.store.UserSessionContext;
import com.dddemo2.store.UserSessionInfo;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @Author zoulinchuan
 * @Date 2025/3/19 11:03
 * @description:
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
