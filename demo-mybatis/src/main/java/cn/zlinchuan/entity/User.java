package cn.zlinchuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zoulinchuan
 */
@Data
@TableName("`user`")
public class User {

    private int id;
    private String username;
    private String age;
}
