package com.pcz.blog.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author picongzhi
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String KEY = "picongzhi.com";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll().
//                antMatchers("/h2-console/**").permitAll().
//                antMatchers("/admins/**").hasRole("ADMIN").
//                and().formLogin().loginPage("/login").failureUrl("/login-error").
//                and().rememberMe().key(KEY).
//                and().exceptionHandling().accessDeniedPage("/403");
//        http.csrf().ignoringAntMatchers("/h2-console/**");
//        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll();
    }
}
