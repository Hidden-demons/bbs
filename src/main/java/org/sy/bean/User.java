package org.sy.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:04
 * @Description: (qwq..)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)

@TableName(value="sys_user")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private int locked;

    private List<Role> roleList;

    public User(Integer id, String username, String password, String salt, int locked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", locked=" + locked +
                '}';
    }
}