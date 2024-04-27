package company.orderApp;

import company.orderApp.jwt.JwtAuthenticationFilter;
import company.orderApp.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                //csrf 은 세션/쿠키 기반의 보안관련 취약점을 이용하는 기법.
                //그래서 jwt과 같이 세션/쿠키가 아닌 토큰 기반으로 보안처리를 하는 경우에는 csrf를 활성화 할 필요는 없다.
                .httpBasic(h -> h.disable())
                .csrf(csrf -> csrf.disable())
                //세션을 사용하지 않음.
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a ->
                        a.requestMatchers(new AntPathRequestMatcher("api/admin/**")).hasRole("ADMIN")
                                //User 권한이 있어야 요청 가능.
                                .requestMatchers("api/users/test").hasRole("USER")
                                .requestMatchers("api/users").permitAll()
                                .requestMatchers("api/users/login").permitAll()
                                .requestMatchers("api/users/refresh").permitAll()
                                .requestMatchers("api/items").hasAnyRole("USER", "ADMIN")
                                //이 외에 모든 요청에 대해서는 인증을 필요로 함.
                                .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .formLogin(f -> f.disable())
                .build();


    }






}
