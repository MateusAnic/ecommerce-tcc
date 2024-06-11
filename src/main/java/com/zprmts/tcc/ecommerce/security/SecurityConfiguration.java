package com.zprmts.tcc.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/").permitAll()

                        .antMatchers("/login").permitAll()
                        .antMatchers("/login/reset-password").hasAnyRole("USUARIO", "ADMIN")

                        .antMatchers("/register").permitAll()
                        .antMatchers("/register/admin").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET,"/perfume/**").permitAll()
                        .antMatchers(HttpMethod.POST,"/perfume/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/perfume/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/perfume/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.POST,"/order/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.PUT,"/order/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.GET,"/order/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/order/**").hasRole("ADMIN")

                        .antMatchers("/foto/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.PUT,"/user").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.PUT,"/user/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/user/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/user/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.POST,"/review/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.GET,"/review/**").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/review/**").hasAnyRole("ADMIN")


                        .anyRequest().denyAll()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
