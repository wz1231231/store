package com.wz.store.config;

import com.wz.store.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
//        List patterns = new ArrayList();
//        patterns.add("bootstrap3/**");
//        patterns.add("/css/**");
//        patterns.add("images/**");
//        patterns.add("js/**");
//        patterns.add("web/register.html");
//        patterns.add("web/login.html");
//        patterns.add("web/index.html");
//        patterns.add("web/product.html");
//        patterns.add("users/reg");
//        patterns.add("users/login");

        registry.addInterceptor(interceptor)
                //也可以用一个List<String> 来设置排除拦截的资源
                //放行静态资源
                .excludePathPatterns("/web/login.html","/web/index.html",
                        "/web/register.html","/web/product.html",
                        "/web/components/**","/web/search.html",
                        "/css/**","/bootstrap3/**", "/images/**","/js/**")
                //放行请求接口和支付宝沙箱接口
                .excludePathPatterns("/users/**","/address/**","/file/**","/districts/**",
                        "/product/**","/cart/**","/order/**","/kaptcha/**",
                        "/alipay/**")
                //不放行/error页面有可能导致白名单失效假象
                .excludePathPatterns("/error");
    }
}
