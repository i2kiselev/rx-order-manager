package ru.isu.i2kiselev.rxordermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/manager/**","/task/**","/employee/**")
                .hasAuthority("ROLE_ADMIN")
                .pathMatchers("/task-panel/**")
                .hasAuthority("ROLE_EMPLOYEE")
                .pathMatchers("/login","/register","/css/**","/js/**","/img/**")
                .permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .authenticationSuccessHandler(redirectServerAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/");
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedirectServerAuthenticationSuccessHandler redirectServerAuthenticationSuccessHandler() {
        RedirectServerAuthenticationSuccessHandler successHandler = new RedirectServerAuthenticationSuccessHandler() {
            @Override
            public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                if (authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
                    this.setLocation(URI.create("/employee"));
                } else {
                    this.setLocation(URI.create("/task-panel/"));
                }
                return super.onAuthenticationSuccess(webFilterExchange, authentication);
            }
        };
        successHandler.setRequestCache(new WebSessionServerRequestCache());
        return successHandler;
    }
}
