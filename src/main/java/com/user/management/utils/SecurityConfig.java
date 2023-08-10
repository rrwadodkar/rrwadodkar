package com.user.management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.user.management.services.DBUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    
    @Autowired
    private DBUserDetailsService userDetailsService; 

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        http.authorizeHttpRequests((requests) -> requests
                                    .requestMatchers("/userService*","/roleService/*","/groupService/*")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated()
                                    .requestMatchers("/authenticate/*")
                                    .permitAll()
                                    .anyRequest()
                                    .anonymous());
                                   
        return http.build();                             

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider(passwordEncoder()); 
        daoProvider.setUserDetailsService(userDetailsService);
        return daoProvider; 
    }


}
