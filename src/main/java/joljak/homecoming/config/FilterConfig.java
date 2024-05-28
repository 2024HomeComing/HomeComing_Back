package joljak.homecoming.config;

import joljak.homecoming.jwt.JwtFilter;
import joljak.homecoming.jwt.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
//        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new JwtFilter(jwtUtil));
//        registrationBean.addUrlPatterns("/api/*"); // JWT 필터를 적용할 URL 패턴
//        return registrationBean;
//    }
//}
