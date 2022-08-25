package com.example.demoSecurity.security;


import com.example.demoSecurity.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration  
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings( "deprecation" )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //we don't have any configuration for our two bean
    // we need to create two beans in our application and tell Spring how we want to upload the user and then create a bean for the password

 private final UserDetailsService userDetailsService;
 private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        //we are going to save the session throw a JSON Web Tokensandisk sd ultra

        //CSRF: cross-site Request Forgery is an attack that forces an aend user to execute unwanted actions on a web application in which they are currently authenticated.
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.authorizeRequests().antMatchers("name of the endpoint").permitAll(); in this way there is no security on the given endpoint

        http.authorizeRequests()
                .antMatchers(GET, "/api/users")      //method to "privatize"
                .hasAnyAuthority("ROLE_MANAGER");         // role that has access to (GET, "/api/user/**)

        http.authorizeRequests()
                .antMatchers(POST, "/api/user/add/**")
                .hasAnyAuthority("ROLE_ADMIN");

        /*
        http.authorizeRequests()
                .antMatchers(POST, "/api/user/add-role-to-user")
                .hasAnyAuthority("ROLE_MANAGER");
*/
       // setAuthorization(http, GET,"/api/users", "ROLE_MANAGER");

        http.authorizeRequests()
                .antMatchers("/api/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN");



        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    }

    //CustomAuthenticationFilter wants anauthentication manager as parameter
    // we have inside the class an authenticationManager so we import it and override it and annotate as @Bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry setAuthorization(HttpSecurity http, HttpMethod httpMethod, String endpoint, String role) throws Exception {

        return  http.authorizeRequests()
                .antMatchers(httpMethod, endpoint)
                .hasAnyAuthority(role);
    }
}
