package com.readmoree.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	

}
