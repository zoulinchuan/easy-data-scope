package cn.zlinchuan.store;

/**
 * 用户session上下文
 *
 * @author zoulinchuan
 */
public class UserSessionContext {
    private static ThreadLocal<UserSessionInfo> threadLocal = new ThreadLocal<>();

    public static UserSessionInfo getUserSession() {
        return threadLocal.get();
    }

    public static void setUserSession(UserSessionInfo session) {
        threadLocal.set(session);
    }

    public static void clean() {
        threadLocal.remove();
    }
}
