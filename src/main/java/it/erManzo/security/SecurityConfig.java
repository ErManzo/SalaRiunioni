package it.erManzo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http.httpBasic().and().authorizeRequests().antMatchers("/login", "/register",
		// "/getUserModel").permitAll().and()
		// .authorizeRequests().antMatchers("/product/getAllProducts",
		// "/product/findOne/**", "/product/findByCategoria/**")
		// .hasAnyRole("USER", "ADMIN",
		// "DBA").and().authorizeRequests().antMatchers("/product/**")
		// .hasAnyRole("ADMIN", "DBA").anyRequest().authenticated().and().logout()
		// .logoutRequestMatcher(new
		// AntPathRequestMatcher("/logout")).permitAll().and().csrf().disable();

		http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/event/**").permitAll()
				.antMatchers("/login", "/register", "/logoutApp", "/getUserModel").permitAll()
				.antMatchers("/event/getModel", "/event/createEvent/**", "/event/delete/**", "/event/getAllEvent/**")
				.hasAnyRole("USER", "ADMIN", "DBA").anyRequest()
				.authenticated().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.and().csrf().disable();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
