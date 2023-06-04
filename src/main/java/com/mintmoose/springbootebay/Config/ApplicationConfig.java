package com.mintmoose.springbootebay.Config;

import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CustomerService customerService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
