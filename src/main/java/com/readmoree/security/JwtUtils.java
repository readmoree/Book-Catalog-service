package com.readmoree.security;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {

	@Value("${spring.security.jwt.secret.key}")
	private String jwtSecret;
	//private  Key key;

	@PostConstruct
	public void init() {
		 // Ensure that jwtSecret is not null or empty
		if (jwtSecret == null || jwtSecret.isEmpty()) {
	        throw new IllegalArgumentException("JWT secret key cannot be null or empty");
	    }
	}
	

	// this method will be invoked by our custom JWT filter
	public String getUserNameFromJwtToken(Claims claims) {
		return claims.getSubject();
	}
	
	public  Claims validateJwtToken(String jwtToken) {
		if (jwtSecret == null || jwtSecret.isEmpty()) {
	        throw new IllegalArgumentException("JWT secret key cannot be null or empty");
	    }
	    System.out.println("JWT Secret: " + jwtSecret); // Log the secret-key

		// try {
		Claims claims = Jwts.parserBuilder() //create JWT parser
				.setSigningKey(new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName())) //sets the SAME secret key for JWT signature verification
				.build()//rets the JWT parser set with the Key
				.parseClaimsJws(jwtToken) //rets JWT with Claims added in the body
				.getBody();//=> JWT valid ,  rets the Claims(payload)	
		
		System.out.println("in validateToken");
		return claims;		
	}
	
	public boolean validateToken(String token) {
	    try {
	        Claims claims = validateJwtToken(token);
	        System.out.println("secret key"+ jwtSecret);
	        // Check if token is expired
	        Date expirationDate = claims.getExpiration();
	        if (expirationDate.before(new Date())) {
	            System.out.println("Token is expired");
	            return false;
	        }

	        // You can also verify the signature at this point
	        return true;

	    } catch (JwtException | IllegalArgumentException e) {
	        System.out.println("Token validation failed: " + e.getMessage());
	        return false;
	    }
	}

}
