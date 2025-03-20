import cn.zlinchuan.TemplateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板处理工具测试类
 *
 * @author zoulinchuan
 */
public class TemplateUtilsTest {
    public static void main(String[] args) {
        String template = "select * from ({{_DATA_SCOPE_FLAG}}) where id = 1";
        Map<String, Object> params = new HashMap<>();
        params.put("_DATA_SCOPE_FLAG", "select * from user where id in (1,2,3,4)");
        System.out.println(TemplateUtils.replacePlaceholders(template, params));
    }
}
