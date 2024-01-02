package com.zerobase.reservation.config;

import com.zerobase.reservation.security.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends SecurityConfigurerAdapter {

    private final JwtAuthenticationFilter authenticationFilter;


    // 아 왜 안되냐... 왜 그래
    // SpringSecurity 버전 문제인데 뭘 써야할지 모르곘다...

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        HttpSecurity logout = http.csrf().disable().cors().disable()
//                .authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers("/status", "/images/**", "/view/join", "/auth/join").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(login -> login
//                        .loginPage("/view/login")
//                        .loginProcessingUrl("/login-process")
//                        .usernameParameter("userid")
//                        .passwordParameter("pw")
//                        .defaultSuccessUrl("/view/dashboard", true)
//                        .permitAll()
//                )
//                .logout(withDefaults());
//
//        return http.build();
//    }
}

