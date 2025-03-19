package com.dddemo.store;

/**
 * @Author zoulinchuan
 * @description: 用户session上下文
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
