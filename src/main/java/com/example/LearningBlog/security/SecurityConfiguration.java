package com.example.LearningBlog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT,"/api/v1/blog/users/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/blog/register","/api/v1/blog/users","/api/v1/blog/register/login","/api/v1/blog/users/admin")
                .permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/blog/**")
                .permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/blog/contact",
                        "/api/v1/blog/posts/{postId}/comments/{userId}"
                )
                .authenticated()
                .anyRequest()
                .hasAuthority("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
