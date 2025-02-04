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
	private String jwtSecret ="fa65159d9dbc22f99ac702ea3d5c89087279420471d8acde3dbab5360c7d74a95f691eff49cfce90b16277b1a06035432e8c392198bf88bcf520ad88eb2a4d9aa56bddeb3e2f04f29f06beca867bd7c4cdb304d97836c06c960356aaa219750306a540fce2e499f9c100bcd171fa3e2eacf73e6c3ee73f0d9846da00392e6dbcc74737b9fa9215ed2c8e55067645fbc71dfecdd74135606bd64d14f1600beb9d99434bffdd83290b8528b02886a7496b2f4b98e54d196bb1201a71186baa8372f494ff30ab452a7158a21d48b90f1f28b57c490cdb46d43398a23e46862fc87a38e98f68f1c8706f6e354a258913ff67cd693a7d8069a8a732c01d7c5b6d2722";

	//private  Key key;

	@PostConstruct
	public void init() {
		 // Ensure that jwtSecret is not null or empty
		if (jwtSecret == null || jwtSecret.isEmpty()) {
	        throw new IllegalArgumentException("JWT secret key cannot be null or empty");
	    }
//	    key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//		System.out.println("secret key : "+ key);
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
