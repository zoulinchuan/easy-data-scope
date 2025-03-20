package cn.zlinchuan.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 用户实体
 *
 * @author zoulinchuan
 */
@Data
@Table("user")
public class User {

    @Id(keyType = KeyType.Auto)
    private int id;
    private String username;
    private String age;
}
