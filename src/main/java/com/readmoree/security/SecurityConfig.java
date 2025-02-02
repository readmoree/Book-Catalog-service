package com.readmoree.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
		http.csrf(customizer -> customizer.disable())
		.authorizeHttpRequests(request -> 
        request.requestMatchers("/book/search",
        		"/book/filter","/book/by-ids?**","/book/all",
        		"/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html",
				"/v*/api-doc*/**","/swagger-ui/**").permitAll() 
        //required explicitly for JS clients (eg React app - to permit pre flight requests)
//        .requestMatchers(HttpMethod.OPTIONS).permitAll()
        	
//       .requestMatchers("/products/purchase/**")
//       .hasRole("CUSTOMER")
       .requestMatchers("/admin/**")
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
	

}
