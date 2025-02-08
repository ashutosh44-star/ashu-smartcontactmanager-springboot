//package com.smartcontactmanager.loginConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.smartcontactmanager.jwt.JwtAuthenticateByAshu;
////import com.smartcontactmanager.jwt.JwtFilter;
////import com.smartcontactmanager.jwt.JwtHelper;
//import com.smartcontactmanager.jwt.JwtRequest;
//
//
//@Configuration
//@EnableWebSecurity
//public class config  {
//	
////	@Autowired
////	private JwtFilter filter;
//	
//	@Autowired
//	private JwtRequest jwtRequest;
//	
////	@Autowired
////	private JwtHelper jwtHelper;
//	
//	@Autowired
//	private JwtAuthenticateByAshu point;
//	
//	@Bean
//	public UserServiceImpl getUserDetailsService() {
//		return new UserServiceImpl();
//	}
//	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider daoDaoAuthenticationProvider() {
//		
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//		return daoAuthenticationProvider;
//	}
//	
//
//	
//	@Bean
//	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration auth) throws Exception {
//		return auth.getAuthenticationManager();
//			}
//
//	    @SuppressWarnings("removal")
//	    @Bean
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/user/**").hasRole("USER")
//                    .requestMatchers("/**").permitAll().requestMatchers("/login")
//                    .permitAll().and().formLogin().loginPage("/login").loginProcessingUrl("/dologin")
//                    .defaultSuccessUrl("/user/dashboard", true)
//                    .and().csrf().disable(); 
//	         http.authenticationProvider(daoDaoAuthenticationProvider());
//        return http.build();
//	    }
////	    @Bean////	    	 http.csrf(csrf -> csrf.disable())

////	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////
////             .authorizeRequests().
////             requestMatchers("/test").authenticated().requestMatchers("/auth/login").permitAll()
////             .anyRequest()
////             .authenticated()
////             .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
////             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////     http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
////     return http.build();
////	  
////	    }
//
//}
//
