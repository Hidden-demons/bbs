package org.sy.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: 辉
 * @Date:2019/09/27 16:33
 * @Description: (qwq..)
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@TableName(value = "sys_role")
public class Role {
    private Integer id;
    private String rolename;
    private String remarks;  //备注
    private int available;

    private List<Permission> permissionList;

    public Role(Integer id, String rolename, String remarks, int available) {
        this.id = id;
        this.rolename = rolename;
        this.remarks = remarks;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", remarks='" + remarks + '\'' +
                ", available=" + available +
                '}';
    }
}