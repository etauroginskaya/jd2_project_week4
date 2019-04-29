package com.gmail.etauroginskaya.springbootmodule.week4.security.config;

import com.gmail.etauroginskaya.springbootmodule.week4.security.handler.ApiAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Order(1)
public class ApiSecurityConfigurer extends WebSecurityConfigurer {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ApiSecurityConfigurer(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService1) {
        super(userDetailsService);
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService1;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests().anyRequest()
                .hasRole("ADMINISTRATOR")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(apiAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AccessDeniedHandler apiAccessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }

    @Bean
    public GrantedAuthorityDefaults grantAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
