package com.innovation.newneekclone.config;

import com.innovation.newneekclone.security.jwt.JwtAuthFilter;
import com.innovation.newneekclone.security.jwt.JwtAuthenticationEntryPoint;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http
                .csrf().disable()
                //.httpBasic().disable() : 헤더에 유저정보를 실어보내는 것으로, 보안에 취약하기 때문에 해제하고 https사용. http.build()와 같이 사용이 가능한가?
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").authenticated() // 인증 시 접근
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .formLogin() // 권한이 없는 페이지 접근 시 .loginPage("__")로 이동.
//                .loginPage("/loginForm")
//                .loginProcessingUrl("/login") // /login 주소 호출 시 시큐리티가 대신 로그인을 진행해주기 때문에 로그인 컨트롤러 불필요
//                .defaultSuccessUrl("/") // /loginForm 접속 성공 시 리턴되는 기본 페이지.
//                .permitAll();
        return http.build();
    }

}
