package cn.zlinchuan;

import cn.zlinchuan.anno.DataScope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DataSource信息保存对象，保存在@DataScope中出现的信息
 *
 * @author zoulinchuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataScopeConfig {
    private Set<String> keys;

    private String template;

    private boolean merge;

    private String logical;

    private boolean flag;

    // 构建方法
    public static DataScopeConfig build(DataScope annotation) {
        DataScopeConfig config = new DataScopeConfig();
        config.keys = Arrays.stream(annotation.keys()).collect(Collectors.toSet());
        config.template = annotation.template();
        config.merge = annotation.merge();
        config.logical = annotation.logical();
        config.flag = annotation.flag();
        return config;
    }
}
