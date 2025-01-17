package com.oliver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oliver.entity.Users;
import com.oliver.mapper.UsersMapper;
import com.oliver.service.UsersService;
import com.oliver.utils.Jwtutils;
import com.oliver.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

    //查询所有用户资料
    @GetMapping("/user")
    public String query()  {

        List<Users> list= usersMapper.findUsers();

        return "查询结果";

    }
    //用户登录
    @PostMapping("/login")
        public Result login(@RequestBody Users users){
        String token= Jwtutils.generateToken(users.getUsername());
        Map<String,Object> date1=usersService.login(users);
        if(date1 != null){
            return Result.ok();
        }
           return   Result.error();


    }
    //jwt跨域认证
    @GetMapping("/info")
    public Result info(@RequestParam("token") String token){

        Map<String,Object> date1=usersService.getUserInfo(token);
        if(date1 != null){
            return Result.ok();
        }
        return   Result.error();


    }
    //用户注册列表

    @PostMapping("/register")
    public Result register(@RequestBody Users users) {
        if(users == null){
            return Result.errorByCodeMessage(401,"传输的数据为null");
        }
        if(users.getUsername() == null||users.getPassword()==null||users.getPhone()==null){
            return Result.errorByCodeMessage(401,"传输的必要数据有null");
        }
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("Username", users.getUsername());
        Users existingUser = usersService.getBaseMapper().selectOne(qw);

        if (existingUser != null) {
            // 用户名已存在，返回错误响应。
            return Result.errorByCodeMessage(401,"用户名已存在");
        } else
            // 将新用户保存到数据库。
            usersService.saveUsers(users);
            return Result.ok();

    }
    // unsubscribe 注销账户
    @PostMapping("/unsubscribe")
    public  Result unsubscribe(@RequestBody Users users){
        if(users==null){
            return Result.error();
        }else{
            usersMapper.deleteById(users);
        }
        return Result.ok();
    }

    //无用测试类
    @GetMapping("/test")
    public List<Users> test(){
        List<Users> date2=usersService.getUsers();
        return date2;
    }
    @GetMapping("/test2")
    public Result test2(){
        Users users=usersMapper.selectById(8989);
        return Result.successByKeyValue("users",users);
    }
}
