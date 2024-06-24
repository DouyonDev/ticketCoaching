package com.odk.ticketcoaching.config;

import com.odk.ticketcoaching.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    //fonction pour hasher le mot de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("ADOUYON")
                .password(passwordEncoder().encode("adouyon"))
                .roles("ADMIN")
                .build();
        UserDetails formateur = User.withUsername("formateur")
                .password(passwordEncoder().encode("formateur"))
                .roles("FORMATEUR")
                .build();
        UserDetails apprenant = User.withUsername("apprenant")
                .password(passwordEncoder().encode("apprenant"))
                .roles("APPRENANT")
                .build();

        return new InMemoryUserDetailsManager(admin,formateur,apprenant);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login","/auth/register").permitAll()
                        .requestMatchers("api/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/formateur/**").hasRole("FORMATEUR")
                        .requestMatchers("api/apprenant/**").hasRole("APPRENANT")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*@Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }*/
}
