package com.example.watertracker.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* Configuration class of spring security. This one is checked
 * before SecurityConfiguration. If the http request address starts
 * with '/api', then it's handled by this config. I am using two
 * different "layers" for security. One for REST API with Basic Auth
 * and csrf turned off. The other one for MVC.
 */
@Configuration
@Order(1)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers( "/api/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .loginPage("/api/login")
                .failureUrl("/api/login?err=true")
                .defaultSuccessUrl("/api/success", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/api/success")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
