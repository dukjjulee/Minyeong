package org.example.minyeong.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")//로그(이름 = JwtUtil)
@Component//스프링 빈으로 등록
public class JwtUtil {

    //JWT 앞에 붙는 인증 타입 "Bearer"
    private static final String BEARER_PREFIX = "Bearer";
    //토큰 유효시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    //시크릿 키
    @Value("${Jwt.secret.key}")
    private String secretKey;
    //암호화 객체 선언 key
    private Key key;
    //사용 암호화 알고리즘 선언 HS256
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        //Base64로 인코딩 된 secretKey를 디코딩 후
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        //key = HS256에서 사용하는 비밀키 생성
        key = Keys.hmacShaKeyFor(bytes);
    }

    //createToken 메소드에 아래 userId, email, nickname 담아 생성
    public  String createToken(Long userId, String email, String nickname) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(userId))// 사용자 아이디 subject에 저장
                        .claim("email", email)// 이메일 클레임
                        .claim("nickname", nickname)//닉네임 클레임
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))// 만료 시간
                        .setIssuedAt(date) //생성시간
                        .signWith(key, signatureAlgorithm)//암호화, 서명
                        .compact();
    }

    //substringToken 생성
    public String substringToken(String tokenValue) {
        //tokenValue에 텍스트가 있고 && tokenValue가 BEARER_PREFIX로 시작 할 경우
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            //"bearer" 7글자 이후만 반환
            return tokenValue.substring(7);
        }
        //예외 발생
        throw new SecurityException("Not Found Token");
    }

    //클레임 타입의 extractClaims 메소드 생성
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)//검증을 위한 key 설정
                .build()//빌드 한다
                .parseClaimsJwt(token)//파싱 실행
                .getBody();//반환
    }
}


