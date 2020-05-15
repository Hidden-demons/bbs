package org.sy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.sy.bean.Role;

import java.util.List;

/**
 * @Auther: 辉
 * @Date:2019/09/29 14:45
 * @Description: (qwq..)
 * @Version 1.0
 */
//接口只能继承另一个接口
//BaseMapper接口中封装了基本的CRUD操作。
public interface IRoleMapper extends BaseMapper<Role> {
    public List<Role> selectByUserId(@Param("userId") int userId);
}
