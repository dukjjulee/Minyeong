package org.example.minyeong.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j//로그
@RequiredArgsConstructor
public class JwtFilter implements Filter {// 들어온 요청이 정상적인 요청인지 확인

    private final JwtUtil jwtUtil;

    @Override//Filter 인터페이스에서 init 메서드 구현
    //init 메서드를 호출
    public void init(FilterConfig filterConfig) throws ServletException {
        //기본 메서드 호출. 초기화
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //Servle 요청, 응답을 http 요청, 응답으로 선언
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //변수 url = 요청 url
        String url = httpRequest.getRequestURI();

        //auth 로 시작하는 경우
        if (url.startsWith("/auth")) {
            //chain.doFilter 호출해 인증 없이 통과
            chain.doFilter(request, response);
            return;
        }

        //변수 bearerJWT 로 헤더의 Authorization 토큰 추출
        String bearerJwt = httpRequest.getHeader("Authorization");

        //bearerJwt가 null 일 시
        if (bearerJwt == null) {
            //400에러 반환합니다.
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
            return;
        }

        //JWT = bearer 프리픽스 제거
        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            // JWT 유효성 검사해 claims 선언
            Claims claims = jwtUtil.extractClaims(jwt);
            if (claims == null) {

                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
                return;
            }
            //chain.doFilter 호출 인증 없이 통과
            chain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("Internal server error", e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        //필터.기본 구현 호출.종료
        Filter.super.destroy();
    }
}