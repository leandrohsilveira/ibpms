package com.github.leandrohsilveira.ibpms.exceptions;

import lombok.Getter;

@Getter
public abstract class ModelException extends RuntimeException {

	private static final long serialVersionUID = 4645473434159630611L;
	
	public ModelException(String message, int status) {
		super(message);
		this.status = status;
	}

	public ModelException(String message, int status, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	
	private int status;
	
}
