package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

/**
 * static 폴더처럼 다른 폴더를 외부접근을 허용케 하는 설정
 * 성 입구에서 문지기가 보는 문서
 */

@Configuration // 설정 파일을 메모리에 로딩하는 어노테이션
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 맡은 일은 다 하게 하고
        WebMvcConfigurer.super.addResourceHandlers(registry);
        // 내가 원하는 폴더 설정을 추가한다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + "./images/")
                .setCachePeriod(10) // 10초 동안 캐시를 유지한다.
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/board/**") // 발동 조건
                .excludePathPatterns("/board/{id:[0-9]+}"); // 발동 제외
    }
}