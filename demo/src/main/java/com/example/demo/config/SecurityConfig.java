package com.example.demo.config;
import com.example.demo.serviceImpl.security.CustomUserDetailService;
import com.example.demo.utility.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
        private final JwtRequestFilter jwtRequestFilter;
        private final CustomUserDetailService customUserDetailService;

        @Autowired
        public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailService customUserDetailService) {
            this.jwtRequestFilter = jwtRequestFilter;
            this.customUserDetailService = customUserDetailService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                            // Permit all users to view posts and access the Swagger UI, but restrict access to post modification
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/User/createUser","/auth/signin", "/posts/getAllPosts", "/posts/{postId}").permitAll()  // Everyone can view posts
                            .requestMatchers("/User/createAdmin").hasRole("ADMIN")// Only admins can access all posts for CRUD operations
                            .anyRequest().authenticated());// Any other requests require authentication
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }


        @Bean
        public AuthenticationManager authManager(HttpSecurity http) throws Exception {
            AuthenticationManagerBuilder authenticationManagerBuilder =
                    http.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(customUserDetailService)
                    .passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

