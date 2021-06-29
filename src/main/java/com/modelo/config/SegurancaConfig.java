package com.modelo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.modelo.entities.Roles;
import com.modelo.repository.RolesRepository;
import com.modelo.services.AutenticadorService;
import com.modelo.services.CityService;
import com.modelo.services.UFService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private AutenticadorService userDetailsService;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private UFService ufService;
	@Autowired
	private CityService cityService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		loadStart();
		ufService.gravarUfLocal();
		cityService.gravarUfLocal();

		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers("/v2/api-docs", 
						"/authenticate", 
						"/swagger-ui.html#/", 
						"/swagger-resources/**",
						"/configuration/security", 
						"/swagger-ui.html", 		
						"/auth/login",
						"/user/change_password",
						"/uf",
						"/uf/*",
						"/city",
						"/city/*",
						"/user/gerencial",
						"/webjars/**")
				.permitAll()
				
				.antMatchers(HttpMethod.GET, "/about").permitAll()
				.antMatchers(HttpMethod.GET, "/terms_use").permitAll()
				.antMatchers(HttpMethod.GET, "/privacy_policy").permitAll()

				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	private void loadStart() {
		List<Roles> roles = rolesRepository.findAll();
		if (roles.size() < 3) {

			Roles student = new Roles();
			student.setName("ROLE_USER");
			student.setType("USER");

			Roles admin = new Roles();
			admin.setName("ROLE_ADMIN");
			admin.setType("ADMIN");

			List<Roles> list = new ArrayList<>();

			list.add(student);
			list.add(admin);

			rolesRepository.saveAll(list);
		}
	}
}
