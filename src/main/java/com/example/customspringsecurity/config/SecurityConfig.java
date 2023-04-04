package com.example.customspringsecurity.config;

import com.example.customspringsecurity.filter.ApiKeyFilter;
import com.example.customspringsecurity.manager.CustomAuthenticationManager;
import com.example.customspringsecurity.provider.ApikeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Value("${api.key}")
    private String key;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http.httpBasic()
                .and()
                .addFilterAt(new ApiKeyFilter(key),
                        BasicAuthenticationFilter.class)
                .authenticationManager(new CustomAuthenticationManager(key))
               .authenticationProvider(new ApikeyProvider(key))
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        var uds=new InMemoryUserDetailsManager();
        var user1= User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();
        uds.createUser(user1);
        return uds;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }













}
