import com.zou.TemplateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 22:36
 * @description: 模板处理工具测试类
 */
public class TemplateUtilsTest {
    public static void main(String[] args) {
        String template = "文本1{flag}文本2{flag2}文本3";
        Map<String, Object> params = new HashMap<>();
        params.put("flag", " =这是标记位= ");
        params.put("flag2", " =这是标记位2= ");
        System.out.println(TemplateUtils.replacePlaceholders(template, params));
    }
}
