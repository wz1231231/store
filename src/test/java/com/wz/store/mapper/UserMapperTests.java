package com.wz.store.mapper;


import com.wz.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("wz");
        user.setPassword("123456");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void findByUsername(){
        User wz = userMapper.findByUsername("wz");
        System.out.println(wz);
    }
    @Test
    public void updatePasswordByUid(){
        Integer integer = userMapper.updatePasswordByUid(34, "123456", "管理员", new Date());

    }
    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(34));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(35);
        user.setPhone("123456789");
        user.setEmail("test@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(35,"123","ls",new Date());
    }
}
