package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig

{
    @Bean

    /*
      IN THIS METHOD THE ROLE WHO HAS "ADMIN" CAN GO THE URL
      WE DISABLE THE  CSRF AND ALSO THE SESSION ID WILL GO STATELESS
     */

    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        httpSecurity
                .authorizeHttpRequests
                        (auth -> auth.
                                requestMatchers("/api/users").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .csrf(csrfconfig -> csrfconfig.disable())
                .sessionManagement
                        (
                                SessionManagementConfigurer ->
                                        SessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean

    /*
           IN THIS METHOD
           THERE ARE TWO USER WITH ROLES "USER" AND  "ADMIN"
            BUT ONLY ADMIN CAN TO THE URL

         ---------- THIS IS FOR ONLY TESTING PURPOSE ---------------


     */


    UserDetailsService myInMemoryDetailService()
    {
        UserDetails normalUser = User.withUsername("Tom")
                .password(passwordEncoder().encode("Tom@2025"))
                .roles("USER").build();

        UserDetails adminUser = User.withUsername("Tamoghno")
                .password(passwordEncoder().encode("Tamo@2026"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(normalUser,adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
