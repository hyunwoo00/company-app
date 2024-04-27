package company.orderApp;


import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NotNull CorsRegistry corsRegistry) {

        //cors 정책 해결을 위해 http://localhost:8081로 오는 메서드들을 허용.
        corsRegistry.addMapping("/**").allowedOrigins("http://localhost:8081").allowedMethods("GET", "POST", "PATCH", "DELETE", "PATCH")
                .allowedHeaders("*").allowCredentials(true);

    }
}
