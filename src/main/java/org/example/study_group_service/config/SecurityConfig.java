package org.example.study_group_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.study_group_service.exceptions.AuthenticationFailureException;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(new BearerTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Добавляем свой фильтр аутентификации
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/login", "/registration", "/api/login", "/api/registration") // Разрешаем доступ к этим страницам
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .successHandler(successHandler())
                                .failureHandler(failureHandler())
                );
        return httpSecurity.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(userService.getEncoder());
    }

    private AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                response.getWriter().append("OK");
                response.setStatus(200);
            }
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                response.getWriter().append("Authentication failure: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        };
    }

    public class BearerTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
        public BearerTokenAuthenticationFilter() {
            super(new RequestMatcher() {
                @Override
                public boolean matches(HttpServletRequest request) {
                    return "POST".equalsIgnoreCase(request.getMethod())
                            && request.getRequestURI().equals("api/login");
                }
            });
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws AuthenticationException {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7); // Убираем "Bearer " из токена
                return authenticateToken(jwt);
            }
            throw new AuthenticationFailureException("Token is missing or invalid");
        }

        private Authentication authenticateToken(String token) {
            String username = userService.validateToken(token);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                BearerAuthenticationToken authToken = new BearerAuthenticationToken(userDetails, token);
                authToken.setAuthenticated(true); // Устанавливаем аутентифицированным
                return authToken;
            }
            throw new AuthenticationFailureException("Invalid token");
        }
    }
}

