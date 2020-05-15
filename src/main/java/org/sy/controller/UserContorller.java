package org.sy.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sy.annotation.AvoidDuplicateToken;
import org.sy.bean.ResponseResult;
import org.sy.bean.StatusCode;
import org.sy.bean.User;
import org.sy.dto.LoginUser;
import org.sy.service.IUserService;
import org.sy.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:25
 * @Description: (qwq..)
 */
@RestController
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserServiceImpl us;

    @Autowired
    private IUserService userService;

    @GetMapping("/findAll")
    public ResponseResult finAll(){

        List<User> list = us.list();

        return new ResponseResult(StatusCode.SUCCESS, true, "查询所有", list);
    }
    @GetMapping
    public ResponseResult finAdd(@RequestBody User user){
        boolean save = us.save(user);

        return new ResponseResult(StatusCode.SUCCESS, true, "查询所有", save);
    }


    @GetMapping("/findAllOK")
    public  List<User> finAllOK(){

        List<User> list = us.list();

        return list;
    }
    @AvoidDuplicateToken(rmove = true)
    @PostMapping("/add")
    @RequiresPermissions("user:add")
    public ResponseResult add(HttpServletRequest request) {
        String msg = request.getParameter("msg");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(msg);

        return null;
    }

    @GetMapping("/remove/{id}")
    @RequiresPermissions("user:remove")
    public ResponseResult remove(@PathVariable (value = "id") Integer id){
        System.out.println("user:remove:id"+id);
        return null;
    }

    @GetMapping("/find")
    @RequiresPermissions("user:find")
    public ResponseResult find() {
        return new ResponseResult(StatusCode.NO_PERMIT, false, "没有权限", null);
    }

    @GetMapping("/test")
    public void test(String username){
        User user = userService.findAllUserInfoByUsername(username);

        System.out.println(user.toString());
        System.out.println(user.getRoleList());
        System.out.println(user.getRoleList().get(0).getPermissionList());

//        List<UserRole> userRoles = userService.selectUserRoles();
//        System.out.println(userRoles);
    }
}
