package com.old.school.selfdestructingmessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
    	
        http
        		// Disable CSRF
        		.csrf().disable()
                // Only admin can perform HTTP delete operation
                .authorizeRequests()//.antMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN)
                // any authenticated user can perform all other operations
                .antMatchers("/admin/notes").authenticated().and().httpBasic().and()
                // Permit all other request without authentication
                .authorizeRequests().anyRequest().permitAll()
                // We don't need sessions to be created.
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
