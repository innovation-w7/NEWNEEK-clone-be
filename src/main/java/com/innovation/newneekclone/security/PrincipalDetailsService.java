package com.innovation.newneekclone.security;

import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// 시큐리티 설정에서 .loginProcessingUrl("/login")
// 로그인 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어있는 loadUserByUsername 함수가 실행
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Authentication 내부에 userDetails가 들어감.
    // Security session 에 인증 객체가 들어감.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findByEmail(username);
        return userEntity
                .map(UserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
