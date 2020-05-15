package org.sy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.sy.bean.User;

import java.util.List;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:05
 * @Description: (qwq..)
 */

//接口只能继承宁一个接口
//BaseMapper接口中封装了基本的增删查改
public interface IUserMapper extends BaseMapper<User> {
    User selectByUsername(@Param("username") String username);
}
