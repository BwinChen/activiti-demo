package com.bwin.activitidemo.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/protected-process*").authenticated()
                .anyRequest().permitAll()
            .and().formLogin().loginPage("/login").defaultSuccessUrl("/homepage").failureUrl("/login?error=true")
            .and().logout().logoutSuccessUrl("/login")
            .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
