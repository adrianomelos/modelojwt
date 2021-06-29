package com.modelo.exceptions;

public class ResourceEmailNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceEmailNotFoundException(Object email) {
		super("Resource not found EMAIL: " + email);
	}

}
