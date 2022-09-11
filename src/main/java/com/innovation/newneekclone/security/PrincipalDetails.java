package com.innovation.newneekclone.security;

import com.innovation.newneekclone.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user){this.user = user;}


    @Override
    // 해당 유저의 권한 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });
//        return collect;

//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return user.getRole().stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        }
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
