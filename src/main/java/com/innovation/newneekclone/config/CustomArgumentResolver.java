package com.innovation.newneekclone.config;

import com.innovation.newneekclone.dto.request.UserLoginRequestDto;
import com.innovation.newneekclone.security.UserDetailsImpl;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return UserLoginRequestDto.class.equals(parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        Object principal = null;
        UserDetailsImpl authentication =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null ) {
            principal = authentication.getUser();
        }

        return principal;
    }

}

