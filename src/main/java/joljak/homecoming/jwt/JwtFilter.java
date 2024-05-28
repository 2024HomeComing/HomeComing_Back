package joljak.homecoming.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter implements Filter {

    private JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("JwtFilter 도달");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (!jwtUtil.isExpired(token)) {
                // 토큰이 유효하면 요청 처리를 계속 진행
                filterChain.doFilter(request, response);
            } else {
                // 토큰이 유효하지 않으면 에러 처리
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            // Authorization 헤더가 없거나 Bearer 타입이 아닌 경우
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
//            httpResponse.getWriter().write("Unauthorized"); // 에러 메시지 출력
            return;
        }
    }
}
