package com.readmoree.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public JwtFilter jwtFilter() {
	    return new JwtFilter();
	}
	
	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		System.out.println("in sec filter chain");
		
		http
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.csrf(customizer -> customizer.disable())
		.authorizeHttpRequests(request -> 
        request.requestMatchers("/book/search",
        		"/book/filter","/book/by-ids?**","/book/public/all","/reviews/book/**",
        		"/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html",
				"/v*/api-doc*/**","/swagger-ui/**","/reviews/book/**","/book/public/**").permitAll() 
        //required explicitly for JS clients (eg React app - to permit pre flight requests)
//        .requestMatchers("*").permitAll()
        	
       .requestMatchers("/reviews/customer/**")
       .hasRole("CUSTOMER")
       .requestMatchers("/book/admin/**","/publisher/admin/**","/author/admin/add")
       .hasRole("ADMIN")        		
        .anyRequest().authenticated())  
  //    .httpBasic(Customizer.withDefaults()) - replacing it by custom JWT filter
        .sessionManagement(session 
        		-> session.sessionCreationPolicy(
        				SessionCreationPolicy.STATELESS));
		//adding custom JWT fi;lter before any auth filter
		http.addFilterBefore(jwtFilter(), 
				UsernamePasswordAuthenticationFilter.class);
	
    return http.build();
	}
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    CorsConfiguration config = new CorsConfiguration();

		    config.setAllowedOrigins(List.of("https://readmoree.com","http://localhost:3000")); // Set allowed origins
		    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		    config.setAllowCredentials(true); // Important for cookies

		    source.registerCorsConfiguration("/**", config);
		    
		    return source;
	    }
	

}
