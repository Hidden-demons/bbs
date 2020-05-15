package org.sy.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: 辉
 * @Date:2019/09/27 16:35
 * @Description: (qwq..)
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@TableName(value = "sys_user_role")
public class UserRole {
    private Integer id;

    @TableField(value="user_id")
    private Integer userId;

    @TableField(value="role_id")
    private Integer roleId;
}