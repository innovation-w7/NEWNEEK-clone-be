package com.innovation.newneekclone.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.newneekclone.dto.ResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        Object invalidJwt = request.getAttribute("INVALID_JWT");

        if (invalidJwt != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ResponseDto<?> msg = ResponseDto.fail("INVALID_TOKEN","토큰이 없거나 만료된 토큰입니다");
            String result = objectMapper.writeValueAsString(msg);
            response.getWriter().write(result);
        }
    }
}
