package com.smartcontactmanager.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
@EnableWebSecurity
public class jwtConfig {

    @Autowired
    private JwtAuthenticateByAshu point;  // Custom authentication entry point for handling errors

    @Autowired
    private JwtFilter jwtFilter;  // JWT Filter to intercept every request

    @Autowired
    private MyAuthProvider authProvider;  // Custom Authentication Provider
    @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
 
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                // Permit login and public routes without token
//                
//                // Any URL starting with '/user/' needs to be authenticated
//                .requestMatchers("/user/**").authenticated().
//                requestMatchers("/register","/dologin","/login","/logout").permitAll()
//                // Other routes can be freely accessed
//                .anyRequest().permitAll()
//            )
//            .formLogin(form -> form.loginPage("/login"))
//            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))  // Custom entry point to handle unauthorized access
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless sessions
//
//            // Add the custom authentication provider for user authentication
//            .authenticationProvider(authProvider)
//
//            // Add JWT filter before Spring Security's UsernamePasswordAuthenticationFilter
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }
}


//@Configuration
//@EnableWebSecurity
//public class jwtConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Autowired
//    private JwtAuthenticateByAshu point;
//
//    @Autowired
//    private MyAuthProvider authProvider;
//
//    @Bean
//    public UserDetailsService getUserDetailsService() {
//        return new UserServiceImpl(); 
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//            .authorizeRequests(auth -> auth
//                .requestMatchers("/test", "/auth/test").authenticated()
//                .requestMatchers("/user/**").hasRole("USER")
//                .anyRequest().permitAll()
//            )
//            .formLogin(form -> form.loginPage("/login"))
//            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.authenticationProvider(authProvider);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}










//package com.smartcontactmanager.jwt;
//
//import org.apache.catalina.security.SecurityConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.smartcontactmanager.loginConfig.UserServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//public class jwtConfig {
//	private JwtFilter jwtFilter;
//
//	@Autowired
//	private JwtAuthenticateByAshu point;
//
//	@Bean
//	public UserDetailsService getUserDetailsService() {
//		return new UserServiceImpl(); 
//	}
//	
//	@Autowired
//	private MyAuthProvider authProvider;
//	
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder(8);
//	}
//
//	public jwtConfig (MyAuthProvider myAuthProvider,JwtFilter jwtFilter) {
//		this.authProvider=myAuthProvider;
//		this.jwtFilter=jwtFilter;
//		
//	}
//	    @SuppressWarnings("deprecation")
//		@Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//	    	 ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl requestMatchers = http.csrf(csrf -> csrf.disable())
//             .authorizeRequests().
//             requestMatchers("/test");
//			requestMatchers.authenticated().requestMatchers("/auth/test").authenticated()
//             .requestMatchers("/user/**").hasRole("USER")
//             .requestMatchers("/**").permitAll().requestMatchers("/login")
//             .permitAll().and().formLogin().loginPage("/login")
//             .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            		 .authenticationProvider(authProvider));
//             
//     http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
//     return http.build();
//
////     @Bean
////	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////         http.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN")
////                 .requestMatchers("/user/**").hasRole("USER")
////                 .requestMatchers("/**").permitAll().requestMatchers("/login")
////                 .permitAll().and().formLogin().loginPage("/login").loginProcessingUrl("/dologin")
////                 .defaultSuccessUrl("/user/dashboard", true)
////                 .and().csrf().disable(); 
////	         http.authenticationProvider(daoDaoAuthenticationProvider());
////     return http.build();
////	    }
//	    }
////	    @Bean
////	    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
////	        return builder.authenticationProvider(authProvider).build();
////	    }
//	    
////	    @Bean
////	    public AuthenticationManager authenticationManagerBean() throws Exception {
////	        return authenticationManagerBean();
////	    }
//}
