package com.easylearn.easylearn.security.config;

import com.easylearn.easylearn.security.auth.filter.UserAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@AllArgsConstructor
public class LocalWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationFilter authenticationFilter;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(
                "/login",
                "/logout",
                "/sign-up",
                "/activate/**",
                "/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/activate/**").permitAll().and()
                .authorizeRequests().antMatchers("/sign-up").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
        http.csrf().ignoringAntMatchers("/sign-up");
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}