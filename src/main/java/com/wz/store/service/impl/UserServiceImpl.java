package com.wz.store.service.impl;

import com.wz.store.entity.User;
import com.wz.store.mapper.UserMapper;
import com.wz.store.service.IUserService;
import com.wz.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     * @param user 用户的数据对象
     */
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User byUsername = userMapper.findByUsername(username);
        if (byUsername != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据:盐值的记录
        user.setSalt(salt);
        String newPassword = getMD5Password(oldPassword, salt);
        user.setPassword(newPassword);


        //补全数据: is_delete设置成0
        user.setIsDelete(0);
        //补全数据: 4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中产生了未知异常");
        }

    }

    @Override
    public User login(User user) {
        String username = user.getUsername();
        String oldPassword = user.getPassword();
        User result = userMapper.findByUsername(username);
        if (result == null){
            throw new UserNotExistException("用户数据不存在");
        }
        //获取盐值
        String salt = result.getSalt();
        String newMD5Password = getMD5Password(oldPassword,salt);
        if (!newMD5Password.equals(result.getPassword())){
            throw new WrongPasswordException("用户密码错误");
        }
        //判断is_delete字段的值是否为1表示被标记为删除
        if (result.getIsDelete() == 1){
            throw new UserNotExistException("用户数据不存在");
        }
        User newUser = new User();
        newUser.setUid(result.getUid());
        newUser.setUsername(result.getUsername());
        newUser.setAvatar(result.getAvatar());
        return newUser;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotExistException("用户数据不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new WrongPasswordException("密码错误");
        }
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新数据产生未知的异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UpdateException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * user对象中的数据phone,email,gender，手动再将uid,username封装user对象中
     * @param uid 用户的id
     * @param username 用户的名称
     * @param user 用户对象的数据
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UpdateException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新数据产生异常");
        }


    }

    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;

    }
}
