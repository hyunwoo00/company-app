package company.orderApp.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    @Value("${admin.username}")
    String username;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {

        //cors preflight 해결
        // option 메서드를 통해 서버가 요청을 받을 수 있는지 확인하는 과정에서 request header의 값을 이용하지 못해 인증을 정상적으로 마치지 못하기 때문에 cors issue 발생.
        // 밑의 과정을 통해서 메서드가 OPTIONS일 경우 ADMIN 권한으로 security filter를 통과한다.

        // -> spring security에서 OPTIONS메서드일 경우 permitAll로 해결 가능.
        /*if (Objects.equals(request.getMethod(), "OPTIONS")) {

            Collection<? extends GrantedAuthority> auths = Arrays.stream("ROLE_ADMIN".split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UserDetails principal = new User(username,"", auths);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", auths);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            return;
        }*/
        // 1. Request Header에서 JWT 토큰 추출.
        String token = resolveToken(request);

        // 2. validateToken으로 토큰 유효성 검사, 블랙리스트에 액세스 토큰이 있는 지 확인.
        if (token != null && jwtTokenProvider.validateToken(token)) {

            //토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와 Security Context에 저장.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        //다음 핕터로 요청 전달.
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
