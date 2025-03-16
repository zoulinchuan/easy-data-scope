package com.zou;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author zoulinchuan
 * @description: 模板处理工具
 */
public class TemplateUtils {
    // 正则表达式模式：匹配 {key} 格式，捕获 key 值
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    /**
     * 替换字符串模板中的占位符
     * @param template 原始字符串，如 "Hello {name}, your code is {code}"
     * @param params 参数映射表，如 Map.of("name", "Alice", "code", "123")
     * @return 替换后的字符串
     */
    public static String replacePlaceholders(String template, Map<String, Object> params) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String key = matcher.group(1); // 提取 { } 中间的内容
            String replacement = (String) params.getOrDefault(key, "{" + key + "}"); // 找不到保留原占位符
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);
        
        return result.toString();
    }
}
