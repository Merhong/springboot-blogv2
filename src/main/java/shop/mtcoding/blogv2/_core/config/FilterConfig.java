package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.mtcoding.blogv2._core.filter.MyFilter1;

/* 필터를 설정하는 파일 */
@Configuration
public class FilterConfig {

    @Bean // 컴포넌트 스캔시 메서드의 리턴 값을 IoC 컨테이너에 띄운다.
    public FilterRegistrationBean<MyFilter1> MyFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1()); // DS 앞에 필터를 달아준다.
        bean.addUrlPatterns("/*"); // 슬래시(/)로 시작하는 모든 주소에 발동됨
        bean.setOrder(0); // 낮은 번호부터 실행됨
        return bean;
    }
}
