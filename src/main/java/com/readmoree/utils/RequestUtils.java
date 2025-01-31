package com.readmoree.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readmoree.dtos.UserDto;

public class RequestUtils {

	public static  UserDto requestUserService(Integer userId) {
		final String URl = "http://192.168.0.102:4000/user/role";
		UserDto user = new UserDto();
		try { 
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(URl))
					.GET()
					.header("authorization", "BEARER eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJTdGVsbGEiLCJjdXN0b21lcklkIjoyLCJlbWFpbCI6InN0ZWxsYUBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MzgyMTc0OTksImV4cCI6MTczOTA4MTQ5OX0.C_QVhLyQbEZojIoFI4CDLxpGmH_1bB3VT38Pvw4jaLo")
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			 // Convert JSON to Java Object
            ObjectMapper objectMapper = new ObjectMapper();
            
           user = (objectMapper.readValue(response.body(), UserDto.class));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
