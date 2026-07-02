package com.spring.spring_rest.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.spring_rest.service.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailsService;
	
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity security)
	        throws Exception {
	    return security
	            .cors(cors -> cors.configure(security))
	            .csrf(AbstractHttpConfigurer::disable)
	            .sessionManagement(session -> session
	                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	            )
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/api/auth/**").permitAll()
	                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	                    .requestMatchers("/api/students/**").hasAnyAuthority("USER", "ADMIN")
	                    .anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults())
	            .build();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager
		(AuthenticationConfiguration authentication) throws Exception{
		return authentication.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
														"http://127.0.0.1:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
		
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
