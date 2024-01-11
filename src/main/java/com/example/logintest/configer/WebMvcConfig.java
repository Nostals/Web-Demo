package com.example.logintest.configer;


import com.example.logintest.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("POST","GET")
                .maxAge(16800)
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    private TokenInterceptor tokenInterceptor;
    //构造方法
    public WebMvcConfig(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
//        excludePath.add("/admin/register");  //登录
        excludePath.add("/admin/**");     //
        excludePath.add("/msg/**");     //
        excludePath.add("/error");
        excludePath.add("/random_photo");
        excludePath.add("/img/**");
        excludePath.add("/images/**");  //静态资源
        excludePath.add("/videos/**");  //静态资源

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /* 通过url访问项目外的目录图片*/
        registry.addResourceHandler("/img/**").addResourceLocations("file:/D:/[]Person/PSD/photoback/I_like/");
    }

}
