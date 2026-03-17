package com.me.crypto.trading.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests(authorize ->
                        authorize
                            .requestMatchers("/v1/**").permitAll()
                            .anyRequest().authenticated()
                )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
