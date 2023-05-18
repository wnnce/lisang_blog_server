package com.zeroxn.blog.core.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.zeroxn.blog.core.interceptor.CorsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lisang
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    /*@Override
    //解决跨域 不再需要@CrossOrigin注解  因为鉴权需要携带自定义Token 而浏览器预检请求无法携带 改用拦截器实现
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true);
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加跨域拦截器 拦截所有路径
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
        //添加鉴权拦截器
        registry.addInterceptor(new SaInterceptor(handler -> {
            //拦截所有路径
            SaRouter.match("/user/**", "article/**", "/label/**", "links/**", "/artalk/**", "file/**",
                            "site/**")
                    //放行前台页面需要的接口
                    .notMatch("/user/sendCode", "/user/login", "/user/info", "/article/search", "/article/list",
                            "/article/top", "/article/info/*", "/article/list/tag", "/article/list/category",
                            "/label/list", "/label/info/*", "/user/link/list", "/links/list")
                    //使用StpUtil进行鉴权
                    .check(r -> StpUtil.checkLogin());
        }));
    }
}