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
@TableName(value = "sys_permission")
public class Permission {
    private Integer id;

    @TableField(value="per_name")
    private String perName;

    private String type;  //权限类型
    private String url;

    @TableField(value="per_code")
    private String perCode;

    @TableField(value="parent_id")
    private Integer parentId;

    @TableField(value="parent_ids")
    private String parentIds;

    @TableField(value="sort_string")
    private String sortString;

    private int available;
}
