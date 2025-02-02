package com.readmoree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.filter.OncePerRequestFilter;

import com.readmoree.security.JwtFilter;
import com.readmoree.security.JwtUtils;

import jakarta.servlet.FilterChain;

import static org.mockito.Mockito.*;


@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	  private JwtFilter jwtFilter;

	    @BeforeEach
	    public void setup() {
	        jwtFilter = new JwtFilter();
	    }
	 

	    @Test
	    public void testDoFilterInternal() throws Exception {
	    	String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJTdGVsbGEiLCJjdXN0b21lcklkIjoyLCJlbWFpbCI6InN0ZWxsYUBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MzgyMTc0OTksImV4cCI6MTczOTA4MTQ5OX0.C_QVhLyQbEZojIoFI4CDLxpGmH_1bB3VT38Pvw4jaLo";
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        request.addHeader("Authorization", "Bearer "+token);

	        MockHttpServletResponse response = new MockHttpServletResponse();

	        // Mock the token validation logic here
	        JwtUtils jwtUtil = mock(JwtUtils.class);
	        when(jwtUtil.validateToken(token)).thenReturn(true);

	        // Execute the filter
	        jwtFilter.doFilterInternal(request, response, mock(FilterChain.class));

	        System.out.println("Token is: " + token);
	        System.out.println("Token valid: " + jwtUtil.validateToken(token));
	        
	        // Verify that the user is set in the SecurityContext
//	        Authentication authentication = jwtUtil.populateAuthenticationTokenFromJWT(token);
//	        assert(authentication != null);
//	        assert(authentication.getName().equals("Stella"));
	    }

}
