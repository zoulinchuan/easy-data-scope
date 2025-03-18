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
        String template = "select * from ({{_DATA_SCOPE_FLAG}}) where id = 1";
        Map<String, Object> params = new HashMap<>();
        params.put("_DATA_SCOPE_FLAG", "select * from user where id in (1,2,3,4)");
        System.out.println(TemplateUtils.replacePlaceholders(template, params));
    }
}
