package com.wz.store.service;



import com.wz.store.entity.User;
import com.wz.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("zs");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = new User();
        user.setUsername("ls");
        user.setPassword("123456");
        User login = userService.login(user);
        System.out.println(login);
    }

    @Test
    public void changePassword(){
        userService.changePassword(35,"ls","123456","654321");

    }
    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(35));
    }
    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("12312414");
        user.setEmail("test01@qq.com");
        user.setGender(0);
        userService.changeInfo(35,"管理员",user);
    }
    @Test
    public void changeAvatar(){
        userService.changeAvatar(35,"321","管理员");
    }

}
