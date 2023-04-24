package com.wz.store.controller;

import com.wz.store.entity.User;
import com.wz.store.service.IUserService;
import com.wz.store.service.ex.InsertException;
import com.wz.store.service.ex.UsernameDuplicatedException;
import com.wz.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public JsonResult<Void> req(User user){
        userService.reg(user);
        return new JsonResult<>(OK);

    }

    @RequestMapping("/login")
    public JsonResult<User> login(User user, HttpSession httpSession){
        User date = userService.login(user);
        httpSession.setAttribute("uid",date.getUid());
        httpSession.setAttribute("username",date.getUsername());
        return new JsonResult<>(OK,date);
    }

    @RequestMapping("resetPassword")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getUserIdFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/queryUser")
    public JsonResult<User> getByUid(HttpSession session){
        User date = userService.getByUid(getUserIdFromSession(session));
        return new JsonResult<>(OK,date);
    }

    @RequestMapping("/updateInfo")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象有四部分的数据: username、phone、email. gender
        //uid数据需要再次封装到user对象中
        userService.changeInfo(getUserIdFromSession(session),user.getUsername(),user);
        return new JsonResult<>(OK);
    }
}
