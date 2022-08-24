package com.example.demoSecurity.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings( "deprecation" )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //we don't have any configuration for our two bean
    // we need to create two beans in our application and tell Spring how we want to upload the user and then create a bean for the password

    UserDetailsService userDetailsService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //there are different ways you can tell spring how to look for the users
        //inMemoryAuthenticatino: I pass username and password spring can check for users whenever users are trying to log into the application
        //jdbcAuthentication: create aservice class and then passing all the queries and and use JDBC to make my own request and override the JDBC user detail manager configure. (?)
        //userDetailsSeervice: is gonna accepta suer detail service which is a bean that we have to override and tell spring how to go look for users.
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
