package com.innovation.newneekclone.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { //extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 헤더에서 JWT 를 받아옴
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        // 유효한 토큰인지 확인.
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받음.
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            // SecurityContext 에 Authentication 객체 저장.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }else{
//            logger.error("Could not set user authentication in security context");
//            request.setAttribute("INVALID_JWT", "INVALID_JWT");
//
//        }
//        chain.doFilter(request, response);
//    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옴
        String token = jwtTokenProvider.resolveToken(request);
        // 유효한 토큰인지 확인.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받음.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체 저장.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            logger.error("Could not set user authentication in security context");
            request.setAttribute("INVALID_JWT", "INVALID_JWT");

        }
        filterChain.doFilter(request, response);
    }
}
