package org.sy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sy.bean.Role;
import org.sy.bean.User;
import org.sy.mapper.IRoleMapper;
import org.sy.mapper.IUserMapper;
import org.sy.service.IUserService;

import java.util.List;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:16
 * @Description: (qwq..)
 */

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public User findAllUserInfoByUsername(String username) {
        //查询用户
        User user = this.baseMapper.selectByUsername(username);

        if(user == null){
            return null;
        }

        //用户的角色集合
        List<Role> roleList = roleMapper.selectByUserId(user.getId());

        user.setRoleList(roleList);

        return user;
    }

    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }

    @Override
    public User findSimpleUserInfoById(Integer id) {
        return null;
    }
}
