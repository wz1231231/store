package com.wz.store.controller;

import com.wz.store.controller.ex.*;
import com.wz.store.service.ex.*;
import com.wz.store.util.JsonResult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类
 */
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    /**
     * 1.当出现了value内的异常之一，就会将下方的方法作为新的控制器方法进行执行
     *   因此该方法的返回值也同时是返回给前端的页面
     * 2.此外还自动将异常对象传递到此方法的参数列表中，这里使用Throwable e来接收
     **/
    @ExceptionHandler({ServiceException.class, FileUploadException.class, ValidCodeNotMatchException.class}) //统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000); //表示用户名重复
//            result.setMessage("用户名被占用");
        }else if (e instanceof InsertException){
            result.setState(5001); //数据库或服务器有问题
        }else if (e instanceof UserNotExistException){
            result.setState(5002); //表示用户数据不存在
        }else if (e instanceof WrongPasswordException){
            result.setState(5003); //表示密码错误
        }else if (e instanceof OriginalPasswordNotMatchException){
            result.setState(5004); //表示原密码错误
        }else if (e instanceof FileEmptyException){
            result.setState(6000); //表示上传文件为空错误
        }else if (e instanceof FileSizeException){
            result.setState(6001); //表示上传文件超过限制错误
        }else if (e instanceof FileStatusException){
            result.setState(6002); //表示上传文件状态异常错误
        }else if (e instanceof FileTypeNotMatchException){
            result.setState(6003); //表示上传文件类型不符错误
        }else if (e instanceof FileUploadIOException){
            result.setState(6004); //表示上传文件IO读取错误
        }else if (e instanceof AddressCountLimitException){
            result.setState(7000); //表示地址数量已超限制
        }else if (e instanceof AddressNotExistsException){
            result.setState(7001); //表示地址不存在
        }else if (e instanceof DeleteException){
            result.setState(7002); //表示服务器或数据地址删除失败
        }else if (e instanceof ProductNotExistsException){
            result.setState(8000); //表示商品不存在
        }else if (e instanceof ProductBadStatusException){
            result.setState(8001); //表示商品状态异常
        }else if (e instanceof UpdateException){
            result.setState(9000); //表示数据更新失败
        }else if (e instanceof CartInfoNotExistsException){
            result.setState(9001); //表示查询的cart数据不存在
        }else if (e instanceof OrderNotExistsException){
            result.setState(3000); //表示查询的order数据不存在
        }else if (e instanceof ValidCodeNotMatchException){
            result.setState(1001); //表示验证码错误
        }
        System.out.println(result.getState());
        System.out.println(result.getMessage());
        //返回异常处理结果
        return result;
    }

    /**
     * Description : 从session中获取用户uid
     * @date 2022/7/11
     * @param session springboot启动时生成的session对象
     * @return java.lang.Integer
     **/
    public final Integer getUserIdFromSession(HttpSession session){
        String uidStr = session.getAttribute("uid").toString();

        return Integer.valueOf(uidStr);
    }

    /**
     * Description : 从session中获取用户username
     * @date 2022/7/11
     * @param session springboot启动时生成的session对象
     * @return java.lang.String
     **/
    public final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
