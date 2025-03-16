package com.zou;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author zoulinchuan
 * @Date 2025年03月16日 星期日 18:07
 * @description: DataSource持有者对象，保存在@DataScope中出现的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataScopeHolder {
    private Set<String> keys;

    private String template;

    private boolean merge;

    private String logical;
}
