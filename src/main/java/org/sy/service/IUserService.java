package org.sy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.sy.bean.User;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:11
 * @Description: IService接口中也是封装基本的CRUD操作，单表的CRUD的就不用写了
 *  * 复杂的业务方法还是必须要自己手写
 */

//封装了基本的CRUD(增删改查)
public interface IUserService extends IService<User> {
    //根据用户名，查询用户信息，包含用户的角色信息 和 用户的权限信息
    public User findAllUserInfoByUsername(String username);

    public User findSimpleUserInfoByUsername(String username);

    public User findSimpleUserInfoById(Integer id);
}
