package ru.test.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.test.filter.JwtFilter;
import ru.test.filter.TokenExceptionResolvingFilter;
import ru.test.jwt.JwtConfig;
import ru.test.jwt.JwtUtilProperties;
import ru.test.util.RequestAuthorizer;
import ru.test.util.TokenExceptionController;

@Configuration
@EnableWebSecurity
@AutoConfigureBefore(WebSecurityConfiguration.class)
public class SecurityConfig {

    @Bean
    public JwtUtilProperties jwtUtilsProperties() {
        return new JwtUtilProperties();
    }

    @Bean
    public TokenExceptionController tokenExceptionController() {
        return new TokenExceptionController();
    }

    @Bean
    @ConditionalOnMissingBean(RequestAuthorizer.class)
    public RequestAuthorizer authorizeConfigurer() {
        return request -> request.anyRequest().authenticated();
    }

    @Bean
    public JwtConfig jwtConfig(JwtUtilProperties jwtUtilProperties) {
        return new JwtConfig(jwtUtilProperties);
    }

    @Bean
    public TokenExceptionResolvingFilter tokenResolvingFilter(HandlerExceptionResolver handlerExceptionResolver) {
        return new TokenExceptionResolvingFilter(handlerExceptionResolver);
    }

    @Bean
    public JwtFilter jwtFilter(JwtConfig jwtConfig) {
        return new JwtFilter(jwtConfig);
    }

    @Bean
    @Order(SecurityProperties.IGNORED_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtFilter jwtFilter,
                                           RequestAuthorizer requestAuthorizer,
                                           TokenExceptionResolvingFilter tokenExceptionResolvingFilter) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestAuthorizer)
                .passwordManagement(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenExceptionResolvingFilter, JwtFilter.class)
                .build();
    }
}
