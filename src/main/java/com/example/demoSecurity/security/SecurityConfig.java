package com.example.demoSecurity.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@SuppressWarnings( "deprecation" )
public class SecurityConfig extends WebSecurityConfigurerAdapter {


}
