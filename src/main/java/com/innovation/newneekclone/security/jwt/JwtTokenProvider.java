package com.innovation.newneekclone.security.jwt;

import com.innovation.newneekclone.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "4oCYc3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LWhhbmdoYWUtYXNzaWdubWVudC1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3Qtc2VjcmV0LWtleeKAmQo=";
    private Key key;
    private long tokenValidTime = 60 * 60 * 1000L; // 유효시간 60분

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {  // 객체 초기화
        //secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // secretKey를 Base64로 인코딩한다.
        key = Keys.hmacShaKeyFor(secretKey.getBytes()); // 되려나?
    }

    // JWT 토큰 생성
    public String createToken(User user) {
        long now = (new Date().getTime());
        Date accessTokenExpiresIn = new Date(now + tokenValidTime);

        Claims claims = Jwts.claims().setSubject(user.getEmail()); // JWT payload 에 저장되는 정보단위
        claims.put("nickname",user.getNickname());// 정보는 key / value 쌍으로 저장된다.

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setExpiration(accessTokenExpiresIn) // 유효시간 저장
                .signWith(key,SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과 signature 에 들어갈 키값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("access-token");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());// 만료시간이 경과되었다면 false, 아니면 true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
