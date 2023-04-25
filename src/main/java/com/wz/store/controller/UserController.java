package com.wz.store.controller;

import com.wz.store.controller.ex.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    //设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("/changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file){
        //判断文件是否为null
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的和后缀类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeNotMatchException("文件类型不支持");
        }
        String parent = session.getServletContext().getRealPath("/") + "upload";
        System.out.println(parent);
        File dir = new File(parent);
        if (!dir.exists()){
            dir.mkdirs();//创建目录
        }
        //获取到这个文件名称，UUID工具来将生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename = "+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase()+suffix;
        File dest = new File(dir,fileName);//是一个空文件
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件中
        }
        catch (FileStatusException e) {
            throw new FileStatusException("文件状态异常");
        }catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        String avatar = "\\upload\\" + fileName;
        System.out.println(avatar);
        userService.changeAvatar(getUserIdFromSession(session),avatar,getUsernameFromSession(session));
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);

    }
}
